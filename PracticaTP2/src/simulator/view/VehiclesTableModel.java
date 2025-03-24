package simulator.view;

import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	private static final long serialVersionUID = 1L;
	
	private Controller ctrl;
	private List<Vehicle> vehicles;
	private String[] _colNames = { "Id", "Location", "Itinerary","CO2 Class", "Max.Speed", "Speed", "Total CO2", "Distance" };


	public VehiclesTableModel(Controller ctrl) {
		this.ctrl = ctrl;
	}
	
	public void addEvent(Vehicle v) {
		vehicles.add(v);
		// observar que si no refresco la tabla no se carga
		// La tabla es la represantación visual de una estructura de datos,
		// en este caso de un ArrayList, hay que notificar los cambios.

		// We need to notify changes, otherwise the table does not refresh.
		fireTableDataChanged();
	}

	public void reset() {
		vehicles.clear();
		;
		fireTableDataChanged();
	}


	@Override
	public int getRowCount() {
		return vehicles == null ?  0 :  vehicles.size();
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
			s = rowIndex;
			break;
		case 1:
			s = vehicles.get(rowIndex).getId();
			break;
		case 2:
			s = vehicles.get(rowIndex).getRoad().getId() + ":" + vehicles.get(rowIndex).getLocation();
			break;
		case 3:
			s = vehicles.get(rowIndex).getItinerary();
			break;
		case 4:
			s = vehicles.get(rowIndex).getContClass();
			break;
		case 5:
			s = vehicles.get(rowIndex).getMaxSpeed();
			break;
		case 6:
			s = vehicles.get(rowIndex).getSpeed();
			break;
		case 7:
			s = vehicles.get(rowIndex).getTotalCO2();
			break;
		case 8:
			s = vehicles.get(rowIndex).getLocation();
		}
		return s;
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
