package at.abl.talks.spring.step3_configuration;

import at.abl.talks.spring.step3_configuration.javaconfiguration.JavaConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationMain {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        context.register(JavaConfiguration.class);
//        context.scan(ConfigurationMain.class.getPackageName() + ".componentscan");

        context.refresh();

        ServiceB serviceB = context.getBean(ServiceB.class);
        serviceB.doBStuff();
    }


}