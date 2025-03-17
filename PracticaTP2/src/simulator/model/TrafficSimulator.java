package simulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.json.JSONObject;

public class TrafficSimulator implements Observable<TrafficSimObserver> {

    RoadMap roadMap;
    Queue<Event> events;
    int time;
    List<TrafficSimObserver> observers ;

    public TrafficSimulator() {
        roadMap = new RoadMap();
        events = new PriorityQueue<Event>();
        time = 0;
        observers= new ArrayList<>();
    }

    public void addEvent(Event e) {
    	events.add(e);
    	for(TrafficSimObserver t: observers) {
    		t.onEventAdded(roadMap, events, e, time);
    	}
    }
    
    public void advance() {
    	++time;
    	while(!events.isEmpty() && events.peek().getTime() == time) {
    		events.poll().execute(roadMap);
    	}
    	roadMap.advanceJunctions(time);
    	roadMap.advanceRoads(time);
    	for(TrafficSimObserver t: observers) {
    		t.onAdvance(roadMap,events,time);
    	}
    }
    
    public void reset() {
    	events.clear();
    	time = 0;
    	roadMap.reset();
    	for(TrafficSimObserver t: observers) {
    		t.onReset(roadMap, events, time);
    	}
    }
    
    public JSONObject report() {
    	JSONObject jo = new JSONObject();
    	jo.put("time", time);
    	jo.put("state",roadMap.report());
    	return jo;
    }

	@Override
	public void addObserver(TrafficSimObserver o) {
		observers.add(o);
		for(TrafficSimObserver t: observers) {
    		t.onRegister(roadMap,events,time);
    	}
		
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		observers.remove(o);
	}
}