package org.onyxplatform.api.java;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.IPersistentMap;
import clojure.lang.PersistentArrayMap;

/**
 * Simple public facing user API mirroring all functionality of underlaying
 * onyx API. This is a static utility class that should not be used as an
 * Object, but should instead be used on objects.
 */
public class API implements OnyxNames {

    /**
    which are referenced in generated workflows (currently onyx-java.instance.bind).
    This solves class-not-found exceptions when they are obliquely referenced in a Job.
    */
    private final static IFn bind;

    /**
     * Loads the required namespaces for use in Java
     */
    static {
        IFn require = Clojure.var(CORE, Require);
        require.invoke(Clojure.read(API));

	// instance binding
        require.invoke(Clojure.read(INSTANCE_BIND));
        bind = Clojure.var(INSTANCE_BIND, Method);
    }

    /**
     * Starts a development environment using an in-memory
     * implementation of ZooKeeper.
     * @param  envConfig     configuration object used for starting environment
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
     * @param  peerConfig    peerConfig object used to specify peerGroup startup conditions
     * @return    peerGroup object representing machine specific resource config
     */
    public static Object startPeerGroup(PeerConfiguration peerConfig) {
        IPersistentMap c = peerConfig.toMap();
        IFn sPeerGroup = Clojure.var(API, StartPeerGroup);
        return sPeerGroup.invoke(c);
    }

    /**
     * Launches n virtual peers.
     * Each peer may be stopped by passing it to the shutdown-peer function.
     * @param  nPeers        number of virtual peers to start
     * @param  peerGroup     peerGroup configuration object specifying peer runtime conditions
     * @return        peers object holding n virtual peers
     */
    public static Object startPeers(int nPeers, Object peerGroup) {
        IFn startPeers = Clojure.var(API , StartPeers);
        return startPeers.invoke(nPeers, peerGroup);
    }

    /**
     * Shuts down the given development environment.
     * @param  env    object representing environment to shutdown
     * @return        null
     */
    public static Object shutdownEnv(Object env) {
        IFn shutdownEnv = Clojure.var(API, ShutdownEnv);
        return shutdownEnv.invoke(env);
    }

    /**
     * Shuts down the peer group, releasing any messaging resources
     * it was holding open.
     * @param  peerGroup     Object representing peerGroup to shutdown
     * @return        null
     */
    public static Object shutdownPeerGroup(Object peerGroup) {
        IFn shutdownPeerGroup = Clojure.var(API, ShutdownPeerGroup);
        return shutdownPeerGroup.invoke(peerGroup);
    }

    /**
     * Shuts down the virtual peer, which releases all of its resources
     * and removes it from the execution of any tasks. This peer will
     * no longer volunteer for tasks. Returns null.
     * @param  peer   Object representing peer to be shutdown
     * @return        null
     */
    public static Object shutdownPeer(Object peer) {
        IFn shutdownPeer = Clojure.var(API, ShutdownPeer);
        return shutdownPeer.invoke(peer);
    }

    /**
     * Like shutdownPeer, but takes a sequence of peers as an argument,
     * shutting each down in order. Returns null.
     * @param  peers  An object representing the sequence of peers to shutdown
     * @return        null
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
     * @param  peerConfig    peerConfig object specifying peer behavior
     * @param  job           job object specifying job
     * @return                IPersistentMap containing the started job object
     */
    public static IPersistentMap submitJob(PeerConfiguration peerConfig, Job job) {
        IFn submitJob = Clojure.var(API, SubmitJob);
        Object c = peerConfig.toMap();
	Object j = job.toArray();
        return (IPersistentMap) submitJob.invoke(c, j);
    }


    /**
     * Kills a currently executing job, given its job ID. All peers executing
     * tasks for this job cleanly stop executing and volunteer to work on other jobs.
     * Task lifecycle APIs for closing tasks are invoked.
     * This job is never again scheduled for execution.
     * @param  peerConfig    peerConfig object
     * @param  jobID         ID of job to kill
     * @return               true/false status dependent on job kill effort
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
     * @param  peerConfig    peerConfig object
     * @return              true/false status of garbage collection effort
     */
    public static boolean gc(PeerConfiguration peerConfig) {
        IFn collectGarbage = Clojure.var(API, GC);
        Object coercedConfig = peerConfig.toMap();
        return (boolean) collectGarbage.invoke(coercedConfig);
    }

    /**
     * Blocks until job-id has had all of its tasks completed or the job is killed.
     * Returns true if the job completed successfully, false if the job was killed.
     * @param  peerConfig    peerConfig object
     * @param  jobID         ID of currently running job
     * @return               true (job complete) or false (job killed)
     */
    public static boolean awaitJobCompletion(PeerConfiguration peerConfig, String jobID) {
        IFn awaitJobCompletion = Clojure.var(API, AwaitJobCompletion);
        Object config = peerConfig.toMap();
        return (boolean) awaitJobCompletion.invoke(config, jobID);
    }
}
