import L1.servlet.MirrorServlet;
import L2.accounts.AccountServiceMemImpl;
import L3.accounts.AccountServiceDbImpl;
import L4.chat.WebSocketChatServlet;
import L5.accountServer.AccountServer;
import L5.accountServer.AccountServerController;
import L5.accountServer.AccountServerControllerMBean;
import L5.accountServer.AccountServerI;
import resources.ResourceServer;
import resources.ResourceServerController;
import resources.ResourceServerControllerMBean;
import account.AccountService;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.AdminServlet;
import servlets.HomePageServlet;
import servlets.ResourceServlet;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class ServletManager {
    static AccountService accountService;
    static AccountServerI accountServer;
    static ResourceServer resourceServer;

    static MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

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

    public static void addAccountServer(ServletContextHandler servletContextHandler) throws Exception{
        accountServer = new AccountServer(10);
        AccountServerControllerMBean serverStatistics = new AccountServerController(accountServer);

        //ObjectName name = new ObjectName("ServerManager:type=AccountServerController");
        ObjectName name = new ObjectName("Admin:type=AccountServerController");
        mbs.registerMBean(serverStatistics, name);
        servletContextHandler.addServlet(new ServletHolder(new HomePageServlet(accountServer)), HomePageServlet.PAGE_URL);
        servletContextHandler.addServlet(new ServletHolder(new AdminServlet(accountServer)), AdminServlet.PAGE_URL);
    }

    private static void addAuthServlets(ServletContextHandler servletContextHandler){
        servletContextHandler.addServlet(new ServletHolder(new servlets.SignUpServlet(accountService)), "/signup");
        servletContextHandler.addServlet(new ServletHolder(new servlets.SignInServlet(accountService)), "/signin");
    }

    public static void addResourceServer(ServletContextHandler servletContextHandler) throws Exception{
        resourceServer = new ResourceServer();
        ResourceServerControllerMBean serverMBean = new ResourceServerController(resourceServer);

        ObjectName name = new ObjectName("Admin:type=ResourceServerController");
        mbs.registerMBean(serverMBean, name);

        servletContextHandler.addServlet(new ServletHolder(new ResourceServlet(resourceServer)), ResourceServlet.PAGE_URL);
    }
}
