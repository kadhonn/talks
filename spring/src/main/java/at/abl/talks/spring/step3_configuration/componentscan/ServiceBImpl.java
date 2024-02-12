package at.abl.talks.spring.step3_configuration.componentscan;

import at.abl.talks.spring.step3_configuration.ServiceA;
import at.abl.talks.spring.step3_configuration.ServiceB;
import org.springframework.stereotype.Service;

@Service
public class ServiceBImpl implements ServiceB {
    private final ServiceA serviceA;

    public ServiceBImpl(ServiceA serviceA) {
        this.serviceA = serviceA;
    }

    public void doBStuff() {
        System.out.println("doing service B stuff...");
        serviceA.doAStuff();
    }
}