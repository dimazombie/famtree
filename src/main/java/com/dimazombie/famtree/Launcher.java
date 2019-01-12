package com.dimazombie.famtree;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.URL;
import java.util.Locale;

public class Launcher {
    public static final String WEBAPP_RESOURCES_LOCATION = "webapp";

    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.ENGLISH);
        Server server = new Server(8889);
        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.setDescriptor(WEBAPP_RESOURCES_LOCATION + "/WEB-INF/web.xml");
        URL webAppDir = Thread.currentThread().getContextClassLoader().getResource(WEBAPP_RESOURCES_LOCATION);
        if (webAppDir == null) {
            throw new RuntimeException(String.format("No %s directory was found into the JAR file", WEBAPP_RESOURCES_LOCATION));
        }
        context.setResourceBase(webAppDir.toURI().toString());
        context.setParentLoaderPriority(true);

        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            // Fix for Windows, so Jetty doesn't lock files
            context.getInitParams().put("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
        }

        server.setHandler(context);
        server.start();
    }
}
