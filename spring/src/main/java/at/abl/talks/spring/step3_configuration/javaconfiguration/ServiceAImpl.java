package at.abl.talks.spring.step3_configuration.javaconfiguration;

import at.abl.talks.spring.step3_configuration.ServiceA;

public class ServiceAImpl implements ServiceA {
    public void doAStuff() {
        System.out.println("doing service A stuff...");
    }
}