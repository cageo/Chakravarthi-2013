package com.modtohafsd.view.event;


import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import com.modtohafsd.model.MODTOHAFSD_CalculateValues;
import com.modtohafsd.view.MODTOHAFSD_MainPanel;
import com.modtohafsd.view.graph.MODTOHAFSD_PlainGraph;


public class MODTOHAFSD_PlotReg extends Applet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static double val[] =null;
	public static double val1[]=null;
	public static int pos=0;
	public static MouseListener ml;
	com.modtohafsd.model.MODTOHAFSD_CalculateValues cv = new com.modtohafsd.model.MODTOHAFSD_CalculateValues();
	MODTOHAFSD_PlainGraph pg = new MODTOHAFSD_PlainGraph(); 

	public void paint(Graphics g){

		MODTOHAFSD_MainPanel.p_Center.removeAll();
		MODTOHAFSD_MainPanel.p_Center.add(MODTOHAFSD_MainPanel.im);
		MODTOHAFSD_CalculateValues.len=0;
		val = new double[50] ;
		val1 = new double[50] ;
		Graphics2D g2 = (Graphics2D)MODTOHAFSD_MainPanel.im.getGraphics();
		g2.setFont(new Font("Arial", 40,12));
		g2.setColor(Color.red);
		MODTOHAFSD_MainPanel.im.setEditable(false);
		MODTOHAFSD_MainPanel.im.setBackground(Color.WHITE);
		MODTOHAFSD_MainPanel.p_Center.validate();

		for (int j = 0; j < val.length; j++){
			val[j] = 0;
			val1[j] = 0;
		}
		pos = 1;
		cv.getAnamolyValues(com.modtohafsd.view.MODTOHAFSD_MainPanel.captureValues());
		Graphics2D g1 = (Graphics2D)MODTOHAFSD_MainPanel.im.getGraphics();
		pg.drawPlainGraph(g1);

		ml = new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				DecimalFormat f = new DecimalFormat("0.##");
				float polyx = e.getX();
				float polyy = e.getY();
				float x[] = new float[50];
				float z[] = new float[50];
				int get = pos;
				float maxAno;

				MODTOHAFSD_CalculateValues.len = get;
				Graphics2D g2 = (Graphics2D)MODTOHAFSD_MainPanel.im.getGraphics();
				if (polyy >= 50 && polyy <= 550 && polyx >= 150 && polyx <= 600){

					MODTOHAFSD_PlotReg.val[get] = (float) Math.abs((MODTOHAFSD_PlainGraph.maxX1 * (150 - polyx)) / 450);
					if(polyy >300){
						maxAno = (float) MODTOHAFSD_CalculateValues.input_min_ano;
						MODTOHAFSD_PlotReg.val1[get] = (float) (maxAno * (polyy - 300) / 250);
					}
					else{
						maxAno = (float) MODTOHAFSD_CalculateValues.input_max_ano;
						MODTOHAFSD_PlotReg.val1[get] = (float) Math.abs(maxAno * (300 - polyy) / 250);
					}

					x[get] = polyx;
					z[get] = polyy;
					g2.setColor(Color.white);
					g2.fillRect(730, 160, 250, 40);

					g2.setColor(Color.BLACK);
					g2.setFont(new Font("Arial", 40, 12));
					g2.drawString("x", polyx, polyy);
					g2.drawString("("+f.format(MODTOHAFSD_PlotReg.val1[get])+")", polyx, polyy);
					g2.setFont(new Font("Arial", 40, 20));
					if(get<=MODTOHAFSD_CalculateValues.i_d_poly)
						g2.setColor(Color.red);
					else
						g2.setColor(Color.blue);

					g2.drawString("Number of points "+get, 740, 180);
					g2.setFont(new Font("Arial", 40, 12));
					MODTOHAFSD_PlotReg.pos++;
				}
				pg.drawPlainGraph(g2);
				repaint();
			} 
			public void mouseDragged(MouseEvent e){
				Graphics2D g2 = (Graphics2D)MODTOHAFSD_MainPanel.im.getGraphics();
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

				if (yer >= 50 && yer <= 550 && xer >= 150 && xer <= 600){
					Graphics2D g2 = (Graphics2D)MODTOHAFSD_MainPanel.im.getGraphics();
					g2.setColor(Color.black);
					g2.drawString("X-coordinates            Y-coordinates", 780, 320);
					g2.setColor(Color.red);
					g2.fillRect(780, 320, 180, 20);
					g2.setColor(Color.black);
					g2.drawString(""+f.format(xpoint)+"                              "+f.format(ypoint)+"", 780, 330);
				}
				Graphics2D g2 = (Graphics2D)MODTOHAFSD_MainPanel.im.getGraphics();
				pg.drawPlainGraph(g2);	
			}

		};

		MODTOHAFSD_MainPanel.im.addMouseListener(ml);	
		MODTOHAFSD_MainPanel.im.addMouseMotionListener((MouseMotionListener)ml);
		g2.setFont(new Font("Arial", 40, 20));
		g2.setColor(Color.black);

	}

}
