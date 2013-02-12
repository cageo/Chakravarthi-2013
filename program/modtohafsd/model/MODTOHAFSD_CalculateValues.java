package com.modtohafsd.model;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import javax.imageio.ImageIO;
import com.modtohafsd.util.MODTOHAFSD_HandleException;
import com.modtohafsd.util.MODTOHAFSD_Utility;
import com.modtohafsd.view.MODTOHAFSD_MainPanel;
import com.modtohafsd.view.MODTOHAFSD_TableView;
import com.modtohafsd.view.constant.*;
import com.modtohafsd.view.event.MODTOHAFSD_PlotReg;


public class MODTOHAFSD_CalculateValues {

	public static int input_n_obs,len,i_d_poly,input_ite_num,input_lambda_st = 0;
	public static double input_ddx_km,input_min_ano,input_max_ano=0;
	public double input_lambda_val[]=null;
	public static double input_sd_val[],input_nob_gob[],input_reg_val[],x[],input_ele_km[],o_dep[]= null;
	public static double reg[],err[],err1[],input_strike_km [],input_y_km[],d_cftnt_arr[],vsd[],dep[],o_funct[]=null;
	public static Object obj[][] = null;
	public static double d_x_km_arr[], d_z_km_arr[],input_zmax_km = 0;
	public double input_zmin_km=0;
	public static double input_al_err=0;
	public static double []o_gc ,in_gob=null; 
	static BufferedImage image;
	public static int o_iter,count=0;
	public static String input_area_name="";
	public static int np=0;
	double  PI = 22.0 / 7.0;
	double  GK = 13.3333;
	public void getAnamolyValues(HashMap h_Map) {

		try {
			input_n_obs = MODTOHAFSD_Utility.convertInteger((String)h_Map.get("N_OBS"));
			input_ddx_km = MODTOHAFSD_Utility.convertDouble((String)h_Map.get("X_KM"));
			input_ele_km = MODTOHAFSD_Utility.convertDoubleArray((String)h_Map.get("ELE_KM"));
			input_nob_gob = MODTOHAFSD_Utility.convertDoubleArray((String)h_Map.get("NOB_GOB"));
			input_min_ano = MODTOHAFSD_Utility.convertDouble((String)h_Map.get("MIN_ANO"));
			input_max_ano =  MODTOHAFSD_Utility.convertDouble((String)h_Map.get("MAX_ANO"));
			input_al_err =  MODTOHAFSD_Utility.convertDouble((String)h_Map.get("AL_ERR"));
			input_zmax_km =  MODTOHAFSD_Utility.convertDouble((String)h_Map.get("DEP_ZMAX"));
			input_area_name = MODTOHAFSD_Utility.convertString((String)h_Map.get("AREA_FE"));
			input_ite_num = MODTOHAFSD_Utility.convertInteger((String)h_Map.get("ITE_RAT"));
		}
		catch(Exception e) {

		}

		x = new double[input_n_obs+1];
		in_gob = new double[input_n_obs+1];
		for (int j = 1; j <= input_n_obs; j++){
			x[j]=0;	
			in_gob[j]=0;
		}
		input_y_km = MODTOHAFSD_GetConstOff.input_y_km;
		input_strike_km = MODTOHAFSD_GetConstHaf.input_strike_km;
		input_reg_val = MODTOHAFSD_GetConstReg.input_reg_val;
		i_d_poly = MODTOHAFSD_GetDegPoly.input_deg_poly;
		input_lambda_st = 1;
		for (int  KL = 1; KL <= input_n_obs; KL++) {
			x[KL] = (KL - 1) * input_ddx_km;
		}
	}

	public void cal(){

		double funct1 = 0.0;
		double  ALER = input_al_err;
		o_gc  = new double[input_n_obs + 1]; 
		o_dep = new double[input_n_obs + 1];
		input_lambda_val = new double[input_n_obs + 1];
		input_sd_val = new double[input_n_obs + 1];
		o_funct  = new double[input_ite_num +5];
		err = new double[input_n_obs + 1];
		err1 = new double[input_n_obs + 1];
		for (int i = 0; i <= input_n_obs; i++){
			o_gc[i]=0;
			o_dep[i]=0;
			input_lambda_val[i]=0;
			input_sd_val[i]=0;
			err[i]=0;
			err1[i]=0;
		}

		for (int j = 1; j <= input_n_obs; j++){
			input_lambda_val[j] = MODTOHAFSD_LambInt.lamval;
			input_sd_val[j] =  MODTOHAFSD_LambInt.sdval;
		}

		double dcz[] = new double[input_n_obs + 1];
		for (int KL = 1; KL <= input_n_obs; KL++) {
			in_gob[KL] = input_nob_gob[KL] - input_reg_val[KL];
		}
		double  b = (x[2] - x[1]) / 2.0;

		for (int KK = 1 ; KK <= input_n_obs; KK++) {
			o_dep[KK] = (1 / input_lambda_val[KK]) * Math.log(1 + ((input_lambda_val[KK] * in_gob[KK]) / (GK * PI * input_sd_val[KK])));
		}      
		GF(input_n_obs,x,input_ele_km,o_dep,input_sd_val,input_lambda_val,input_strike_km,input_y_km,input_ddx_km,b,o_gc);

		for(int K = 1 ; K <= input_n_obs; K++) {
			err[K] = in_gob[K] - o_gc[K];
			funct1 = funct1 + Math.pow((in_gob[K] - o_gc[K]), 2);
		}      

		int iter  = 0;
		double funct2 = 0.0;

		while(iter<input_ite_num)     { 
			iter = iter + 1;

			for (int ll = 1; ll <= input_n_obs; ll++){
				err1[ll] = in_gob[ll] - o_gc[ll];
			}
			for (int KK = 1; KK <= input_n_obs; KK++) {
				err[KK] = in_gob[KK] - o_gc[KK];
				dcz[KK] = (input_sd_val[KK] * Math.exp(input_lambda_val[KK] * o_dep[KK])); 
				o_dep[KK] = o_dep[KK] + ((err[KK] / (GK * PI * dcz[KK])));
				if(o_dep[KK] <= input_zmin_km)
					o_dep[KK] = input_zmin_km;

				if(o_dep[KK] > input_zmax_km )
					o_dep[KK] = input_zmax_km;
			}      
			GF(input_n_obs, x, input_ele_km,o_dep, input_sd_val, input_lambda_val, input_strike_km, input_y_km, input_ddx_km, b, o_gc);
			funct2 = 0.0;
			for (int LI = 1 ; LI <= input_n_obs ; LI++) {
				funct2 = funct2 + Math.pow(in_gob[LI] - o_gc[LI], 2);
			}      
			o_iter = iter;
			o_funct[iter] = funct1;

			setGraphValues(input_n_obs, iter, x, input_nob_gob, in_gob, o_gc, o_dep, funct1, err1, input_area_name);
			drawGraph();

			if (funct2 - funct1 <= 0 ) {
				if (funct2 - ALER <= 0) {
					break;
				}
				else  {
					funct1 = funct2;
				}
			}
			else
				break;

		}
	}

	public void invcal(){

		double dpar = 0.1;
		double funct1 = 0.0;
		double  ALER = input_al_err;
		o_gc  = new double[input_n_obs + 1]; 
		o_dep = new double[input_n_obs + 1];
		input_lambda_val = new double[input_n_obs + 1];
		input_sd_val = new double[input_n_obs + 1];
		o_funct  = new double[input_ite_num +5];
		err = new double[input_n_obs + 1];
		err1 = new double[input_n_obs + 1];
		for (int i = 0; i <= input_n_obs; i++){
			o_gc[i]=0;
			o_dep[i]=0;
			input_lambda_val[i]=0;
			input_sd_val[i]=0;
			err[i]=0;
			err1[i]=0;
		}
		for (int j = 1; j <= input_n_obs; j++){
			input_lambda_val[j] = MODTOHAFSD_LambInt.lamval;
			input_sd_val[j] =  MODTOHAFSD_LambInt.sdval;
		}

		double []par = new double[input_n_obs + 1];
		double []par1 = new double[input_n_obs + 1];
		double []par2 = new double[input_n_obs + 1];
		double []dupar = new double[input_n_obs + 1];
		double []g1 = new double[input_n_obs + 1];
		double []g2 = new double[input_n_obs + 1];
		double [][]st = new double[input_n_obs + 1][input_n_obs + 1];
		double []actz = new double[input_n_obs + 1];	  
		double []b = new double[input_n_obs + 1];
		double []KS = new double[2];
		double LAMBDA = 0.5;
		int np1 = input_n_obs + 1;
		double [][]p = new double[input_n_obs+1][np1+1];
		for (int KL = 1; KL <= input_n_obs; KL++) {
			in_gob[KL] = input_nob_gob[KL]-input_reg_val[KL];
		}
		double  t = (x[2] - x[1]) / 2.0;

		for (int KK = 1 ; KK <= input_n_obs; KK++) {
			par[KK] = (1 / input_lambda_val[KK]) * Math.log(1 + ((input_lambda_val[KK] * in_gob[KK]) / (GK * PI * input_sd_val[KK])));
		}      
		GF(input_n_obs,x,input_ele_km,par,input_sd_val,input_lambda_val,input_strike_km,input_y_km,input_ddx_km,t,o_gc);
		funct1=0.0;
		for(int K = 1 ; K <= input_n_obs; K++) {
			err[K] = in_gob[K] - o_gc[K];
			funct1 = funct1 + Math.pow((in_gob[K] - o_gc[K]), 2);
		}      

		int iter  = 0;
		double funct2 = 0.0;
		while(iter< input_ite_num)     { 
			iter = iter + 1;
			for(int  KJ = 1; KJ <= input_n_obs; KJ++) {
				actz[KJ] = par[KJ];
			}      

			for(int K = 1;K <= input_n_obs; K++) {
				par1[K] = par[K];
			}      
			for (int  I = 1; I <= input_n_obs; I++) {
				par1[I] = par[I] + dpar/2.0;
				GF(input_n_obs,x,input_ele_km,par1,input_sd_val,input_lambda_val,input_strike_km,input_y_km,input_ddx_km,t,g1);
				par1[I] = par[I] - dpar/2.0;
				GF(input_n_obs,x,input_ele_km,par1,input_sd_val,input_lambda_val,input_strike_km,input_y_km,input_ddx_km,t,g2);
				for(int K=1;K<=input_n_obs;K++) {
					st[I][K] = (g1[K] - g2[K]) /dpar;
				}
			}      
			for (int  J=1; J <= np1; J++) {
				for (int I=1; I <= input_n_obs;I++) {
					p[I][J]=0.0;
				}
			}
			for(int J = 1;J <= input_n_obs;J++)  {
				for(int I = 1;I <= input_n_obs;I++) {
					for(int K = 1;K <= input_n_obs;K++) {
						p[I][J] = p[I][J] + st[I][K] * st[J][K];
					}
				}
			}
			for (int J = 1; J <= input_n_obs;J++) {
				for (int K = 1; K <= input_n_obs;K++) {
					p[J][np1] = p[J][np1] + err[K] * st[J][K];
				}
			}

			do {
				double  CON = LAMBDA + 1.0;
				for(int I = 1; I <= input_n_obs;I++) {
					dupar[I] = par[I];
				} 
				for (int L = 1;L <= input_n_obs; L++) {

					for (int J = 1;J <= input_n_obs; J++) {

						if (L - J == 0) {
							p[L][J] = p[L][J] * CON;
						}
					}
				}
				SIMEQ(p,b,input_n_obs,KS);
				for (int I=1;  I <= input_n_obs; I++) {
					par2[I] = dupar[I] + b[I];
				}     
				for (int KK = 1; KK <= input_n_obs; KK++) {
					if(par2[KK] <= input_zmin_km) 
						par2[KK] = input_zmin_km;
					if(par2[KK] > input_zmax_km) 
						par2[KK] = input_zmax_km;
				}     

				GF(input_n_obs,x,input_ele_km,par2,input_sd_val,input_lambda_val,input_strike_km,input_y_km,input_ddx_km,t,o_gc);
				funct2 = 0.0;
				for (int K = 1; K <= input_n_obs; K++) {
					err[K] = in_gob[K] - o_gc[K];
					funct2 = funct2 + Math.pow(err[K],2);
				} 
				if (funct1 - funct2 < 0) {

					LAMBDA = LAMBDA * 2;			

					if (LAMBDA - 13 < 0) {

						for (int I = 1; I <= input_n_obs; I++) {

							for (int J = 1; J <= input_n_obs; J++) {

								if(I - J == 0)
									p[I][J] = p[I][J] / CON;
							}
						}
					}
					else 
						break;
				}

			}while (funct1 <= funct2);

			o_iter = iter;
			o_funct[iter] = funct1; 
			funct1 = funct2;

			for(int I = 1; I <= input_n_obs; I++) {
				par[I] = par2[I];
			}
			for(int I = 1; I <= input_n_obs; I++) {
				o_dep[I] = par2[I];
			}
			LAMBDA = LAMBDA / 2.0;

			setGraphValues(input_n_obs, iter, x, input_nob_gob, in_gob, o_gc, o_dep,  funct1, err, input_area_name);
			drawGraph();

			if (funct2 - funct1 <= 0 ) {
				if (funct2 - ALER <= 0) {
					break;
				}
				else  {
					funct1 = funct2;
				}
			}
			else
				break;

		}
	}

	public double [] GF(int n,double []x,double ele[],double []z,double []sd,double []la,double []yy,double []ydist,double ddx,double t,double []gc) {

		double xx = 0,z2 = 0,y1 = 0,gff,ggc=0;
		double []effy = new double [3]; 
		double []gg2 = new double [3];
		double z1 = 0.0;
		double GK = 13.3333;
		for(int JJ = 1 ; JJ <= n ;JJ++){
			gc[JJ]=0.0;
		}      
		for(int II = 1 ; II <= n ; II++){
			for(int LL = 1 ; LL <= n; LL++) {
				xx = x[II] - x[LL];
				z2 = z[LL];
				effy[1] = yy[LL] - ydist[LL];
				effy[2] = yy[LL] + ydist[LL];
				double dx = ddx/10;
				double zb = z2 - z1;
				double dz = 0,dc = 0; 
				double []zk = new double[1000];
				double []gs = new double[1000];

				int nd = (int)(zb / dx) + 1;
				int n1 = nd / 2;
				if (nd - (2 * n1 ) < 0 || nd - ( 2 * n1 ) > 0) {
					nd = nd + 1;
				}
				dz = zb / nd;
				int n2 = nd + 1;
				for (int JZ = 1 ; JZ <= n2; JZ++) {
					zk[JZ] = z1 + dz * (JZ - 1);
				}      
				for (int JZ = 1; JZ <= n2; JZ++) {
					dc = (sd[LL] * Math.exp(la[LL] * zk[JZ])); 
					for (int LK = 1 ; LK <= 2; LK++) {
						y1 = effy[LK];
						double t1 = GK * dc;
						double t2 = Math.atan((y1 * (xx + t)) / (Math.sqrt(Math.pow((xx + t), 2) + Math.pow(y1, 2) + Math.pow(zk[JZ]-ele[II], 2)) * (zk[JZ]-ele[II])));
						double t3 = Math.atan((y1 * (xx - t)) / (Math.sqrt(Math.pow((xx - t), 2) + Math.pow(y1, 2) + Math.pow(zk[JZ]-ele[II], 2))* (zk[JZ]-ele[II])));
						gg2[LK] = t1 * (t2 - t3);
					}
					gs[JZ] = (gg2[1] + gg2[2]) / 2.0;
				}      
				gff = SIMP(gs,zk,n2,ggc);
				gc[II] = gc[II] + gff;

			}
		}
		return gc;
	}      

	public double SIMP(double []gs,double []z,int n,double ggc) {

		double dz = z[2]-z[1];
		double sum1 = 0.0;
		double sum2 = 0.0;
		int n1 = n / 2;
		int n4 = n1 - 1;
		for(int I = 1; I <= n1; I++) {
			int n2 = 2 * I;
			sum1 = sum1 + gs[n2];
		}      
		for(int I = 1; I <= n4; I++) {
			int n3 = 2 * I +1;
			sum2 = sum2 + gs[n3];
		}      
		ggc = gs[1]+4*sum1+2*sum2+gs[n];
		ggc = ggc * dz / 3.0;
		return ggc;
	}	


	public static double []SIMEQ(double p[][], double b[], int n, double KS[]) {

		int I = n + 1;
		double []a = new double[n*n+1];

		for (int I1 = 1; I1 <= n; I1++) {

			for (int I2 = 1; I2 <= n; I2++) {
				int I3 = (I1 - 1) * n + I2;
				a[I3] = p[I2][I1];
			}
		}

		for (int I4 = 1;I4 <= n; I4++) {
			b[I4] = p[I4][I];
		}
		double tol = 0;
		KS[0] = 0;
		int JJ = - n;
		int IT;
		int NY = 0;
		for (int J = 1;J <= n; J++) {
			int JY = J + 1;
			JJ = JJ + n + 1;
			double biga = 0;
			IT = JJ - J;
			int imax = 0;
			for (int i = J; i <= n; i++) {
				int IJ = IT + i;
				if (Math.abs(biga) - Math.abs(a[IJ]) < 0) {

					biga = a[IJ];
					imax = i;
				}
			}
			int I1 = 0;
			if (Math.abs(biga) - tol <= 0) {
				KS[1] = 1;
				return KS;
			}
			else {
				I1 = J + n * (J - 2);
				IT = imax - J;
			}

			double save;
			for (int K = J;K <= n; K++) {
				I1 = I1 + n;
				int I2 = I1 + IT;
				save = a[I1];
				a[I1] = a[I2];
				a[I2] = save;
				a[I1] = a[I1] / biga;
			}
			save = b[imax];
			b[imax] = b[J];
			b[J] = save / biga;
			int IQS = 0;

			if (J - n < 0 || J - n > 0) {

				IQS = n * (J - 1);
				for (int IX = JY;IX <= n; IX++) {
					int IXJ = IQS + IX;
					IT = J - IX;
					for (int JX = JY;JX <= n; JX++) {
						int IXJX = n * (JX - 1) + IX;
						int JJX = IXJX + IT;
						a[IXJX] = a[IXJX] - (a[IXJ] * a[JJX]);
					}
					b[IX] = b[IX] - (b[J] * a[IXJ]);
				}
			}
		}
		NY = n - 1;
		IT = n * n;

		for (int J = 1;J <= NY; J++) {
			int ia = IT - J;
			int ib = n - J;
			int ic = n;
			for (int K = 1;K <= J; K++) {
				b[ib] = b[ib] - a[ia] * b[ic];
				ia = ia - n;
				ic = ic - 1;
			}
		}
		return b;
	}

	public void getCoefficients() throws MODTOHAFSD_HandleException {

		d_x_km_arr = new double[len + 1];
		d_z_km_arr = new double[len + 1];
		for (int i = 0; i <= len; i++){
			if ( i == 0){
				d_x_km_arr[i] = 0;
				d_z_km_arr[i] = 0;
			}
			else{

				d_x_km_arr[i] = MODTOHAFSD_PlotReg.val[i];
				d_z_km_arr[i] = (MODTOHAFSD_PlotReg.val1[i]);

			}
		}

		double d_coeff_values[] = new double[i_d_poly + 1];
		double d_coeff_xzval_arr[] = new double[i_d_poly + 1];
		double d_coeff_zval_arr[][] = new double[i_d_poly + 1][i_d_poly + 1] ;
		double d_sum_lmatrix = 0;
		double d_sum_rmatrix = 0;

		for (int l = 0; l < i_d_poly + 1; l++){
			d_sum_lmatrix = 0;
			for(int i = 0; i < d_x_km_arr.length; i++){
				if (l == 0){
					d_sum_lmatrix = d_sum_lmatrix + d_z_km_arr[i];
				}
				else{
					double d_sum_xz = d_z_km_arr[i] * Math.pow(d_x_km_arr[i], l);
					d_sum_lmatrix = d_sum_lmatrix+d_sum_xz;  	
				}
			}  	
			d_coeff_xzval_arr[l] = d_sum_lmatrix;
		}

		double d_rmatrix_arr[] = new double[2 * i_d_poly + 1];
		for (int l = 0; l < d_rmatrix_arr.length; l++){
			d_sum_rmatrix = 0;
			for (int i = 0; i < d_x_km_arr.length; i++){
				if (l == 0){
					d_sum_rmatrix = d_sum_rmatrix + d_x_km_arr[i];
					d_rmatrix_arr[l] = d_sum_rmatrix;
				}
				else{
					double d_sum_zpow = Math.pow(d_x_km_arr[i], l);    
					d_sum_rmatrix = d_sum_rmatrix + d_sum_zpow;
					d_rmatrix_arr[l] = d_sum_rmatrix;
				}
			}
		}
		for (int i = 0; i < i_d_poly + 1; i++){

			for (int j = 0; j < i_d_poly + 1; j++){
				if ( i == 0 && j == 0){
					d_coeff_zval_arr[i][j] = d_x_km_arr.length - 1;
				}
				else{
					d_coeff_zval_arr[i][j] = d_rmatrix_arr[i + j]; 
				}
			}
		}

		int index[] = new int[i_d_poly + 1];
		d_coeff_values = solve(d_coeff_zval_arr, d_coeff_xzval_arr, index);

		d_cftnt_arr = new double[i_d_poly + 2];
		d_cftnt_arr[0] = 0.0;

		for (int i = 1; i <= i_d_poly + 1; i++){
			if(d_coeff_values[i - 1]<=0||d_coeff_values[i - 1]>0)
				d_cftnt_arr[i] = d_coeff_values[i - 1];
			else
				d_cftnt_arr[i] = MODTOHAFSD_GetConstReg.regval;	
		}
	}

	public  double[] solve(double a[][],double b[], int index[]) {

		int n = b.length;
		double x[] = new double[n];
		gaussian(a, index);

		for (int i = 0; i < n - 1; ++i) {
			for (int j = i + 1; j < n; ++j) {
				b[index[j]] -= a[index[j]][i] * b[index[i]];
			}
		}
		x[n - 1] = b[index[n - 1]] / a[index[n - 1]][n - 1];
		for (int i = n - 2; i >= 0; --i) {
			x[i] = b[index[i]];
			for (int j = i + 1; j < n; ++j) {
				x[i] -= a[index[i]][j] * x[j];
			}
			x[i] /= a[index[i]][i];
		}
		return x;
	}

	public  void gaussian(double a[][],int index[]) {

		int n = index.length;
		double c[] = new double[n];

		for (int i = 0; i < n; ++i) index[i] = i;
		for (int i = 0; i < n; ++i) {
			double c1 = 0;
			for (int j = 0; j < n; ++j) {
				double c0 = Math.abs(a[i][j]);
				if (c0 > c1) c1 = c0;
			}
			c[i] = c1;
		}

		int k = 0;
		for (int j = 0; j < n - 1; ++j) {
			double pi1 = 0;
			for (int i = j; i < n; ++i) {
				double pi0 = Math.abs(a[index[i]][j]);
				pi0 /= c[index[i]];
				if (pi0 > pi1) {
					pi1 = pi0;
					k = i;
				}
			}
			int itmp = index[j];
			index[j] = index[k];
			index[k] = itmp;
			for (int i = j + 1; i < n; ++i) {
				double pj = a[index[i]][j] / a[index[j]][j];
				a[index[i]][j] = pj;
				for (int l=j+1; l<n; ++l)
					a[index[i]][l] -= pj*a[index[j]][l];

			}
		}
	}

	public static void denCal(int n){

		int i = 1;
		double Z1 = 0;
		double []depth = new double[input_n_obs+1];
		for (int k = 1; k <= input_n_obs; k++){
			depth[k] = o_dep[k];
		}
		double Z2 = MODTOHAFSD_Utility .findMaximumNumber1(depth);
		vsd = new double[(int) Math.pow(input_n_obs,2)];
		dep = new double[(int) Math.pow(input_n_obs,2)];
		while(Z1 <= Z2){
			double dc = MODTOHAFSD_LambInt.sdval*Math.exp(MODTOHAFSD_LambInt.lamval*Z1);
			vsd[i] = dc;
			dep[i] = Z1;
			Z1 = Z1 + 0.1;
			i++;
		}
		count = i;
		vsd[count] = MODTOHAFSD_LambInt.sdval*Math.exp(MODTOHAFSD_LambInt.lamval*Z2);
		dep[count] = Z2;

	}

	public static void  setGraphValues(int i_no_obs,int ite,double []dis,double []GOBS,double []Boug,double []GCAL,double []Dep, double funct,double []Err,String Area_fe) {

		obj = new Object[i_no_obs+1][7];
		DecimalFormat df1 = new DecimalFormat("0.####");
		DecimalFormat d = new DecimalFormat("0.##");
		DecimalFormat df =new DecimalFormat("0.###");
		for (int K = 1; K <= i_no_obs; K++){

			obj[K][0] = "" + dis[K];
			obj[K][1] = "" + df.format(GOBS[K]);
			obj[K][2] = "" + df.format(input_reg_val[K]);	
			obj[K][3] = "" + df.format(Boug[K]);
			obj[K][4] = "" + df.format(GCAL[K]);
			obj[K][5] = "" + df.format(Err[K]);
			obj[K][6] = "" + df.format(Dep[K]);
		}   

		obj[0][0] ="ITERATION";
		obj[0][1] =	"=" +" "+ite;
		MODTOHAFSD_TableView.val.setText("");

		MODTOHAFSD_TableView.val.appendText("\n");
		MODTOHAFSD_TableView.val.append("Polynomial coefficients of regional background:-\n");
		MODTOHAFSD_TableView.val.appendText("-------------------------------------------\n");
		MODTOHAFSD_TableView.val.appendText("\n");

		for (int i = 1; i <= i_d_poly+1; i++){
			MODTOHAFSD_TableView.val.appendText("f"+(i - 1)+" = " +df1.format(d_cftnt_arr[i])+ "\n");
		}		

		MODTOHAFSD_TableView.val.appendText("\n");

	}

	public static void drawGraph(){

		final com.modtohafsd.view.MODTOHAFSD_DrawGraph dg = new com.modtohafsd.view.MODTOHAFSD_DrawGraph();

		try	{
			int width = 1280;
			int height = 650;
			BufferedImage buffer = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);				
			Graphics g1 = buffer.createGraphics();
			g1.setColor(Color.WHITE);
			g1.fillRect(0,0,width,height);
			Graphics2D g2 = (Graphics2D)g1 ;
			dg.plot(g2);
			dg.drawGraph(g2);
			float maxEle = (float) MODTOHAFSD_Utility.findMaximumNumber1(MODTOHAFSD_CalculateValues.input_ele_km);
			if(maxEle==0)
				dg.plotZCoordinates(g2);   
			else
				dg.plotZCoordinatesele(g2);   	
			dg.plotXYCoordinates(g2);
			dg.plotXPoly(g2,120,200,50);
			dg.plot(g2);
			dg.drawOBJ(g2);
			int i=1;
			Color c = Color.MAGENTA;
			denCal(i);
			dg.drawSd(g2,c,i);
			dg.idex(g2);

			FileOutputStream os = new FileOutputStream( MODTOHAFSD_CalculateValues.input_area_name +".jpg");
			ImageIO.write(buffer, "jpg", os);
			os.close();

			String path =  MODTOHAFSD_CalculateValues.input_area_name  +".jpg";
			image = ImageIO.read(new File(path));

			Graphics g_image = MODTOHAFSD_MainPanel.img.getGraphics();
			g_image.drawImage(image, -60,-30,image.getWidth(), image.getHeight(),dg);

			MouseListener ml3 = new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					Graphics g_image = MODTOHAFSD_MainPanel.img.getGraphics();
					g_image.drawImage(image, -60,-30,image.getWidth(), image.getHeight(),dg);
				}
			};
			MODTOHAFSD_MainPanel.img.addMouseListener(ml3);	
		}
		catch (Exception e2) {

			e2.printStackTrace();
		}
	}
}
