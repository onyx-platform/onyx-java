package org.onyxplatform.api.java;

/**
 * Abstracts use of and centralizes location of onyx keywords.
 * This interface is implemented by any base classes which
 * uses onyx-api via IFn interop.
 */
public interface OnyxNames {


	/**
	 * Interop
	 */
	public static final String CORE 				= "clojure.core";
	public static final String INTEROP 				= "onyx.interop";
	public static final String API 					= "onyx.api";

	public static final String Require 				= "require";
	public static final String Keyword 				= "keyword";

	/**
	 * Environment and Peer Configuration
	 */
	public static final String EnvConfig 			= "env-config";
	public static final String PeerConfig 			= "peer-config";

	/**
	 * Onyx Components
	 */
	public static final String OnyxTaskScheduler	= "task-scheduler";
	public static final String OnyxWorkflow			= "workflow";
	public static final String OnyxCatalog			= "catalog";
	public static final String OnyxLifecycles		= "lifecycles";
	public static final String OnyxFlowConditions	= "flow-conditions";
	public static final String OnyxWindows			= "windows";
	public static final String OnyxTriggers			= "triggers";

	/**
	 * Environment and Peer Control
	 */
	public static final String StartEnv 			= "start-env";
	public static final String ShutdownEnv 			= "shutdown-env";

	public static final String StartPeers 			= "start-peers";
	public static final String ShutdownPeer			= "shutdown-peer";
	public static final String ShutdownPeers 		= "shutdown-peers";

	public static final String StartPeerGroup 		= "start-peer-group";
	public static final String ShutdownPeerGroup 	= "shutdown-peer-group";

	/*
	Job Control
	 */
	public static final String SubmitJob 			= "submit-job";
	public static final String KillJob 				= "kill-job";
	public static final String AwaitJobCompletion 	= "await-job-completion";
	public static final String CollectGarbage 		= "gc";

	/**
	 * Generic TypeCasting
	 */
	public static final String CastTypes 			= "cast-types";

	/**
	 * Type Coercion
	 */
	public static final String CoerceWorkflow 		= "coerce-workflow";
	public static final String CoerceCatalog 		= "coerce-catalog";
	public static final String CoerceLifecycles 	= "coerce-lifecycles";
	public static final String CoerceFlowConditions = "coerce-flow-conditions";
	public static final String CoerceWindows 		= "coerce-windows";
	public static final String CoerceTriggers 		= "coerce-triggers";
}
