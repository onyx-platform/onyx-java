package org.onyxplatform.api.java;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

public enum TaskScheduler 
	implements OnyxNames
{
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

    public Object toCljString() {
	IFn requireFn = Clojure.var(CORE, Require);
	IFn kwFn = Clojure.var(CORE, Keyword);
	return kwFn.invoke(strRepr);
    }
}
