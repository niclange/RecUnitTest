package org.RecUnitTest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: nicolas
 * Date: 22/11/13
 * Time: 18:55
 * To change this template use File | Settings | File Templates.
 */
public class Recorder  {
    private static Logger log = Logger.getLogger(Recorder.class);
    private Scenario scenario;

    /**
     * Constructeur
     */
    public Recorder(){
        scenario = new Scenario();
        scenario.setRecordList(new ArrayList<Record>());

    }

    /**
     * method recording action
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    public final Object records(ProceedingJoinPoint joinPoint) throws Throwable {
        String name = joinPoint.getSignature().toShortString();

        Record record = new Record();
        record.setMethodName(name);
        record.setArguments(Arrays.asList(joinPoint.getArgs()));

        record.setResult(joinPoint.proceed());

        scenario.getRecordList().add(record);
        if(log.isDebugEnabled()) {
            log.debug(record.toString());
        }
        return record.getResult();
    }

}
