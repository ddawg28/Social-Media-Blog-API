package DAO;

import java.sql.*;

import org.h2.command.Prepared;

import Model.Message;
import Util.ConnectionUtil;
import java.util.*;

public class MessageDAO {
    
    public Message insertMessage(Message msg) {
        try {
            Connection conn = ConnectionUtil.getConnection();
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, msg.getPosted_by());
            ps.setString(2, msg.getMessage_text());
            ps.setLong(3, System.currentTimeMillis());
            ps.executeUpdate();
            ResultSet pkeys = ps.getGeneratedKeys();
            if (pkeys.next()) {
                int message_id = pkeys.getInt("message_id");
                return new Message(message_id, msg.getPosted_by(), msg.getMessage_text(), msg.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages() {
        List<Message> result = new ArrayList<Message>();
        try {
            Connection conn = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM message";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public Message getMessageById(int message_id) {
        try {
            Connection conn = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, message_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message result = new Message();
                result.setMessage_id(message_id);
                result.setPosted_by(rs.getInt("posted_by"));
                result.setMessage_text(rs.getString("message_text"));
                result.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
                return result;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageById(int message_id) {
        try {
            Connection conn = ConnectionUtil.getConnection();
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean updateMessageById(int message_id, String message_text) {
        try {
            Connection conn = ConnectionUtil.getConnection();
            String sql = "UPDATE message SET message_text = ?, time_posted_epoch = ? WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, message_text);
            ps.setLong(2, System.currentTimeMillis());
            ps.setInt(3, message_id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Message> getAllMessagesByAccountId(int account_id) {
        List<Message> result = new ArrayList<Message>();
        try {
            Connection conn = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message msg = new Message();
                msg.setMessage_id(rs.getInt("message_id"));
                msg.setPosted_by(rs.getInt("posted_by"));
                msg.setMessage_text(rs.getString("message_text"));
                msg.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
                result.add(msg);
            }
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
