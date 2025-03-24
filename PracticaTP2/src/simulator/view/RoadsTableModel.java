package simulator.view;

import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
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
	}
	
	@Override
	public int getRowCount() {
		return _colNames.length;
	}

	@Override
	public int getColumnCount() {
		return roads == null ? 0: roads.size();
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
		// TODO Auto-generated method stub
		return null;
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
