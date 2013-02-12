package com.modtohafsd.view.constant;

import java.awt.*;
import java.awt.event.*;
import com.modtohafsd.model.MODTOHAFSD_CalculateValues;
import com.modtohafsd.util.MODTOHAFSD_Utility;
import com.modtohafsd.view.MODTOHAFSD_MainPanel;

public class MODTOHAFSD_GetConstOff implements ActionListener {
	public double offval=0; 
	TextField offset = new TextField(12);
	public static double input_y_km[]= new double[MODTOHAFSD_CalculateValues.input_n_obs+1];
	Button bt = new Button("Submit");
	public void constOff(){
		MODTOHAFSD_MainPanel.p_Center.removeAll();
		MODTOHAFSD_MainPanel.p_Center.validate();
		com.modtohafsd.view.MODTOHAFSD_MainPanel.p_Center.repaint();
		MODTOHAFSD_MainPanel.p_Center.add(MODTOHAFSD_MainPanel.graphLabel);
		MODTOHAFSD_MainPanel.p_Center.add(new Label("Offset(km):",Label.LEFT));
		MODTOHAFSD_MainPanel.p_Center.add(offset);
		MODTOHAFSD_MainPanel.p_Center.add(bt);
		bt.addActionListener(this); 
		MODTOHAFSD_MainPanel.p_Center.validate();
	}
	public void actionPerformed(ActionEvent ae){
		if(ae.getActionCommand().equals("Submit")){
			try {
				offval = MODTOHAFSD_Utility.convertDouble(offset.getText());
			} catch (Exception e) {

				e.printStackTrace();
			}
			for(int i =1;i<= MODTOHAFSD_CalculateValues.input_n_obs;i++){

				input_y_km[i]= offval;

			}
		}
	}
}
