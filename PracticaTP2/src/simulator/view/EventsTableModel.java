 package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver{

	private static final long serialVersionUID = 1L;
	
	private Controller ctrl;
	private List<Event> _events;
	private String[] _colNames = {"Time", "Description" };

	public EventsTableModel(Controller ctrl) {
		this.ctrl = ctrl;
		_events = new ArrayList<>();
		ctrl.addObserver(this);     
	}

	public void addEvent(Event e) {
		_events.add(e);
		_events.sort(null);
		fireTableDataChanged();
	}

	
	
	public void reset() {
		_events.clear();
		;
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	public int getRowCount() {
		return _events == null ? 0 : _events.size();
	}

	@Override
	// método obligatorio
	// así es como se va a cargar la tabla desde el ArrayList
	// el índice del arrayList es el número de fila pq en este ejemplo
	// quiero enumerarlos.
	//
	// returns the value of a particular cell
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = _events.get(rowIndex).getTime();
			break;
		case 1:
			s = _events.get(rowIndex).toString();
			break;
		}
		return s;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		_events.clear();
		for (Event e : events) {
	        _events.add(e);
	    }
		fireTableDataChanged();
		
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		
		addEvent(e);
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		_events.clear();
		
		fireTableDataChanged(); 
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		_events.clear();
	    for (Event e : events) {
	        _events.add(e);
	    }
	    _events.sort(null);
	    fireTableDataChanged(); 
	}

}
