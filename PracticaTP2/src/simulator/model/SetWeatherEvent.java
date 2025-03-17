package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {
	
	List<Pair<String,Weather>> ws;

	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
		  super(time);
		  if(ws == null) throw new IllegalArgumentException("Illegal arguments.");
		  this.ws = ws;
	}
	
	@Override
	void execute(RoadMap map) {
		for(Pair<String,Weather> p : ws) {
			Road r = map.getRoad(p.getFirst());
			if(r == null) throw new IllegalArgumentException("Illegal arguments.");
			r.setWeather(p.getSecond());	
		}
	}

}
