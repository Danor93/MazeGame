package graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Maze implements GraphInterface<Place> {

	private Place[][] maze;
	private Place startPlace;
	private Place endPlace;
	private int size;

	public Maze(int size, int startx, int starty, int endx, int endy) {
		maze = new Place[size][size];
		startPlace = new Place(startx, starty, size);
		endPlace = new Place(endx, endy, size);
		this.size = size;
		/* add to maze the start and end places. */
		maze[startx][starty] = startPlace;
		maze[endx][endy] = endPlace;
	}

	public boolean addWall(int x, int y) {/* add wall and check if the x and y is in the range. */
		if (x < 0 || x > size - 1 || y < 0 || y > size - 1) {
			throw new IllegalArgumentException();
		}
		if (maze[x][y] == null) {
			maze[x][y] = new Place(x, y, size);
			return true;
		}
		return false;
	}

	public String toString() {/* create string of the maze. */
		// start - S , end - E , null - '.' , wall - @ //
		StringBuilder mazeString = new StringBuilder();
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze.length; j++) {
				if (maze[i][j] != null) {
					if (maze[i][j].equals(startPlace)) {
						mazeString.append("S");
					} else {
						if (maze[i][j].equals(endPlace)) {
							mazeString.append("E");
						} else {
							mazeString.append("@");
						}
					}
				} else {
					mazeString.append(".");
				}
			}
			mazeString.append("\n");
		}
		return mazeString.toString();
	}

	public boolean isSolvable() {

		try {
			Graph<Place> graph = new Graph<>();
			Set<Place> vertices = new HashSet<>();
			graph.addVertex(startPlace);
			vertices.add(startPlace);

			graph.addVertex(endPlace);
			vertices.add(endPlace);

			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (maze[i][j] == null) {/* if we found null we add it as way we can move. */
						Place p = new Place(i, j, size);
						graph.addVertex(p);
						vertices.add(p); /* local set to use in createEdges. */
					}
				}
			}
			createEdges(graph, vertices);
			return graph.connected(startPlace, endPlace);
		} catch (GraphException e) {
			e.printStackTrace();
		}

		return false;
	}

	private void createEdges(Graph<Place> graph, Set<Place> vertices) throws GraphException {

		Iterator<Place> it = vertices.iterator();
		while (it.hasNext()) { /* move all over the vertices and edges with his neighbors. */
			Place current = it.next();
			Set<Place> neighborsSet = getNeighborsSet(current.getX(), current.getY());
			for (Place place : neighborsSet) {
				if (graph.hasEdge(current, place)) { /* continue if we got this edge already. */
					continue;
				}
				graph.addEdge(current, place);
			}
		}
	}

	private Set<Place> getNeighborsSet(int x, int y) {/* return set with all the neighbors of this place(x,y). */
		Set<Place> neighborsSet = new HashSet<>();
		if (x < 0 || x >= size || y < 0 || y >= size) {
			return null;
		}
		if (x > 0) {/* above neighbors */
			addNeighbors(neighborsSet, x - 1, y);
		}
		if (x < size - 1) {/* bottom neighbors */
			addNeighbors(neighborsSet, x + 1, y);
		}
		if (y > 0) {/* left neighbors */
			addNeighbors(neighborsSet, x, y - 1);
		}
		if (y < size - 1) {/* right neighbors */
			addNeighbors(neighborsSet, x, y + 1);
		}
		return neighborsSet;
	}

	private void addNeighbors(Set<Place> neighborsSet, int x, int y) {
		if (maze[x][y] == null) {
			neighborsSet.add(new Place(x, y, size));
		} else {
			if (maze[x][y].equals(startPlace) || maze[x][y].equals(endPlace)) {
				neighborsSet.add(maze[x][y]);
			}
		}
	}

	@Override
	public Collection<Place> neighbours(Place v) {/* return set of the neighbors of */
		return getNeighborsSet(v.getX(), v.getY());
	}
}
