package com.modtohafsd.util;

import com.modtohafsd.model.MODTOHAFSD_CalculateValues;

public class MODTOHAFSD_Utility {

	public static double convertDouble(String str) throws Exception {

		Double temp = new Double(str.trim());
		return temp.doubleValue();
	}

	public static String convertString(String str) throws Exception {

		String temp = new String(str.trim());
		return temp;
	}

	public static int convertInteger(String str) throws Exception {

		Integer temp = new Integer(str.trim());
		return temp.intValue();
	}

	public static int findMaximumNumber( double observe[]) {

		double max = 0.0d;
		for (int i = 0; i < observe.length; i++) {

			if (Math.abs(observe[i]) > Math.abs(max)) {

				max = observe[i];
			}
		}

		int maxVal = (int) max/3*5;
		return maxVal;
	}

	public static int findMaximumNumber( double observe) {

		double max = 0.0d;
		int maxVal=0;	
		max = observe;

		if (max < 5) {

			maxVal = 5;
		}
		else if (max >= 5 && max <= 10) {

			maxVal = 10;
		}
		else if ( max > 10 && max <= 15) {

			maxVal = 15;
		}
		else if (max > 15 && max <= 20) {

			maxVal = 20;
		}
		else
		{
			maxVal = MODTOHAFSD_CalculateValues.o_iter;
		}

		return maxVal;
	}

	public static double findMaximumNumber1( double observe[]) {

		double max = 0.0d;
		for (int i = 1; i < observe.length; i++) {

			if (Math.abs(observe[i]) > Math.abs(max)) {

				max =(observe[i]);
			}
		}

		double maxVal =  max;
		return maxVal;
	}
	public static double findMinimumNumber1( double observe[]) {

		double max = 0.0d;
		for (int i = 1; i < observe.length; i++) {

			if ((observe[i]) < (max)) {

				max = (observe[i]);
			}
		}

		double maxVal =  max;
		return maxVal;
	}

	public static double findMaximumNumber( double observe[], double anoVal) {

		double max = anoVal;
		for (int i = 1; i < observe.length; i++) {

			if ((observe[i]) > (max)) {

				max = (observe[i]);
			}
		}

		double maxVal =  max;
		return maxVal;
	}


	public static double[] convertDoubleArray(String str) throws Exception {

		java.util.StringTokenizer st = new java.util.StringTokenizer(str, ",");
		String temp = "";
		java.util.ArrayList arr = new java.util.ArrayList();

		while(st.hasMoreTokens()) {

			temp = st.nextToken();
			arr.add(temp);
		}
		double d_array[] = new double[arr.size() + 1];

		for(int i = 0; i <= arr.size(); i++) {

			if (i == 0)
				d_array[i] = 0.0;
			else
				d_array[i] = convertDouble( arr.get(i-1).toString() );
		}
		return d_array;
	}


	public static double[] DoubleArray(String str) throws Exception {

		java.util.StringTokenizer st = new java.util.StringTokenizer(str, ",");
		String temp = "";
		java.util.ArrayList arr = new java.util.ArrayList();

		while(st.hasMoreTokens()) {

			temp = st.nextToken();
			arr.add(temp);
		}
		double d_array[] = new double[arr.size() + 1];

		for(int i = 0; i < arr.size(); i++) {
			d_array[i] = convertDouble( arr.get(i).toString() );
		}
		return d_array;
	}

	public static int[] convertIntegerArray(String str) throws Exception {

		java.util.StringTokenizer st = new java.util.StringTokenizer(str, ",");
		String temp = "";
		java.util.ArrayList arr = new java.util.ArrayList();

		while(st.hasMoreTokens()) {

			temp = st.nextToken();
			arr.add(temp);
		}
		int d_array[] = new int[arr.size() + 1];

		for(int i = 0; i < arr.size(); i++) {

			d_array[i] = convertInteger( arr.get(i).toString() );

		}
		return d_array;
	}
}
