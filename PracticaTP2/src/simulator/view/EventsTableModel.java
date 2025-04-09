<<<<<<< Updated upstream
package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
=======
 package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
	private String[] _colNames = { "Time", "Description" };
=======
	private String[] _colNames = {"Time", "Description" };
>>>>>>> Stashed changes

	public EventsTableModel(Controller ctrl) {
		this.ctrl = ctrl;
		_events = new ArrayList<>();
<<<<<<< Updated upstream
=======
		ctrl.addObserver(this);     
>>>>>>> Stashed changes
	}

	public void addEvent(Event e) {
		_events.add(e);
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
		// observar que si no refresco la tabla no se carga
		// La tabla es la represantación visual de una estructura de datos,
		// en este caso de un ArrayList, hay que notificar los cambios.

		// We need to notify changes, otherwise the table does not refresh.
<<<<<<< Updated upstream
		fireTableDataChanged();
	}

=======
		_events.sort(null);
		fireTableDataChanged();
	}

	
	
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
			s = rowIndex;
			break;
		case 1:
			s = _events.get(rowIndex).getTime();
			break;
		case 2:
=======
			s = _events.get(rowIndex).getTime();
			break;
		case 1:
>>>>>>> Stashed changes
			s = _events.get(rowIndex).toString();
			break;
		}
		return s;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
<<<<<<< Updated upstream
		// TODO Auto-generated method stub
=======
		_events.clear();
		for (Event e : events) {
	        _events.add(e);
	    }
		fireTableDataChanged();
>>>>>>> Stashed changes
		
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
<<<<<<< Updated upstream
		// TODO Auto-generated method stub
		
=======
		
		addEvent(e);
>>>>>>> Stashed changes
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
<<<<<<< Updated upstream
		// TODO Auto-generated method stub
		
=======
		_events.clear();
		
		fireTableDataChanged(); 
>>>>>>> Stashed changes
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
<<<<<<< Updated upstream
		// TODO Auto-generated method stub
		
	}
=======
		_events.clear();
	    for (Event e : events) {
	        _events.add(e);
	    }
	    _events.sort(null);
	    fireTableDataChanged(); 
	}

>>>>>>> Stashed changes
}
