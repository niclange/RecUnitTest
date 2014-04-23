package org.RecUnitTest;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nicolas
 * Date: 25/11/13
 * Time: 08:54
 * To change this template use File | Settings | File Templates.
 */
public class Scenario implements Serializable {


    private String name;
    private List<Record> recordList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("recordList", recordList)
                .toString();
    }
}
