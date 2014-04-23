package org.proxyRecorder;

import java.lang.reflect.Method;

/**
 * Recorder interface
 * Created with IntelliJ IDEA.
 * User: nicolas
 * Date: 13/12/13
 * Time: 09:01
 * To change this template use File | Settings | File Templates.
 */
public interface Recorder {
    /**
     *
     * record method
     *
     * @param method method to record
     * @param params method params to record
     * @param returned result to record
     */
    void record(Method method, Object[] params, Object returned);

    /**
     * Record method
     *
     * @param method method to record
     * @param params method params to record
     * @param thrown throwable to record
     */
    void record(Method method, Object[] params, Throwable thrown);

}
