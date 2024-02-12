package at.abl.talks.spring.step2_context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.support.GenericApplicationContext;

public class ContextMain {
    public static void main(String[] args) {
        DefaultListableBeanFactory contextOrBeanFactory = new DefaultListableBeanFactory();
//        GenericApplicationContext contextOrBeanFactory = new GenericApplicationContext();

        BeanDefinitionRegistry beanDefinitionRegistry = contextOrBeanFactory;
        BeanFactory beanFactory = contextOrBeanFactory;


        System.out.println("now registering bean definitions");
        beanDefinitionRegistry.registerBeanDefinition("serviceA", createServiceABeanDefinition());
        beanDefinitionRegistry.registerBeanDefinition("postProcessor", createPostProcessorBeanDefinition());

//        contextOrBeanFactory.refresh();

        System.out.println("now getting bean serviceA");
        ServiceA serviceA = beanFactory.getBean(ServiceA.class);
        System.out.println("now using serviceA");
        serviceA.doAStuff();


//        ApplicationContext context = contextOrBeanFactory;
//        context.getEnvironment().getProperty("server.port");
//        context.publishEvent(new ApplicationEvent("hello") {});
    }

    private static BeanDefinition createPostProcessorBeanDefinition() {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();

        beanDefinition.setBeanClass(PostProcessor.class);

        return beanDefinition;
    }

    private static BeanDefinition createServiceABeanDefinition() {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();

        beanDefinition.setBeanClass(ServiceA.class);

        return beanDefinition;
    }
}

class PostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("post processing bean with name: " + beanName + " and type " + bean.getClass().getName());
        if (bean instanceof ServiceA) {
            ((ServiceA) bean).postProcess();
        }
        return bean;
    }
}

class ServiceA {

    private boolean postProcessed = false;

    public ServiceA() {
        System.out.println("constructor service A");
    }

    public void postProcess() {
        System.out.println("post processed service A");
        postProcessed = true;
    }


    public void doAStuff() {
        System.out.println("doing service A stuff... and is postProcessed: " + postProcessed);
    }
}