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
	
	

}
