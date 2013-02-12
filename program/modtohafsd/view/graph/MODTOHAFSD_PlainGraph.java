package com.modtohafsd.view.graph;

import java.awt.*;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;
import com.modtohafsd.model.MODTOHAFSD_CalculateValues;
import com.modtohafsd.view.MODTOHAFSD_DrawGraph;
import com.modtohafsd.view.constant.MODTOHAFSD_GetConstReg;

public class MODTOHAFSD_PlainGraph {

	int i_no_obs=0;
	public static float maxX1=0;
	public static Object obj1[][]=null;
	public void drawPlainGraph(Graphics2D g2){
		g2.setColor(Color.RED);
		g2.setFont(new Font("Arial", 20, 12));
		g2.drawString("____:",625,70);
		g2.drawString("Bouguer anomalies",660,70);
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Arial", 20, 12));
		i_no_obs = MODTOHAFSD_CalculateValues.input_n_obs;
		double obser[] = new double[i_no_obs + 1];
		for (int i = 1; i <= i_no_obs; i++) {

			obser[i] = MODTOHAFSD_CalculateValues.x[i];
		}
		maxX1 = (float) MODTOHAFSD_CalculateValues.x[i_no_obs];
		float points = maxX1 / 5;
		DecimalFormat f = new DecimalFormat("0.#");
		g2.drawLine(150,50,150,300);
		g2.drawString("DISTANCE(km)", 315, 540);
		String []a={"A", "N", "O", "M", "A", "L", "Y", "(m", "G", "a", "l", "s)"};
		for (int i = 0; i < a.length; i++) {

			g2.drawString(""+a[i], 80,  160 + ( i * 20 ) );
		}
		g2.drawString("|",(float) 598, 550);
		g2.drawString(""+f.format(MODTOHAFSD_CalculateValues.x[i_no_obs]),600, 570);
		int xInterval=90; 
		for (int x = xInterval, j =1; x < 600; x+=xInterval)
		{
			if(j > 4)
				break;
			g2.drawString("|",150+x,550);
			g2.drawString("" + f.format(points*j), 147+x, 570);
			j++;
		}
		points = (float) (MODTOHAFSD_CalculateValues.input_max_ano / 5) ;
		g2.drawString(""+MODTOHAFSD_CalculateValues.input_max_ano, 115, 50);
		int yInterval = 50;
		for (int x = 50, j = 4; x < 250; x += yInterval){

			g2.drawString("-", 148, 55 + x);
			g2.drawString("" + (points * j), 115, 55 + x);
			j--;
		}
		g2.draw(new Line2D.Float(150, 550, 600, 550));
		g2.draw(new Line2D.Float(150, 300, 150, 550));
		float maxY = (float) MODTOHAFSD_CalculateValues.input_min_ano;	
		g2.drawString("0", 135, 305);	
		points = (float) (MODTOHAFSD_CalculateValues.input_min_ano / 5) ;
		g2.drawString(""+MODTOHAFSD_CalculateValues.input_min_ano, 115, 550);
		int yInter = 50;
		for (int x = yInter, j = 1; x < 250; x += yInter){
			if(j>4)
				break;
			g2.drawString("-", 148, 305 + x);
			g2.drawString("" + (points * j), 115, 305 + x);
			j++;
		}

		float xpoint = 0;
		float gypoint = 0;
		float prevx = 150;
		float prevy ;
		if(MODTOHAFSD_CalculateValues.input_nob_gob[1]<0){
			maxY = (float) MODTOHAFSD_CalculateValues.input_min_ano;
			prevy = 300+(float)( ( 250 * (MODTOHAFSD_CalculateValues.input_nob_gob[1]) / maxY ) );
		}
		else{
			maxY = (float) MODTOHAFSD_CalculateValues.input_max_ano;
			prevy = 300-(float)( ( 250 * (MODTOHAFSD_CalculateValues.input_nob_gob[1]) / maxY ) );
		}
		for (int k = 1; k <= i_no_obs; k++) {

			xpoint = (float)( 450 * obser[k] / maxX1);
			if(MODTOHAFSD_CalculateValues.input_nob_gob[k]<0){
				maxY = (float) MODTOHAFSD_CalculateValues.input_min_ano;
				gypoint = 300+(float)( ( 250 * (MODTOHAFSD_CalculateValues.input_nob_gob[k]) / maxY ) );
			}
			else{
				maxY = (float) MODTOHAFSD_CalculateValues.input_max_ano;
				gypoint = 300-(float)( ( 250 * (MODTOHAFSD_CalculateValues.input_nob_gob[k]) / maxY ) );
			}

			g2.setColor(Color.RED);
			g2.draw(new Line2D.Float(prevx, prevy, 150 + xpoint, gypoint  ));

			prevx = 150 + xpoint;
			prevy = gypoint ;
		}   

	}

	public void newAno(Graphics2D g){

		g.setFont(new Font("Arial", 20, 12));
		g.setColor(Color.DARK_GRAY);
		g.drawString("____:",625,110);
		g.drawString("Residual anomalies",660,110);
		g.setColor(Color.GREEN);
		g.drawString("____:",625,90);
		g.drawString("Assumed/Estimated regional",660,90);
		float maxY,iniano ;
		double ano;
		obj1 = new Object[i_no_obs+1][4];
		float prevx = 150;
		float prevy;
		iniano = (float)(MODTOHAFSD_CalculateValues.input_nob_gob[1]-MODTOHAFSD_DrawGraph.fc[1]);
		if(iniano < 0){
			maxY = (float) MODTOHAFSD_CalculateValues.input_min_ano;
			prevy = (float)( 300 + ( 250 *( MODTOHAFSD_CalculateValues.input_nob_gob[1]-MODTOHAFSD_DrawGraph.fc[1]) / maxY ) );//starting point for the y-axis
		}
		else{
			maxY = (float) MODTOHAFSD_CalculateValues.input_max_ano;
			prevy = (float)( 300 - ( 250 *( MODTOHAFSD_CalculateValues.input_nob_gob[1]-MODTOHAFSD_DrawGraph.fc[1]) / maxY ) );
		}
		float xpoint = 0;
		float ypoint = 0;


		for (int k = 1; k <= MODTOHAFSD_CalculateValues.input_n_obs; k++) {

			xpoint = (float)( 450 * MODTOHAFSD_CalculateValues.x[k] / maxX1);
			ano = (float)MODTOHAFSD_CalculateValues.input_nob_gob[k]-MODTOHAFSD_GetConstReg.input_reg_val[k];
			MODTOHAFSD_CalculateValues.in_gob[k] = ano;
			if(ano<0){
				maxY = (float) MODTOHAFSD_CalculateValues.input_min_ano;
				ypoint = 300+(float)( ( 250 * (ano) / maxY ) );
			}
			else{
				maxY = (float) MODTOHAFSD_CalculateValues.input_max_ano;
				ypoint = 300-(float)( ( 250 * (ano) / maxY ) );
			}

			g.setFont(new Font("Arial", 20, 20));
			g.setColor(Color.DARK_GRAY);
			g.draw(new Line2D.Float(prevx, prevy, 150 + xpoint,  ypoint  ));

			prevx = 150 + xpoint;
			prevy =  ypoint ;
			DecimalFormat df =new DecimalFormat("0.###");
			obj1[k][0]= "" + MODTOHAFSD_CalculateValues.x[k];
			obj1[k][1]= "" + df.format(MODTOHAFSD_CalculateValues.input_nob_gob[k]);
			obj1[k][2]= "" + df.format(MODTOHAFSD_GetConstReg.input_reg_val[k]);
			obj1[k][3]= "" + df.format(ano);
		}   
	}
}

