package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	private static final long serialVersionUID = 1L;
	
	private Controller ctrl;
	private List<Road> roads;
	private String[] _colNames = { "Id", "Length", "Weather", "Max.Speed", "Speed Limit", "Total CO2", "CO2 Limit"};
	
	
	public RoadsTableModel(Controller ctrl) {
		this.ctrl = ctrl;
		roads = new ArrayList<>();
		ctrl.addObserver(this);     
	}
	
	public void addEvent(Road r) {
		roads.add(r);
		// observar que si no refresco la tabla no se carga
		// La tabla es la represantación visual de una estructura de datos,
		// en este caso de un ArrayList, hay que notificar los cambios.

		// We need to notify changes, otherwise the table does not refresh.
		fireTableDataChanged();
	}

	public void reset() {
		roads.clear();
		;
		fireTableDataChanged();
	}

	
	@Override
	public int getRowCount() {
		return roads == null ? 0: roads.size();
		
	}

	@Override
	public int getColumnCount() {
		return _colNames.length;
	}
	
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = roads.get(rowIndex).getId();
			break;
		case 1:
			s = roads.get(rowIndex).getLength();
			break;
		case 2: 
			s = roads.get(rowIndex).getWeather();
			break;
		case 3: 
			s = roads.get(rowIndex).getMaxSpeed();
			break;
		case 4:
			s = roads.get(rowIndex).getSpeedLimit();
			break;
		case 5:
			s = roads.get(rowIndex).getTotalCO2();
			break;
		case 6:
			s = roads.get(rowIndex).getContLimit();
			break;
		}
		return s;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		roads.clear();
		for(Road j:map.getRoads()) {
			
			roads.add(j);
		} 
		fireTableDataChanged();

	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		roads.clear();
		
		fireTableDataChanged(); 

	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		roads.clear();
		for(Road j:map.getRoads()) {
			
			roads.add(j);
		}
		fireTableDataChanged();
	}

}
