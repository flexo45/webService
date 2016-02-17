import L1.servlet.MirrorServlet;
import account.IAccountService;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class ServletManager {
    static IAccountService accountService;

    public static void addMirrorServlet(ServletContextHandler servletContextHandler){
        servletContextHandler.addServlet(new ServletHolder(new MirrorServlet()), "/mirror/*");
    }

    public static void addmInMemoryAuthServlets(ServletContextHandler servletContextHandler){
        accountService = new L2.accounts.AccountService();
        addAuthServlets(servletContextHandler);
    }

    public static void addDBAuthServlets(ServletContextHandler servletContextHandler){
        accountService = new L3.accounts.AccountService();
        addAuthServlets(servletContextHandler);
    }

    private static void addAuthServlets(ServletContextHandler servletContextHandler){
        servletContextHandler.addServlet(new ServletHolder(new servlet.SignUpServlet(accountService)), "/signup");
        servletContextHandler.addServlet(new ServletHolder(new servlet.SignInServlet(accountService)), "/signin");
    }
}
