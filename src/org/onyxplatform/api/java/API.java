package org.onyxplatform.api.java;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentArrayMap;

public class API implements OnyxNames {

    static {
        IFn require = Clojure.var(CORE, Require);
        require.invoke(Clojure.read(API));
    }

    public static Object startEnv(EnvConfiguration envConfig) {
        IFn startEnv = Clojure.var(API, StartEnv);
        Object coercedConfig = envConfig.toCljMap();
        return startEnv.invoke(coercedConfig);
    }

    public static Object startPeerGroup(PeerConfiguration peerConfig) {
        IFn startPeerGroup = Clojure.var(API, StartPeerGroup);
        Object coercedConfig = peerConfig.toCljMap();
        return startPeerGroup.invoke(coercedConfig);
    }

    public static Object startPeers(int nPeers, Object peerGroup) {
        IFn startPeers = Clojure.var(API , StartPeers);
        return startPeers.invoke(nPeers, peerGroup);
    }

    public static Object shutdownEnv(Object env) {
        IFn shutdownEnv = Clojure.var(API, ShutdownEnv);
        return shutdownEnv.invoke(env);
    }

    public static Object shutdownPeerGroup(Object peerGroup) {
        IFn shutdownPeerGroup = Clojure.var(API, ShutdownPeerGroup);
        return shutdownPeerGroup.invoke(peerGroup);
    }

    public static Object shutdownPeers(Object peers) {
        IFn shutdownPeer = Clojure.var(API, ShutdownPeers);
        return shutdownPeer.invoke(peers);
    }

    public static boolean submitJob(PeerConfiguration peerConfig, Job job) {
        IFn submitJob = Clojure.var(API, SubmitJob);
	    Object coercedJob = job.toCljMap();
        Object coercedConfig = peerConfig.toCljMap();
        return (boolean) submitJob.invoke(coercedConfig, coercedJob);
    }

    public static boolean killJob(PeerConfiguration peerConfig, String jobID) {
        IFn killJob = Clojure.var(API, KillJob);
        Object coercedConfig = peerConfig.toCljMap();
        return (boolean) killJob.invoke(coercedConfig, jobID);
    }

    public static boolean collectGarbage(PeerConfiguration peerConfig) {
        IFn collectGarbage = Clojure.var(API, CollectGarbage);
        Object coercedConfig = peerConfig.toCljMap();
        return (boolean) collectGarbage.invoke(coercedConfig);
    }

    public static boolean awaitJobCompletion(PeerConfiguration peerConfig, String jobID) {
        IFn awaitJobCompletion = Clojure.var(API, AwaitJobCompletion);
        Object coercedConfig = peerConfig.toCljMap();
        return (boolean) awaitJobCompletion.invoke(coercedConfig, jobID);
    }
}
