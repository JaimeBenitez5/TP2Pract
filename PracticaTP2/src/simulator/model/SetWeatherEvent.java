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

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Change CO2 class: [");
		for (Pair<String, Weather> p : ws) {
			s.append("(").append(p.getFirst()).append(",").append(p.getSecond().toString()).append(")");
			if (!p.equals(ws.get(ws.size()-1))) s.append(",");
		}
		
		s.append("]");
		return s.toString();
	}
}
