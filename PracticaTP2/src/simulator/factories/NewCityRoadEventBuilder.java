package simulator.factories;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder {

	public NewCityRoadEventBuilder() {
		super("new_city_road", "a new city road");
	}

	@Override
	protected Event createEvent(int time, String id, String src, String dest, int length, int co2lim, int maxSpeed, Weather w){
		return new NewCityRoadEvent(time,id,src,dest,length,co2lim,maxSpeed,w);
	}

}
