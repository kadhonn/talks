package at.abl.talks.spring.step1_beans;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

public class BeansMain {
    public static void main(String[] args) {
        DefaultListableBeanFactory listableBeanFactory = new DefaultListableBeanFactory();

        BeanDefinitionRegistry beanDefinitionRegistry = listableBeanFactory;
        BeanFactory beanFactory = listableBeanFactory;

        System.out.println("now registering bean definitions");
        beanDefinitionRegistry.registerBeanDefinition("serviceA", createServiceABeanDefinition());
        beanDefinitionRegistry.registerBeanDefinition("serviceB", createServiceBBeanDefinition());

        System.out.println("now getting bean serviceB");
        ServiceB serviceB = beanFactory.getBean("serviceB", ServiceB.class);
        System.out.println("now using serviceB");
        serviceB.doBStuff();
    }

    private static BeanDefinition createServiceBBeanDefinition() {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();

        beanDefinition.setBeanClass(ServiceB.class);
//        beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);

        ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
        constructorArgumentValues.addIndexedArgumentValue(0,
                new ConstructorArgumentValues.ValueHolder(new RuntimeBeanReference(ServiceA.class)));
        beanDefinition.setConstructorArgumentValues(constructorArgumentValues);

        return beanDefinition;
    }

    private static BeanDefinition createServiceABeanDefinition() {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();

        beanDefinition.setBeanClass(ServiceA.class);
        beanDefinition.setInitMethodName("init");

        return beanDefinition;
    }
}

class ServiceB {

    private final ServiceA serviceA;

    public ServiceB(ServiceA serviceA) {
        System.out.println("constructor service B");
        this.serviceA = serviceA;
    }

    public void doBStuff() {
        System.out.println("doing service B stuff...");
        serviceA.doAStuff();
    }
}

class ServiceA {

    public ServiceA() {
        System.out.println("constructor service A");
    }

    public void init() {
        System.out.println("init service A");
    }

    public void doAStuff() {
        System.out.println("doing service A stuff...");
    }
}