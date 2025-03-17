package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {
	
	private Factory<LightSwitchingStrategy> lssFactory;
	private Factory<DequeuingStrategy> dqsFactory;

	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super("new_junction", "a new junction");
		this.lssFactory = lssFactory;
		this.dqsFactory = dqsFactory;
	}

	@Override
	protected Event create_instance(JSONObject data) {
		int time = data.getInt("time");
		String id= data.getString("id");
		JSONArray ja = data.getJSONArray("coor");
		JSONObject strategy = data.getJSONObject("ls_strategy");
		LightSwitchingStrategy lss = lssFactory.create_instance(strategy);
		strategy = data.getJSONObject("dq_strategy");
		DequeuingStrategy dqs = dqsFactory.create_instance(strategy);
		return new NewJunctionEvent(time, id, lss, dqs, ja.getInt(0), ja.getInt(1));
	}

}
