package simulator.view;

<<<<<<< Updated upstream
=======
import java.util.ArrayList;
>>>>>>> Stashed changes
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	private static final long serialVersionUID = 1L;
	
	private Controller ctrl;
	private List<Junction> junctions;
<<<<<<< Updated upstream
	private String[] _colNames = { "Id", "Green", "Queues" };
=======
	private String[] _colNames = {"Id", "Green", "Queues" };
>>>>>>> Stashed changes


	public JunctionsTableModel(Controller ctrl) {
		this.ctrl = ctrl;
<<<<<<< Updated upstream
=======
		junctions = new ArrayList<>();
		ctrl.addObserver(this);     
>>>>>>> Stashed changes
	}
	
	public void addEvent(Junction j) {
		junctions.add(j);
		// observar que si no refresco la tabla no se carga
		// La tabla es la represantación visual de una estructura de datos,
		// en este caso de un ArrayList, hay que notificar los cambios.

		// We need to notify changes, otherwise the table does not refresh.
		fireTableDataChanged();
	}

	public void reset() {
		junctions.clear();
		;
		fireTableDataChanged();
	}

	
	@Override
	public int getRowCount() {
		return junctions == null ? 0: junctions.size();
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
<<<<<<< Updated upstream
			s = rowIndex;
			break;
		case 1:
			s = junctions.get(rowIndex).getId();
			break;
		case 2:
			s = junctions.get(rowIndex);
			break;
=======
			s = junctions.get(rowIndex).getId();
			break;
		case 1:
			s = junctions.get(rowIndex).getGreenLight();
			break;
		case 2:
			s = junctions.get(rowIndex).getQueues();
>>>>>>> Stashed changes
		}
		return s;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
<<<<<<< Updated upstream
		// TODO Auto-generated method stub

=======
		junctions.clear();
		for(Junction j:map.getJunctions()) {
			junctions.add(j);
		}
		fireTableDataChanged();
>>>>>>> Stashed changes
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
<<<<<<< Updated upstream
		// TODO Auto-generated method stub
=======
		junctions.clear();
		
		fireTableDataChanged(); 
>>>>>>> Stashed changes

	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
<<<<<<< Updated upstream
		// TODO Auto-generated method stub

=======
		junctions.clear();
		for(Junction j:map.getJunctions()) {
			
			junctions.add(j);
		}
		fireTableDataChanged();
>>>>>>> Stashed changes
	}

}
