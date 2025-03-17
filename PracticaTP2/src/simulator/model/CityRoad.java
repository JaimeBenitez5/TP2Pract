package simulator.model;

public class CityRoad extends Road {

	public CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		int x;
		switch (weather){
		case WINDY ,STORM:
			x=10;
			break;
		default:
			x=2;
			break;
		}
		if(totalPollution-x>=0) {
			totalPollution-=x;
		}

	}

	@Override
	void updateSpeedLimit() {
		
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		
		return ((11-v.getContClass())*getSpeedLimit())/11;
	}

}
