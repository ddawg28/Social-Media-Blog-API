package Service;

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }
    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO) {
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    public Message addMessage(Message msg) {
        if (msg.getMessage_text().isBlank()) {
            return null;
        }
        if (msg.getMessage_text().length() > 255) {
            return null;
        }
        if (accountDAO.checkExistsAccountById(msg.posted_by) == false) {
            return null;
        }
        return messageDAO.insertMessage(msg);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
}
