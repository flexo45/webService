package L4.chat;

import org.eclipse.jetty.websocket.servlet.*;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "WebSocketChatServlet", urlPatterns = {"/chat"})
public class WebSocketChatServlet extends WebSocketServlet {
    private final static int LOGOUT_TIME = 10 * 60 * 1000;
    private final ChatService chatService;

    public WebSocketChatServlet() {
        this.chatService = new ChatService();
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);

        factory.setCreator(
                new WebSocketCreator() {
                    @Override
                    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
                        return new ChatWebSocket(chatService);
                    }
                }
        );
    }
}
