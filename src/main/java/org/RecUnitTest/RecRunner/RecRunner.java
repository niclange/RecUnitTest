package org.RecUnitTest.RecRunner;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nicolas
 * Date: 26/11/13
 * Time: 09:36
 * To change this template use File | Settings | File Templates.
 */
public class RecRunner extends Runner {
    private Description description;
    private List<Class<?>> classList;

    public RecRunner(List<Class<?>> classList){
        this.classList = classList;
        this.description = new Description


    }
    @Override
    public Description getDescription() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void run(RunNotifier runNotifier) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
