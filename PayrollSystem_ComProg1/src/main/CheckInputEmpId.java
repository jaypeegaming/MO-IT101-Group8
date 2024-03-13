package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CheckInputEmpId {
	// Method to validate and check the input employee ID
	public String checkInputEmpId(String inputEmpId) throws IOException {
		// FileReader and BufferedReader for reading employee data
		FileReader fr = new FileReader("./data/MotorPH Employee Data.csv");
		BufferedReader br = new BufferedReader(fr);

		// Instance of CountWeeklyHours class
		CountWeeklyHours weeklyHours = new CountWeeklyHours();

		// Variables
		String line;
		ArrayList<String> empNumList = new ArrayList<String>();

		// Read the employee data and store employee numbers in a list
		while ((line = br.readLine()) != null) {
			String[] cols = line.split(",");
			String empNum = cols[0];
			empNumList.add(empNum.trim());
		}

		// Scanner for user input
		Scanner scanner = new Scanner(System.in);

		// Validate user input employee ID
		String userInput = inputEmpId;
		userInput = userInput.replaceAll("[^\\d.]", "0");
		int inputInt = Integer.parseInt(userInput);

		// Get the range of valid employee numbers
		String firstEmpNum = empNumList.get(1);
		int firstEmpNumInt = Integer.parseInt(firstEmpNum);
		String lastEmpNum = empNumList.get(empNumList.size() - 1);
		int lastEmpNumInt = Integer.parseInt(lastEmpNum);

		// Check if the input employee ID is valid
		if (userInput.length() != 5) {
			while (userInput.length() != 5) {
				System.out.println();
				System.out.print("Please enter a five digit Employee number\n(Choose from: " + firstEmpNum + "-" + lastEmpNum + "): ");
				userInput = scanner.nextLine();
				userInput = userInput.replaceAll("[^\\d.]", "0");
				inputInt = Integer.parseInt(userInput);
			}
		}

		if (userInput.length() == 5) {
			while ((inputInt < firstEmpNumInt) || inputInt > lastEmpNumInt) {
				System.out.println();
				System.out.print("Input is not a valid Employee Number\n(Choose from: " + firstEmpNum + "-" + lastEmpNum + "): ");
				userInput = scanner.nextLine();
				userInput = userInput.replaceAll("[^\\d.]", "0");
				inputInt = Integer.parseInt(userInput);
			}
		}

		// Get the index of the validated input employee ID and return it
		int empNumIndex = empNumList.indexOf(userInput);
		String checkedInputEmpId = empNumList.get(empNumIndex);

		// Close BufferedReader
		br.close();

		return checkedInputEmpId;
	}
}
