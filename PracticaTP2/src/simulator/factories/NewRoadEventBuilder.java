package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event> {

	public NewRoadEventBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}

	@Override
	protected Event create_instance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		String src = data.getString("src");
		String dest = data.getString("dest");
		int length = data.getInt("length");
		int co2lim = data.getInt("co2limit");
		int maxSpeed = data.getInt("maxspeed");
		Weather w = Weather.valueOf(data.getString("weather"));
		return createEvent(time,id,src,dest,length,co2lim,maxSpeed,w);
	}

	abstract Event createEvent(int time, String id, String src, String dest, int length, int co2lim, int maxSpeed, Weather w);
}
