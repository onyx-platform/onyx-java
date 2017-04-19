package org.onyxplatform.api.java;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

/**
 * Simple public facing user API mirroring all functionality of underlaying
 * onyx API.
 */
public class API implements OnyxNames {

    /**
     * Loads the onyx-api namespace for use by Java
     */
    static {
        IFn require = Clojure.var(CORE, Require);
        require.invoke(Clojure.read(API));
    }

    /**
     * Starts a development environment using an in-memory
     * implementation of ZooKeeper.
     * @param  EnvConfiguration envConfig     configuration object used for
     *                                          starting environment
     * @return                  object representing the started environment
     */
    public static Object startEnv(EnvConfiguration envConfig) {
        IFn startEnv = Clojure.var(API, StartEnv);
        Object config = envConfig.toMap();
        return startEnv.invoke(config);
    }

    /**
     * Starts a set of shared resources that are used across all virtual peers
     * on this machine.
     * Optionally takes a monitoring configuration map.
     * See the User Guide for details.
     * @param  PeerConfiguration peerConfig    peerConfig object used to specify
     *                                          peerGroup startup conditions
     * @return                   peerGroup object representing
     *                                   machine specific resource config
     */
    public static Object startPeerGroup(PeerConfiguration peerConfig) {
        IFn startPeerGroup = Clojure.var(API, StartPeerGroup);
        Object config = peerConfig.toMap();
        return startPeerGroup.invoke(config);
    }

    /**
     * Launches n virtual peers.
     * Each peer may be stopped by passing it to the shutdown-peer function.
     * @param  int    nPeers        number of virtual peers to start
     * @param  Object peerGroup     peerGroup configuration object specifying
     *                              peer runtime conditions
     * @return        peers object holding n virtual peers
     */
    public static Object startPeers(int nPeers, Object peerGroup) {
        IFn startPeers = Clojure.var(API , StartPeers);
        return startPeers.invoke(nPeers, peerGroup);
    }

    /**
     * Shuts down the given development environment.
     * @param  Object env           object representing environment to shutdown
     * @return        returns null
     */
    public static Object shutdownEnv(Object env) {
        IFn shutdownEnv = Clojure.var(API, ShutdownEnv);
        return shutdownEnv.invoke(env);
    }

    /**
     * Shuts down the peer group, releasing any messaging resources
     * it was holding open.
     * @param  Object peerGroup     Object representing peerGroup to shutdown
     * @return        Always null
     */
    public static Object shutdownPeerGroup(Object peerGroup) {
        IFn shutdownPeerGroup = Clojure.var(API, ShutdownPeerGroup);
        return shutdownPeerGroup.invoke(peerGroup);
    }

    /**
     * Shuts down the virtual peer, which releases all of its resources
     * and removes it from the execution of any tasks. This peer will
     * no longer volunteer for tasks. Returns null.
     * @param  Object peer          Object representing peer to be shutdown
     * @return        Always null
     */
    public static Object shutdownPeer(Object peer) {
        IFn shutdownPeer = Clojure.var(API, ShutdownPeer);
        return shutdownPeer.invoke(peer);
    }

    /**
     * Like shutdownPeer, but takes a sequence of peers as an argument,
     * shutting each down in order. Returns null.
     * @param  Object peers         An object representing the sequence of peers
     *                              to shutdown
     * @return        Always null
     */
    public static Object shutdownPeers(Object peers) {
        IFn shutdownPeers = Clojure.var(API, ShutdownPeers);
        return shutdownPeers.invoke(peers);
    }

    /**
     * Takes a peer configuration, job map, and optional monitoring config,
     * sending the job to the cluster for eventual execution. Returns a map
     * with success keyword indicating if the job was submitted to ZooKeeper.
     * The job map may contain a metadata key, among other keys described
     * in the user guide. The metadata key may optionally supply a job-id value.
     * Repeated submissions of a job with the same job-id will be treated as an
     * idempotent action. If a job has been submitted more than once,
     * the original task IDs associated with the catalog will be returned,
     * and the job will not run again, even if it has been killed or completed.
     * If two or more jobs with the same job-id are submitted, each will race
     * to write a content-addressable hash value to ZooKeeper. All subsequent
     * submitting jobs must match the hash value exactly, otherwise the
     * submission will be rejected. This forces all jobs under the same job-id
     * to have exactly the same value.
     * @param  PeerConfiguration peerConfig    peerConfig object specifying
     *                                          peer behavior
     * @param  Job               job           job object specifying job
     * @return                   Map containing started job
     */
    public static boolean submitJob(PeerConfiguration peerConfig, Job job) {
        IFn submitJob = Clojure.var(API, SubmitJob);
	Object j = job.toArray();
        Object c = peerConfig.toMap();
        return (boolean) submitJob.invoke(c, j);
    }

    /**
     * Kills a currently executing job, given its job ID. All peers executing
     * tasks for this job cleanly stop executing and volunteer to work on other jobs.
     * Task lifecycle APIs for closing tasks are invoked.
     * This job is never again scheduled for execution.
     * @param  PeerConfiguration peerConfig    peerConfig object
     * @param  String            jobID         ID of job to kill
     * @return                   true/false status dependent on job kill effort
     */
    public static boolean killJob(PeerConfiguration peerConfig, String jobID) {
        IFn killJob = Clojure.var(API, KillJob);
        Object config = peerConfig.toMap();
        return (boolean) killJob.invoke(config, jobID);
    }

    /**
     * Invokes the garbage collector on Onyx. Compresses all local replicas
     * for peers, decreasing memory usage. Also deletes old log entries from
     * ZooKeeper, freeing up disk space.
     * @param  PeerConfiguration peerConfig    peerConfig object
     * @return                   true/false status of garbage collection effort
     */
    public static boolean collectGarbage(PeerConfiguration peerConfig) {
        IFn collectGarbage = Clojure.var(API, CollectGarbage);
        Object coercedConfig = peerConfig.toMap();
        return (boolean) collectGarbage.invoke(coercedConfig);
    }

    /**
     * Blocks until job-id has had all of its tasks completed or the job is killed.
     * Returns true if the job completed successfully, false if the job was killed.
     * @param  PeerConfiguration peerConfig    peerConfig object
     * @param  String            jobID         ID of currently running job
     * @return                   true (job complete) or false (job killed)
     */
    public static boolean awaitJobCompletion(PeerConfiguration peerConfig, String jobID) {
        IFn awaitJobCompletion = Clojure.var(API, AwaitJobCompletion);
        Object config = peerConfig.toMap();
        return (boolean) awaitJobCompletion.invoke(config, jobID);
    }
}
