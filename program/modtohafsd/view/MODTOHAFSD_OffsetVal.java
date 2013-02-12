package com.modtohafsd.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import com.modtohafsd.model.MODTOHAFSD_CalculateValues;
import com.modtohafsd.view.getvalues.MODTOHAFSD_GetOffsetVal;

public class MODTOHAFSD_OffsetVal implements ActionListener {
	public static JTable table;
	Button bt  = new Button("Submit"); 
	public static Object off[][];
	public  void val() {
		off  = new Object[MODTOHAFSD_CalculateValues.input_n_obs+1][2];
		MODTOHAFSD_MainPanel.p_Center.removeAll();
		MODTOHAFSD_MainPanel.p_Center.repaint();
		Label label = new Label("Column A: Prism number ", Label.CENTER);
		label.setFont(new Font("Arial", 10, 18));
		Label label1 = new Label("Column B: Offset value ", Label.CENTER);
		label1.setFont(new Font("Arial", 10, 18));
		MODTOHAFSD_MainPanel.p_Center.add(MODTOHAFSD_MainPanel.graphLabel);
		MODTOHAFSD_MainPanel.p_Center.add(label);
		MODTOHAFSD_MainPanel.p_Center.add(label1);
		table = new JTable(MODTOHAFSD_CalculateValues.input_n_obs, 2);
		table.setPreferredScrollableViewportSize(new Dimension(300,300));		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setAutoscrolls(true);
		MODTOHAFSD_MainPanel.p_Center.add(scrollPane);
		MODTOHAFSD_MainPanel.p_Center.add(bt);
		bt.addActionListener(this);
		MODTOHAFSD_MainPanel.p_Center.validate();
		MODTOHAFSD_MainPanel.p_Center.setVisible(true);		
	}
	public void actionPerformed(ActionEvent ae){
		if(ae.getActionCommand().equals("Submit")){
			MODTOHAFSD_GetOffsetVal gfv = new MODTOHAFSD_GetOffsetVal();
			gfv.getVal();
		}
	}
}
