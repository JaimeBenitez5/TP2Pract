package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {

    public NewVehicleEventBuilder() {
        super("new_vehicle", "A new vehicle");
    }

    @Override
    protected void fill_in_data(JSONObject o) {
        o.put("time", "The time at which the event is executed");
        o.put("maxspeed", "The vehicle's max speed");
        o.put("currentSpeed", "The current vehicle's speed");
        o.put("status", "The current vehicle's state");
        o.put("road", "The current road where the car is");
        o.put("pollutionLevel", "The vehicle's pollution level"); 	
 
    }

    @Override
    protected Event create_instance(JSONObject data) {
        int time = data.getInt("time");
        String id = data.getString("id");
        int maxSpeed = data.getInt("maxspeed");
        int contClass = data.getInt("class");
        JSONArray ja = data.getJSONArray("itinerary");
        List<String>itinerary= new ArrayList<String>();
        for(int i=0; i<ja.length(); ++i) {
        	String juncId = ja.getString(i);
        	itinerary.add(juncId);
        }
        return new NewVehicleEvent(time, id, maxSpeed, contClass, itinerary);
    }

}