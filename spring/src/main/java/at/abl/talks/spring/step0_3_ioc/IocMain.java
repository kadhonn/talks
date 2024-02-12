package at.abl.talks.spring.step0_3_ioc;

public class IocMain {
    public static void main(String[] args) {
        ServiceA serviceA = new ServiceA();
        ServiceB serviceB = new ServiceB(serviceA);

        serviceB.doBStuff();
    }
}

class ServiceA {
    public void doAStuff() {
        System.out.println("doing service A stuff...");
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