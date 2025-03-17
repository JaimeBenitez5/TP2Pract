package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {

	public SetWeatherEventBuilder() {
		super("set_weather", "a new set weather event");
	}

	@Override
	protected Event create_instance(JSONObject data) {
		int time = data.getInt("time");
		JSONArray ja= data.getJSONArray("info");
		List<Pair<String,Weather>> ws= new ArrayList<Pair<String,Weather>>();
		for(int i=0;i< ja.length();++i) {
			JSONObject jo=ja.getJSONObject(i);
			Weather w = Weather.valueOf(jo.getString("weather"));
			Pair<String,Weather> p=new Pair<String, Weather>(jo.getString("road"),w);
			ws.add(p);
		}
		return new SetWeatherEvent(time, ws);
	}

}
