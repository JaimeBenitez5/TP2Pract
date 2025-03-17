package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

public class Controller {
	private TrafficSimulator trafficSimulator;
	private Factory<Event> eventsFactory;

	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		this.trafficSimulator = sim;
		this.eventsFactory = eventsFactory;
	}
	
	public void loadEvents(InputStream in) {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray ja = jo.getJSONArray("events");
		for(int i = 0; i<ja.length();++i) {
			Event e = this.eventsFactory.create_instance(ja.getJSONObject(i));
			trafficSimulator.addEvent(e);
		}
	}
	
	public void run(int n, OutputStream out) {
		PrintStream p = new PrintStream(out);
		
		p.println('{');
		p.println( "  \"states\": [");
		
		for (int i = 0; i < n - 1; i++) {
		    trafficSimulator.advance();
		    p.print(trafficSimulator.report());
		    p.println(",");
		}

		if (n > 0) {
		    trafficSimulator.advance();
		    p.print(trafficSimulator.report());
		}
		
		p.println();
		p.println(']');
		p.println('}');
	}
	
	void run(int n) {
		for(int i=0; i<n;++i) {
			trafficSimulator.advance();
		}
	}
	
	public void reset() {
		this.trafficSimulator.reset();
	}
	
	void addObserver(TrafficSimObserver o){
		trafficSimulator.addObserver(o);
	}
	
	void removeObserver(TrafficSimObserver o) {
		trafficSimulator.removeObserver(o);
	}
	 
	void addEvent(Event e) {
		trafficSimulator.addEvent(e);
	}
}
