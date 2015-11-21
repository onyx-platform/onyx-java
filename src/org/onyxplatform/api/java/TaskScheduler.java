package org.onyxplatform.api.java;

public enum TaskScheduler {
       
    BALANCED("onyx.task-scheduler/balanced"),
    PERCENTAGE("onyx.task-scheduler/percentage");
    
    private final String strRepr;

    private TaskScheduler(String s) {
        strRepr = s;
    }

    @Override
    public String toString() {
        return strRepr;
    }
    
}
