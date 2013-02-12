package com.modtohafsd.view;

import java.applet.Applet;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import com.modtohafsd.model.MODTOHAFSD_CalculateValues;
import com.modtohafsd.util.MODTOHAFSD_Utility;
import com.modtohafsd.view.constant.MODTOHAFSD_GetConstReg;


public class MODTOHAFSD_DrawGraph extends Applet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	float maxZ,maxX,maxY,diff;
	public static double[] fc;
	double obs[];

	public void drawGraph(Graphics2D g2) {

		g2.setFont(new Font("Arial", 20, 12));
		g2.setColor(Color.BLACK);
		maxX = (float) MODTOHAFSD_CalculateValues.x[MODTOHAFSD_CalculateValues.input_n_obs];
		diff = (float) ( MODTOHAFSD_CalculateValues.input_ddx_km / 2);
		diff = (float) (450 * diff / maxX);		
		g2.draw(new Line2D.Float(150-diff, 50 + 20,150-diff , 550 + 20));
		g2.drawLine(70, 45, 1040, 45);

		g2.drawString("DISTANCE(km)", 490, 315);
		String []a={"A", "N", "O", "M", "A", "L", "Y", "(m", "G", "a", "l", "s)"};
		String []b={"D", "E", "P", "T", "H", "(k", "m)"}; 
		for (int i = 0; i < a.length; i++) {

			g2.drawString(""+a[i], 80, 20 + 60 + ( i * 20 ) );
		}

		for (int i = 0; i < b.length; i++) {

			g2.drawString(""+b[i], 80, 20 + 350 + ( i * 20 ) ); 	
		}

	}

	public void plot(Graphics2D g) {

		g.setFont(new Font("Arial", 20, 12));
		g.setColor(Color.black);
		maxX = (float) MODTOHAFSD_CalculateValues.x[MODTOHAFSD_CalculateValues.input_n_obs];
		maxY = (float) MODTOHAFSD_CalculateValues.input_min_ano;
		double inidep = MODTOHAFSD_Utility.findMaximumNumber1(MODTOHAFSD_CalculateValues.o_dep); 
		maxZ = (float) inidep;
		DecimalFormat df = new DecimalFormat("0.#");
		diff = (float) ( MODTOHAFSD_CalculateValues.input_ddx_km / 2);
		diff = (float) (450 * diff / maxX);	
		g.drawString("0",120-diff,120);
		g.drawString("-",146-diff,124);
		g.drawString(""+MODTOHAFSD_CalculateValues.input_max_ano  ,115-diff,70);
		g.drawString("-",146-diff,74);
		g.drawString("|", 600, 330);
		g.drawString(""+df.format(MODTOHAFSD_CalculateValues.x[MODTOHAFSD_CalculateValues.input_n_obs]),597,350);
		float points = maxX / 5;
		int zInterval=50;
		DecimalFormat f = new DecimalFormat("0.#");
		int xInterval=90; 
		g.drawString("|",150 , 330);
		g.drawString("0",147, 350);
		for (int x = xInterval, j = 1; x < 600; x+=xInterval) {

			if(j > 4)
				break;
			g.drawString("|",150 + x, 330);
			g.drawString("" + f.format(points*j), 147 + x, 350);
			j++;
		}


		float point = maxZ / 5 ;
		for (int x = zInterval + 250,j = 1; x < 550; x+=zInterval) {

			g.drawString("-", 146-diff, 50 + x + 23);
			g.drawString("" +df.format(point * j), 115-diff, 50 + x +20);
			j++;
		}
	}


	public void plotXYCoordinates (Graphics2D g2){

		g2.setFont(new Font("Arial", 20, 12));
		float maxY1;
		float prevx = 150;
		float prevy = 0;
		float prevy1 = 0;
		if (MODTOHAFSD_CalculateValues.o_gc[1] < 0){
			maxY1 = (float) MODTOHAFSD_CalculateValues.input_min_ano;
			prevy = 120 + (float)( ( 200 * (MODTOHAFSD_CalculateValues.in_gob[1]) / maxY1 ) );
		}
		else{
			maxY1 = (float) MODTOHAFSD_CalculateValues.input_max_ano;
			prevy = 120 - (float)( ( 50 * (MODTOHAFSD_CalculateValues.in_gob[1]) / maxY1 ) );
		}
		if (MODTOHAFSD_CalculateValues.input_nob_gob[1] < 0){
			maxY1 = (float) MODTOHAFSD_CalculateValues.input_min_ano;
			prevy1 = 120 + (float)( ( 200 * (MODTOHAFSD_CalculateValues.input_nob_gob[1]) / maxY1 ) );
		}
		else{
			maxY1 = (float) MODTOHAFSD_CalculateValues.input_max_ano;
			prevy1 = 120 - (float)( ( 50 * (MODTOHAFSD_CalculateValues.input_nob_gob[1]) / maxY1 ) );
		}

		float xpoint = 0;
		float ypoint = 0;
		float gypoint = 0;         
		float gpoint = 0;

		for (int k = 1; k <= MODTOHAFSD_CalculateValues.input_n_obs; k++) {

			xpoint = (float)( 450 * MODTOHAFSD_CalculateValues.x[k] / maxX);
			//ypoint = (float)( ( 200 * MODTOHAFSD_CalculateValues.o_gc[k] / maxY ) );
			//gypoint = (float)( (200 * MODTOHAFSD_CalculateValues.in_gob[k] / maxY ) );

			if (MODTOHAFSD_CalculateValues.input_nob_gob[k] < 0){
				maxY1 = (float) MODTOHAFSD_CalculateValues.input_min_ano;
				gpoint = 120 + (float)( ( 200 * (MODTOHAFSD_CalculateValues.input_nob_gob[k]) / maxY1 ) );
			}
			else{
				maxY1 = (float) MODTOHAFSD_CalculateValues.input_max_ano;
				gpoint = 120 - (float)( ( 50 * (MODTOHAFSD_CalculateValues.input_nob_gob[k]) / maxY1 ) );
			}
			if (MODTOHAFSD_CalculateValues.o_gc[k] < 0){
				maxY1 = (float) MODTOHAFSD_CalculateValues.input_min_ano;
				ypoint = 120 + (float)( ( 200 * (MODTOHAFSD_CalculateValues.o_gc[k]) / maxY1 ) );
			}
			else{
				maxY1 = (float) MODTOHAFSD_CalculateValues.input_max_ano;
				ypoint = 120 - (float)( ( 50 * (MODTOHAFSD_CalculateValues.o_gc[k]) / maxY1 ) );
			}
			if (MODTOHAFSD_CalculateValues.in_gob[k] < 0){
				maxY1 = (float) MODTOHAFSD_CalculateValues.input_min_ano;
				gypoint = 120 + (float)( ( 200 * (MODTOHAFSD_CalculateValues.in_gob[k]) / maxY1 ) );
			}
			else{
				maxY1 = (float) MODTOHAFSD_CalculateValues.input_max_ano;
				gypoint = 120 - (float)( ( 50 * (MODTOHAFSD_CalculateValues.in_gob[k]) / maxY1 ) );
			}

			g2.setFont(new Font("Arial", 20, 20));
			g2.setColor(Color.DARK_GRAY);
			g2.draw(new Line2D.Float(prevx, prevy, 150 + xpoint,  gypoint  ));
			g2.setColor(Color.RED);
			g2.draw(new Line2D.Float(prevx, prevy1, 150 + xpoint, gpoint ));

			g2.setColor(Color.BLUE);
			g2.setFont(new Font("Arial", 20, 40));
			g2.drawString(".", 150 + xpoint -6 ,  ypoint + 3);

			prevx = 150 + xpoint;
			prevy = gypoint;
			prevy1 = gpoint;

		}   
	}


	public void plotXPoly (Graphics2D g2,int diff,int mul,int mul1) {

		int i_no_obs = MODTOHAFSD_CalculateValues.input_n_obs;
		obs = new double[i_no_obs + 1];
		for (int i = 1; i <= i_no_obs; i++) {
			obs[i] = MODTOHAFSD_CalculateValues.x[i];
		}
		maxX = (float) obs[i_no_obs];
		float s = 0;
		fc = new double[i_no_obs + 1];
		float spoint = 150;
		float fcpoint ;
		if (MODTOHAFSD_CalculateValues.d_cftnt_arr[1]<0){
			maxY = (float)(MODTOHAFSD_CalculateValues.input_min_ano);
			fcpoint = diff + (float)( mul * MODTOHAFSD_CalculateValues.d_cftnt_arr[1] / maxY); 
		}
		else{
			maxY = (float)Math.abs(MODTOHAFSD_CalculateValues.input_max_ano);
			fcpoint = diff - (float)( mul1 * MODTOHAFSD_CalculateValues.d_cftnt_arr[1] / maxY); 
		}
		float xpoint = 0;
		float ypoint = 0;
		for (int j = 1;j <= i_no_obs; j++) {
			fc[j] = 0;			
		}
		for (int j = 1; j <= i_no_obs; j++) {

			s = (float) MODTOHAFSD_CalculateValues.x[j];

			for (int i = 1; i<=MODTOHAFSD_CalculateValues.i_d_poly + 1; i++){
				fc[j] = (float) (fc[j] + MODTOHAFSD_CalculateValues.d_cftnt_arr[i] * Math.pow(s, i - 1));
				MODTOHAFSD_GetConstReg.input_reg_val[j] = fc[j];             
			}
			xpoint = (float)( 450 * s / maxX);
			if(fc[j]<0){
				maxY = (float)(MODTOHAFSD_CalculateValues.input_min_ano);
				ypoint = diff + (float)( mul * fc[j] / maxY);
			}
			else{
				maxY = (float)Math.abs(MODTOHAFSD_CalculateValues.input_max_ano);
				ypoint = diff - (float)( mul1 * fc[j] / maxY);
			}

			g2.setColor(Color.GREEN);
			g2.draw(new Line2D.Float(spoint, fcpoint, (float)150 + xpoint, ypoint));
			spoint = (float) 150 + xpoint;
			fcpoint =  ypoint;
		}
	}


	public void plotZCoordinates (Graphics2D g2) {

		g2.setFont(new Font("Arial", 20, 12));
		float xpoint = 0;
		float xpoint1 = 0;
		float zpoint = 0;	
		g2.setColor(Color.BLACK);
		g2.drawString("0", 105,330);
		DecimalFormat f = new DecimalFormat("0.#");
		int yInterval=50;
		float points = maxY / 4;
		for (int x = yInterval, j = 1; x < 250; x+=yInterval){

			g2.drawString("-", 146-diff, 100 + x + 24);
			g2.drawString("" + f.format(points * j), 115-diff, 100 + x + 20);
			j++;
		}
		g2.setColor(Color.RED);
		g2.fill(new Rectangle2D.Float(150-diff, 321, 450+diff+diff , 250 ));
		Color col[] = {Color.YELLOW,Color.PINK,Color.ORANGE,Color.PINK,Color.YELLOW,Color.ORANGE,Color.WHITE,Color.PINK,Color.YELLOW,Color.ORANGE,Color.WHITE};
		Color col1[] = {Color.MAGENTA,Color.GREEN,Color.BLUE,Color.BLUE,Color.CYAN,Color.BLUE,Color.GREEN,Color.CYAN,Color.GREEN,Color.DARK_GRAY,Color.MAGENTA};
		for (int i = 0; i < MODTOHAFSD_CalculateValues.input_lambda_st; i++){
			for (int k = 1; k <= MODTOHAFSD_CalculateValues.input_n_obs; k++) {

				if(i>11){
					col[i] = Color.YELLOW;
					col1[i] = Color.MAGENTA;
				}
				diff = (float) ( MODTOHAFSD_CalculateValues.input_ddx_km / 2);
				diff = (float) (450 * diff / maxX);		
				xpoint = (float) (450 * MODTOHAFSD_CalculateValues.x[k] / maxX);
				if (k == 1){
					GradientPaint gradient = new GradientPaint(10, 10, Color.YELLOW, 30, 200, Color.MAGENTA, true);
					g2.setPaint(gradient);
					zpoint =(float) (250 * MODTOHAFSD_CalculateValues.o_dep[k] / maxZ);
					g2.fill(new Rectangle2D.Float(150-diff, 320,diff+diff, zpoint ));
					//g2.fill(new Rectangle2D.Float(150+xpoint, 320, diff, zpoint ));
				}
				else{
					if(k<MODTOHAFSD_CalculateValues.input_n_obs) {
						xpoint1 = (float) (450 * MODTOHAFSD_CalculateValues.x[k+1] / maxX);
					}
					zpoint =(float) (250 * MODTOHAFSD_CalculateValues.o_dep[k] / maxZ);

					GradientPaint gradient = new GradientPaint(10, 10, col[i], 30, 200, col1[i], true);
					g2.setPaint(gradient);
					g2.fill(new Rectangle2D.Float(150+xpoint-diff ,320,xpoint1-xpoint, zpoint ));
				}
				if(k==MODTOHAFSD_CalculateValues.input_n_obs){
					zpoint =(float) (250 * MODTOHAFSD_CalculateValues.o_dep[k]/ maxZ);
					g2.fill(new Rectangle2D.Float(150+xpoint-diff, 320,diff+diff, zpoint ));
					//g2.fill(new Rectangle2D.Float(150+xpoint-diff ,320,diff, zpoint ));
				}

			}   
		}
		g2.setColor(Color.BLACK);
		g2.draw(new Line2D.Float(150-diff, 320, 600+diff, 320 ));
	}

	public void plotZCoordinatesele (Graphics2D g2) {

		g2.setFont(new Font("Arial", 20, 12));
		DecimalFormat f = new DecimalFormat("0.#");
		int yInterval=50;
		float points = maxY / 4;
		for (int x = yInterval, j = 1; x < 250; x+=yInterval){
			if(j>3)
				break;
			g2.drawString("-", 146-diff, 100 + x + 24);
			g2.drawString("" + f.format(points * j), 115-diff, 100 + x + 20);
			j++;
		}

		float maxEle = (float) MODTOHAFSD_Utility.findMaximumNumber1(MODTOHAFSD_CalculateValues.input_ele_km);
		maxEle = Math.abs(maxEle);
		float xpoint = 0;
		float prevx = 150;
		float elepoint[] = new float[MODTOHAFSD_CalculateValues.input_n_obs+1];
		float preelepoint[] = new float[MODTOHAFSD_CalculateValues.input_n_obs+1];
		float dis[] = new float[MODTOHAFSD_CalculateValues.input_n_obs+1];
		float predis[] = new float[MODTOHAFSD_CalculateValues.input_n_obs+1];
		float preele = 320 - (float)( ( 25 * Math.abs(MODTOHAFSD_CalculateValues.input_ele_km[1]) / maxEle ) );
		float xpoint1 = 0;
		float zpoint = 0;	

		g2.setColor(Color.RED);
		g2.fill(new Rectangle2D.Float(150-diff, 295, 450+diff+diff , 275 ));
		Color col = Color.YELLOW;
		Color col1 = Color.MAGENTA;

		for (int k = 1; k <= MODTOHAFSD_CalculateValues.input_n_obs; k++) {


			diff = (float) ( MODTOHAFSD_CalculateValues.input_ddx_km / 2);
			diff = (float) (450 * diff / maxX);		
			xpoint = (float) (450 * MODTOHAFSD_CalculateValues.x[k] / maxX);
			if (k == 1){
				GradientPaint gradient = new GradientPaint(10, 10, col, 30, 190, col1, true);
				g2.setPaint(gradient);
				zpoint =(float) (275 * MODTOHAFSD_CalculateValues.o_dep[k] / maxZ);
				g2.fill(new Rectangle2D.Float(150-diff, 295,diff+diff, zpoint ));
				//g2.fill(new Rectangle2D.Float(150+xpoint, 295, diff, zpoint ));
			}
			else{
				if(k<MODTOHAFSD_CalculateValues.input_n_obs) {
					xpoint1 = (float) (450 * MODTOHAFSD_CalculateValues.x[k+1] / maxX);
				}
				zpoint =(float) (275 * MODTOHAFSD_CalculateValues.o_dep[k] / maxZ);
				GradientPaint gradient = new GradientPaint(10, 10, col, 30, 190, col1, true);
				g2.setPaint(gradient);
				g2.fill(new Rectangle2D.Float(150+xpoint-diff ,295,xpoint1-xpoint, zpoint ));
			}
			if(k==MODTOHAFSD_CalculateValues.input_n_obs){
				zpoint =(float) (275 * MODTOHAFSD_CalculateValues.o_dep[k]/ maxZ);
				g2.fill(new Rectangle2D.Float(150+xpoint-diff, 295,diff+diff, zpoint ));
				//g2.fill(new Rectangle2D.Float(150+xpoint-diff ,295,diff, zpoint ));
			}

		}   

		for (int k = 1; k <= MODTOHAFSD_CalculateValues.input_n_obs; k++) {
			g2.setColor(Color.BLACK);
			xpoint = (float) (450 * MODTOHAFSD_CalculateValues.x[k] / maxX);
			dis[k] = xpoint;
			elepoint[k] = 320 - (float) (25 * Math.abs(MODTOHAFSD_CalculateValues.input_ele_km[k]) / maxEle);
			preelepoint[k]= preele;
			predis[k]= prevx;
			g2.draw(new Line2D.Float(prevx, preele, (float)150 + xpoint, elepoint[k]));
			prevx = 150+xpoint;
			preele = elepoint[k];
		}
		for(float i = (float)1.0; i<=25;){
			for (int k = 1; k <= MODTOHAFSD_CalculateValues.input_n_obs; k++) {
				g2.setColor(Color.WHITE);
				g2.draw(new Line2D.Float(predis[k], preelepoint[k]-i, (float)150+dis[k], elepoint[k]-i));
			}
			i=(float) (i+0.5);

		}
		g2.setColor(Color.BLACK);
		g2.drawLine(150, 50 + 20, 150, 550 + 20);
		g2.drawString("DISTANCE(km)", 333, 315);
	}

	public void drawOBJ(Graphics2D g2) {

		g2.setFont(new Font("Arial", 20, 12));
		g2.setColor(Color.BLACK);
		g2.drawLine(780, 45, 780, 580);
		g2.drawLine(70, 580, 1040, 580);
		g2.drawLine(1040, 45, 1040, 580); 
		g2.drawLine(70, 45, 70, 580);

		g2.drawLine(820, 70, 820, 160);
		g2.drawLine(820, 160, 910, 160);
		g2.drawString("J", 800, 90);

		double maxOb = MODTOHAFSD_Utility.findMaximumNumber1(MODTOHAFSD_CalculateValues.o_funct);
		int ini = MODTOHAFSD_Utility.findMaximumNumber(MODTOHAFSD_CalculateValues.o_iter);
		if(ini == 5)
			ini = ini + 1;
		int maxiter = ( ini / 3 * 5 ) * 2;
		int point;
		int	xInterval = 22;
		point = ( ( ini ) / 3 * 5 ) / 5;
		for (int x = xInterval, j = 1; x < 90 ; x += xInterval) {
			if(ini>1000){
				g2.drawString("'", 821 + x, 170);
				g2.drawString("" + (point * j), 820 + x-20 +(5*j) , 175);
			}
			else{
				g2.drawString("'", 821 + x, 170);
				g2.drawString("" + (point * j), 820 + x - 3, 175);
			}
			j++;
		}
		float prevx = 820;
		float prevy = 70;
		float xpoint = 0;
		float ypoint = 0;
		DecimalFormat d1= new DecimalFormat("0.#####");
		for (int i = 1; i <= MODTOHAFSD_CalculateValues.o_iter; i++) {

			xpoint = (float)( 250 * i / maxiter );
			ypoint = 70 + (float) - ( ( 90 * (MODTOHAFSD_CalculateValues.o_funct[i]) / maxOb ) );
			if(i == MODTOHAFSD_CalculateValues.o_iter){
				g2.draw(new Line2D.Float(prevx, prevy, 820 + xpoint - 4, 90 + ypoint));
			}
			else {
				g2.draw(new Line2D.Float(prevx, prevy, 820 + xpoint, 90 + ypoint));
			}
			prevx = 820 + xpoint;
			prevy = 90 + ypoint;

		}
		DecimalFormat d= new DecimalFormat("0.#");
		g2.drawString(" "+d.format(MODTOHAFSD_CalculateValues.o_funct[1]), 780, 70);
		g2.drawString(" "+d1.format(MODTOHAFSD_CalculateValues.o_funct[MODTOHAFSD_CalculateValues.o_iter]), 820 + xpoint, 90 + ypoint);
		g2.setFont(new Font("Arial", 40,11));
		g2.drawString ("Iterations",850,186); 
	}

	public void drawSd(Graphics2D g2,Color col,int n) {

		g2.setFont(new Font("Arial", 20, 12));
		g2.setColor(Color.black);
		g2.drawLine(780, 200, 1040, 200);
		g2.setColor(Color.red);
		g2.setFont(new Font("Arial", 20, 12));
		DecimalFormat d= new DecimalFormat("0.#");
		DecimalFormat d1= new DecimalFormat("0.###");
		double inidep = MODTOHAFSD_Utility .findMaximumNumber1(MODTOHAFSD_CalculateValues.o_dep);
		maxZ = (float) inidep;	
		g2.draw(new Line2D.Float(820, 320, 820,  570));

		g2.drawString(""+d.format(inidep), 800, (float)(320 +  250 * inidep / maxZ));
		g2.drawString("-",820,(float)(320 +  250 * inidep / maxZ) + 2);
		g2.drawString("0",807 , 320);
		g2.drawLine(820, 320, 910, 320);

		double maxOb1 = Math.abs(MODTOHAFSD_Utility.findMaximumNumber1(MODTOHAFSD_CalculateValues.input_sd_val));

		DecimalFormat df = new DecimalFormat("0.#"); 
		float points = maxZ / 5 ;
		int zInterval = 50;
		for (int x = zInterval+250,j = 1; x < 550; x+=zInterval) {

			if(j>4)
				break;

			g2.drawString("-", 820, 50 + x + 22);
			g2.drawString("" +df.format(points * j), 800,50 + x + 20);
			j++;

		}

		float prevx = 820+ (float) ( ( 90 * ( Math.abs(MODTOHAFSD_CalculateValues.vsd[1] )) / maxOb1 ) );
		float prevy = 320;
		float xpoint = 0;
		float ypoint = 0;
		for (int i = 1; i <= MODTOHAFSD_CalculateValues.count; i++) {
			xpoint = (float)( 90 * Math.abs(MODTOHAFSD_CalculateValues.vsd[i]) / maxOb1 );
			ypoint = (float)( 250 * MODTOHAFSD_CalculateValues.dep[i] / maxZ );
			g2.setColor(col);
			g2.draw(new Line2D.Float(prevx, prevy, 820 + xpoint, 320 + ypoint));
			prevx = 820 + xpoint;
			prevy = 320 + ypoint;
		}

		//g2.drawString(""+MODTOHAFSD_GetLambdaVal.start[n]+"-"+MODTOHAFSD_GetLambdaVal.end[n],940,335+n*15);
		//g2.drawString(""+d1.format(MODTOHAFSD_GetLambdaVal.lambda[n] ),990,335+n*15);
		g2.setColor(Color.black);
		g2.drawString(""+d1.format(-maxOb1 ),900 ,320 );
		g2.setFont(new Font("Arial", 20,15));
		g2.drawString("Variation of density contrast " , 800,235);
		g2.drawString("with depth" , 850,255);
		g2.setFont(new Font("Arial", 40,11));
		g2.drawString ("Density contrast",830,285);
		g2.drawString ("(gm/cc)",843,295);
		g2.drawString("Z(km)", 790,(float)( 320+((250*inidep/maxZ))/2));

	}

	public void idex(Graphics2D g){

		g.setColor(Color.BLUE);
		g.setFont(new Font("Arial", 20, 50));
		g.drawString(" ...    ",605,190);
		g.setFont(new Font("Arial", 20, 12));
		g.drawString("Modeled anomalies",660,190);
		g.setColor(Color.RED);
		g.drawString("____:",625,120);
		g.drawString("Bouguer anomalies",660,120);
		g.setColor(Color.DARK_GRAY);
		g.drawString("____:",625,170);
		g.drawString("Residual anomalies",660,170);
		g.setColor(Color.GREEN);
		g.drawString("____:",625,140);
		g.drawString("Assumed/Estimated",660,140);
		g.drawString(" regional",670,150);
		GradientPaint gradient = new GradientPaint(10, 10, Color.yellow, 30, 100, Color.MAGENTA, true);
		g.setPaint(gradient);
		g.fillRect(645, 395, 30, 20);
		g.setColor(Color.black);
		g.drawString("Estimated depth ",680,410);
		g.drawString(" structure",685,420);
		g.setColor(Color.red);
		g.fillRect(645, 425, 30, 20);
		g.setColor(Color.black);
		g.drawString("Basement ",680,440);
	}

}

