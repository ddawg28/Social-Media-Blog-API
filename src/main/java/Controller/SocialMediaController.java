package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;
import Service.*;
import java.util.*;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    // instantiate service classes
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::loginAccountHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account newAccount = accountService.addAccount(account);
        if (newAccount != null) {
            ctx.json(om.writeValueAsString(newAccount));
        } else {
            ctx.status(400);
        }
    }

    private void loginAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account accountResult = accountService.getAccount(account);
        if (accountResult != null) {
            ctx.json(om.writeValueAsString(accountResult));
        } else {
            ctx.status(401);
        }
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Message msg = om.readValue(ctx.body(), Message.class);
        Message result = messageService.addMessage(msg);
        if (result != null) {
            ctx.json(om.writeValueAsString(result));
        } else {
            ctx.status(400);
        }
    }

    private void getMessagesHandler(Context ctx) {
        ctx.json(messageService.getAllMessages());
    }

    private void getMessageByIdHandler(Context ctx) {
        Message result = messageService.getMessageById(Integer.parseInt(ctx.pathParam("message_id")));
        if (result != null) {
            ctx.json(result);
        } else {
            ctx.status(200);
        }
    }
    
    private void deleteMessageByIdHandler(Context ctx) {
        Message result = messageService.deleteMessageById(Integer.parseInt(ctx.pathParam("message_id")));
        if (result != null) {
            ctx.json(result);
        } else {
            ctx.status(200);
        }
    }
}