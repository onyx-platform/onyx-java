package org.onyxplatform.api.java.utils;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.IPersistentMap;

import org.onyxplatform.api.java.OnyxNames;
import org.onyxplatform.api.java.OnyxMap;
import org.onyxplatform.api.java.PeerConfiguration;

import org.onyxplatform.api.java.utils.MapFns;

/**
 * The Timbre utility class handles configuration of the log level for a
 * given PeerConfiguration. Logs are based on the Timbre library, and
 * configuration is handled entirely in an IPersistentMap. The map can be used
 * to fully configure what information is displayed in the Onyx Log.
 * For more information, see https://github.com/ptaoussanis/timbre
 * This utility class also contains methods for passing messages
 */
public class Timbre implements OnyxNames {


    /**
     * Sets the log configuration for the given PeerConfiguration to the given
     * log configuration map. This map should contain key value pairs in the
     * form specified at: https://github.com/ptaoussanis/timbre
     * @param  peerConfig    A PeerConfiguration that will be updated with the new log configuration
     * @param  logMap        An IPersistentMap containing log configuration information
     * @return               updated PeerConfiguration
     */
    public static PeerConfiguration configure(PeerConfiguration peerConfig,
                                                 IPersistentMap logMap){
        return (PeerConfiguration) peerConfig.addObjectParameter(TIMBRE_LOG_KEY, logMap);
    }

    /**
     * Sets the log configuration for the given PeerConfiguration to the
     * map loaded from the EDN resource at the given path. This map should
     * contain key value pairs in the form specified at:
     * https://github.com/ptaoussanis/timbre
     * @param  peerConfig    PeerConfiguration to be updated with the new log configuration
     * @param  configPath    String path to the file containing the EDN map configuration
     * @return                updated PeerConfiguration
     */
    public static PeerConfiguration configure(PeerConfiguration peerConfig,
                                                 String configPath){
        OnyxMap configObject = MapFns.fromResources(configPath);
        IPersistentMap logMap = configObject.toMap();
        return configure(peerConfig, logMap);
    }

    /**
     * Passes the contents as an info level message to the log.
     * @param message An object containing an info level message to be passed to the log
     */
    public static void info(Object message){
        IFn requireFn = Clojure.var(CORE, Require);
        requireFn.invoke(Clojure.read(TIMBRE));
        IFn messageFn = Clojure.var(TIMBRE, MessageFn);
        messageFn.invoke(message);
    }

}
