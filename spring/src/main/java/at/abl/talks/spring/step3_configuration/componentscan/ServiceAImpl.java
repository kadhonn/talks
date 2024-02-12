package at.abl.talks.spring.step3_configuration.componentscan;

import at.abl.talks.spring.step3_configuration.ServiceA;
import org.springframework.stereotype.Component;

@Component
public class ServiceAImpl implements ServiceA {
    public void doAStuff() {
        System.out.println("doing service A stuff...");
    }
}