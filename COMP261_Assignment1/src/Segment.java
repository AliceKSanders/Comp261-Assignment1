import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Segment {
	private int id;
	private double length;
	private int nodeid1;
	private int nodeid2;
	private List<Double> coords = new ArrayList<Double>();
	
	
	public Segment(int id, double length, int nodeid1, int nodeid2, List<Double> coords) {
		this.id = id;
		this.length = length;
		this.nodeid1 = nodeid1;
		this.nodeid2 = nodeid2;
		this.coords = coords;
	}
	
	public void draw(Graphics g, Location origin, double scale) {
		
		Point p1 = Location.newFromLatLon(coords.get(0), coords.get(1)).asPoint(origin, scale);
		for (int i = 2; i < coords.size(); i = i + 2) {
			Point p2 = Location.newFromLatLon(coords.get(i), coords.get(i+1)).asPoint(origin, scale);
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
			p1 = p2;
		}
	}

	public int getId() {
		return id;
	}
	
	
}
