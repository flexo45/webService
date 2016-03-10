import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.io.File;

public class Main {

    static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception{

        PropertiesReader.readProperties();

        System.setProperty("log4j.configuration", new File(".", PropertiesReader.getLog4jConfigFile()).toURI().toURL().toString());

        int port;

        if(args.length < 1){
            logger.error("Use default port");
            port = PropertiesReader.getPort();
        }
        else {
            String portString = args[0];
            port = Integer.valueOf(portString);
        }

        logger.info("Starting at http://127.0.0.1:" + port);

        Server server = new Server(port);

        ServletContextHandler contextHandler =
                new ServletContextHandler(ServletContextHandler.SESSIONS);

        server.setHandler(contextHandler);

        ServletManager.addAccountServer(contextHandler);
        ServletManager.addMirrorServlet(contextHandler);
        //ServletManager.addDBAuthServlets(contextHandler);
        ServletManager.addChatService(contextHandler);
        ServletManager.addResourceServer(contextHandler);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("static");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, contextHandler});
        server.setHandler(handlers);

        server.start();

        System.out.println("Server started");

        server.join();
    }
}
