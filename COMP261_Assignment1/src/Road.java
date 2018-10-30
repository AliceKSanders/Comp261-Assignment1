import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Road {
	private int roadId;
	private int type;
	private String label;
	private String city;
	private int oneway;
	private int speed;
	private int roadClass;
	private int notForCar;
	private int notForPede;
	private int notForBicy;
	
	private List<Segment> segmentList = new ArrayList<>();
	private Color current = Color.WHITE;
	
	public Road(int roadId, int type, String label, String city, int oneway, int speed, int roadClass, int notForCar,
			int notForPede, int notForBicy) {
		
		this.roadId = roadId;
		this.type = type;
		this.label = label;
		this.city = city;
		this.oneway = oneway;
		this.speed = speed;
		this.roadClass = roadClass;
		this.notForCar = notForCar;
		this.notForPede = notForPede;
		this.notForBicy = notForBicy;
	}
	
	public int getRoadId() { return roadId; }
	
	public String getRoadType() { return this.label; }
	
	public List<Segment> getSegments() { return this.segmentList; }
	
	public void addSegment(Segment s){
		this.segmentList.add(s);
	}
	
//	//Method to highlight segment onSearch
//	public void hightlightSeg() {
//		current = Color.RED;
//		System.out.println("Change Color Segment");
//	}
	
}
