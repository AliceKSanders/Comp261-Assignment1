import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//Alice Sanders
//300407621

public class RoadMap extends GUI {

	public RoadMap() {

	}
	
	private final int nodeSize = 10;
	
	private int roadTypeId;
	private int nodeTypeId;

	
	//Map<Integer, Road> roadsList = new HashMap<>();
	Map<Integer, Segment> segments;
	Graph graph = new Graph();

	private Graphics g;
	public Location origin;
	public double scale;
	private List<Road> roadSearch;
	
	//Create a new roadtrie
	static final RoadTrie trie = new RoadTrie();

	@Override
	protected void redraw(Graphics g) {
		
		// TODO Auto-generated method stub
		g.setColor(Color.BLACK);

		for (Node node : graph.getNodes()) {
			if(node.getId()==nodeTypeId) {
				g.setColor(Color.RED);
				
			}
			else {
				g.setColor(Color.BLACK);
			}
			node.draw(g, this.origin, scale);
		}
		
		//g.setColor(Color.BLACK);

		for (Road r : graph.getRoads()) {
			for (Segment s : r.getSegments()) {
				if(s.getId()==roadTypeId) {
					g.setColor(Color.CYAN);
					System.out.println("Color");
					//s.draw(g, this.origin, scale);
				}
				else {
					g.setColor(Color.WHITE);
				}
				s.draw(g, this.origin, scale);
			}
			
		}
		
	}
	
	//Scale and origin Method
	private void setDimensions() {
		double north = Double.NEGATIVE_INFINITY;
		double south = Double.POSITIVE_INFINITY;
		double west = Double.POSITIVE_INFINITY;
		double east = Double.NEGATIVE_INFINITY;
		
		for (Node n : graph.getNodes()) {
			if (n.nodeLoc.x < west) {
				west = n.nodeLoc.x;
			}
			if (n.nodeLoc.x > east) {
				east = n.nodeLoc.x;
			}
			if (n.nodeLoc.y > north) {
				north = n.nodeLoc.y;
			}
			if (n.nodeLoc.y < south) {
				south = n.nodeLoc.y;
			}
		}
		origin = new Location(west, north);
		
		//Got this scale calculation hint from a friend. 
		scale = Math.min(400 / (east - west), 400 / (north - south));
	}
	//Reset 
	//new origin=(origin.x+dx, origin.y+dy);
	

	@Override
	protected void onClick(MouseEvent e) {
		// TODO Auto-generated method stub

		Location mousePosition = Location.newFromPoint(e.getPoint(), origin, scale);
		
		// Get the X and Y position
		int clickX = e.getX();
		int clickY = e.getY();
		
		//The set currently doesn't remove duplicates.
		//Need to fix it to not print out the road of itself? 
		
		for (Node n : graph.getNodes()) {
			if (n.nodeLoc.isClose(mousePosition, 0.1)) {
				nodeTypeId = n.getId();
				
				//This is a method in Node which changes the color of the selected Node
				//Change the color of the node.				
				//n.hightlight();

				//Print the text
				getTextOutputArea().append("ID of intersection: " + n.getId() +"\n");
				
				//Using a set removes duplicates.
				Set<Road> roads = new HashSet<>();
				//List<String> roads = new ArrayList<>();
				List<Segment> outgoing = n.getOutgoingList();
				List<Segment> incoming = n.getIncomingList();
				
				for(Segment s  : incoming) {
					roads.add(graph.getRoadWithId(s.getId()));
				}
				
				for (Segment s : outgoing) {
					//s.getId();
					//graph.getRoads();
					//roads.add(graph.getRoads(s.getId()));
					roads.add(graph.getRoadWithId(s.getId()));
				}
				
				for(Road r : roads) {
					//getTextOutputArea().append("Roads at intersection: " + r.getRoadId() +"\n");
					getTextOutputArea().append("Road names at intersection: " + r.getRoadType() +"\n");
				}

				break;
			}
		}
	}

	@Override
	protected void onSearch() {
		//Version Core
		String text = getSearchBox().getText();
		//for (Road road : roadsList.values()) {
			for (Road road : this.graph.getRoads()) {
				
				//System.out.println("Printing");
			 if (text.equals(road.getRoadType())) {
				 roadTypeId = road.getRoadId();
				 System.out.println(roadTypeId);
				 getTextOutputArea().append("Road name: " + road.getRoadType() +"\n");
				 getTextOutputArea().append("Road Id: " + road.getRoadId() +"\n");
				//break;
				//System.out.println("K road");
				//roadSearch.add(road);
			}
		}
	}
		//Trie Struct	


	@Override
	protected void onMove(Move m) {
		// TODO Auto-generated method stub
		// Make Case statements
		// NORTH
		// SOUTH
		// EAST
		// WEST
		// ZOOM_IN
		// ZOOM_OUT

		switch (m) {

		case NORTH:
			// origin = new Location(origin.x, origin.y + 1);
			// System.out.println("North");
			// break;
			origin = new Location(origin.x, origin.y + 1);
			break;

		case EAST:
			origin = new Location(origin.x + 1, origin.y);
			System.out.println("East");
			break;

		case SOUTH:
			origin = new Location(origin.x, origin.y - 1);
			System.out.println("South");
			break;

		case WEST:
			origin = new Location(origin.x - 1, origin.y);
			System.out.println("West");
			break;

		case ZOOM_IN:
			System.out.println("Zoom in");
			scale *= 1.5;

			break;
		case ZOOM_OUT:
			//scale -= scale*0.1;
			System.out.println("Zoom out");
			scale /= 1.5;
			break;
		default:
			break;
		}

	}

	@Override
	protected void onLoad(File nodes, File roads, File segments, File polygons) {
		loadNodes(nodes);
		loadRoads(roads);
		loadSegments(segments);
		System.out.println("Data loading complete.");
		setDimensions();
		redraw();
		System.out.println(this.graph.getRoads().size());
		System.out.println(this.graph.getSegmentCount());
	}

	
	protected void loadNodes(File nodes) {
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(nodes);
			br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				String[] info = line.split("\t");
				int id = Integer.parseInt(info[0]);
				double lat = Double.parseDouble(info[1]);
				double longi = Double.parseDouble(info[2]);
				Node n = new Node(id, lat, longi);
				this.graph.addNode(n);
				// nodeMap.put(id, n);
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void loadRoads(File roads) {
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(roads);
			br = new BufferedReader(fr);
			br.readLine();
			String line;
			while ((line = br.readLine()) != null) {
				String[] info = line.split("\t");
				int roadid = Integer.parseInt(info[0]);
				int type = Integer.parseInt(info[1]);
				String label = info[2];
				String city = info[3];
				int oneway = Integer.parseInt(info[4]);
				int speed = Integer.parseInt(info[5]);
				int roadclass = Integer.parseInt(info[6]);
				int notforcar = Integer.parseInt(info[7]);
				int notforpede = Integer.parseInt(info[8]);
				int notforbicy = Integer.parseInt(info[9]);
				Road r = new Road(roadid, type, label, city, oneway, speed, roadclass, notforcar, notforpede,
						notforbicy);
				
				this.graph.addRoad(r);
				// roadsList.put(roadid, r);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void loadSegments(File segments) {
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(segments);
			br = new BufferedReader(fr);
			br.readLine();
			String line;
			while ((line = br.readLine()) != null) {
				String[] info = line.split("\t");
				int id = Integer.parseInt(info[0]);
				double length = Double.parseDouble(info[1]);
				int nodeid1 = Integer.parseInt(info[2]);
				int nodeid2 = Integer.parseInt(info[3]);
				List<Double> coords = new ArrayList<Double>();
				for (int i = 4; i < info.length; i++) {
					coords.add(Double.parseDouble(info[i]));
				}
				Segment s = new Segment(id, length, nodeid1, nodeid2, coords);
				this.graph.getRoadWithId(id).addSegment(s);
				this.graph.getNodeWithId(nodeid1).addIncoming(s);
				this.graph.getNodeWithId(nodeid2).addOutgoing(s);
				// segmentsList.add(s);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new RoadMap();
	}

}
