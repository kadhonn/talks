package at.abl.talks.spring.step0_2_non_ioc;

public class NonIocMain {
    public static void main(String[] args) {
        ServiceB serviceB = new ServiceB();

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

    public ServiceB() {
        serviceA = new ServiceA();
    }

    public void doBStuff() {
        System.out.println("doing service B stuff...");
        serviceA.doAStuff();
    }
}
