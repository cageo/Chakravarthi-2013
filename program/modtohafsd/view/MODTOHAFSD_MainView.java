package com.modtohafsd.view;


import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.modtohafsd.control.MODTOHAFSD_Controller;
import com.modtohafsd.model.MODTOHAFSD_CalculateValues;

public class MODTOHAFSD_MainView extends Frame{


	private static final long serialVersionUID = 1L;

	public static void main(String s[])
	{
		MODTOHAFSD_MainView cm = new MODTOHAFSD_MainView();
		cm.setSize(1280, 768);
		cm.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				JFrame frame = null;
				int r =	JOptionPane.showConfirmDialog(
						frame,
						"Exit MODTOHAFSD ?",
						"Confirm Exit ",
						JOptionPane.YES_NO_OPTION);
				if(r == JOptionPane.YES_OPTION ){
					if(MODTOHAFSD_Controller.success == false){
						String fileName = MODTOHAFSD_CalculateValues.input_area_name+".jpg";
						File f = new File(fileName);
						f.delete();
					}
					System.exit(0);

				}
			}
		});
		cm.setTitle("MODTOHAFSD");
		cm.setResizable(true);
		cm.add(new MODTOHAFSD_MainPanel(cm));
		cm.setVisible(true);

	}

}
