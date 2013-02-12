package com.modtohafsd.view;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import javax.swing.JFileChooser;
import com.modtohafsd.model.MODTOHAFSD_CalculateValues;
import com.modtohafsd.util.MODTOHAFSD_Utility;
import com.modtohafsd.view.constant.*;
import com.modtohafsd.view.event.MODTOHAFSD_PlotReg;



public class MODTOHAFSD_MainPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Panel p_North;
	public static Panel p_East;
	public static Panel p_Center;
	public static TextField inputValues [] = new TextField[15];
	public static TextArea img = new TextArea(46,140);	
	public static TextArea im = new TextArea(46,140);
	public static TextArea im1 = new TextArea(46,140);
	public static TextArea im2 = new TextArea(46,140);
	public static Object lamb[][];
	public static double arr[];
	public static Label graphLabel;
	Object rowdata[][]={};

	/**Field Area Name*/
	final static int AREA_FE = 0;
	/**Iterations*/
	final static int ITE_RAT = 1;
	/**Number of observations*/
	final static int N_OBS = 2 ;
	/**Distance(km)*/
	final static int X_KM = 3;
	/**Elevations(km)*/
	final static int ELE_KM = 4;
	/**observed anomalies*/
	final static int NOB_GOB = 5;
	/**Minimum anomaly*/
	final static int MIN_ANO = 6;
	/**Maximum anomaly*/
	final static int MAX_ANO =	7;
	/**Allowable Error*/
	final static int AL_ERR =	8;
	/**Maximum Depth*/
	final static int DEP_ZMAX =	9;


	public MODTOHAFSD_MainPanel(MODTOHAFSD_MainView cm){

		this.setLayout(new BorderLayout());

		p_East = new Panel ();
		p_Center = new Panel();
		p_North = new Panel ();
		graphLabel = new Label("GRAVITY MODELING OF 2.5-D SEDIMENTARY BASINS USING EXPONENTIAL DENSITY FUNCTION", Label.CENTER);
		graphLabel.setFont(new Font("Arial", 10, 18));
		p_Center.add(graphLabel);

		for(int i = 0; i < 10; i++){
			inputValues[i] = new TextField();
		}
		MODTOHAFSD_MainPanel.populateNorthPanel();
		MODTOHAFSD_TableView.populateEastPanel(rowdata);
		this.add(p_North, BorderLayout.NORTH);
		p_Center.setSize(1000, 760);
		this.add(p_Center, BorderLayout.CENTER);
		img.setEditable(false);
		p_Center.add(img);
		this.add(p_East, BorderLayout.EAST);
		this.setVisible(true);
		MenuBar mb = new MenuBar();
		cm.setMenuBar(mb);
		Menu file = new Menu("File");
		Menu file2 = new Menu("Model information");
		Menu file3 = new Menu("Interpretation");
		MenuItem i1,i2,i3,i5,i6,i7,i8,i21,i23,i10;
		file.add(i1 = new MenuItem("New"));
		file.add(i2 = new MenuItem("Load file"));
		file.add(i3 = new MenuItem("Save file"));
		file.add(i5 = new MenuItem("Clear"));
		file.add(i6 = new MenuItem("Exit"));
		Menu off  = new Menu("Offset");
		MenuItem off1,off2;
		off.add(off1 = new MenuItem("Constant offset"));
		off.add(off2 = new MenuItem("Variable offset"));
		file2.add(off);
		Menu half  = new Menu("Half strike");
		MenuItem h1,h2;
		half.add(h1 = new MenuItem("Constant half strike"));
		half.add(h2 = new MenuItem("Variable half strike"));
		file2.add(half);
		file2.addSeparator();
		file2.add(i7 = new MenuItem("Surface density & Lambda"));
		Menu reg  = new Menu("Regional");
		MenuItem r1,r2;
		reg.add(r1 = new MenuItem("Constant regional"));
		reg.add(r2 = new MenuItem("Variable regional"));
		file3.add(reg);
		file2.addSeparator();
		file3.add(i23 = new MenuItem("Draw/Edit polynomial"));
		file2.addSeparator();
		Menu mod  = new Menu("Analysis");
		MenuItem m1,m2;
		mod.add(m1 = new MenuItem("Modeling"));
		mod.add(m2 = new MenuItem("Inversion"));
		file3.add(mod);
		file3.add(i21 = new MenuItem("Save and Print"));
		Menu help  = new Menu("Help");
		help.add(i10 = new MenuItem("Flow module"));
		help.add(i8 = new MenuItem("Readme"));
		mb.add(file);
		mb.add(file2);
		mb.add(file3);
		mb.add(help);
		i1.addActionListener(new com.modtohafsd.control.MODTOHAFSD_Controller());
		i2.addActionListener(new com.modtohafsd.control.MODTOHAFSD_Controller());
		i3.addActionListener(new com.modtohafsd.control.MODTOHAFSD_Controller());
		i5.addActionListener(new com.modtohafsd.control.MODTOHAFSD_Controller());
		i6.addActionListener(new com.modtohafsd.control.MODTOHAFSD_Controller());
		i7.addActionListener(new com.modtohafsd.control.MODTOHAFSD_Controller());
		off1.addActionListener(new com.modtohafsd.control.MODTOHAFSD_Controller());
		off2.addActionListener(new com.modtohafsd.control.MODTOHAFSD_Controller());
		h1.addActionListener(new com.modtohafsd.control.MODTOHAFSD_Controller());
		h2.addActionListener(new com.modtohafsd.control.MODTOHAFSD_Controller());
		r1.addActionListener(new com.modtohafsd.control.MODTOHAFSD_Controller());
		r2.addActionListener(new com.modtohafsd.control.MODTOHAFSD_Controller());
		m1.addActionListener(new com.modtohafsd.control.MODTOHAFSD_Controller());
		m2.addActionListener(new com.modtohafsd.control.MODTOHAFSD_Controller());
		i21.addActionListener(new com.modtohafsd.control.MODTOHAFSD_Controller());
		i23.addActionListener(new com.modtohafsd.control.MODTOHAFSD_Controller());
		i10.addActionListener(new com.modtohafsd.control.MODTOHAFSD_Controller());
		i8.addActionListener(new com.modtohafsd.control.MODTOHAFSD_Controller());
	}

	public static void populateNorthPanel(){

		p_North.setLayout(new GridLayout(5,3));

		p_North.add(new Label("Area /Profile"));
		p_North.add(inputValues[0]);
		p_North.add(new Label("Number of iterations"));
		p_North.add(inputValues[1]);
		p_North.add(new Label("Number of observations"));
		p_North.add(inputValues[2]);
		p_North.add(new Label("Station interval (km)"));
		p_North.add(inputValues[3]);
		p_North.add(new Label("Elevations(km)"));
		p_North.add(inputValues[4]);
		p_North.add(new Label("Observed anomalies (mGal)"));
		p_North.add(inputValues[5]);
		p_North.add(new Label("Minimum anomaly (mGal)"));
		p_North.add(inputValues[6]);
		p_North.add(new Label("Maximum anomaly (mGal)"));
		p_North.add(inputValues[7]);
		p_North.add(new Label("Permissible error"));
		p_North.add(inputValues[8]);
		p_North.add(new Label("Maximum permissible depth (km)"));
		p_North.add(inputValues[9]);
	}

	public static HashMap captureValues(){

		HashMap h_Map = new HashMap();

		try {

			h_Map.put("N_OBS", inputValues[N_OBS].getText());
			h_Map.put("X_KM", inputValues[X_KM].getText());
			h_Map.put("ELE_KM", inputValues[ELE_KM].getText());
			h_Map.put("NOB_GOB", inputValues[NOB_GOB].getText());
			h_Map.put("MIN_ANO", inputValues[MIN_ANO].getText());
			h_Map.put("MAX_ANO", inputValues[MAX_ANO].getText());
			h_Map.put("AL_ERR", inputValues[AL_ERR].getText());
			h_Map.put("DEP_ZMAX", inputValues[DEP_ZMAX].getText());
			h_Map.put("AREA_FE",inputValues[AREA_FE].getText());
			h_Map.put("ITE_RAT",inputValues[ITE_RAT].getText());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return h_Map;

	}

	public static void clearPanel(TextArea p) {

		Graphics g = p.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1280, 650);
	}

	public static void loadData(){
		try{ 
			String current = System.getProperty("user.dir");
			JFileChooser chooser=new  JFileChooser(current);
			int returnVal = chooser.showOpenDialog(null);
			int count = 0;  
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				File f = chooser.getSelectedFile();
				BufferedReader br=new BufferedReader(new FileReader(f));
				String st;
				st = br.readLine();
				count++;

				while((st)!=null){

					try {

						if (count==1){
							MODTOHAFSD_MainPanel.inputValues[MODTOHAFSD_MainPanel.AREA_FE].setText(""+st);
						}
						if (count==2){
							MODTOHAFSD_MainPanel.inputValues[MODTOHAFSD_MainPanel.ITE_RAT].setText(""+st);
						}
						if (count==3){
							MODTOHAFSD_MainPanel.inputValues[MODTOHAFSD_MainPanel.N_OBS].setText(""+st);
						}
						if (count==4){
							MODTOHAFSD_MainPanel.inputValues[MODTOHAFSD_MainPanel.X_KM].setText(""+st);
						}
						if (count==5){
							MODTOHAFSD_MainPanel.inputValues[MODTOHAFSD_MainPanel.ELE_KM].setText(""+st);
						}
						if (count==6){
							MODTOHAFSD_MainPanel.inputValues[MODTOHAFSD_MainPanel.NOB_GOB].setText(""+st);
						}
						if (count==7){
							MODTOHAFSD_MainPanel.inputValues[MODTOHAFSD_MainPanel.MIN_ANO].setText(""+st);
						}
						if (count==8){
							MODTOHAFSD_MainPanel.inputValues[MODTOHAFSD_MainPanel.MAX_ANO].setText(""+st);
						}
						if (count==9){
							MODTOHAFSD_MainPanel.inputValues[MODTOHAFSD_MainPanel.AL_ERR].setText(""+st);
						}
						if (count==10){
							MODTOHAFSD_MainPanel.inputValues[MODTOHAFSD_MainPanel.DEP_ZMAX].setText(""+st);
						}
						if (count==11){
							MODTOHAFSD_LambInt.sdval = MODTOHAFSD_Utility.convertDouble(st);
						}
						if (count==12){
							MODTOHAFSD_LambInt.lamval = MODTOHAFSD_Utility.convertDouble(st);
						}
						if (count==13){
							MODTOHAFSD_GetConstOff.input_y_km = MODTOHAFSD_Utility.convertDoubleArray(st);
						}
						if (count==14){
							MODTOHAFSD_GetConstHaf.input_strike_km = MODTOHAFSD_Utility.convertDoubleArray(st);
						}
						if (count==15){
							MODTOHAFSD_CalculateValues.len = MODTOHAFSD_Utility.convertInteger(st);
						}
						if (count==16){
							MODTOHAFSD_PlotReg.val = MODTOHAFSD_Utility.convertDoubleArray(st);
						}
						if (count==17){
							MODTOHAFSD_PlotReg.val1 = MODTOHAFSD_Utility.convertDoubleArray(st);
						}
						if (count==18){
							MODTOHAFSD_GetDegPoly.input_deg_poly = MODTOHAFSD_Utility.convertInteger(st);
						}					
						if (count==19){
							MODTOHAFSD_CalculateValues.d_cftnt_arr = MODTOHAFSD_Utility.convertDoubleArray(st);
						}						
						if (count==20){
							MODTOHAFSD_GetConstReg.input_reg_val = MODTOHAFSD_Utility.convertDoubleArray(st);
						}
					}
					catch(Exception e) {

						e.printStackTrace();
					}
					st = br.readLine();
					count++;

				}
			}
		}
		catch(Exception e){

		}
	}


	public static void clearDefaultValues(){
		inputValues[N_OBS].setText("");
		inputValues[X_KM].setText("");
		inputValues[ELE_KM].setText("");
		inputValues[NOB_GOB].setText("");
		inputValues[MIN_ANO].setText("");
		inputValues[MAX_ANO].setText("");
		inputValues[AL_ERR].setText("");
		inputValues[DEP_ZMAX].setText("");
		inputValues[AREA_FE].setText("");
		inputValues[ITE_RAT].setText("");
	}
}