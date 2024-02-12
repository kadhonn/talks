package at.abl.talks.spring.step3_configuration.javaconfiguration;

import at.abl.talks.spring.step3_configuration.ServiceA;
import at.abl.talks.spring.step3_configuration.ServiceB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfiguration {
    @Bean
    public ServiceA serviceA() {
        return new ServiceAImpl();
    }

    @Bean
    public ServiceB serviceB(ServiceA serviceA) {
        return new ServiceBImpl(serviceA);
    }
}
