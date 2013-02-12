package com.modtohafsd.view.constant;

import java.awt.*;
import java.awt.event.*;
import com.modtohafsd.util.MODTOHAFSD_Utility;
import com.modtohafsd.view.*;


public class MODTOHAFSD_LambInt implements ActionListener {
	public static double lamval=0; 
	public static double sdval=0; 
	public static TextField lam = new TextField(12);
	public static TextField sd = new TextField(12);
	Button bt = new Button("Submit");
	public void constLamSd(){
		MODTOHAFSD_MainPanel.p_Center.removeAll();
		MODTOHAFSD_MainPanel.p_Center.validate();
		MODTOHAFSD_MainPanel.p_Center.repaint();
		MODTOHAFSD_MainPanel.p_Center.add(MODTOHAFSD_MainPanel.graphLabel);

		MODTOHAFSD_MainPanel.p_Center.add(new Label("Surface density contrast(gm/cc):",Label.LEFT));
		MODTOHAFSD_MainPanel.p_Center.add(sd);
		MODTOHAFSD_MainPanel.p_Center.add(new Label("Lambda(1/km):",Label.LEFT));
		MODTOHAFSD_MainPanel.p_Center.add(lam);
		MODTOHAFSD_MainPanel.p_Center.add(bt);
		bt.addActionListener(this);
		MODTOHAFSD_MainPanel.p_Center.validate();
	}
	public void actionPerformed(ActionEvent ae){
		if(ae.getActionCommand().equals("Submit")){
			try {
				lamval = MODTOHAFSD_Utility.convertDouble(lam.getText());
				sdval = MODTOHAFSD_Utility.convertDouble(sd.getText());
			} catch (Exception e) {

				e.printStackTrace();
			}


		}
	}
}
