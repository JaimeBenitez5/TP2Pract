package simulator.model;

import java.util.List;

import simulator.misc.Pair;


public class SetContClassEvent extends Event {
	
	List<Pair<String,Integer>> cs;

	public SetContClassEvent(int time, List<Pair<String,Integer>> cs)  {
		  super(time);
		  if(cs == null) throw new IllegalArgumentException("Illegal arguments.");
		  this.cs = cs;
	}
	
	@Override
	void execute(RoadMap map) {
		for(Pair<String,Integer> p : cs) {
			Vehicle v = map.getVehicle(p.getFirst());
			if(v == null) throw new IllegalArgumentException("Illegal arguments.");
			v.setContClass(p.getSecond());
		}

	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Change CO2 class: [");
		for (Pair<String, Integer> p : cs) {
			s.append("(").append(p.getFirst()).append(",").append(p.getSecond()).append(")");
			if (!p.equals(cs.get(cs.size()-1))) s.append(",");
		}
		
		s.append("]");
		return s.toString();
	}

}
