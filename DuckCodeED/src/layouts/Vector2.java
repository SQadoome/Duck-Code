package layouts;

public class Vector2 {

	public int x = 0;
	public int y = 0;
	
	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// ---
	public static Vector2 addVectors(Vector2 vector1, Vector2 vector2) {
		int combinedX = vector1.x + vector2.x;
		int combinedY = vector1.y + vector2.y;
		return new Vector2(combinedX, combinedY);
	}
	
	public void setVector(Vector2 vector) {
		x = vector.x;
		y = vector.y;
	}
	
}
