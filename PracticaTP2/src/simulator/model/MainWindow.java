package simulator.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;
import simulator.view.EventsTableModel;
import simulator.view.JunctionsTableModel;
import simulator.view.MapByRoadsComponent;
import simulator.view.MapComponent;
import simulator.view.RoadsTableModel;
import simulator.view.VehiclesTableModel;

public class MainWindow extends JFrame {

	private Controller _ctrl;

	public MainWindow(Controller ctrl) {
		super("Traffic Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);

		mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(_ctrl),BorderLayout.PAGE_END);
		
		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);

		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);

		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);

		
		Border b = BorderFactory.createLineBorder(Color.black , 2);
		// tables
		JPanel eventsView = createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
		eventsView. setBorder(BorderFactory. createTitledBorder (b, "Events",TitledBorder.LEFT, TitledBorder.TOP));
		eventsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(eventsView);


		JPanel vehiclesView = createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Events");
		vehiclesView. setBorder(BorderFactory. createTitledBorder (b, "Vehicles",TitledBorder.LEFT, TitledBorder.TOP));
		vehiclesView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(vehiclesView);
		
		JPanel roadsView = createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Events");
		roadsView. setBorder(BorderFactory. createTitledBorder (b, "Roads",TitledBorder.LEFT, TitledBorder.TOP));
		roadsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(roadsView);
		
		JPanel junctionsView = createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Events");
		junctionsView. setBorder(BorderFactory. createTitledBorder (b, "Junctions",TitledBorder.LEFT, TitledBorder.TOP));
		junctionsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(junctionsView);
		
		// maps
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		mapView. setBorder(BorderFactory. createTitledBorder (b, "Map",TitledBorder.LEFT, TitledBorder.TOP));
		mapsPanel.add(mapView);
		
		JPanel mapByRoadView = createViewPanel( new MapByRoadsComponent(_ctrl), "MapByRoad");
		mapByRoadView. setBorder(BorderFactory. createTitledBorder (b, "Map by Road",TitledBorder.LEFT, TitledBorder.TOP));
		mapsPanel.add(mapByRoadView);
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setSize(1920, 1080);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel( new BorderLayout() );
		p.add(new JScrollPane(c));
		return p;
	}
}