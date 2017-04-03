package org.onyxplatform.api.java;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.PersistentVector;
import clojure.lang.PersistentHashMap;
import java.util.Map;

/**
 * A workflow is the structural specification of an Onyx program.
 * Its purpose is to articulate the paths that data flows through
 * the cluster at runtime. It is specified via a directed, acyclic graph.
 * The workflow representation is a vector of vectors. Each inner vector
 * contains exactly two elements, which are keywords. The keywords represent
 * nodes in the graph, and the vector represents a directed edge from the
 * first node to the second.
 */
public final class Workflow implements OnyxNames
{

	protected PersistentVector edges;

    static {
		IFn requireFn = Clojure.var(CORE, Require);
		requireFn.invoke(Clojure.read(INTEROP));
    }

	/**
	 * Creates a new Workflow object with an empty set of edges.
	 * @return new Workflow object
	 */
	public Workflow() {
		edges = PersistentVector.EMPTY;
	}

	/**
	 * Creates a new Workflow object using a set of edges from a previous
	 * workflow.
	 * @param  Workflow wf            existing Workflow containing edges to use
	 * @return          new Workflow object
	 */
	private Workflow(Workflow wf) {
		edges = wf.edges;
	}

	/**
	 * Adds an edge to the workflow edges content vector.
	 * An edge is a pair of strings, where the first string is the
	 * name of the independent task and the second
	 * string is the name of the dependent task.
	 * @param String srcTask name of independent task
	 * @param String dstTask name of dependent task
	 */
	public void addEdge(String srcTask, String dstTask) {
    	PersistentVector pair = PersistentVector.create(srcTask, dstTask);
		edges = edges.cons(pair);
	}

	/**
	 * Returns a Java array representation of the Workflow edges content vector.
	 * This method does not alter the existing PersistentVector edge container.
	 */
	public Object[] graph() {
		return edges.toArray();
	}

	/**
	 * Coerces Workflow object edge contents into proper onyx workflow.
	 * Returns the onyx representation without altering the existing edge variable.
	 * @return [description]
	 */
	public PersistentVector cljGraph() {
		IFn coerceWorkflow = Clojure.var(INTEROP, CoerceWorkflow);
		return (PersistentVector) coerceWorkflow.invoke(edges);
	}
}
