package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CheckInputEmpId {

	// Method to check if the input employee ID is valid
	public String checkInputEmpId(String inputEmpId) throws IOException {

		// FileReader and BufferedReader for reading the employee data file
		FileReader fr = new FileReader("./data/MotorPH Employee Data.csv");
		BufferedReader br = new BufferedReader(fr);
		CountWeeklyHours weeklyHours = new CountWeeklyHours();
		String line;

		// Instantiate a list to store employee numbers
		ArrayList<String> empNumList = new ArrayList<String>();

		// Read each line of the employee data file
		while ((line = br.readLine()) != null) {
			// Split each row by comma
			String[] cols = line.split(",");

			// Place all employee numbers in the list
			String empNum = cols[0];
			empNumList.add(empNum.trim());
		}

		// Get user input
		String userInput = inputEmpId;

		// Determine the range of valid employee numbers for display purposes
		String firstEmpNum = empNumList.get(1);
		int firstEmpNumInt = Integer.parseInt(firstEmpNum);
		String lastEmpNum = empNumList.get(empNumList.size() - 1);
		int lastEmpNumInt = Integer.parseInt(lastEmpNum);

		Scanner scanner = new Scanner(System.in);

		// Validate user input against the list of employee numbers
		while (empNumList.indexOf(userInput) < 0) {
			System.out.print("\nInput is not a valid Employee Number\n(Choose from: " + firstEmpNum + "-" + lastEmpNum + "): ");
			userInput = scanner.nextLine();
		}

		return userInput;
	}
}
