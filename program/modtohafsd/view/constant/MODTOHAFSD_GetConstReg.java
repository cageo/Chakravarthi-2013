package com.modtohafsd.view.constant;

import java.awt.*;
import java.awt.event.*;
import com.modtohafsd.model.MODTOHAFSD_CalculateValues;
import com.modtohafsd.util.MODTOHAFSD_Utility;
import com.modtohafsd.view.MODTOHAFSD_MainPanel;

public class MODTOHAFSD_GetConstReg implements ActionListener {
	public static double regval=0; 
	TextField reg = new TextField(12);
	Button bt = new Button("Submit");
	public static double input_reg_val[] = null;//new double[MODTOHAFSD_CalculateValues.input_n_obs+1];
	public void constReg(){
		input_reg_val=new double[MODTOHAFSD_CalculateValues.input_n_obs+1];
		for(int i =1;i<= MODTOHAFSD_CalculateValues.input_n_obs;i++){
			input_reg_val[i]= 0;
		}
		MODTOHAFSD_MainPanel.p_Center.removeAll();
		MODTOHAFSD_MainPanel.p_Center.validate();
		com.modtohafsd.view.MODTOHAFSD_MainPanel.p_Center.repaint();
		MODTOHAFSD_MainPanel.p_Center.add(MODTOHAFSD_MainPanel.graphLabel);

		MODTOHAFSD_MainPanel.p_Center.add(new Label("Regional(mGal):",Label.LEFT));
		MODTOHAFSD_MainPanel.p_Center.add(reg);
		MODTOHAFSD_MainPanel.p_Center.add(bt);
		bt.addActionListener(this); 
		MODTOHAFSD_MainPanel.p_Center.validate();
	}
	public void actionPerformed(ActionEvent ae){
		if(ae.getActionCommand().equals("Submit")){
			try {
				regval = MODTOHAFSD_Utility.convertDouble(reg.getText());
			} catch (Exception e) {

				e.printStackTrace();
			}
			for(int i =1;i<= MODTOHAFSD_CalculateValues.input_n_obs;i++){
				input_reg_val[i]= regval;
			}
			MODTOHAFSD_GetDegPoly.input_deg_poly=0;
			MODTOHAFSD_CalculateValues.d_cftnt_arr[1] = regval;
		}
	}
}
