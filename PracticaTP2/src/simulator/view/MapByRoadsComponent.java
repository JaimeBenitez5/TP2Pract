package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class MapByRoadsComponent extends JComponent implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;

	private static final int _JRADIUS = 10;

	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;

	private RoadMap _map;

	private Image _car;
	
	private Image cont0 = loadImage("cont_0.png");
	private Image cont1 = loadImage("cont_1.png");
	private Image cont2  = loadImage("cont_2.png");
	private Image cont3 = loadImage("cont_3.png");
	private Image cont4 = loadImage("cont_4.png");
	private Image cont5 = loadImage("cont_5.png");
	
	private Image sun= loadImage("sun.png");
	private Image rain= loadImage("rain.png");
	private Image wind= loadImage("wind.png");
	private Image storm= loadImage("storm.png");
	private Image cloud= loadImage("cloud.png");
	
	public MapByRoadsComponent(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
		this.setPreferredSize(new Dimension(300,200));
	}

	private void initGUI() {
		_car = loadImage("car.png");
		
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			updatePrefferedSize();
			drawMap(g);
		}
	}

	private void drawMap(Graphics g) {
		drawRoads(g);
		drawVehicles(g);
		drawJunctions(g);
	}

	private void drawRoads(Graphics g) {
		int i=0;
		int x1= 50;
		int x2= getWidth()-100;
		for (Road r : _map.getRoads()) {
			int y =(i+1)*50;
			g.setColor(Color.BLACK);
			g.drawString(r.getId(),25,y);
			g.drawLine( x1, y, x2, y);
			int C = (int) Math.floor(Math.min((double) r.getTotalCO2()/(1.0 + (double) r.getContLimit()),1.0) / 0.19);
			Image im;
			switch (C) {
			case 0: 
				im=cont0;
				break;
			case 1:
				im=cont1;
				break;
			case 2:
				im=cont2;
				break;
			case 3:
				im=cont3;
				break;
			case 4:
				im=cont4;
				break;
			default:
				im=cont5;
			};
			Image im2;
			switch (r.getWeather()) {
			case SUNNY: 
				im2=sun;
				break;
			case CLOUDY:
				im2=cloud;
				break;
			case WINDY:
				im2=wind;
				break;
			case STORM:
				im2=storm;
				break;
			default:
				im2=rain;
			};
			g.drawImage(im2, x2+10, y-15, 32, 32, this);
			g.drawImage(im, x2+50, y-15, 32, 32, this);
			++i;
		}

	}

	private void drawVehicles(Graphics g) {
		int i=0;
		int x1= 50;
		int x2= getWidth()-100;
		for (Road r : _map.getRoads()) {
			for(Vehicle v: r.getVehicles()) {
				if (v.getStatus() != VehicleStatus.ARRIVED) {
					int y =(i+1)*50;

					// The calculation below compute the coordinate (vX,vY) of the vehicle on the
					// corresponding road. It is calculated relatively to the length of the road, and
					// the location on the vehicle


					// Choose a color for the vehcile's label and background, depending on its
					// contamination class
					int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
					g.setColor(new Color(0, vLabelColor, 0));
					int x = x1 + (int) ((x2 - x1) * ((double)v.getLocation()/ (double) v.getRoad().getLength()))	;
					// draw an image of a car (with circle as background) and it identifier
					g.fillOval(x - 1, y - 6, 14, 14);
					g.drawImage(_car, x, y - 6, 12, 12, this);
					g.drawString(v.getId(), x, y - 6);
					
				}
			}
			++i;
		}
	}

	private void drawJunctions(Graphics g) {
		int i=0;
		for (Road r : _map.getRoads()) {

			// (x,y) are the coordinates of the junction
			int x = 50;
			int y =(i+1)*50;

			// draw a circle with center at (x,y) with radius _JRADIUS
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			g.drawString(r.getSrc().getId(), x-4, y-10);

			// draw the junction's identifier at (x,y)
			x= getWidth()-100;
			y=(i+1)*50;
			
			g.drawString(r.getDest().getId(),x-4,y-10);
			
			g.setColor(r.getDest().getGreenLight()==r.getId()?_GREEN_LIGHT_COLOR:_RED_LIGHT_COLOR);
			g.fillOval(x - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);

			
			++i;
		}
	}

	// this method is used to update the preffered and actual size of the component,
	// so when we draw outside the visible area the scrollbars show up
	private void updatePrefferedSize() {
		int maxW = 200;
		int maxH = 200;
		for (Junction j : _map.getJunctions()) {
			maxW = Math.max(maxW, j.getX());
			maxH = Math.max(maxH, j.getY());
		}
		maxW += 20;
		maxH += 20;
		if (maxW > getWidth() || maxH > getHeight()) {
			setPreferredSize(new Dimension(maxW, maxH));
			setSize(new Dimension(maxW, maxH));
		}
	}

	// This method draws a line from (x1,y1) to (x2,y2) with an arrow.
	// The arrow is of height h and width w.
	// The last two arguments are the colors of the arrow and the line

	// loads an image from a file
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}

	public void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			_map = map;
			repaint();
		});
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

}
