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
}
