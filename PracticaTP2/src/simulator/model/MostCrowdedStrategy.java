package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	
	private int timeSlot;
	
	public MostCrowdedStrategy(int timeSlot){
		if(timeSlot <= 0) throw new IllegalArgumentException("Illegal arguments.");
		this.timeSlot = timeSlot;
	}
	
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		if(roads.size()==0) return -1; 
		else if(currGreen == -1) return findLongestQueue(qs, 0);
		else if(currTime - lastSwitchingTime < timeSlot) return currGreen;
		else return findLongestQueue(qs, currGreen+1%roads.size());
	}
	
	private int findLongestQueue(List<List<Vehicle>> qs, int ini) {
		int max = 0;
		int maxI = 0;
		for(int i = ini; i<qs.size();++i) {
			if(max < qs.get(i).size()) {
				max = qs.get(i).size();
				maxI = i;
			}
		}
		return maxI;
	}
}
