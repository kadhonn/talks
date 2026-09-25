package at.abl.agenttalk.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class MyClassFileTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(Module module, ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("Getting classloadhook for class " + className + "from classloader " + loader);
        return null;
    }
}
