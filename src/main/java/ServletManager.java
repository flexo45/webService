import L1.servlet.MirrorServlet;
import L2.accounts.AccountServiceMemImpl;
import L3.accounts.AccountServiceDbImpl;
import L4.chat.WebSocketChatServlet;
import account.AccountService;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class ServletManager {
    static AccountService accountService;

    public static void addMirrorServlet(ServletContextHandler servletContextHandler){
        servletContextHandler.addServlet(new ServletHolder(new MirrorServlet()), "/mirror/*");
    }

    public static void addmInMemoryAuthServlets(ServletContextHandler servletContextHandler){
        accountService = new AccountServiceMemImpl();
        addAuthServlets(servletContextHandler);
    }

    public static void addDBAuthServlets(ServletContextHandler servletContextHandler){
        accountService = new AccountServiceDbImpl();
        addAuthServlets(servletContextHandler);
    }

    public static void addChatService(ServletContextHandler servletContextHandler){
        servletContextHandler.addServlet(new ServletHolder(new WebSocketChatServlet()), "/chat");
    }

    private static void addAuthServlets(ServletContextHandler servletContextHandler){
        servletContextHandler.addServlet(new ServletHolder(new servlet.SignUpServlet(accountService)), "/signup");
        servletContextHandler.addServlet(new ServletHolder(new servlet.SignInServlet(accountService)), "/signin");
    }
}
