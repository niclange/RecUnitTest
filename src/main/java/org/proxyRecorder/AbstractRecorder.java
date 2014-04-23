package org.proxyRecorder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * User: nicolas
 * Date: 13/12/13
 * Time: 09:13
 */
public abstract class AbstractRecorder implements Recorder {

    /**
     * recording methods list
     */
    private List<Method> recordingMethods = new ArrayList<Method>();

    /**
     * Add method to record
     * @param method to record
     */

    public final void add(final Method method) {
        recordingMethods.add(method);
    }

    /**
     * Add method class to record
     * @param clazz class
     * @param method method name
     * @param params params
     * @throws SecurityException exception
     * @throws NoSuchMethodException exception
     */
    public final void add(final Class clazz, final String method, final Class... params)
            throws SecurityException, NoSuchMethodException {
        add(clazz.getMethod(method, params));
    }

    /**
     * Add class to record all methods
     * @param clazz class
     */
    public final void add(final Class clazz) {
        for (Method method : clazz.getMethods()) {
            recordingMethods.add(method);
        }
    }

    /**
     * check if the method is adding ti record
     * @param method method to check
     * @return
     */
    public final boolean isRecording(final Method method) {
        return recordingMethods.contains(method);
    }
}
