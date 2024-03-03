package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GetSalaryRates {
	
	public String getHourlyRate(String inputEmpId) throws NumberFormatException, IOException {
		FileReader fr = new FileReader ("./data/MotorPH Employee Data.csv");
		BufferedReader br = new BufferedReader (fr);
		String line;
	
		//instantiate lists
		ArrayList<String> empNumList = new ArrayList<String>();
		ArrayList<String> hourlyRateList = new ArrayList<String>();
		
		// if line is not null, split each row element by comma
		while ((line = br.readLine()) != null) {
			//comma as separators
			String[] cols = line.split(",");
			//System.out.println("Employee Number: " + cols[0]+" ; "+"Employee Name: "+cols[1]+", "+cols[2]+" ; "+"Birthday: "+cols[3]);

			//place all employee number in one list
			String empNum = cols[0];
			empNumList.add(empNum.trim());
			//place all employee number in one list
			String hourlyRate = cols[18];
//			Float hourlyRateDouble = Float.parseFloat(hourlyRate.trim());
			hourlyRateList.add(hourlyRate);
			
			
			
		}
		//to show output of each list
		//System.out.println(empNumList);
		//System.out.println(hourlyRateList);
		
		int hourlyRateIndex = empNumList.indexOf(inputEmpId);
		
		String empHourlyRate = hourlyRateList.get(hourlyRateIndex);
		
		br.close();
		
		return empHourlyRate;
		
	}
	
	public void getMonthlyRate() {
		
	}
	
}
