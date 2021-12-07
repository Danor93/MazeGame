package graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ConnectionChecker<V> {

	private GraphInterface<V> graphInterface;
	private Set<V> visited;

	public ConnectionChecker(GraphInterface<V> g) {
		graphInterface = g;
		visited = new HashSet<>();
	}

	public boolean check(V v1, V v2) {
		if (visited.contains(v1)) {/* if we were in this vertex so we return false; */
			return false;
		}
		visited.add(v1);/* add the current start to the visited set. */
		if (v1.equals(v2)) {/* check if we found the end. */
			return true;
		}
		Collection<V> childSet = graphInterface.neighbours(v1);/* make set of the childs of this vertex. */
		if (childSet != null) {
			for (V child : childSet) {/* call that function with all his childs. */

				if (child != null) {
					if (check(child, v2)) {/* if we found the end, return true. */
						return true;
					}
				}
			}
		}
		return false;
	}
}
