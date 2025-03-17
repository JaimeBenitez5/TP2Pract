package simulator.model;

import java.util.Comparator;

public class MiComparatorVehiculo implements Comparator<Vehicle> {

	public MiComparatorVehiculo() {
		 super();
	}

	@Override
	public int compare(Vehicle o1, Vehicle o2) {
		 if(o1.getLocation()<o2.getLocation()) return 1;
		 else if(o1.getLocation()>o2.getLocation()) return -1;
		 else return 0;
	}

}
