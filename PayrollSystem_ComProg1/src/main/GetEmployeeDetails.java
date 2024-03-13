// Import statements

package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

// Class declaration

public class GetEmployeeDetails {
	// Method to get details of a specific employee
	public void getEmployeeDetails(String inputEmpId) throws IOException, ParseException {
		// Open file for reading
		FileReader fr = new FileReader("./data/MotorPH Employee Data.csv");
		BufferedReader br = new BufferedReader(fr);

		// Create instances of necessary classes
		CountWeeklyHours weeklyHours = new CountWeeklyHours();
		ComputeMonthlySalary monthlySalary = new ComputeMonthlySalary();
		CountMonthlyHours monthlyHours = new CountMonthlyHours();
		String line;

		// Lists to store employee details
		ArrayList<String> empNumList = new ArrayList<String>();
		ArrayList<String> nameList = new ArrayList<String>();
		ArrayList<String> birthdayList = new ArrayList<String>();

		// Read file line by line and populate lists with employee details
		while ((line = br.readLine()) != null) {
			// Split line by comma
			String[] cols = line.split(",");

			// Extract employee number and add to list
			String empNum = cols[0];
			empNumList.add(empNum.trim());

			// Extract employee name and add to list
			String name = (cols[1] + ", " + cols[2]);
			nameList.add(name.trim());

			// Extract employee birthday and add to list
			String birthday = cols[3];
			birthdayList.add(birthday.trim());
		}

		// Create scanner object to get user input
		Scanner scanner = new Scanner(System.in);

		// Find index of input employee ID in employee number list
		int empNumIndex = empNumList.indexOf(inputEmpId);

		// Display employee details
		System.out.println();
		System.out.println("------------------------------------");
		System.out.println();
		System.out.println("Employee Number: " + empNumList.get(empNumIndex));
		System.out.println("Employee Name: " + nameList.get(empNumIndex));
		System.out.println("Employee Birthday: " + birthdayList.get(empNumIndex));
		System.out.println();
		System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
		System.out.println(".												  				.");
		System.out.println("	Sub-Menu: 													.");
		System.out.println(".	1. Compute for this employee's Weekly Hours & Weekly Salary	.");
		System.out.println(".	2. Compute for this employee's Monthly Hours				.");
		System.out.println(".	3. Compute Net Monthly Salary								.");
		System.out.println(".	4. Go back to Main menu										.");
		System.out.println(".	5. None														.");
		System.out.println(".																.");
		System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
		System.out.println();
		System.out.print("Please enter your choice: ");
		String userInputSubOption = scanner.nextLine();

		// Perform actions based on user input
		if (userInputSubOption.equals("1") == true) {
			// Compute weekly hours and salary
			weeklyHours.countWeeklyHours(inputEmpId);
		} else if (userInputSubOption.equals("2") == true) {
			// Compute monthly hours
			monthlyHours.countMonthlyHours(inputEmpId);
		} else if (userInputSubOption.equals("3") == true) {
			// Compute net monthly salary
			monthlySalary.computeMonthlySalary(inputEmpId);
		} else if (userInputSubOption.equals("4") == true) {
			// Go back to main menu
			Main.main(null);
		} else if (userInputSubOption.equals("5")) {
			// Display thank you message and close buffered reader
			System.out.println();
			System.out.println("------------------------------------");
			System.out.println("Thank you for using MotorPh Payroll!");
			br.close();
		}
	}
}
