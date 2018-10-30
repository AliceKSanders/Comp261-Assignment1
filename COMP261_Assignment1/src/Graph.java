import java.util.ArrayList;
import java.util.List;

//Adjacency List

public class Graph {
	private List<Node> nodeList;
	private List<Road> roadList;
	
	public Graph() {
		this.nodeList = new ArrayList<>();
		this.roadList = new ArrayList<>();
	}
	
	public Node getNodeWithId(int id) {
		for(Node node : this.nodeList) {
			if(id == node.getId()) {
				return node;
			}
		}
		return null;
	}
	
	public Road getRoadWithId(int roadId) {
		for(Road road : this.roadList) {
			if(roadId == road.getRoadId()) {
				return road;
			}
		}
		return null;
	}
	
	public int getSegmentCount() {
		int sum = 0;
		for(Road road: this.roadList) {
			sum += road.getSegments().size();
		}
		return sum;
	}
	
	public List<Node> getNodes() { return this.nodeList; }
	
	public List<Road> getRoads() { return this.roadList; }
	
	public void addNode(Node n) {
		this.nodeList.add(n);
	}
	
	public void addRoad(Road r) {
		this.roadList.add(r);
	}
}
