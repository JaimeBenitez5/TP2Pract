package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	
	private List<Junction> junctionList;
	private List<Road> roadList;
	private List<Vehicle> vehicleList;
	private Map<String,Junction> junctionMap;
	private Map<String,Road> roadMap;
	private Map<String,Vehicle> vehicleMap;
	
	RoadMap(){
		junctionList = new ArrayList<>();
        roadList = new ArrayList<>();
        vehicleList = new ArrayList<>();
        junctionMap = new HashMap<>();
        roadMap = new HashMap<>();
        vehicleMap = new HashMap<>();
	}
	
	public void advanceJunctions(int time) {
		for(Junction j : junctionList) {
			j.advance(time);
		}
	}
	
	public void advanceRoads(int time) {
		for(Road r : roadList) {
			r.advance(time);
		}
	}
	
	public void addJunction(Junction j) {
		if(!junctionMap.containsKey(j.getId())) {
			junctionList.add(j);
			junctionMap.put(j.getId(),j);
		}
		else throw new IllegalArgumentException("Id already in use.");
	}
	
	public void addRoad(Road r) {
		if(!roadMap.containsKey(r.getId())&& 
				junctionMap.containsKey(r.getDest().getId())&&
					junctionMap.containsKey(r.getSrc().getId())) {
			roadList.add(r);
			roadMap.put(r.getId(),r);
		}
		
		else throw new IllegalArgumentException("Illegal arguments.");
	
	}
	
	public void addVehicle(Vehicle v) {
		if(!vehicleMap.containsKey(v.getId()) && validItinerary(v.getItinerary())) {
			vehicleList.add(v);
			vehicleMap.put(v.getId(), v);
		}
		else throw new IllegalArgumentException("Id already in use.");
	}
	
	private boolean validItinerary(List<Junction> itinerary) {
		for(int i = 0; i < itinerary.size()-1; ++i) {
			//if(!itinerary.get(i).exists(junctionList) || !itinerary.get(i+1).exists(junctionList)) return false;
			if(itinerary.get(i).roadTo(itinerary.get(i+1)) == null) return false;		
		}
		return true;
	}


	public Junction getJunction(String id) {
		return junctionMap.get(id);
	}
	
	public Road getRoad(String id) {
		return roadMap.get(id);
	}
	
	public Vehicle getVehicle(String id) {
		return vehicleMap.get(id);
	}
	
	public List<Junction> getJunctions(){
		return Collections.unmodifiableList(junctionList);
	}
	
	public List<Road> getRoads(){
		return Collections.unmodifiableList(roadList);
	}
	
	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(vehicleList);
	}
	
	public void reset() {
		this.junctionList.clear();
		this.junctionMap.clear();
		this.roadList.clear();
		this.roadMap.clear();
		this.vehicleList.clear();
		this.vehicleMap.clear();
	}
	
	public JSONObject report() {
		JSONObject jo= new JSONObject();
		JSONArray junctions = new JSONArray();
		JSONArray roads = new JSONArray();
		JSONArray vehicles = new JSONArray();
        for(Junction j:junctionList) {
        	junctions.put(j.report());
        }
        jo.put("junctions", junctions);
        for(Road r : roadList) {
        	roads.put(r.report());
        }
        jo.put("roads", roads);
        for (Vehicle v : vehicleList) {
			vehicles.put(v.report());
		}
        jo.put("vehicles", vehicles);
        return jo;
	}


}
