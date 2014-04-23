package org.proxyRecorder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**                             .
 * InvocationHandler for the service to record
 * User: nicolas
 * Date: 13/12/13
 * Time: 09:25
 */
public class ServiceInvocationHandler<I> implements InvocationHandler {
    private final Recorder recorder;
    private final I service;

    private ServiceInvocationHandler(I service, final Recorder recorder) {
        this.service = service;
        this.recorder = recorder;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object returned;
        try {
            returned = method.invoke(service, args);
        } catch (Throwable t) {
            recorder.record(method, args, t);
            throw t;
        }
        recorder.record(method, args, returned);
        return returned;
    }

    public static <I> I createInvocationHandler(I service, final Recorder recorder) {
        InvocationHandler hanlder = new ServiceInvocationHandler<I>(service, recorder);
        return (I) Proxy.newProxyInstance(service.getClass().getClassLoader(),
                new Class[]{service.getClass()}, hanlder);
    }
}
