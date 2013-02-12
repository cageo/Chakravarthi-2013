package com.modtohafsd.view.constant;

import java.awt.*;
import java.awt.event.*;
import com.modtohafsd.model.MODTOHAFSD_CalculateValues;
import com.modtohafsd.util.MODTOHAFSD_Utility;
import com.modtohafsd.view.MODTOHAFSD_MainPanel;

public class MODTOHAFSD_GetConstHaf implements ActionListener{
	public int hafval=0; 
	TextField haf = new TextField(12);
	Button bt = new Button("Submit");
	public static double input_strike_km[] = new double[MODTOHAFSD_CalculateValues.input_n_obs+1];
	public void constHaf(){
		MODTOHAFSD_MainPanel.p_Center.removeAll();
		MODTOHAFSD_MainPanel.p_Center.validate();
		MODTOHAFSD_MainPanel.p_Center.repaint();
		MODTOHAFSD_MainPanel.p_Center.add(MODTOHAFSD_MainPanel.graphLabel);

		MODTOHAFSD_MainPanel.p_Center.add(new Label("Halfstrike(km):",Label.LEFT));
		MODTOHAFSD_MainPanel.p_Center.add(haf);
		MODTOHAFSD_MainPanel.p_Center.add(bt);
		bt.addActionListener(this);
		MODTOHAFSD_MainPanel.p_Center.validate();
	}
	public void actionPerformed(ActionEvent ae){
		if(ae.getActionCommand().equals("Submit")){
			try {
				hafval = MODTOHAFSD_Utility.convertInteger(haf.getText());
			} catch (Exception e) {

				e.printStackTrace();
			}
			for(int i =1;i<= MODTOHAFSD_CalculateValues.input_n_obs;i++){

				input_strike_km[i]= hafval;

			}

		}
	}
}
