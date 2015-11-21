package org.onyxplatform.api.java;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentArrayMap;

public class API {

    private final static IFn castTypes;
    private final static IFn kw;
    
    static {       
        IFn require = Clojure.var("clojure.core", "require");
        
        require.invoke(Clojure.read("onyx.api"));
        require.invoke(Clojure.read("onyx.interop"));
        
        castTypes = Clojure.var("onyx.interop", "cast-types");
        kw = Clojure.var("clojure.core", "keyword");
    }
    
    public static Object startEnv(EnvConfiguration envConfig) {
        IFn startEnv = Clojure.var("onyx.api", "start-env");        
        Object coercedConfig = castTypes.invoke("env-config", envConfig.toMap());
        return startEnv.invoke(coercedConfig);
    }
    
    public static Object startPeerGroup(PeerConfiguration peerConfig) {
        IFn startPeerGroup = Clojure.var("onyx.api", "start-peer-group");
        Object coercedConfig = castTypes.invoke("peer-config", peerConfig.toMap());
        return startPeerGroup.invoke(coercedConfig);
    }
    
    public static Object startPeers(int nPeers, Object peerGroup) {
        IFn startPeers = Clojure.var("onyx.api" , "start-peers");
        return startPeers.invoke(nPeers, peerGroup);
    }
    
    public static Object shutdownEnv(Object env) {
        IFn shutdownEnv = Clojure.var("onyx.api", "shutdown-env");
        return shutdownEnv.invoke(env);
    }
    
    public static Object shutdownPeerGroup(Object peerGroup) {
        IFn shutdownPeerGroup = Clojure.var("onyx.api", "shutdown-peer-group");
        return shutdownPeerGroup.invoke(peerGroup);
    }
    
    public static Object shutdownPeers(Object peers) {
        IFn shutdownPeer = Clojure.var("onyx.api", "shutdown-peers");
        return shutdownPeer.invoke(peers);
    }
    
    public static boolean submitJob(PeerConfiguration peerConfig, Job job) {
        IFn submitJob = Clojure.var("onyx.api", "submit-job");
        
        PersistentArrayMap coercedJob = PersistentArrayMap.EMPTY;
        
        coercedJob = (PersistentArrayMap) coercedJob.assoc(kw.invoke("workflow"), Coerce.coerceWorkflow(job.workflow));
        coercedJob = (PersistentArrayMap) coercedJob.assoc(kw.invoke("catalog"), Coerce.coerceCatalog(job.catalog));
        coercedJob = (PersistentArrayMap) coercedJob.assoc(kw.invoke("lifecycles"), Coerce.coerceLifecycles(job.lifecycles));
        coercedJob = (PersistentArrayMap) coercedJob.assoc(kw.invoke("task-scheduler"), Coerce.coerceTaskScheduler(job.taskScheduler));
        
        Object coercedConfig = castTypes.invoke("peer-config", peerConfig.toMap());
        
        return (boolean) submitJob.invoke(coercedConfig, coercedJob);
    }
    
    public static boolean killJob(PeerConfiguration peerConfig, String jobID) {
        IFn killJob = Clojure.var("onyx.api", "kill-job");
        Object coercedConfig = castTypes.invoke("peer-config", peerConfig.toMap());
        return (boolean) killJob.invoke(coercedConfig, jobID);
    }
    
    public static boolean gc(PeerConfiguration peerConfig) {
        IFn gc = Clojure.var("onyx.api", "gc");
        Object coercedConfig = castTypes.invoke("peer-config", peerConfig.toMap());
        return (boolean) gc.invoke(coercedConfig);
    }
    
    public static boolean awaitJobCompletion(PeerConfiguration peerConfig, String jobID) {
        IFn awaitJobCompletion = Clojure.var("onyx.api", "await-job-completion");
        Object coercedConfig = castTypes.invoke("peer-config", peerConfig.toMap());
        return (boolean) awaitJobCompletion.invoke(coercedConfig, jobID);
    }
    
}
