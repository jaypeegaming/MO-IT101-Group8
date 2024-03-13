package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GetSalaryRates {

	// Method to get the hourly wage of an employee
	public String getHourlyRate(String inputEmpId) throws NumberFormatException, IOException {
		// Open the data file for the employee
		FileReader fr = new FileReader ("./data/MotorPH Employee Data.csv");
		BufferedReader br = new BufferedReader (fr);
		String line;

		// List to store the employee numbers
		ArrayList<String> empNumList = new ArrayList<String>();
		// List to store the corresponding hourly rates
		ArrayList<String> hourlyRateList = new ArrayList<String>();

		// Reading each line from the file
		while ((line = br.readLine()) != null) {
			// Splitting the line into components using the comma as a separator
			String[] cols = line.split(",");
			//System.out.println("Employee Number: " + cols[0]+" ; "+"Employee Name: "+cols[1]+", "+cols[2]+" ; "+"Birthday: "+cols[3]);

			// Adding the employee number from the line to the list
			String empNum = cols[0];
			empNumList.add(empNum.trim());
			// Adding the hourly rate from the line to the list
			String hourlyRate = cols[18];
			// Finding the index of the requested employee in the list
			hourlyRateList.add(hourlyRate);



		}
		//to show output of each list
		//System.out.println(empNumList);
		//System.out.println(hourlyRateList);

		int hourlyRateIndex = empNumList.indexOf(inputEmpId);
		// Getting the hourly rate of the employee
		String empHourlyRate = hourlyRateList.get(hourlyRateIndex);

		// Close the file
		br.close();

		// Returning the hourly rate
		return empHourlyRate;

	}

	// Method to get the monthly wage of an employee
	public void getMonthlyRate() {

	}

}
