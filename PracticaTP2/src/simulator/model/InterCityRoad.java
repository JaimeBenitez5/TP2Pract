	package simulator.model;

public class InterCityRoad extends Road {

	public InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	
	}

	@Override
	void reduceTotalContamination() {
		int x=0;
		switch(weather) {
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
			currentLimitSpeed=maxSpeed/2;
		}
		else currentLimitSpeed= maxSpeed;
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		if(weather == Weather.STORM) {
			return (getSpeedLimit()*8)/10;
		}
		return getSpeedLimit();
	}

}
