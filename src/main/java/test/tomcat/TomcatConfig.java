package test.tomcat;

import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import jakarta.inject.Singleton;
import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.Wrapper;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.jasper.servlet.JasperInitializer;
import org.apache.jasper.servlet.JspServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.jsp.JspFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Singleton
public class TomcatConfig implements BeanCreatedEventListener<Tomcat> {

    @Override
    public Tomcat onCreated(BeanCreatedEvent<Tomcat> event) {
        Tomcat tomcat = event.getBean();
        JspFactory.getDefaultFactory();
        String webappDirLocation = "src/main/webapp/";
        /*String webappDirLocation = "src/main/webapp/";


        //The port that we should run on can be set into an environment variable
        //Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        tomcat.setPort(Integer.valueOf(webPort));
        tomcat.setAddDefaultWebXmlToWebapp(false);
        StandardContext ctx = (StandardContext) tomcat.addWebapp("/servlet", new File(webappDirLocation).getAbsolutePath());
        System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());
        ctx.setAltDDName(webappDirLocation + "/WEB-INF/web.xml");
        // Declare an alternative location for your "WEB-INF/classes" dir
        // Servlet 3.0 annotation will work
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);*/
        // Create a context for the servlet
        Context context = tomcat.addContext("/servlet", new File(webappDirLocation).getAbsolutePath());
        context.addServletContainerInitializer(new JasperInitializer(), null);
        JspServlet jspServlet = new JspServlet();
        Wrapper jspServlet1 = Tomcat.addServlet(context, "JspServlet", jspServlet);

        jspServlet1.addMapping("*.jsp");
        jspServlet1.addMapping("*.jspx");
        jspServlet1.addMapping("*.jspf");
        jspServlet1.addMapping("*.xsp");

        // Set the JspServlet's init parameters
        jspServlet1.addInitParameter("fork", "false");
        jspServlet1.addInitParameter("xpoweredBy", "false");
        jspServlet1.addInitParameter("compilerSourceVM", "1.8");
        jspServlet1.addInitParameter("compilerTargetVM", "1.8");

        jspServlet1.setLoadOnStartup(3);
        jspServlet1.setMultipartConfigElement(new MultipartConfigElement(""));



        return tomcat;
    }
}
