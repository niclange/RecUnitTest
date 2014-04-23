package org.RecUnitTest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import com.google.common.base.Objects;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: nicolas
 * Date: 25/11/13
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public class Player {
    /**
     * Logger
     */
    private static Logger log = Logger.getLogger(Player.class);

    /**
     * test scenario
     */
    private Scenario scenario;
    /**
     * current record
     */
    private Record currentRecord;

    /**
     * Constructor
     * @param scenario scenario of test
     */
    public Player(Scenario scenario){
        this.scenario = scenario;
        this.currentRecord = scenario.getRecordList().get(0);
    }

    /**
     * Constructor  default
     */
    public Player(){

    }

    /**
     * method recording action
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    public final Object play(ProceedingJoinPoint joinPoint) throws Throwable {
        String name = joinPoint.getSignature().toShortString();

        //check arguments
        for (int i = 0;i < joinPoint.getArgs().length; i++){
           assertTrue(Objects.equal(joinPoint.getArgs()[i], currentRecord.getArguments().get(i)));
        }

        if (log.isDebugEnabled()) {
            log.debug(currentRecord.toString() + " / " + name);
        }

        //return result
        Object result = currentRecord.getResult();

        //next record
        int i = scenario.getRecordList().indexOf(currentRecord) + 1;
        if (this.scenario.getRecordList().size() > i) {
            currentRecord = scenario.getRecordList().get(i);
        }

        return result;
    }

}
