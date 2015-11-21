package org.onyxplatform.api.java;

import java.util.Arrays;

public class OnyxJava {

    public static void main(String[] args) {
        EnvConfiguration envConfig = new EnvConfiguration();
        envConfig = envConfig.addParameter("zookeeper/address", "127.0.0.1:2188");
        envConfig = envConfig.addParameter("zookeeper/server?", true);
        envConfig = envConfig.addParameter("zookeeper.server/port", 2188);
        envConfig = envConfig.addParameter("onyx.bookkeeper/server?", true);
        envConfig = envConfig.addParameter("onyx.bookkeeper/local-quorum?", true);
        envConfig = envConfig.addParameter("onyx.bookkeeper/local-quorum-ports", Arrays.asList(3196, 3197, 3198));
        envConfig = envConfig.addParameter("onyx/id", "my-id");
        
        PeerConfiguration peerConfig = new PeerConfiguration();
        peerConfig = peerConfig.addParameter("zookeeper/address", "127.0.0.1:2188");
        peerConfig = peerConfig.addParameter("onyx.peer/job-scheduler", "onyx.job-scheduler/greedy");
        peerConfig = peerConfig.addParameter("onyx.peer/zookeeper-timeout", 60000);
        peerConfig = peerConfig.addParameter("onyx.messaging.aeron/embedded-driver?", true);
        peerConfig = peerConfig.addParameter("onyx.messaging/allow-short-circuit?", false);
        peerConfig = peerConfig.addParameter("onyx.messaging/impl", "aeron");
        peerConfig = peerConfig.addParameter("onyx.messaging/peer-port", 40199);
        peerConfig = peerConfig.addParameter("onyx.messaging/bind-addr", "localhost");
        peerConfig = peerConfig.addParameter("onyx/id", "my-id");
        
        Workflow workflow = new Workflow();
        workflow = workflow.addEdge("read-input", "increment-number");
        workflow = workflow.addEdge("increment-number", "write-output");
        
        Task task1 = new Task();
        task1 = task1.addParameter("onyx/name", "read-input");
        task1 = task1.addParameter("onyx/type", "input");
        task1 = task1.addParameter("onyx/plugin", "onyx.plugin.core-async/input");
        task1 = task1.addParameter("onyx/medium", "input");
        task1 = task1.addParameter("onyx/max-peers", 1);
        task1 = task1.addParameter("onyx/batch-size", 20);
        task1 = task1.addParameter("onyx/doc", "My docstring here.");
        
        Task task2 = new Task();
        task2 = task2.addParameter("onyx/name", "increment-number");
        task2 = task2.addParameter("onyx/fn", "clojure.core/identity");
        task2 = task2.addParameter("onyx/type", "function");
        task2 = task2.addParameter("onyx/batch-size", 20);
        
        Task task3 = new Task();
        task3 = task3.addParameter("onyx/name", "write-output");
        task3 = task3.addParameter("onyx/plugin", "onyx.plugin.core-async/output");
        task3 = task3.addParameter("onyx/medium", "output");
        task3 = task3.addParameter("onyx/max-peers", 1);
        task3 = task3.addParameter("onyx/batch-size", 20);
        task3 = task3.addParameter("onyx/type", "output");
        
        Catalog catalog = new Catalog();
        catalog = catalog.addTask(task1);
        catalog = catalog.addTask(task2);
        catalog = catalog.addTask(task3);
                  
        LifecycleCalls inputInjectCalls = new LifecycleCalls();
        inputInjectCalls = inputInjectCalls.addParameter("lifecycle/task", "read-input");
        inputInjectCalls = inputInjectCalls.addParameter("lifecycle/calls", "onyx.temp/in-calls");
        
        LifecycleCalls inputCASCalls = new LifecycleCalls();
        inputCASCalls = inputCASCalls.addParameter("lifecycle/task", "read-input");
        inputCASCalls = inputCASCalls.addParameter("lifecycle/calls", "onyx.plugin.core-async/reader-calls");

        LifecycleCalls outputInjectCalls = new LifecycleCalls();
        outputInjectCalls = outputInjectCalls.addParameter("lifecycle/task", "write-output");
        outputInjectCalls = outputInjectCalls.addParameter("lifecycle/calls", "onyx.temp/out-calls");
      
        LifecycleCalls outputCASCalls = new LifecycleCalls();
        outputCASCalls = outputCASCalls.addParameter("lifecycle/task", "write-output");
        outputCASCalls = outputCASCalls.addParameter("lifecycle/calls", "onyx.plugin.core-async/writer-calls");

        Lifecycles lifecycles = new Lifecycles();
        lifecycles = lifecycles.addLifecycleCalls(inputInjectCalls);
        lifecycles = lifecycles.addLifecycleCalls(inputCASCalls);
        lifecycles = lifecycles.addLifecycleCalls(outputInjectCalls);
        lifecycles = lifecycles.addLifecycleCalls(outputCASCalls);
        
        TaskScheduler ts = TaskScheduler.BALANCED;
        
        Job job = new Job(ts);
        job = job.addWorkflow(workflow);
        job = job.addCatalog(catalog);
        job = job.addLifecycles(lifecycles);
        
        Object env = API.startEnv(envConfig);
        Object peerGroup = API.startPeerGroup(peerConfig);
        Object peers = API.startPeers(3, peerGroup);
        
        //API.submitJob(peerConfig, job);     
        
        API.shutdownPeers(peers);
        API.shutdownPeerGroup(peerGroup);
        API.shutdownEnv(env);
    }
    
}
