package at.abl.agenttalk.agent;

import java.lang.instrument.Instrumentation;

public class Agent {
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("Hey form Agent premain!");

        instrumentation.addTransformer(new MyClassFileTransformer());
    }

}
