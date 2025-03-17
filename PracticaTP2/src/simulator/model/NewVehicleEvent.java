package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {
	
	private List<String> itinerary;
	private String id;
	private int maxSpeed;
	private int contClass;
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		  super(time);
		  this.id = id;
		  this.maxSpeed = maxSpeed;
		  this.contClass = contClass;
		  this.itinerary = itinerary;
	}
	
	@Override
	void execute(RoadMap map) {
		List<Junction> itinerary = new ArrayList<Junction>();
		for(String s : this.itinerary) {
			itinerary.add(map.getJunction(s));
		}
		Vehicle vehicle = new Vehicle(id,maxSpeed,contClass,itinerary);
		map.addVehicle(vehicle);
		vehicle.moveToNextRoad();
	}
	
	@Override
	public String toString() {
		return "New Vehicle '"+id+"'";
	}

}
