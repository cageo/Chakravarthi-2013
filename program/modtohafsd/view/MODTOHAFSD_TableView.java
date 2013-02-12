package com.modtohafsd.view;

import java.awt.*;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class MODTOHAFSD_TableView extends Panel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static TextArea val = new TextArea(5,5);


	public static  void populateEastPanel(Object rowData[][]) {
		MODTOHAFSD_MainPanel.p_East.removeAll();
		MODTOHAFSD_MainPanel.p_East.setLayout(new GridLayout(2,1));
		Object columnNames[] = {"Distance(km)", "Bouguer anamolies (mGal)","Estimated regional (mGal)", "Residual anamolies (mGal)", "Modeled anamolies (mGal)","Error(mGal)","Depth(km)"};
		JTable table = new JTable(rowData, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(300,550));		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setAutoscrolls(true);
		MODTOHAFSD_MainPanel.p_East.add(scrollPane);
		val.setEditable(false);
		MODTOHAFSD_MainPanel.p_East.add(val);
		MODTOHAFSD_MainPanel.p_East.validate();
		MODTOHAFSD_MainPanel.p_East.setVisible(true);		
	}
	public static  void populateEastPanel1(Object rowData[][]) {
		MODTOHAFSD_MainPanel.p_East.removeAll();
		MODTOHAFSD_MainPanel.p_East.setLayout(new GridLayout(2,1));
		Object columnNames[] = {"Distance(km)", "Bouguer anamolies (mGal)","Estimated regional (mGal)", "Residual anamolies (mGal)"};
		JTable table = new JTable(rowData, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(300,550));		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setAutoscrolls(true);
		MODTOHAFSD_MainPanel.p_East.add(scrollPane);
		val.setEditable(false);
		MODTOHAFSD_MainPanel.p_East.add(val);
		MODTOHAFSD_MainPanel.p_East.validate();
		MODTOHAFSD_MainPanel.p_East.setVisible(true);		
	}

}

