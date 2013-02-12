package com.modtohafsd.util;

import java.awt.*;
import com.modtohafsd.view.MODTOHAFSD_MainPanel;

public class MODTOHAFSD_HandleException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MODTOHAFSD_HandleException(){
		Graphics g = MODTOHAFSD_MainPanel.img.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, 1000, 600);
		g.setColor(Color.black);
		g.setFont(new Font("Arial", 20, 40));
		g.drawString("ERROR...", 400, 325);
	}
}
