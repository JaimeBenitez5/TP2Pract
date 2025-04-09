package simulator.view;

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

import simulator.model.Road;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog {
	
	int status;
	JButton ok;
    JButton cancel;
    DefaultComboBoxModel<Road> defaultRoadBox;
    JComboBox<Road> roadBox;
    DefaultComboBoxModel<Weather> defaultWeatherBox;
    JComboBox<Weather> weatherBox;
    JSpinner ticksSpinner;
    
    ChangeWeatherDialog() {
        this.setSize(800, 200);
        this.setModal(true);
        this.setTitle("Change CO2 Class");

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel l = new JLabel("Schedule an event to change the weather of a road after a given number of simulation ticks from now.");
        mainPanel.add(l, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        Weather[] wA= {Weather.SUNNY, Weather.CLOUDY, Weather.RAINY, Weather.WINDY, Weather.STORM};
        
        defaultRoadBox = new DefaultComboBoxModel();
        roadBox = new JComboBox<>(defaultRoadBox);
        roadBox.setPreferredSize(new Dimension(200, 30));

        defaultWeatherBox = new DefaultComboBoxModel(wA);
        weatherBox = new JComboBox<>(defaultWeatherBox);
        weatherBox.setPreferredSize(new Dimension(100, 30));

        ticksSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
        ticksSpinner.setPreferredSize(new Dimension(100, 30));

        centerPanel.add(new JLabel("Road:"));
        centerPanel.add(roadBox);
        centerPanel.add(new JLabel("Weather:"));
        centerPanel.add(weatherBox);
        centerPanel.add(new JLabel("Ticks:"));
        centerPanel.add(ticksSpinner);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ok = new JButton("Ok");
        ok.addActionListener((e) -> {
			if (defaultRoadBox.getSelectedItem() != null) {
				status = 1;
				ChangeWeatherDialog.this.setVisible(false);
			}
		});
        cancel = new JButton("Cancel");
        cancel.addActionListener((e) -> {
			status = 2;
			ChangeWeatherDialog.this.setVisible(false);
	});
        buttonsPanel.add(ok);
        buttonsPanel.add(cancel);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        this.add(mainPanel);
        this.setLocationRelativeTo(ViewUtils.getWindow(this));
        this.setVisible(false);
    }

    public int open(Frame parent, List<Road> roads) {
    	// update the comboxBox model -- if you always use the same no
    	// need to update it, you can initialize it in the constructor.
    	//
    	defaultRoadBox.removeAllElements();
    	if(roads!=null) {
    		for (Road v : roads)
    			defaultRoadBox.addElement(v);
    	}
    	// You can change this to place the dialog in the middle of the parent window.
    	//
    	setLocation(parent.getLocation().x + 10, parent.getLocation().y + 10);
    	this.setLocationRelativeTo(ViewUtils.getWindow(parent));
    	setVisible(true);
    	return status;
    }
    
    public Road getRoad() {
    	return (Road) defaultRoadBox.getSelectedItem();
    }
    
    public Weather getWeather() {
    	return (Weather) defaultWeatherBox.getSelectedItem();
    }
    
    public Integer getTicks() {
    	return (Integer) ticksSpinner.getValue();
    }
}
