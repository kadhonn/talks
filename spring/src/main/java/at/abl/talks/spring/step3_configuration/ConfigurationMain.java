package at.abl.talks.spring.step3_configuration;

import at.abl.talks.spring.step3_configuration.javaconfiguration.JavaConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

public class ConfigurationMain {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        context.register(JavaConfiguration.class);
//        context.scan(ConfigurationMain.class.getPackageName()+".componentscan");

        context.refresh();

        ServiceB serviceB = context.getBean(ServiceB.class);
        serviceB.doBStuff();
    }


}