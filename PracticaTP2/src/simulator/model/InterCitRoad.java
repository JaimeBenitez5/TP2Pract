package simulator.model;

public class InterCitRoad extends Road {

	public InterCitRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	void reduceTotalContamination() {
		int x=0;
		switch(weatherConditions) {
		case SUNNY:
			x=2;
			break;
		case CLOUDY:
			x=3;
			break;
		case RAINY:
			x=10;
			break;
		case WINDY:
			x=15;
			break;
		case STORM:
			x=20;
			break;
		}
		totalPollution= ((100-x)*totalPollution)/100;

	}

	@Override
	void updateSpeedLimit() {
		if(totalPollution>pollutionLimit) {
			maxSpeed=maxSpeed/2;
		}

	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		if(weatherConditions==Weather.STORM) {
			return getSpeedLimit()*8/10;
		}
		return getSpeedLimit();
	}

}
