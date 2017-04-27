package onyxplatform.test;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.OnyxNames;
import org.onyxplatform.api.java.Catalog;
import org.onyxplatform.api.java.Task;
import org.onyxplatform.api.java.instance.CatalogUtils;
import org.onyxplatform.api.java.utils.MapFns;

public class SingleCljTest extends SingleFnTest {

    private IPersistentMap setupMap;
    private Task task;

    public SingleCljTest(String setupEdn){
        setupMap = MapFns.fromResources(setupEdn).toMap();
        IFn require = Clojure.var(OnyxNames.CORE, OnyxNames.Require);
        require.invoke(Clojure.read((String) MapFns.get(setupMap, "namespace")));
    }

    protected void createCatalog(){
        catalog = new Catalog();
        createTask();
        catalog.addTask(task);
        updateCatalog();
		System.out.println("Clojure Catalog Created: ");
		System.out.println(catalog.toString());
    }

    protected void setup(){
        defaultSetup(setupMap);
    }

    private void createTask(){
        String taskMapEdn = (String) MapFns.get(setupMap, "task");
        task = new Task(MapFns.fromResources(taskMapEdn));
    }

}
