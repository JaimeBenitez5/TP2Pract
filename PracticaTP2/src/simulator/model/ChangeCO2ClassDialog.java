package simulator.model;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import extra.jdialog.ex1.Dish;
import simulator.view.ViewUtils;

public class ChangeCO2ClassDialog extends JDialog {

	int status;
    JButton ok;
    JButton cancel;
    JComboBox<Vehicle> vehicleBox;
    DefaultComboBoxModel<Vehicle> defaultVehicleBox;
    JComboBox<Integer> co2ClassBox;
    DefaultComboBoxModel<Integer> defaultCo2ClassBox;
    JSpinner ticksSpinner;
    
    ChangeCO2ClassDialog() {
    	this.setModal(true);
        this.setSize(800, 200);
        this.setTitle("Change CO2 Class");

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel l = new JLabel("Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now.");
        mainPanel.add(l, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        defaultVehicleBox= new DefaultComboBoxModel<>();
        vehicleBox = new JComboBox<>(defaultVehicleBox);
        
        vehicleBox.setPreferredSize(new Dimension(200, 30));

        Integer[] numeros = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        defaultCo2ClassBox = new DefaultComboBoxModel<Integer>(numeros);
        co2ClassBox = new JComboBox<>(defaultCo2ClassBox);
        
        co2ClassBox.setPreferredSize(new Dimension(100, 30));

        ticksSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
        ticksSpinner.setPreferredSize(new Dimension(100, 30));

        centerPanel.add(new JLabel("Vehicle:"));
        centerPanel.add(vehicleBox);
        centerPanel.add(new JLabel("CO2 Class:"));
        centerPanel.add(co2ClassBox);
        centerPanel.add(new JLabel("Ticks:"));
        centerPanel.add(ticksSpinner);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ok = new JButton("Ok");
        ok.addActionListener((e) -> {
			if (defaultVehicleBox.getSelectedItem() != null) {
				status = 1;
				ChangeCO2ClassDialog.this.setVisible(false);
			}
		});
        cancel = new JButton("Cancel");
        cancel.addActionListener((e) -> {
				status = 2;
				ChangeCO2ClassDialog.this.setVisible(false);
		});
        buttonsPanel.add(ok);
        buttonsPanel.add(cancel);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        this.add(mainPanel);
        
        this.setVisible(false);
    }
    
//	    public int mostrarDialogo(List<Vehicle> vehicles) {
//	    	if(vehicles!=null)
//	    	vehicleBox.setModel(new DefaultComboBoxModel<>(vehicles.toArray(new Vehicle[0])));
//	    	
//	    	this.setVisible(true);
//	    	return 0;
//	    }
	    public int open(Frame parent, List<Vehicle> vehicles) {

			// update the comboxBox model -- if you always use the same no
			// need to update it, you can initialize it in the constructor.
			//
			defaultVehicleBox.removeAllElements();
			if(vehicles!=null) {
			for (Vehicle v : vehicles)
				defaultVehicleBox.addElement(v);
			}
			// You can change this to place the dialog in the middle of the parent window.
			//
			setLocation(parent.getLocation().x + 10, parent.getLocation().y + 10);
			this.setLocationRelativeTo(ViewUtils.getWindow(parent));
			setVisible(true);
			return status;
		}
	    
	    public Vehicle getVehicle() {
			return (Vehicle)defaultVehicleBox.getSelectedItem();
		}
	    
	    public Integer getCo2Class() {
			return (Integer)defaultCo2ClassBox.getSelectedItem();
		}
	    
	    public Integer getTicks() {
			return (Integer)ticksSpinner.getValue();
		}
}
