package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject {

	
	private Junction source;
	private Junction destination;
	private int length;
	protected int maxSpeed;
	protected int currentLimitSpeed;
	protected int pollutionLimit;
	protected Weather weather;
	protected int totalPollution;
	private List<Vehicle> vehicles;
	private MiComparatorVehiculo vComp;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		  super(id);
		  if(maxSpeed>0 && contLimit>=0 && length>0 && 
				  srcJunc!= null && destJunc !=null && weather!=null) {
			  this.source=srcJunc;
			  this.destination=destJunc;
			  this.pollutionLimit=contLimit;
			  this.length=length;
			  this.weather=weather;
			  this.vehicles = new ArrayList<Vehicle>();
			  this.vComp = new MiComparatorVehiculo();
			  this.maxSpeed= maxSpeed;
			  this.currentLimitSpeed = maxSpeed;
			  this.totalPollution=0;
			  srcJunc.addOutGoingRoad(this);
			  destJunc.addIncommingRoad(this);
		  }
		  else throw new IllegalArgumentException("Illegal arguments.");
	}



	@Override
	public JSONObject report() {
		JSONObject jo= new JSONObject();
		jo.put("id",_id);
		jo.put("speedlimit",currentLimitSpeed);
		jo.put("weather", weather.toString());
		jo.put("co2", totalPollution);
		JSONArray ja= new JSONArray();
		for(Vehicle v : vehicles) {
			ja.put(v.getId());
		}
		jo.put("vehicles", ja);
		return jo;
	}
	
	public void enter(Vehicle v) {
		if(v.getSpeed() == 0 && v.getLocation() == 0) vehicles.add(v);
		else throw new IllegalArgumentException("Illegal arguments.");
	}

	public void exit(Vehicle v) {
		vehicles.remove(v);
	}
	
	public void setWeather(Weather w) {
		if(w!= null) {
			weather=w;
		}
		else throw new IllegalArgumentException("Illegal arguments.");
	}
	
	public void addContamination(int c) {

		if(c>=0) {
			totalPollution+=c;
		}
		else throw new IllegalArgumentException("Illegal arguments.");
	}
	
	
	abstract void reduceTotalContamination() ;
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);
	
	public void advance(int currTime) {
		reduceTotalContamination();
		updateSpeedLimit();
		for(Vehicle v: vehicles) {
			int speed= calculateVehicleSpeed(v);
			v.setSpeed(speed);
			v.advance(speed);
		}
		Collections.sort(vehicles,vComp);
	}
	
	public int getLength() {
		return length;
	}
	public Junction getDest() {
		return destination;
	}
	public Junction getSrc() {
		return source;
	}
	public Weather getWeather() {
		return weather;
	}
	public int getContLimit() {
		return pollutionLimit;
	}
	public int getMaxSpeed() {
		return maxSpeed;
	}
	public int getTotalCO2() {
		return totalPollution;
	}
	public int getSpeedLimit() {
		return currentLimitSpeed;
	}
	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(vehicles);
	}
	
	
}
