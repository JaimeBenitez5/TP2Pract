package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	private static final long serialVersionUID = 1L;
	
	private Controller ctrl;
	private List<Vehicle> vehicles;
	private String[] _colNames = { "Id", "Location", "Itinerary","CO2 Class", "Max.Speed", "Speed", "Total CO2", "Distance" };


	public VehiclesTableModel(Controller ctrl) {
		this.ctrl = ctrl;
		vehicles = new ArrayList<>();
		ctrl.addObserver(this);     
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
			s = vehicles.get(rowIndex).getId();
			break;
		case 1:
			switch(vehicles.get(rowIndex).getStatus()) {
			case VehicleStatus.TRAVELING:
				s = vehicles.get(rowIndex).getRoad().getId() + ":" + vehicles.get(rowIndex).getLocation();
				break;
			case VehicleStatus.PENDING:
				s = "Pending";
				break;
			case VehicleStatus.ARRIVED:
				s = "Arrived";
				break;
			default: s= "Waiting:"+vehicles.get(rowIndex).getJunction().getId();
			}
			break;
		case 2:
			s = vehicles.get(rowIndex).getItinerary();
			break;
		case 3:
			s = vehicles.get(rowIndex).getContClass();
			break;
		case 4:
			s = vehicles.get(rowIndex).getMaxSpeed();
			break;
		case 5:
			s = vehicles.get(rowIndex).getSpeed();
			break;
		case 6:
			s = vehicles.get(rowIndex).getTotalCO2();
			break;
		case 7:
			s = vehicles.get(rowIndex).getTotalDistance();
		}	
		return s;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		vehicles.clear();
		for(Vehicle j:map.getVehicles()) {
			vehicles.add(j);
		}
		fireTableDataChanged();

	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		

	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		vehicles.clear();
		
		fireTableDataChanged(); 

	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		for(Vehicle j:map.getVehicles()) {
			
			vehicles.add(j);
		}
		fireTableDataChanged();
	}

}
