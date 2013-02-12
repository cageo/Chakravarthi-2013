package com.modtohafsd.control;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DecimalFormat;
import javax.swing.*;

import com.modtohafsd.model.MODTOHAFSD_CalculateValues;
import com.modtohafsd.util.MODTOHAFSD_HandleException;
import com.modtohafsd.view.*;
import com.modtohafsd.view.constant.*;
import com.modtohafsd.view.event.*;
import com.modtohafsd.view.graph.MODTOHAFSD_PlainGraph;

public class MODTOHAFSD_Controller implements ActionListener{

	com.modtohafsd.model.MODTOHAFSD_CalculateValues cv = new com.modtohafsd.model.MODTOHAFSD_CalculateValues();
	Object rowdata[][]={};
	public static boolean success = false;

	public void actionPerformed(ActionEvent ae) {

		if(ae.getActionCommand().equals("Variable regional")) {

			cv.getAnamolyValues(com.modtohafsd.view.MODTOHAFSD_MainPanel.captureValues());
			MODTOHAFSD_MainPanel.im.removeMouseListener(MODTOHAFSD_PlotReg.ml);	
			MODTOHAFSD_MainPanel.im.removeMouseMotionListener((MouseMotionListener)MODTOHAFSD_PlotReg.ml);
			MODTOHAFSD_GetDegPoly gdp = new MODTOHAFSD_GetDegPoly();
			gdp.constHaf();

		} else if(ae.getActionCommand().equals("Draw/Edit polynomial")){
			cv.getAnamolyValues(com.modtohafsd.view.MODTOHAFSD_MainPanel.captureValues());   
			if(MODTOHAFSD_CalculateValues.i_d_poly!=0){
				try{
					if (MODTOHAFSD_CalculateValues.len <= MODTOHAFSD_CalculateValues.i_d_poly){
						JFrame frame = null;
						JOptionPane.showMessageDialog(frame,
								"The polynomial fit requires\n"
								+"degree+1 data points.\n"
								+"Use additional data points",
								"Error!",
								JOptionPane.ERROR_MESSAGE);
						throw new MODTOHAFSD_HandleException();
					}
					cv.getCoefficients();
					Graphics g1 =  MODTOHAFSD_MainPanel.img.getGraphics();  
					MODTOHAFSD_EditReg er = new MODTOHAFSD_EditReg();

					er.paint(g1); 
					com.modtohafsd.view.MODTOHAFSD_TableView.populateEastPanel1(MODTOHAFSD_PlainGraph.obj1);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}

		}else if(ae.getActionCommand().equals("Surface density & Lambda")){

			MODTOHAFSD_TableView.populateEastPanel(rowdata);
			cv.getAnamolyValues(MODTOHAFSD_MainPanel.captureValues());
			DecimalFormat df = new DecimalFormat("0.####");
			MODTOHAFSD_LambInt li = new MODTOHAFSD_LambInt();
			String lamval = df.format(MODTOHAFSD_LambInt.lamval); 
			MODTOHAFSD_LambInt.lam.setText(lamval);
			String sdval = df.format(MODTOHAFSD_LambInt.sdval); 
			MODTOHAFSD_LambInt.sd.setText(sdval);
			li.constLamSd();
		}else if(ae.getActionCommand().equals("Modeling")) {

			MODTOHAFSD_MainPanel.p_Center.removeAll();
			MODTOHAFSD_MainPanel.p_Center.add(MODTOHAFSD_MainPanel.graphLabel);
			MODTOHAFSD_MainPanel.p_Center.add(MODTOHAFSD_MainPanel.img);
			MODTOHAFSD_MainPanel.img.setEditable(false);
			MODTOHAFSD_MainPanel.img.setBackground(Color.WHITE);
			MODTOHAFSD_MainPanel.p_Center.validate();
			MODTOHAFSD_MainPanel.p_East.removeAll();
			MODTOHAFSD_MainPanel.p_East.validate();
			MODTOHAFSD_TableView.populateEastPanel(rowdata);
			MODTOHAFSD_MainPanel.p_East.validate();
			com.modtohafsd.view.MODTOHAFSD_MainPanel.clearPanel(MODTOHAFSD_MainPanel.img);
			cv.getAnamolyValues(com.modtohafsd.view.MODTOHAFSD_MainPanel.captureValues());
			cv.cal();
			com.modtohafsd.view.MODTOHAFSD_TableView.populateEastPanel(MODTOHAFSD_CalculateValues.obj);
			com.modtohafsd.view.MODTOHAFSD_MainPanel.p_East.repaint();
			com.modtohafsd.view.MODTOHAFSD_MainView mv = new com.modtohafsd.view.MODTOHAFSD_MainView();

			mv.setResizable(true);

		}else if(ae.getActionCommand().equals("Inversion")) {

			MODTOHAFSD_CalculateValues.obj = null;
			MODTOHAFSD_TableView.populateEastPanel(rowdata);
			MODTOHAFSD_MainPanel.p_Center.removeAll();
			MODTOHAFSD_MainPanel.p_Center.add(MODTOHAFSD_MainPanel.graphLabel);
			MODTOHAFSD_MainPanel.p_Center.add(MODTOHAFSD_MainPanel.img);
			MODTOHAFSD_MainPanel.img.setEditable(false);
			MODTOHAFSD_MainPanel.img.setBackground(Color.WHITE);
			MODTOHAFSD_MainPanel.p_Center.validate();
			MODTOHAFSD_MainPanel.p_East.removeAll();
			MODTOHAFSD_MainPanel.p_East.validate();
			MODTOHAFSD_TableView.populateEastPanel(rowdata);
			MODTOHAFSD_MainPanel.p_East.validate();
			com.modtohafsd.view.MODTOHAFSD_MainPanel.clearPanel(MODTOHAFSD_MainPanel.img);
			cv.getAnamolyValues(com.modtohafsd.view.MODTOHAFSD_MainPanel.captureValues());
			cv.invcal();
			com.modtohafsd.view.MODTOHAFSD_TableView.populateEastPanel(MODTOHAFSD_CalculateValues.obj);
			com.modtohafsd.view.MODTOHAFSD_MainPanel.p_East.repaint();
			com.modtohafsd.view.MODTOHAFSD_MainView mv = new com.modtohafsd.view.MODTOHAFSD_MainView();

			mv.setResizable(true);

		}else if(ae.getActionCommand().equals("Save and Print")){

			try{
				String current = System.getProperty("user.dir");
				File img_file = new File(MODTOHAFSD_CalculateValues.input_area_name+".jpg");
				JFileChooser saveFile = new JFileChooser(current);
				File OutFile = saveFile.getSelectedFile();
				FileWriter myWriter = null;   
				if(saveFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){   

					OutFile = saveFile.getSelectedFile();   

					if (OutFile.canWrite() || !OutFile.exists()){   

						File dir = new File(OutFile.getParent());
						success = img_file.renameTo(new File(dir,img_file.getName()));
						System.out.println("Save successful"+success);
						myWriter = new FileWriter(OutFile+".html");   
						myWriter.write(" </table> </td> <td> <img src = '"+ MODTOHAFSD_CalculateValues.input_area_name +".jpg'></td></tr></table>");
						myWriter.write("<html> <Body onLoad = \"window.print()\"><table> <tr> <td>" +
								"<table border = 1> <tr> <th colspan = 4>LOCATION:- "+MODTOHAFSD_CalculateValues.input_area_name+"</th> </tr>");      
						DecimalFormat df =new DecimalFormat("0.###");
						DecimalFormat d = new DecimalFormat("0.##");
						//myWriter.write(" <tr><th colspan = 4> PROFILE NUMBER:-"+"     "+MODTOHAFSD_CalculateValues.input_profile_num+" </th></tr>");
						myWriter.write(" <tr><th colspan = 4> ITERATION NUMBER:-"+"     "+MODTOHAFSD_CalculateValues.o_iter+" </th></tr>");
						myWriter.write("<tr > <th>Distance (km) </th><th> Bouguer anamolies (mGal) </th> <th>  Assumed/Estimated regional (mGal) </th><th> Residual anomalies(mGal) </th><th>Modeled anomalies(mGal) </th><th>Error(mGal) </th><th>Depth(km) </th> </tr>");

						for ( int K = 1; K <= MODTOHAFSD_CalculateValues.input_n_obs; K++){

							myWriter.write("<tr> <td>" + d.format(MODTOHAFSD_CalculateValues.x[K])+"</td><td>"+df.format(MODTOHAFSD_CalculateValues.input_nob_gob[K])+"</td> <td>"+df.format(MODTOHAFSD_DrawGraph.fc[K])+"</td><td>"+df.format(MODTOHAFSD_CalculateValues.in_gob[K])+"</td><td>"+df.format(MODTOHAFSD_CalculateValues.o_gc[K])+"</td><td>"+df.format(MODTOHAFSD_CalculateValues.err[K])+"</td><td>"+df.format(MODTOHAFSD_CalculateValues.o_dep[K])+"</td></tr>");
						}   
						myWriter.write("</table>");

						DecimalFormat d1 =new DecimalFormat("0.####");
						myWriter.write("<BR>");
						myWriter.write("Polynomial coefficients of regional background:"+"<BR>");
						myWriter.write("-------------------------------------------<BR>");
						for ( int i = 1; i <= MODTOHAFSD_CalculateValues.i_d_poly + 1; i++) {

							myWriter.write("f"+ ( i - 1 ) +" = "+d1.format(MODTOHAFSD_CalculateValues. d_cftnt_arr[i])+"<BR>");
						}
						myWriter.write("<BR>");

						myWriter.close();
					}   
				}   
				else  
				{   
					//pops up error message    

				}   

			}  
			catch(Exception e1) {

				e1.printStackTrace();
			}
		}else if(ae.getActionCommand().equals("Load file")){

			MODTOHAFSD_MainPanel.loadData();
		}else if(ae.getActionCommand().equals("Clear")||ae.getActionCommand().equals("New")){

			MODTOHAFSD_MainPanel.clearDefaultValues();
			MODTOHAFSD_MainPanel.clearPanel(MODTOHAFSD_MainPanel.img);
			com.modtohafsd.view.MODTOHAFSD_TableView.populateEastPanel(rowdata);
			com.modtohafsd.view.MODTOHAFSD_TableView.val.setText("");
			MODTOHAFSD_CalculateValues.len = 0;
			setZero();
		}else if(ae.getActionCommand().equals("Save file")){

			try{
				String current = System.getProperty("user.dir");
				JFileChooser saveFile = new JFileChooser(current);
				File OutFile = saveFile.getSelectedFile();
				FileWriter myWriter = null;   
				if(saveFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){   

					OutFile = saveFile.getSelectedFile();   

					if (OutFile.canWrite() || !OutFile.exists()){   

						myWriter = new FileWriter(OutFile+".txt");   
						myWriter.write(""+MODTOHAFSD_CalculateValues.input_area_name);
						myWriter.append(System.getProperty("line.separator"));
						myWriter.write(""+MODTOHAFSD_CalculateValues.input_ite_num);
						myWriter.append(System.getProperty("line.separator"));
						myWriter.write(""+MODTOHAFSD_CalculateValues.input_n_obs);
						myWriter.append(System.getProperty("line.separator"));
						myWriter.write(""+MODTOHAFSD_CalculateValues.input_ddx_km);
						myWriter.append(System.getProperty("line.separator"));
						for (int i = 1; i <= MODTOHAFSD_CalculateValues.input_n_obs; i++){
							myWriter.write(""+MODTOHAFSD_CalculateValues.input_ele_km[i]+",") ;
						}
						myWriter.append(System.getProperty("line.separator"));
						for (int i = 1; i <= MODTOHAFSD_CalculateValues.input_n_obs; i++){
							myWriter.write(""+MODTOHAFSD_CalculateValues.input_nob_gob[i]+",") ;
						}
						myWriter.append(System.getProperty("line.separator"));
						myWriter.write(""+MODTOHAFSD_CalculateValues.input_min_ano);
						myWriter.append(System.getProperty("line.separator"));
						myWriter.write(""+MODTOHAFSD_CalculateValues.input_max_ano);
						myWriter.append(System.getProperty("line.separator"));
						myWriter.write(""+MODTOHAFSD_CalculateValues.input_al_err);
						myWriter.append(System.getProperty("line.separator"));
						myWriter.write(""+MODTOHAFSD_CalculateValues.input_zmax_km);
						myWriter.append(System.getProperty("line.separator"));
						myWriter.write(""+MODTOHAFSD_LambInt.sdval);
						myWriter.append(System.getProperty("line.separator"));
						myWriter.write(""+MODTOHAFSD_LambInt.lamval);
						myWriter.append(System.getProperty("line.separator"));
						for (int i = 1; i <= MODTOHAFSD_CalculateValues.input_n_obs; i++){
							myWriter.write(""+(MODTOHAFSD_CalculateValues.input_y_km[i])+",") ;
						}
						myWriter.append(System.getProperty("line.separator"));
						for (int i = 1; i <= MODTOHAFSD_CalculateValues.input_n_obs; i++){
							myWriter.write(""+(MODTOHAFSD_CalculateValues.input_strike_km[i])+",") ;
						}

						myWriter.append(System.getProperty("line.separator"));
						myWriter.write(""+MODTOHAFSD_CalculateValues.len);
						myWriter.append(System.getProperty("line.separator"));
						for (int i = 1; i <= MODTOHAFSD_CalculateValues.len; i++){
							myWriter.write(MODTOHAFSD_CalculateValues.d_x_km_arr[i]+",");
						}
						myWriter.append(System.getProperty("line.separator"));
						for (int i = 1; i <= MODTOHAFSD_CalculateValues.len; i++){
							myWriter.write(MODTOHAFSD_CalculateValues.d_z_km_arr[i]+",");
						}
						myWriter.append(System.getProperty("line.separator"));
						myWriter.write(""+MODTOHAFSD_CalculateValues.i_d_poly);
						myWriter.append(System.getProperty("line.separator"));
						for (int i = 1; i <= MODTOHAFSD_CalculateValues.i_d_poly+1; i++){
							myWriter.write(MODTOHAFSD_CalculateValues.d_cftnt_arr[i]+",");
						}
						myWriter.append(System.getProperty("line.separator"));	
						for (int i = 1; i <= MODTOHAFSD_CalculateValues.input_n_obs; i++){
							myWriter.write(""+(MODTOHAFSD_GetConstReg.input_reg_val[i])+",") ;
						}

						myWriter.close();
					}
				}
				else  
				{   
					//pops up error message    

				}   

			}  
			catch(Exception e1) {

				e1.printStackTrace();
			}
		}else if(ae.getActionCommand().equals("Constant offset")){
			cv.getAnamolyValues(MODTOHAFSD_MainPanel.captureValues());
			MODTOHAFSD_GetConstOff gc = new MODTOHAFSD_GetConstOff();
			gc.constOff();

		}else if(ae.getActionCommand().equals("Constant half strike")){

			cv.getAnamolyValues(MODTOHAFSD_MainPanel.captureValues());
			MODTOHAFSD_GetConstHaf gch = new MODTOHAFSD_GetConstHaf();
			gch.constHaf();
		}else if(ae.getActionCommand().equals("Constant regional")){
			cv.getAnamolyValues(MODTOHAFSD_MainPanel.captureValues());
			MODTOHAFSD_GetConstReg gcr = new MODTOHAFSD_GetConstReg();
			gcr.constReg();
			for(int i=1;i<=MODTOHAFSD_CalculateValues.input_n_obs;i++){
				MODTOHAFSD_CalculateValues.len=0;
			}
		}else if(ae.getActionCommand().equals("Variable offset")){

			try{
				MODTOHAFSD_TableView.populateEastPanel(rowdata);
				cv.getAnamolyValues(MODTOHAFSD_MainPanel.captureValues());
				MODTOHAFSD_OffsetVal ov = new MODTOHAFSD_OffsetVal();
				ov.val();
				DecimalFormat df = new DecimalFormat("0.##"); 

				for (int i = 1;i <= MODTOHAFSD_CalculateValues.input_n_obs; i++){
					for (int j = 0;j < 2; j++){
						MODTOHAFSD_OffsetVal.off[i-1][1]= df.format(MODTOHAFSD_GetConstOff.input_y_km[i]);
						MODTOHAFSD_OffsetVal.off[i-1][0]= df.format(i);
						MODTOHAFSD_OffsetVal.table.setValueAt(MODTOHAFSD_OffsetVal.off[i-1][j],i-1, j);	
					}
				}
			}
			catch(Exception e){

			}

		}else if(ae.getActionCommand().equals("Variable half strike")){

			try{
				MODTOHAFSD_TableView.populateEastPanel(rowdata);
				cv.getAnamolyValues(MODTOHAFSD_MainPanel.captureValues());
				MODTOHAFSD_HalfstrikeVal sv = new MODTOHAFSD_HalfstrikeVal();
				sv.val();
				DecimalFormat df = new DecimalFormat("0.##");

				for (int i = 1; i <= MODTOHAFSD_CalculateValues.input_n_obs; i++){
					for (int j = 0; j < 2; j++){
						MODTOHAFSD_HalfstrikeVal.haf[i-1][1]= df.format(MODTOHAFSD_GetConstHaf.input_strike_km[i]);
						MODTOHAFSD_HalfstrikeVal.haf[i-1][0]= df.format(i);
						MODTOHAFSD_HalfstrikeVal.table.setValueAt(MODTOHAFSD_HalfstrikeVal.haf[i-1][j],i-1, j);	
					}
				}
			}
			catch(Exception e){

			}
		}else if(ae.getActionCommand().equals("Flow module")){

			try {
				String path =  "Flow module.txt"; 
				File pptFile = new File(path);
				if (pptFile.exists()) {
					if (Desktop.isDesktopSupported()) {
						Desktop.getDesktop().open(pptFile);
					} else {
						System.out.println("Desktop is not supported!");
					}

				} else {
					System.out.println("File does not exists!");
				}

				System.out.println("Done");

			} catch (Exception ex) {
				JFrame frame = null;
				JOptionPane.showMessageDialog(frame,
						"System cannot open the file\n",
						"Error!",
						JOptionPane.ERROR_MESSAGE);

			}
		}else if(ae.getActionCommand().equals("Readme")){

			try {
				String path =  "Readme.txt"; 
				File pptFile = new File(path);
				if (pptFile.exists()) {
					if (Desktop.isDesktopSupported()) {
						Desktop.getDesktop().open(pptFile);
					} else {
						System.out.println("Desktop is not supported!");
					}

				} else {
					System.out.println("File does not exists!");
				}

				System.out.println("Done");

			} catch (Exception ex) {
				JFrame frame = null;
				JOptionPane.showMessageDialog(frame,
						"System cannot open the file\n",
						"Error!",
						JOptionPane.ERROR_MESSAGE);

			}
		}else if(ae.getActionCommand().equals("Exit")){
			JFrame frame = null;

			int r =	JOptionPane.showConfirmDialog(
					frame,
					"Exit MODTOHAFSD ?",
					"Confirm Exit ",
					JOptionPane.YES_NO_OPTION);
			if(r == JOptionPane.YES_OPTION )	
				if(success==false){
					String fileName = MODTOHAFSD_CalculateValues.input_area_name+".jpg";
					File f = new File(fileName);
					f.delete();
				}
			System.exit(0);
		}
	}
	public void setZero(){
		MODTOHAFSD_CalculateValues.input_area_name = "";
		MODTOHAFSD_CalculateValues.count=0;
		MODTOHAFSD_CalculateValues.d_cftnt_arr=null;
		MODTOHAFSD_CalculateValues.d_x_km_arr = null;
		MODTOHAFSD_CalculateValues.d_z_km_arr = null;
		MODTOHAFSD_CalculateValues.dep=null;
		MODTOHAFSD_CalculateValues.err=null;
		MODTOHAFSD_CalculateValues.err1=null;
		MODTOHAFSD_CalculateValues.i_d_poly = 0;
		MODTOHAFSD_CalculateValues.in_gob=null;
		MODTOHAFSD_CalculateValues.input_al_err=0;
		MODTOHAFSD_CalculateValues.input_ddx_km=0;
		MODTOHAFSD_CalculateValues.input_ele_km=null;
		MODTOHAFSD_CalculateValues.input_ite_num=0;
		MODTOHAFSD_CalculateValues.input_lambda_st=0;
		MODTOHAFSD_CalculateValues.input_max_ano=0;
		MODTOHAFSD_CalculateValues.input_min_ano=0;
		MODTOHAFSD_CalculateValues.input_n_obs=0;
		MODTOHAFSD_CalculateValues.input_nob_gob=null;
		MODTOHAFSD_CalculateValues.input_reg_val=null;
		MODTOHAFSD_CalculateValues.input_sd_val=null;
		MODTOHAFSD_CalculateValues.input_strike_km=null;
		MODTOHAFSD_CalculateValues.input_y_km=null;
		MODTOHAFSD_CalculateValues.input_zmax_km=0;
		MODTOHAFSD_CalculateValues.len=0;
		MODTOHAFSD_CalculateValues.np=0;
		MODTOHAFSD_CalculateValues.o_dep=null;
		MODTOHAFSD_CalculateValues.o_funct=null;
		MODTOHAFSD_CalculateValues.o_gc=null;
		MODTOHAFSD_CalculateValues.o_iter=0;
		MODTOHAFSD_CalculateValues.obj=null;
		MODTOHAFSD_CalculateValues.vsd=null;
		MODTOHAFSD_CalculateValues.x=null;


	}
}

