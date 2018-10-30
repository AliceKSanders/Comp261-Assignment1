import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Node {
	private int id;
	private double lat;
	private double longi;
	public Location nodeLoc;
	
	private Point pos;
	
	private List<Segment> incomingList;
	private List<Segment> outgoingList;
	
	//private Color current = Color.BLACK;
	
	public Node(int id, double lat, double longi) {
		this.id = id;
		this.lat = lat;
		this.longi = longi;
		nodeLoc = Location.newFromLatLon(lat, longi);
		
		this.incomingList = new ArrayList<>();
		this.outgoingList = new ArrayList<>();
	}
	
	/**
	 * 
	 * @return
	 */
	public int getId() { return this.id; }
	
	/**
	 * 
	 * @param s
	 */
	public void addIncoming(Segment s) {
		this.incomingList.add(s);
	}
	
	/**
	 * 
	 * @param s
	 */
	public void addOutgoing(Segment s) {
		this.outgoingList.add(s);
	}

	/**
	 * 
	 * @param g
	 * @param origin
	 * @param scale
	 */
	public void draw(Graphics g, Location origin, double scale) {
		Point p = nodeLoc.asPoint(origin, scale);
		//g.setColor(Color.BLACK);
		g.fillOval(p.x, p.y, 3, 3);
	}
	
//Didnt need this...
//	public void hightlight() {
//		current = Color.RED;
//		System.out.println("Change Color");
//	}
	
	public void moveUp() {
		this.pos.y += 100;
	}

	public List<Segment> getIncomingList() {
		return incomingList;
	}

	public List<Segment> getOutgoingList() {
		return outgoingList;
	}
}
