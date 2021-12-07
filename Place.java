package graph;

public class Place {

	private int x;
	private int y;

	public Place(int x, int y, int bound) {
		if (x < 0 || x > bound - 1 || y < 0 || y > bound - 1) {
			throw new IllegalArgumentException();
		}
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Place)) {
			return false;
		}
		Place place = (Place) obj;
		return this.x == place.x && this.y == place.y;
	}

	@Override
	public int hashCode() {/* use primary numbers to make the new hashCode. */
		return x * 13 + y * 17;
	}
}
