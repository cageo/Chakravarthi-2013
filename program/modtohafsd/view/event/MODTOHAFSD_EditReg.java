package com.modtohafsd.view.event;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import com.modtohafsd.model.MODTOHAFSD_CalculateValues;
import com.modtohafsd.util.MODTOHAFSD_HandleException;
import com.modtohafsd.view.MODTOHAFSD_MainPanel;
import com.modtohafsd.view.MODTOHAFSD_TableView;
import com.modtohafsd.view.event.MODTOHAFSD_PlotReg;
import com.modtohafsd.view.graph.MODTOHAFSD_PlainGraph;

public class MODTOHAFSD_EditReg {
	public static float zpoint = 0;
	public static int count = 0;
	public static MouseListener ml1;
	com.modtohafsd.view.MODTOHAFSD_DrawGraph dg = new com.modtohafsd.view.MODTOHAFSD_DrawGraph();
	MODTOHAFSD_PlainGraph pg = new MODTOHAFSD_PlainGraph();
	com.modtohafsd.model.MODTOHAFSD_CalculateValues cv = new com.modtohafsd.model.MODTOHAFSD_CalculateValues();

	public void paint(Graphics g){
		final DecimalFormat f = new DecimalFormat("0.##");
		MODTOHAFSD_MainPanel.p_Center.removeAll();
		MODTOHAFSD_MainPanel.p_Center.add(MODTOHAFSD_MainPanel.im2);
		MODTOHAFSD_MainPanel.im2.setEditable(false);
		MODTOHAFSD_MainPanel.im2.setBackground(Color.WHITE);
		MODTOHAFSD_MainPanel.p_Center.validate();
		cv.getAnamolyValues(com.modtohafsd.view.MODTOHAFSD_MainPanel.captureValues());
		try {
			cv.getCoefficients();
		} catch (MODTOHAFSD_HandleException e2) {

		}

		Graphics2D g2 = (Graphics2D)MODTOHAFSD_MainPanel.im2.getGraphics();
		g2.setFont(new Font("Arial", 20, 12));
		pg.drawPlainGraph(g2);
		dg.plotXPoly(g2,300,250,250);
		pg.newAno(g2);
		pg.drawPlainGraph(g2);
		g2.setColor(Color.black);
		float ypoint,maxY;
		for(int i = 1;i<= MODTOHAFSD_CalculateValues.len;i++){
			float xpoint  = (float) (450*MODTOHAFSD_CalculateValues.d_x_km_arr[i]/MODTOHAFSD_PlainGraph.maxX1);
			if(MODTOHAFSD_CalculateValues.d_z_km_arr[i]<0){

				maxY = (float)(MODTOHAFSD_CalculateValues.input_min_ano);
				ypoint = 300+(float)( 250 *  MODTOHAFSD_CalculateValues.d_z_km_arr[i]/ maxY);
			}
			else{

				maxY = (float)Math.abs(MODTOHAFSD_CalculateValues.input_max_ano);
				ypoint = 300-(float)( 250 * MODTOHAFSD_CalculateValues.d_z_km_arr[i] / maxY);

			}

			g2.setFont(new Font("Arial", 20, 12));
			g2.drawString("("+f.format(MODTOHAFSD_CalculateValues.d_z_km_arr[i])+")", 150+xpoint-8, ypoint-10);
			g2.drawString("X", 150+xpoint-3, ypoint+3);
		}	

		ml1 = new MouseAdapter(){

			public void mouseDragged(MouseEvent e1) {
				DecimalFormat f = new DecimalFormat("0.##");

				float xer = e1.getX();
				float yer = e1.getY();
				float maxAno,ypoint ;
				if(yer>300){
					maxAno = (float) MODTOHAFSD_CalculateValues.input_min_ano;
					ypoint = (float) (maxAno * (yer - 300) / 250);
				}
				else{

					maxAno = (float) MODTOHAFSD_CalculateValues.input_max_ano;
					ypoint = (float) Math.abs(maxAno * (300 - yer) / 250);
				}

				float xpoint =(float) Math.abs((MODTOHAFSD_PlainGraph.maxX1 * (150 - xer)) / 450);
				Graphics2D g = (Graphics2D)MODTOHAFSD_MainPanel.im2.getGraphics();
				if (yer >= 50 && yer <= 550 && xer >= 150 && xer <= 600){

					g.setColor(Color.black);
					g.drawString("X-coordinates            Y-coordinates", 780, 320);
					g.setColor(Color.red);
					g.fillRect(780, 320, 180, 20);
					g.setColor(Color.black);
					g.drawString(""+f.format(xpoint)+"                              "+f.format(ypoint)+"", 780, 330);
				}
				pg.drawPlainGraph(g);
				dg.plotXPoly(g,300,250,250);
				pg.newAno(g);
				pg.drawPlainGraph(g);
			}	


			public void mousePressed(MouseEvent e1) {

				float x1 = e1.getX();
				float y1 = e1.getY();
				float diff = (float) (MODTOHAFSD_CalculateValues.x[MODTOHAFSD_CalculateValues.input_n_obs]/100);
				float xer = (float) Math.abs((MODTOHAFSD_PlainGraph.maxX1 * (150 - (x1-3))) / 450);
				Graphics2D g2 = (Graphics2D)MODTOHAFSD_MainPanel.im2.getGraphics();
				if (y1 > 50 && y1 < 550 && x1 <= 600){
					for (int kk = 1; kk <= MODTOHAFSD_CalculateValues.len; kk++){
						if (Math.abs((float)MODTOHAFSD_CalculateValues.d_x_km_arr[kk] - xer )<= diff  ){
							count = kk;
						}
						g2.setColor(Color.white);
						g2.drawString(""+count, 750, 380);
					}
				}
				pg.drawPlainGraph(g2);
				dg.plotXPoly(g2,300,250,250);
				pg.newAno(g2);
				pg.drawPlainGraph(g2);
			}
			public void mouseMoved(MouseEvent e){

				DecimalFormat f = new DecimalFormat("0.##");
				float xer = e.getX();
				float yer = e.getY();
				float maxAno,ypoint ;
				if(yer>300){
					maxAno = (float) MODTOHAFSD_CalculateValues.input_min_ano;
					ypoint = (float) (maxAno * (yer - 300) / 250);
				}
				else{
					maxAno = (float) MODTOHAFSD_CalculateValues.input_max_ano;
					ypoint = (float) Math.abs(maxAno * (300 - yer) / 250);
				}

				float xpoint =(float) Math.abs((MODTOHAFSD_PlainGraph.maxX1 * (150 - xer)) / 450);
				Graphics2D g2 = (Graphics2D)MODTOHAFSD_MainPanel.im2.getGraphics();
				if (yer >= 50 && yer <= 550 && xer >= 150 && xer <= 600){

					g2.setColor(Color.black);
					g2.drawString("X-coordinates            Y-coordinates", 780, 320);
					g2.setColor(Color.red);
					g2.fillRect(780, 320, 180, 20);
					g2.setColor(Color.black);
					g2.drawString(""+f.format(xpoint)+"                              "+f.format(ypoint)+"", 780, 330);
				}
				pg.drawPlainGraph(g2);
				dg.plotXPoly(g2,300,250,250);
				pg.newAno(g2);
				pg.drawPlainGraph(g2);
			}
			public void mouseReleased(MouseEvent e1) {

				float x = e1.getX();
				float y = e1.getY();
				float maxAno,ypoint ,maxY;
				if(y>300){
					maxAno = (float) MODTOHAFSD_CalculateValues.input_min_ano;
					ypoint = (float) (maxAno * (y - 300) / 250);
				}
				else{

					maxAno = (float) MODTOHAFSD_CalculateValues.input_max_ano;
					ypoint = (float) Math.abs(maxAno * (300 - y) / 250);
				}

				Graphics2D g2 = (Graphics2D)MODTOHAFSD_MainPanel.im2.getGraphics();
				MODTOHAFSD_PlainGraph pg = new MODTOHAFSD_PlainGraph();
				if (y > 50 && y <= 550 && x <= 600){

					MODTOHAFSD_PlotReg.val1[count]= ypoint;
					MODTOHAFSD_CalculateValues.d_z_km_arr[count] = ypoint;

				}
				try {
					cv.getCoefficients();
				} catch (MODTOHAFSD_HandleException e) {

				}
				MODTOHAFSD_MainPanel.clearPanel(MODTOHAFSD_MainPanel.im2);
				DecimalFormat d = new DecimalFormat("0.##");
				DecimalFormat df1 = new DecimalFormat("0.####");
				Graphics2D g1 = (Graphics2D)MODTOHAFSD_MainPanel.im2.getGraphics();
				pg.drawPlainGraph(g1);
				dg.plotXPoly(g1,300,250,250);
				pg.newAno(g1);
				com.modtohafsd.view.MODTOHAFSD_TableView.populateEastPanel1(MODTOHAFSD_PlainGraph.obj1);
				MODTOHAFSD_TableView.val.setText("");
				MODTOHAFSD_TableView.val.appendText("\n");
				MODTOHAFSD_TableView.val.append("Polynomial coefficients of regional background:-\n");
				MODTOHAFSD_TableView.val.appendText("-------------------------------------------\n");
				MODTOHAFSD_TableView.val.appendText("\n");

				for (int i = 1; i <= MODTOHAFSD_CalculateValues.i_d_poly+1; i++){
					MODTOHAFSD_TableView.val.appendText("f"+(i - 1)+" = " +df1.format(MODTOHAFSD_CalculateValues.d_cftnt_arr[i])+ "\n");
				}		

				g1.setColor(Color.black);
				for(int i = 1;i<= MODTOHAFSD_CalculateValues.len;i++){
					float xpoint  = (float) (450*MODTOHAFSD_CalculateValues.d_x_km_arr[i]/MODTOHAFSD_PlainGraph.maxX1);
					if(MODTOHAFSD_CalculateValues.d_z_km_arr[i]<0){

						maxY = (float)(MODTOHAFSD_CalculateValues.input_min_ano);
						ypoint = 300+(float)( 250 *  MODTOHAFSD_CalculateValues.d_z_km_arr[i]/ maxY);

					}
					else{

						maxY = (float)Math.abs(MODTOHAFSD_CalculateValues.input_max_ano);
						ypoint = 300-(float)( 250 * MODTOHAFSD_CalculateValues.d_z_km_arr[i] / maxY);

					}

					g2.setFont(new Font("Arial", 20, 12));
					g2.drawString("("+f.format(MODTOHAFSD_CalculateValues.d_z_km_arr[i])+")", 150+xpoint-8, ypoint-10);
					g2.drawString("X", 150+xpoint-3, ypoint+3);
				}	
			}

		};

		MODTOHAFSD_MainPanel.im2.addMouseListener(ml1);	
		MODTOHAFSD_MainPanel.im2.addMouseMotionListener((MouseMotionListener)ml1);
	}
}
