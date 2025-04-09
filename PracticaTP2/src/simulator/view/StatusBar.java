package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Collection;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.Border;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {

	private Controller ctrl;
	private JLabel time;
	private JLabel eventAdded;
	private List<Event> e;
	
	StatusBar(Controller ctrl){
		ctrl.addObserver(this);
		this.ctrl= ctrl;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setVisible(true);
		this.time= new JLabel("0");
		this.eventAdded= new JLabel("Welcome");
		this.add(new JLabel("Time: "));
		this.add(time);
		this.add(Box.createRigidArea(new Dimension(150,40)));
		this.add(new JSeparator());
		this.add(new JLabel("Event added: "));
		this.add(eventAdded);
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		this.time.setText(time+"");
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		this.eventAdded.setText(e.toString());
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		this.time.setText(time+"");
		
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}
	
}
