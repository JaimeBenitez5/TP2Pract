package simulator.model;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;

import simulator.control.Controller;

public class ControlPanel extends JPanel implements TrafficSimObserver, ActionListener {

	Controller ctrl;
	JFileChooser ficheros;// no se hacer lo de los ficheros
	
	ControlPanel (Controller ctrl){
		this.ctrl= ctrl;
		initializeGui();
	}
	
	private void initializeGui() {
		JToolBar toolB = new JToolBar();
		this.add(toolB, BorderLayout.PAGE_START);
		JButton load = new JButton();
		load.setActionCommand("load");
		load.addActionListener(this);
		load.setIcon(new ImageIcon("resources/icons/open.png"));
		load.setFocusable(false);
		toolB.add(load);
		
		toolB.addSeparator();
		
		JButton co2 = new JButton();
		co2.setActionCommand("co2");
		co2.addActionListener(this);
		co2.setIcon(new ImageIcon("resources/icons/co2class.png"));
		co2.setFocusable(false);
		toolB.add(co2);
		
		JButton weather = new JButton();
		weather.setActionCommand("weather");
		weather.addActionListener(this);
		weather.setIcon(new ImageIcon("resources/icons/weather.png"));
		weather.setFocusable(false);
		toolB.add(weather);
		
		toolB.addSeparator();
		
		JButton run = new JButton();
		run.setActionCommand("run");
		run.addActionListener(this);
		run.setIcon(new ImageIcon("resources/icons/run.png"));
		run.setFocusable(false);
		toolB.add(run);
		
		JButton stop = new JButton();
		stop.setActionCommand("stop");
		stop.addActionListener(this);
		stop.setIcon(new ImageIcon("resources/icons/stop.png"));
		stop.setFocusable(false);
		toolB.add(stop);
		
		JLabel t= new JLabel("Ticks: ");
		toolB.add(t);
		
		JSpinner spinner= new JSpinner(new SpinnerNumberModel(10,1,10000,1));
		spinner.setToolTipText("Simulation steps to run: 1-10000");
		spinner.setMaximumSize(new Dimension(80,40));
		spinner.setMinimumSize(new Dimension(80,40));
		spinner.setPreferredSize(new Dimension(80,40));
		toolB.add(spinner);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
