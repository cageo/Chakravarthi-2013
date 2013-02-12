package com.modtohafsd.view.constant;

import java.awt.*;
import java.awt.event.*;
import com.modtohafsd.util.MODTOHAFSD_Utility;
import com.modtohafsd.view.MODTOHAFSD_MainPanel;
import com.modtohafsd.view.event.MODTOHAFSD_PlotReg;

public class MODTOHAFSD_GetDegPoly implements ActionListener{
	public int polyval=0; 
	TextField p = new TextField(12);
	Button bt = new Button("Submit");
	public static int input_deg_poly=0;
	public void constHaf(){
		MODTOHAFSD_MainPanel.p_Center.removeAll();
		MODTOHAFSD_MainPanel.p_Center.validate();
		com.modtohafsd.view.MODTOHAFSD_MainPanel.p_Center.repaint();
		MODTOHAFSD_MainPanel.p_Center.add(MODTOHAFSD_MainPanel.graphLabel);

		MODTOHAFSD_MainPanel.p_Center.add(new Label("Degree of polynomial:",Label.LEFT));
		MODTOHAFSD_MainPanel.p_Center.add(p);
		MODTOHAFSD_MainPanel.p_Center.add(bt);
		bt.addActionListener(this);

		MODTOHAFSD_MainPanel.p_Center.validate();
	}
	public void actionPerformed(ActionEvent ae){
		if(ae.getActionCommand().equals("Submit")){
			try {
				polyval = MODTOHAFSD_Utility.convertInteger(p.getText());
			} catch (Exception e) {

				e.printStackTrace();
			}
			input_deg_poly  = polyval;

			MODTOHAFSD_PlotReg pf = new MODTOHAFSD_PlotReg();
			Graphics g = MODTOHAFSD_MainPanel.p_Center.getGraphics();
			pf.paint(g);

		}
	}
}
