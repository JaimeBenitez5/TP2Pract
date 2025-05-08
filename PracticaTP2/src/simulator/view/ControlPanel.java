package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class ControlPanel extends JPanel implements TrafficSimObserver, ActionListener {

	List<Vehicle> vehicles;
	List<Road> roads;
	Controller _ctrl;
	JFileChooser ficheros;
	int time ;
	JButton co2;
	JButton weather;
	JButton load;
	ChangeCO2ClassDialog co2D;
	ChangeWeatherDialog wD;
	JButton run;
	JSpinner spinner;
	JSpinner delay;
	JButton stop;
	JButton exit;
	volatile Thread _thread;
	
	
	ControlPanel (Controller ctrl){
		this._ctrl= ctrl;
		initializeGui();
		_ctrl.addObserver(this);
	}
	
	private void initializeGui() {
		this.setLayout(new BorderLayout());
		
		co2D= new ChangeCO2ClassDialog();
		wD= new ChangeWeatherDialog();
		
		JToolBar toolB = new JToolBar();
		this.add(toolB);
		
		load = new JButton();
		load.setActionCommand("load");
		load.addActionListener(this);
		load.setIcon(new ImageIcon("resources/icons/open.png"));
		load.setFocusable(false);
		toolB.add(load);
		
		toolB.addSeparator();
		
		co2 = new JButton();
		co2.setActionCommand("co2");
		co2.addActionListener(this);
		co2.setIcon(new ImageIcon("resources/icons/co2class.png"));
		co2.setFocusable(false);
		toolB.add(co2);
		
		weather = new JButton();
		weather.setActionCommand("weather");
		weather.addActionListener(this);
		weather.setIcon(new ImageIcon("resources/icons/weather.png"));
		weather.setFocusable(false);
		toolB.add(weather);
		
		toolB.addSeparator();
		
		run = new JButton();
		run.setActionCommand("run");
		run.addActionListener(this);
		run.setIcon(new ImageIcon("resources/icons/run.png"));
		run.setFocusable(false);
		toolB.add(run);
		
		stop = new JButton();
		stop.setActionCommand("stop");
		stop.addActionListener(this);
		stop.setIcon(new ImageIcon("resources/icons/stop.png"));
		stop.setFocusable(false);
		toolB.add(stop);
		
		JLabel t= new JLabel("Ticks: ");
		toolB.add(t);
		
		spinner= new JSpinner(new SpinnerNumberModel(10,1,10000,1));
		spinner.setToolTipText("Simulation steps to run: 1-10000");
		spinner.setMaximumSize(new Dimension(80,40));
		spinner.setMinimumSize(new Dimension(80,40));
		spinner.setPreferredSize(new Dimension(80,40));
		toolB.add(spinner);
		
		JLabel d = new JLabel(" Delay: ");
		toolB.add(d);
		
		delay = new JSpinner(new SpinnerNumberModel(0,0,1000,1));
		delay.setToolTipText("Delay between simulation steps: 0-1000");
		delay.setMaximumSize(new Dimension(80,40));
		delay.setMinimumSize(new Dimension(80,40));
		delay.setPreferredSize(new Dimension(80,40));
		toolB.add(delay);
		
		toolB.add(Box.createGlue());
		
		toolB.addSeparator();
		
		exit= new JButton();
		exit.setActionCommand("stop");
		exit.addActionListener(this);
		exit.setIcon(new ImageIcon("resources/icons/exit.png"));
		exit.setFocusable(false);
		toolB.add(exit);
	}
	
	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		vehicles=map.getVehicles();
		roads=map.getRoads();
		this.time=time;

	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
	

	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		vehicles=map.getVehicles();
		roads=map.getRoads();
		this.time=time;

	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==co2) {
			int botonpulsado= co2D.open(ViewUtils.getWindow(this), vehicles);
			if(botonpulsado==1) {
				Vehicle v=co2D.getVehicle();
				int co2class= co2D.getCo2Class();
				int ticks= co2D.getTicks();
				List<Pair<String,Integer>>  cs= new ArrayList<>();
				cs.add(new Pair(v.getId(),co2class));
				_ctrl.addEvent(new SetContClassEvent(ticks+time, cs));
			}
		}
		if(e.getSource()==weather) {
			int botonpulsado= wD.open(ViewUtils.getWindow(this), roads);
			if(botonpulsado==1) {
				Road r=wD.getRoad();
				Weather w= wD.getWeather();
				int ticks= wD.getTicks();
				List<Pair<String,Weather>>  ws= new ArrayList<>();
				ws.add(new Pair(r.getId(),w));
				_ctrl.addEvent(new SetWeatherEvent(ticks+time, ws));
			}
		}
		if(e.getSource()==load) {
			ficheros= new JFileChooser("resources/examples");
			int slection = ficheros.showOpenDialog(ViewUtils.getWindow(this));
			if (slection ==JFileChooser.OPEN_DIALOG){
			File file = ficheros.getSelectedFile() ;
			InputStream in;
			try {
				_ctrl.reset();
				in = new FileInputStream(file);
				_ctrl.loadEvents(in); 	
			} catch (FileNotFoundException e1) {
				
				e1.printStackTrace();
			}
			
			}

		}
		if(e.getSource()==exit) {
			int result = JOptionPane.showConfirmDialog(
				    ViewUtils.getWindow(this),
				    "¿Estás seguro de que deseas salir del programa?",
				    "Confirmar salida",
				    JOptionPane.YES_NO_OPTION,
				    JOptionPane.QUESTION_MESSAGE
				);

				if (result == JOptionPane.YES_OPTION) {
				    System.exit(0);
				}
		}
		if(e.getSource()==run) {
			co2.setEnabled(false);
			weather.setEnabled(false);
			load.setEnabled(false);
			run.setEnabled(false);
			exit.setEnabled(false);
			_thread = new Thread();
			run_sim((int)spinner.getValue(), (long)(int)delay.getValue());

		}
		if(e.getSource()== stop) {
			if(_thread != null) {
				_thread.interrupt();
			}
		}
	}
	
	private void run_sim(int n, long delay) {
		
		while (n > 0 && !Thread.interrupted()) {
	
			try {
				_ctrl.run(1);
				Thread.sleep(delay);
	         	
			} catch (Exception e) {
				ViewUtils.showErrorMsg(this, "error");
				co2.setEnabled(true);
				weather.setEnabled(true);
				load.setEnabled(true);
				run.setEnabled(true);
				exit.setEnabled(true);
				//_thread.interrupt();
				_thread = null;
			}
			--n;
		}
			co2.setEnabled(true);
			weather.setEnabled(true);
			load.setEnabled(true);
			run.setEnabled(true);
			exit.setEnabled(true);
			_thread = null;
		}
	}


