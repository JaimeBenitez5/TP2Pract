package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {

	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss", "a new lightSwitchingStrategy");

	}

	@Override
	protected LightSwitchingStrategy create_instance(JSONObject data) {
		int timeslot = data.optInt("timeslot",1);
		return new MostCrowdedStrategy(timeslot);
	}

}
