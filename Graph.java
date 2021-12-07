package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph<V> {

	private Set<V> vertices;
	private Map<V, Set<V>> edges;

	public Graph() {
		vertices = new HashSet<V>();
		edges = new HashMap<V, Set<V>>();
	}

	public void addVertex(V v) throws GraphException {
		if (vertices.contains(v)) {
			throw new GraphException("already exist");
		} else {/* add the vertex to the set. */
			vertices.add(v);
			edges.put(v, new HashSet<>());
		}
	}

	public void addEdge(V v1, V v2) throws GraphException {
		if (hasEdge(v1, v2)) {/*
								 * if there is an edge between v1 to v2 , or some of them aren't exist in
								 * vertices set i throw exception.
								 */
			throw new GraphException("This edge already exist");
		}
		if (!vertices.contains(v1) || !vertices.contains(v2)) {
			throw new GraphException("This vertex doesn't exist");
		}
		// add v2 to v1 set.
		edges.get(v1).add(v2);
		edges.get(v2).add(v1);
	}

	public boolean hasEdge(V v1, V v2) {/* check if v1 contain v2,then v2 contain v1 too. */
		if (!(edges.containsKey(v1))) {
			return true;
		}
		if (!(edges.containsKey(v2))) {
			return true;
		}
		if (edges.get(v1).contains(v2) || edges.get(v2).contains(v1)) {
			return true;
		}
		return false;
	}

	public boolean connected(V v1, V v2) throws GraphException {
		if ((!(vertices.contains(v1))) || (!(vertices.contains(v2)))) {
			throw new GraphException("This vertix doest exsist in the graph");
		}

		Set<V> visited = new HashSet<V>();
		return connectedRec(v1, v2, visited);
	}

	private boolean connectedRec(V Start, V End, Set<V> visited) {
		if (visited.contains(Start)) {/* if we were in this vertex so we return false; */
			return false;
		}
		visited.add(Start);/* add the current start to the visited set. */

		if (Start.equals(End)) {/*
								 * check if we found the end.
								 */
			return true;
		}
		Set<V> Vals = edges.get(Start);/* make set of the child's of this vertex. */
		if (Vals != null) {/* call that function with all his child's. */
			for (V v : Vals) {
				if (connectedRec(v, End, visited) == true) {/* if we found the end, return true. */
					return true;
				}
			}

		}
		return false;

	}

}
