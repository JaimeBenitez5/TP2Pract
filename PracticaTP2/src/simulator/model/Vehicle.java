package simulator.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {

	private List<Junction> itinerary;
	private int maxSpeed ;
	private int currentSpeed;
	private VehicleStatus status;
	private Road road;
	private int location;
	private int pollutionLevel;
	private int totalPollution;
	private int totalDistance;
	private int contJunction;

	protected Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		if(maxSpeed>0 && contClass>=0&& contClass<=10&&itinerary.size()>=2) {
			this.maxSpeed=maxSpeed;
			this.itinerary=Collections.unmodifiableList(itinerary);
			this.pollutionLevel=contClass;
			this.status= VehicleStatus.PENDING;
			this.contJunction = 0;
			road=null;

		}
		else throw  new IllegalArgumentException("Illegal arguments.");
	}
	
	@Override
	void advance(int time) {
		int previousLocation=location;
		if(status==VehicleStatus.TRAVELING) {
			if(location+currentSpeed< road.getLength()) {
				location+=currentSpeed;
			}
			else {
				location=road.getLength();
				itinerary.get(contJunction).enter(this);
				this.setSpeed(0);
				this.status=VehicleStatus.WAITING;
			}
			int d = location-previousLocation;
			totalDistance += d;
			totalPollution+= (d)*pollutionLevel;
			road.addContamination((d)*pollutionLevel);
		}

		//si el coche llega al final de la carretera entra a la cola de la junction
	}

	void moveToNextRoad() {
		if(status!=VehicleStatus.PENDING && status!=VehicleStatus.WAITING) {
			throw new IllegalArgumentException("Incorrect vehicle status.");
		}
		if(road!=null) road.exit(this);
		if(contJunction == itinerary.size()-1) {
			status = VehicleStatus.ARRIVED;
			this.setSpeed(0);
			road = null;
		}
		else {
			Road r = itinerary.get(contJunction).roadTo(itinerary.get(contJunction+1));
			this.road = r;
			location = 0;
			status = VehicleStatus.TRAVELING;
			contJunction++;
			road.enter(this);
		}
	}
	@Override
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("id", this._id);
		jo.put("speed", this.currentSpeed);
		jo.put("distance", this.totalDistance);
		jo.put("co2", this.totalPollution);
		jo.put("class" , this.pollutionLevel);
		jo.put("status", this.status.toString());
		if(road!=null)jo.put("road", this.road.getId());
		if(location>0 && status!=VehicleStatus.ARRIVED)jo.put("location", this.location);
		return jo;
	}

	void setSpeed(int s) {
		if(s<0) throw new IllegalArgumentException("Illegal arguments(negative speed).");
		if(status == VehicleStatus.TRAVELING) {
			if (s < maxSpeed) {
				currentSpeed = s;
			} else {
				currentSpeed = maxSpeed;
			}
		}
	}

	void setContClass(int c){
		if(c>=0 && c<=10) {
			pollutionLevel = c;
		}
		else throw  new IllegalArgumentException("Illegal arguments(contamination class not in range).");
	}

	public int getLocation() {
		return location;	
	}

	public int getSpeed() {
		return currentSpeed;
	}
	public int getMaxSpeed() {
		return maxSpeed;
	}
	public int getContClass() {
		return pollutionLevel;
	}
	public VehicleStatus getStatus(){
		return status;
	}

	public int getTotalCO2(){
		return totalPollution;
	}

	public List<Junction> getItinerary(){
		return Collections.unmodifiableList(itinerary);
	}

	public Road getRoad() {
		return road;
	}

}
