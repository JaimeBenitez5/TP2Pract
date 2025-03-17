package simulator.model;

import java.util.*;


import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {

	private List<Road> inRoads;
	private Map<Junction,Road> outRoadsByJunction;
	private List<List<Vehicle>> queues;
	private Map<Road,List<Vehicle>> queuesByRoad;
	private int greenLightIndex;
	private int lastSwitchTime;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor;
	private int yCoor;

	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(id);
		if(lsStrategy == null || dqStrategy == null|| xCoor<0 || yCoor <0) 
			throw new IllegalArgumentException("Illegal arguments.");
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		inRoads = new LinkedList<Road>();
		outRoadsByJunction = new HashMap<Junction, Road>();
		queues = new ArrayList<List<Vehicle>>();
		queuesByRoad = new HashMap<Road,List<Vehicle>>();  
		greenLightIndex=-1;
	}

	@Override
	void advance(int time) {
		if(greenLightIndex != -1) {
			List<Vehicle> q = dqStrategy.dequeue(queues.get(greenLightIndex));
			for(Vehicle v : q) {
				v.moveToNextRoad();
				this.queues.get(greenLightIndex).remove(v);
			}
		}
		int aux= greenLightIndex;
		greenLightIndex = lsStrategy.chooseNextGreen(inRoads, queues, greenLightIndex, lastSwitchTime, time);
		if(aux!=greenLightIndex)lastSwitchTime = time;
	}

	@Override
	public JSONObject report() {
		JSONObject jo= new JSONObject();
		jo.put("id", _id);
		if(greenLightIndex != -1)jo.put("green", inRoads.get(greenLightIndex).getId());
		else jo.put("green", "none");
		JSONArray roads = new JSONArray();
		for(Road r: inRoads) {
			JSONObject lista=new JSONObject();
			lista.put("road", r.getId());
			JSONArray vehicles = new JSONArray();
			for(Vehicle v: queuesByRoad.get(r)) {
				vehicles.put(v.getId());
			}
			lista.put("vehicles",vehicles);
			roads.put(lista);
		}
		jo.put("queues", roads);

		return jo;
	}

	void addIncommingRoad(Road r) {
		if(r.getDest().equals(this)) {
			inRoads.add(r);
			List<Vehicle> lv = new ArrayList<Vehicle>();
			queues.add(lv);
			queuesByRoad.put(r, lv);		
		}

	}

	void addOutGoingRoad(Road r) {
		if(r.getSrc().equals(this)) {
			outRoadsByJunction.put(r.getDest(),r);
		}
	}

	void enter(Vehicle v) {
		queuesByRoad.get(v.getRoad()).add(v);
	}

	Road roadTo(Junction j) {
		return outRoadsByJunction.get(j);
	}



}
