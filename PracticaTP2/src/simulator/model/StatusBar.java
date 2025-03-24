package simulator.model;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.Border;

import simulator.control.Controller;

public class StatusBar extends JPanel implements TrafficSimObserver {

	private Controller ctrl;
	private JLabel time;
	private JLabel eventAdded;
	
	StatusBar(Controller ctrl){
		this.ctrl= ctrl;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setVisible(true);
		this.time= new JLabel("0");
		this.eventAdded= new JLabel("");
		this.add(new JLabel("Time: "));
		this.add(time);
		this.add(Box.createRigidArea(new Dimension(150,40)));
		this.add(new JSeparator());
		this.add(new JLabel("Event added "));
		this.add(eventAdded);
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}
	
}
