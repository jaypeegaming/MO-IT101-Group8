package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class CountWeeklyHours {

	// Method to count weekly hours for an employee
	public void countWeeklyHours(String inputEmpId) throws ParseException, IOException {
		// FileReader and BufferedReader for reading the attendance record
		FileReader fr = new FileReader("./data/AttendanceRecordv3.csv");
		BufferedReader br = new BufferedReader(fr);

		// Instances of other classes for various functionalities
		ComputeMonthlySalary monthlySalary = new ComputeMonthlySalary();
		GetSalaryRates salaryRate = new GetSalaryRates();
		GetCalendarDates calendarDates = new GetCalendarDates();
		GetEmployeeDetails employeeDetails = new GetEmployeeDetails();
		CountMonthlyHours monthlyHours = new CountMonthlyHours();
		String line;
		int totalHours = 0;

		//instantiate lists to store attendance records
		ArrayList<String> empNumList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		ArrayList<Date> timeInList = new ArrayList<Date>();
		ArrayList<Date> timeOutList = new ArrayList<Date>();

		DateFormat sdfTime = new SimpleDateFormat("HH:mm");

		// Read the header line of the CSV file
		br.readLine();

		// Process each line of the CSV file
		while ((line = br.readLine()) != null) {
			// Split each row by comma
			String[] cols = line.split(",");

			//place all employee number in one list
			String empNum = cols[0];
			empNumList.add(empNum.trim());
			//place all date in one list
			String dateString = cols[1];
			dateList.add(dateString.trim());
			//place all time in in one list
			String timeInString = cols[2];
			Date timeIn = sdfTime.parse(timeInString.trim());
			timeInList.add(timeIn);
			//place all timeout in one list
			String timeOutString = cols[3];
			Date timeOut = sdfTime.parse(timeOutString.trim());
			timeOutList.add(timeOut);
		}

		// Scanner for user input
		Scanner scanner = new Scanner(System.in);

		// Prompt user for input date
		String userInputDate;
		Date inputDate = null;
		boolean validDate = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		do {
			System.out.println();
			System.out.println(". . . . . . . . . . . . . . . . . . .");
			System.out.println();
			System.out.println("Please enter the date you \nwant to be included (ex. 25/12/2022):");
			userInputDate = scanner.nextLine();

			try {
				// Parse the input date
				inputDate = dateFormat.parse(userInputDate);
				validDate = true;
			} catch (ParseException e) {
				System.out.println("Invalid date format or value. Please follow the format dd/mm/yyyy and ensure the date is valid.");
			}
		} while (!validDate);

		// Convert inputDate to a String before passing it to getDatesOfTheWeek() method
		String inputDateString = dateFormat.format(inputDate);
		ArrayList<String> weekDays = calendarDates.getDatesOfTheWeek(inputDateString);

		// Variable to keep track of iteration count
		int z = 0;
		int minutes = 0;
		int hours = 0;
		long dailyHoursRendered = 0;
		String remarks;
		Date timeInDefault = sdfTime.parse("08:00");

		// Display table header
		System.out.println("------------------------------------");
		System.out.println("Employee ID     Date           Time In     Time Out     Hours Rendered      Remarks");

		// Loop through the attendance records and calculate weekly hours
		do {
			for (int i = 0; i < empNumList.size() & z < 7; i++) {
				Boolean empIdBoolean = inputEmpId.equals(empNumList.get(i));
				Boolean dateBoolean = weekDays.get(z).equals(dateList.get(i));
				if (empIdBoolean && dateBoolean) {
					z++;
					Date timeOutChoice = timeOutList.get(i);
					Date timeInChoice = timeInList.get(i);
					if (timeInChoice.getTime() < 0) {
						dailyHoursRendered = 0;
						totalHours += 0;
						remarks = " - Absent";
					} else if (timeInChoice.getTime() > 900000) {
						dailyHoursRendered = (timeOutChoice.getTime() - timeInChoice.getTime());
						if (dailyHoursRendered > (5 * 1000 * 60 * 60)) {
							totalHours += dailyHoursRendered - (1 * 1000 * 60 * 60);
						} else {
							totalHours += dailyHoursRendered;
						}
						remarks = "- Late";
					} else {
						dailyHoursRendered = (timeOutChoice.getTime() - timeInDefault.getTime());
						if (dailyHoursRendered > (5 * 1000 * 60 * 60)) {
							totalHours += dailyHoursRendered - (1 * 1000 * 60 * 60);
						} else {
							totalHours += dailyHoursRendered;
						}
						remarks = " - OK";
					}

					if (dailyHoursRendered > (5 * 1000 * 60 * 60)) {
						long minutesRendered = (dailyHoursRendered / (1000 * 60)) % 60;
						long hoursRendered = ((dailyHoursRendered / (1000 * 60 * 60))) - 1;
						String timeOutChoiceString = sdfTime.format(timeOutChoice);
						String timeInChoiceString = sdfTime.format(timeInChoice);

						System.out.println(empNumList.get(i) + "           " + dateList.get(i) + "     " + timeInChoiceString + "       " + timeOutChoiceString + "        " + hoursRendered + "Hrs. " + minutesRendered + "mins.       " + remarks);
					} else {
						long minutesRendered = (dailyHoursRendered / (1000 * 60)) % 60;
						long hoursRendered = ((dailyHoursRendered / (1000 * 60 * 60)));
						String timeOutChoiceString = sdfTime.format(timeOutChoice);
						String timeInChoiceString = sdfTime.format(timeInChoice);

						System.out.println(empNumList.get(i) + "           " + dateList.get(i) + "     " + timeInChoiceString + "       " + timeOutChoiceString + "        " + hoursRendered + "Hrs. " + minutesRendered + "mins.       " + remarks);
					}
				}
			}
			z++;
			continue;
		} while (dateList.indexOf(weekDays.get(0)) < 0 && (z < 7));

		// Calculate total weekly hours
		minutes = (totalHours / (1000 * 60)) % 60;
		hours = (totalHours / (1000 * 60 * 60));
		System.out.println();
		System.out.println("Total Weekly Hours:      " + hours + "Hrs. " + minutes + "mins. ");

		// Get hourly rate for the employee
		String hourlyRate = salaryRate.getHourlyRate(inputEmpId);
		System.out.println("Hourly Rate:             " + hourlyRate);

		// Calculate total weekly salary
		double hourlyRateDouble = Double.parseDouble(hourlyRate);
		double weeklySalary = (hours + (minutes / 60)) * hourlyRateDouble;
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		System.out.println("                       -------------");
		System.out.println("Total weekly salary:     " + formatter.format(weeklySalary));

		// Display sub-menu options
		System.out.println();
		System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . .");
		System.out.println(".                                                .");
		System.out.println(".    Sub-Menu:                                   .");
		System.out.println(".    1. View Basic Details                       .");
		System.out.println(".    2. Compute for this employee's Monthly Hours.");
		System.out.println(".    3. Compute Net Monthly Salary               .");
		System.out.println(".    4. Go back to Main menu                     .");
		System.out.println(".    5. Exit                                     .");
		System.out.println(".                                                .");
		System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . ");
		System.out.println();
		System.out.print("Please enter your choice: ");
		String userInputSubOption = scanner.nextLine();
		if (userInputSubOption.equals("1")) {
			employeeDetails.getEmployeeDetails(inputEmpId);
		} else if (userInputSubOption.equals("2")) {
			monthlyHours.countMonthlyHours(inputEmpId);
		} else if (userInputSubOption.equals("3")) {
			monthlySalary.computeMonthlySalary(inputEmpId);
		} else if (userInputSubOption.equals("4")) {
			Main.main(null);
		} else if (userInputSubOption.equals("5")) {
			System.out.println();
			System.out.println();
			System.out.println("||===========================================||");
			System.out.println("||    Thank you for using MotorPh Portal!    ||");
			System.out.println("||===========================================||");
		}

		// Close BufferedReader
		br.close();
	}
}
