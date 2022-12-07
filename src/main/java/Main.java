import di.GuiceListener;
import flyway.FlywayInitializer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import server.JettyServer;

import javax.ws.rs.core.Application;

public class Main extends Application {
    public static void main(String[] args) throws Exception {
        FlywayInitializer.initDb();
        final Server server = JettyServer.build();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.addServlet(HttpServletDispatcher.class, "/");
        context.addEventListener(new GuiceListener());

        server.setHandler(context);
        server.start();
    }
}
