package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class CountMonthlyHours {
	// Method to count monthly hours for an employee
	public void countMonthlyHours(String inputEmpId) throws IOException, ParseException {
		// FileReader and BufferedReader for reading the attendance record
		FileReader fr = new FileReader("./data/AttendanceRecordv3.csv");
		BufferedReader br = new BufferedReader(fr);

		// Instances of other classes for various functionalities
		ComputeMonthlySalary monthlySalary = new ComputeMonthlySalary();
		GetSalaryRates salaryRate = new GetSalaryRates();
		GetCalendarDates calendarDates = new GetCalendarDates();
		GetEmployeeDetails employeeDetails = new GetEmployeeDetails();
		CountWeeklyHours weeklyHours = new CountWeeklyHours();

		// Variables for processing data
		String line;
		int totalHours = 0;

		// ArrayLists to store data from the CSV file
		ArrayList<String> empNumList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		ArrayList<Date> timeInList = new ArrayList<Date>();
		ArrayList<Date> timeOutList = new ArrayList<Date>();

		// Date formats for parsing time
		DateFormat sdfTime = new SimpleDateFormat("HH:mm");

		// Read the header line of the CSV file
		br.readLine();

		// Process each line of the CSV file
		while ((line = br.readLine()) != null) {
			// Split each row by comma
			String[] cols = line.split(",");

			// Handling incomplete data
			if (cols.length < 4) {
				System.out.println("Incomplete data: " + line);
				continue;
			}

			// Store employee number, date, time in, and time out in respective lists
			String empNum = cols[0];
			empNumList.add(empNum.trim());
			String dateString = cols[1];
			dateList.add(dateString.trim());
			String timeInString = cols[2];
			Date timeIn = sdfTime.parse(timeInString.trim());
			timeInList.add(timeIn);
			String timeOutString = cols[3];
			Date timeOut = sdfTime.parse(timeOutString.trim());
			timeOutList.add(timeOut);
		}

		Scanner scanner = new Scanner(System.in);

		System.out.println();
		System.out.println(". . . . . . . . . . . . . . . . . . .");
		System.out.println();

		// Add error handling for date input format and validity
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date inputDate = null;
		boolean validInputDate = false;
		do {
			System.out.print("Please enter the date you want to be included (ex. 25/12/2022): ");
			String inputDateString = scanner.nextLine();
			try {
				// Parse the input date
				inputDate = dateFormat.parse(inputDateString);

				// Validate the parsed date
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(inputDate);
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				int month = calendar.get(Calendar.MONTH) + 1; // January is 0
				int year = calendar.get(Calendar.YEAR);

				// Check if day, month, and year are within valid ranges
				if (day != Integer.parseInt(inputDateString.substring(0, 2)) ||
						month != Integer.parseInt(inputDateString.substring(3, 5)) ||
						year != Integer.parseInt(inputDateString.substring(6))) {
					throw new ParseException("Invalid date", 0);
				}

				// Check for valid month range (1 to 12) and day range based on month
				if (month < 1 || month > 12 || day < 1 || day > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
					throw new ParseException("Invalid date", 0);
				}

				validInputDate = true;
			} catch (ParseException | NumberFormatException | IndexOutOfBoundsException e) {
				System.out.println("\nInvalid date format or value. Please follow the format dd/mm/yyyy and ensure the date is valid.\n");
			}
		} while (!validInputDate);

		String inputDateStr = dateFormat.format(inputDate); // Convert Date object to String

		ArrayList<String> monthDays = calendarDates.getDatesOfTheMonth(inputDateStr);

		// Iterate over each date of the month
		System.out.println();
		int z = 0;
		int minutes = 0;
		int hours = 0;
		long dailyHoursRendered = 0;
		String remarks;
		Date timeInDefault = sdfTime.parse("08:00");
		System.out.println("|-----------------------------------------------------------------------------------------|");
		System.out.println("|   Employee ID     Date           Time In     Time Out     Hours Rendered      Remarks   |");
		System.out.println("|-----------------------------------------------------------------------------------------|");
		do {
			for (int i = 0; i < empNumList.size() && z < monthDays.size(); i++) {
				Boolean empIdBoolean = inputEmpId.equals(empNumList.get(i));
				Boolean dateBoolean = monthDays.get(z).equals(dateList.get(i));

				if (empIdBoolean == true && dateBoolean == true) {
					z++;
					Date timeOutChoice = timeOutList.get(i);
					Date timeInChoice = timeInList.get(i);

					// Calculate daily hours rendered and update total hours
					if (timeInChoice.getTime() < 0) {
						dailyHoursRendered = 0;
						totalHours += 0;
						remarks = " - Absent  |";
					} else if (timeInChoice.getTime() > 900000) {
						dailyHoursRendered = (timeOutChoice.getTime() - timeInChoice.getTime());
						if (dailyHoursRendered > (5 * 1000 * 60 * 60)) {
							totalHours += dailyHoursRendered - (1 * 1000 * 60 * 60);
						} else {
							totalHours += dailyHoursRendered;
						}
						remarks = " - Late      |";
					} else {
						dailyHoursRendered = (timeOutChoice.getTime() - timeInDefault.getTime());
						if (dailyHoursRendered > (5 * 1000 * 60 * 60)) {
							totalHours += dailyHoursRendered - (1 * 1000 * 60 * 60);
						} else {
							totalHours += dailyHoursRendered;
						}
						remarks = " - OK      |";
					}

					// Print employee attendance details
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
				} else {
					continue;
				}
			}
			z++;
			continue;
		} while (z < monthDays.size());

		// Calculate total monthly hours and display
		minutes = (totalHours / (1000 * 60)) % 60;
		hours = (totalHours / (1000 * 60 * 60));
		System.out.println("|                                                                                         |");
		System.out.println("|-----------------------------------------------------------------------------------------|");
		System.out.println();
		System.out.println("Total Monthly Hours:            " + hours + "Hrs. " + minutes + "mins. ");
		System.out.println("===========================================================");

		// Prompt user for submenu choice
		System.out.println();
		System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
		System.out.println(".                                                                 .");
		System.out.println(".  Sub-Menu:                                                      .");
		System.out.println(".  1. View Basic Details                                          .");
		System.out.println(".  2. Compute for this employee's Weekly Hours & Weekly Salary    .");
		System.out.println(".  3. Compute Net Monthly Salary                                  .");
		System.out.println(".  4. Go back to Main menu                                        .");
		System.out.println(".  5. Exit                                                        .");
		System.out.println(".                                                                 .");
		System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
		System.out.println();
		System.out.print("Please enter your choice: ");
		String userInputSubOption = scanner.nextLine();

		// Handle user submenu choice
		if (userInputSubOption.equals("1") == true) {
			employeeDetails.getEmployeeDetails(inputEmpId);
		} else if (userInputSubOption.equals("2") == true) {
			weeklyHours.countWeeklyHours(inputEmpId);
		} else if (userInputSubOption.equals("3") == true) {
			monthlySalary.computeMonthlySalary(inputEmpId);
		} else if (userInputSubOption.equals("4") == true) {
			Main.main(null);
		} else if (userInputSubOption.equals("5") == true) {
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
