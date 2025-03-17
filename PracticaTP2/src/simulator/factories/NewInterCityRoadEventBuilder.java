package simulator.factories;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder {

	public NewInterCityRoadEventBuilder() {
		super("new_inter_city_road", "a new inter city road");
		
	}

	@Override
	protected Event createEvent(int time, String id, String src, String dest, int length, int co2lim, int maxSpeed, Weather w) {
		return new NewInterCityRoadEvent(time,id,src,dest,length,co2lim,maxSpeed,w);
	}

}
