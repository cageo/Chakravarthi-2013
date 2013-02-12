package com.modtohafsd.view.getvalues;

import com.modtohafsd.model.MODTOHAFSD_CalculateValues;
import com.modtohafsd.util.MODTOHAFSD_Utility;
import com.modtohafsd.view.MODTOHAFSD_HalfstrikeVal;
import com.modtohafsd.view.constant.MODTOHAFSD_GetConstHaf;

public class MODTOHAFSD_GetHalfstrikeVal {

	public void getVal(){

		for(int i=0;i< MODTOHAFSD_CalculateValues.input_n_obs;i++){
			for(int j=0;j<2;j++){

				MODTOHAFSD_HalfstrikeVal.haf[i][j]=MODTOHAFSD_HalfstrikeVal.table.getValueAt(i, j);	

			}
		}

		for(int i=1;i<=MODTOHAFSD_CalculateValues.input_n_obs;i++){

			String val1 = (String)MODTOHAFSD_HalfstrikeVal.haf[i-1][0];
			String val2 = 	(String)MODTOHAFSD_HalfstrikeVal.haf[i-1][1];

			try{
				MODTOHAFSD_GetConstHaf.input_strike_km[i]=MODTOHAFSD_Utility.convertDouble(val2);

			}
			catch(Exception e){

			}


		}
	}
}
