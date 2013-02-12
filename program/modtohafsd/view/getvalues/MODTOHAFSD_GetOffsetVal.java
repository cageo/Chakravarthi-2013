package com.modtohafsd.view.getvalues;

import com.modtohafsd.model.MODTOHAFSD_CalculateValues;
import com.modtohafsd.util.MODTOHAFSD_Utility;
import com.modtohafsd.view.MODTOHAFSD_OffsetVal;
import com.modtohafsd.view.constant.MODTOHAFSD_GetConstOff;

public class MODTOHAFSD_GetOffsetVal {

	public void getVal(){

		for(int i=0;i< MODTOHAFSD_CalculateValues.input_n_obs;i++){
			for(int j=0;j<2;j++){
				MODTOHAFSD_OffsetVal.off[i][j]=MODTOHAFSD_OffsetVal.table.getValueAt(i, j);	
			}

		}

		for(int i=1;i<=MODTOHAFSD_CalculateValues.input_n_obs;i++){

			String val1 = (String)MODTOHAFSD_OffsetVal.off[i-1][0];
			String val2 = 	(String)MODTOHAFSD_OffsetVal.off[i-1][1];

			try{
				MODTOHAFSD_GetConstOff.input_y_km[i]=MODTOHAFSD_Utility.convertDouble(val2);

			}
			catch(Exception e){

			}

		}
	}
}
