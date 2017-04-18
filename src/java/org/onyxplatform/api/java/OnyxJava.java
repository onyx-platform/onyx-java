package org.onyxplatform.api.java;

import java.util.Arrays;

public class OnyxJava {

    public static void main(String[] args) {

        EnvConfiguration envConfig = new EnvConfiguration();
        envConfig.addParameter("zookeeper/address", "127.0.0.1:2188");
        envConfig.addParameter("zookeeper/server?", true);
        envConfig.addParameter("zookeeper.server/port", 2188);
        envConfig.addParameter("onyx.bookkeeper/server?", true);
        envConfig.addParameter("onyx.bookkeeper/local-quorum?", true);
        envConfig.addParameter("onyx.bookkeeper/local-quorum-ports", Arrays.asList(3196, 3197, 3198));
        envConfig.addParameter("onyx/id", "my-id");

        PeerConfiguration peerConfig = new PeerConfiguration();
        peerConfig.addParameter("zookeeper/address", "127.0.0.1:2188");
        peerConfig.addParameter("onyx.peer/job-scheduler", "onyx.job-scheduler/greedy");
        peerConfig.addParameter("onyx.peer/zookeeper-timeout", 60000);
        peerConfig.addParameter("onyx.messaging.aeron/embedded-driver?", true);
        peerConfig.addParameter("onyx.messaging/allow-short-circuit?", false);
        peerConfig.addParameter("onyx.messaging/impl", "aeron");
        peerConfig.addParameter("onyx.messaging/peer-port", 40199);
        peerConfig.addParameter("onyx.messaging/bind-addr", "localhost");
        peerConfig.addParameter("onyx/id", "my-id");


        TaskScheduler ts = TaskScheduler.BALANCED;

	Job job = new Job(ts);

        job.addWorkflowEdge("read-input", "increment-number");
        job.addWorkflowEdge("increment-number", "write-output");

        Task task1 = new Task();
        task1.addParameter("onyx/name", "read-input");
        task1.addParameter("onyx/type", "input");
        task1.addParameter("onyx/plugin", "onyx.plugin.core-async/input");
        task1.addParameter("onyx/medium", "input");
        task1.addParameter("onyx/max-peers", 1);
        task1.addParameter("onyx/batch-size", 20);
        task1.addParameter("onyx/doc", "My docstring here.");

	job.addCatalogTask(task1);

        Task task2 = new Task();
        task2.addParameter("onyx/name", "increment-number");
        task2.addParameter("onyx/fn", "clojure.core/identity");
        task2.addParameter("onyx/type", "function");
        task2.addParameter("onyx/batch-size", 20);

	job.addCatalogTask(task2);

        Task task3 = new Task();
        task3.addParameter("onyx/name", "write-output");
        task3.addParameter("onyx/plugin", "onyx.plugin.core-async/output");
        task3.addParameter("onyx/medium", "output");
        task3.addParameter("onyx/max-peers", 1);
        task3.addParameter("onyx/batch-size", 20);
        task3.addParameter("onyx/type", "output");

	job.addCatalogTask(task3);

        Lifecycle inputInject = new Lifecycle();
        inputInject.addParameter("lifecycle/task", "read-input");
        inputInject.addParameter("lifecycle/calls", "onyx.temp/in-calls");

	job.addLifecycle(inputInject);

        Lifecycle inputCAS = new Lifecycle();
        inputCAS.addParameter("lifecycle/task", "read-input");
        inputCAS.addParameter("lifecycle/calls", "onyx.plugin.core-async/reader-calls");

	job.addLifecycle(inputCAS);

        Lifecycle outputInject = new Lifecycle();
        outputInject.addParameter("lifecycle/task", "write-output");
        outputInject.addParameter("lifecycle/calls", "onyx.temp/out-calls");

	job.addLifecycle(outputInject);

        Lifecycle outputCAS = new Lifecycle();
        outputCAS.addParameter("lifecycle/task", "write-output");
        outputCAS.addParameter("lifecycle/calls", "onyx.plugin.core-async/writer-calls");

	job.addLifecycle(outputCAS);

        Object env = API.startEnv(envConfig);
        Object peerGroup = API.startPeerGroup(peerConfig);
        Object peers = API.startPeers(3, peerGroup);

        API.submitJob(peerConfig, job);

        API.shutdownPeers(peers);
        API.shutdownPeerGroup(peerGroup);
        API.shutdownEnv(env);
    }

}
