package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GovtDeductions {
	
	public static double sss(double monthlyGrossSalary) throws IOException {
		FileReader fr = new FileReader ("./data/SSS Contribution.csv");
		BufferedReader br = new BufferedReader (fr);
		String line;
		
		ArrayList<Double> fromList = new ArrayList<Double>();
		ArrayList<Double> toList = new ArrayList<Double>();
		ArrayList<Double> conList = new ArrayList<Double>();
		
		br.readLine();
		while ((line = br.readLine()) != null) {
			String[] cols = line.split(",");
			
			String colsFrom = cols[0];
			double colsFromInt = Double.parseDouble(colsFrom);
			fromList.add(colsFromInt);
			
			String colsTo = cols[1];
			colsTo = colsTo.replaceAll("[^\\d.]", "0");
			double colsToInt = Double.parseDouble(colsTo);
			toList.add(colsToInt);
			
			String colsCon = cols[2];
			double colsConInt = Double.parseDouble(colsCon);
			conList.add(colsConInt);
				
		}
//		System.out.println(fromList);
//		System.out.println(toList);
//		System.out.println(conList);

		double sssCon = 0;
		
		for (int i = 0; (monthlyGrossSalary > toList.get(i)) && (i < fromList.size()-1); i++ ) {
			sssCon = conList.get(i+1);
		}
		
//		System.out.println("Monthly salary: "+ monthlySalary);
//		System.out.println("SSS contribution: "+ sssCon);
		
		br.close();
		return sssCon;
	}

	public static double philHealth(double monthlyGrossSalary) {
		double premiumRate = 0.03;
		double premium;
		double philHealthEmpShare;
		
		if (monthlyGrossSalary >= 60000) {
			premium = 1800;
		} else if (monthlyGrossSalary > 10000) {
			premium = monthlyGrossSalary * premiumRate;
		} else {
			premium = 300;
		}
		
		philHealthEmpShare = premium * 0.50;
		
		DecimalFormat decFormat = new DecimalFormat("0.00");
		decFormat.setRoundingMode(RoundingMode.UP);
		
//		System.out.println();
//		System.out.println("Monthly Basic Salary: "+ String.format("%.2f", monthlySalary));
//		System.out.println("3% Premium Rate: "+ String.format("%.2f", premium));
//		System.out.println("Employee Share (50%): "+ String.format("%.2f", philHealthEmpShare));
		
		return philHealthEmpShare;

	}
	
	public static double pagIbig(double monthlyGrossSalary) {
		double pagIbigEmpShare;
		
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
		
//		System.out.println("Monthly Salary: "+String.format("%.2f", monthlySalary));
//		System.out.println("Pag-ibig Employee share: "+String.format("%.2f", pagIbigEmpShare));
		
		return pagIbigEmpShare;

	}
}
