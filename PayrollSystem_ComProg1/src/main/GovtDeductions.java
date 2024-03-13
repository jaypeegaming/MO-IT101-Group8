package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GovtDeductions {

	// Method for calculating Social Security System (SSS) contribution
	public static double sss(double monthlyGrossSalary) throws IOException {
		FileReader fr = new FileReader ("./data/SSS Contribution.csv");
		// Create a buffered reader to read text from the file reader

		BufferedReader br = new BufferedReader (fr);
		String line;

		// Initialize lists to store data from the csv file
		ArrayList<Double> fromList = new ArrayList<Double>();
		ArrayList<Double> toList = new ArrayList<Double>();
		ArrayList<Double> conList = new ArrayList<Double>();

		// Skip the first line of the csv file
		br.readLine();

		// Read the rest of the lines from the csv file

		while ((line = br.readLine()) != null) {
			String[] cols = line.split(",");

			// Split the line by commas to get the individual columns

			String colsFrom = cols[0];
			double colsFromInt = Double.parseDouble(colsFrom);
			fromList.add(colsFromInt);

			// Parse the to column and add it to the to list
			String colsTo = cols[1];
			colsTo = colsTo.replaceAll("[^\\d.]", "0");
			double colsToInt = Double.parseDouble(colsTo);
			toList.add(colsToInt);

			// Parse the contribution column and add it to the contribution list

			String colsCon = cols[2];
			double colsConInt = Double.parseDouble(colsCon);
			conList.add(colsConInt);

		}

		// Initialize the SSS contribution

		double sssCon = 0;
		// Find the appropriate SSS contribution based on the monthly gross salary

		for (int i = 0; (monthlyGrossSalary > toList.get(i)) && (i < fromList.size()-1); i++ ) {
			sssCon = conList.get(i+1);
		}

		// Close the buffered reader

		br.close();
		// Return the SSS contribution
		return sssCon;
	}
	// Method for calculating PhilHealth contribution
	public static double philHealth(double monthlyGrossSalary) {
		// Set the premium rate
		double premiumRate = 0.03;
		double premium;
		double philHealthEmpShare;

		// Calculate the premium based on the gross salary
		if (monthlyGrossSalary >= 60000) {
			premium = 1800;
		} else if (monthlyGrossSalary > 10000) {
			premium = monthlyGrossSalary * premiumRate;
		} else {
			premium = 300;
		}

		// Calculate the employee's share of the PhilHealth contribution
		philHealthEmpShare = premium * 0.50;

		// Format the result to two decimal places and round up
		DecimalFormat decFormat = new DecimalFormat("0.00");
		decFormat.setRoundingMode(RoundingMode.UP);


		// Return the employee's share of the PhilHealth contribution
		return philHealthEmpShare;

	}

	// Method for calculating Pag-ibig contribution
	public static double pagIbig(double monthlyGrossSalary) {
		double pagIbigEmpShare;


		// Calculate the employee's share of the Pag-ibig contribution based on the gross salary
		if (monthlyGrossSalary > 1500) {
			pagIbigEmpShare = monthlyGrossSalary * .02;
			if (pagIbigEmpShare > 100) {
				pagIbigEmpShare = 100;
			}

		}else if (monthlyGrossSalary > 1000) {
			pagIbigEmpShare = monthlyGrossSalary * .01;

		} else {
			pagIbigEmpShare = 0;
		}


		// Return the employee's share of the Pag-ibig contribution
		return pagIbigEmpShare;

	}
}
