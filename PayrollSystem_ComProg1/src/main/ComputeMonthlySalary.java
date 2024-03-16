
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

public class ComputeMonthlySalary {

	public void computeMonthlySalary(String inputEmpId) throws IOException, ParseException {
		FileReader fr = new FileReader ("./data/AttendanceRecordv3.csv");
		BufferedReader br = new BufferedReader (fr);
		ComputeMonthlySalary monthlySalary = new ComputeMonthlySalary();
		GetSalaryRates salaryRate = new GetSalaryRates();
		GetCalendarDates calendarDates = new GetCalendarDates();
		GovtDeductions govtDeductions = new GovtDeductions();
		GetEmployeeDetails employeeDetails = new GetEmployeeDetails();
		CountWeeklyHours weeklyHours = new CountWeeklyHours();
		CountMonthlyHours monthlyHours = new CountMonthlyHours();
		String line;
		int totalHours = 0;

		//instantiate lists
		ArrayList<String> empNumList = new ArrayList<String>();
		ArrayList<String> dateList = new ArrayList<String>();
		ArrayList<Date> timeInList = new ArrayList<Date>();
		ArrayList<Date> timeOutList = new ArrayList<Date>();

		DateFormat sdfTime = new SimpleDateFormat("HH:mm");

		br.readLine();
		// if line is not null, split each row element by comma
		while ((line = br.readLine()) != null) {
			//comma as separators
			String[] cols = line.split(",");
//				System.out.println("Employee Number: " + cols[0]+" ; Date: "+cols[1]+" ; Time In: "+cols[2]+" ; Time Out: "+cols[3]);

			//place all employee number in one list
			String empNum = cols[0];
			empNumList.add(empNum.trim());
			//place all date in one list
			String dateString = cols[1];
//				Date date = sdfDate.parse(dateString);
			dateList.add(dateString.trim());
			//place all time in in one list
			String timeInString = cols[2];
			Date timeIn = sdfTime.parse(timeInString.trim());
			timeInList.add(timeIn);
			//place all timeout in one list
			String timeOutString = cols[3];
			Date timeOut  = sdfTime.parse(timeOutString.trim());
			timeOutList.add(timeOut);

		}

		Scanner scanner = new Scanner (System.in);

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


		System.out.println();
		int z = 0;
		int minutes = 0;
		int hours = 0;
		long dailyHoursRendered = 0;
		Date timeInDefault = sdfTime.parse("08:00");

		System.out.println("------------------------------------");

		do {

			for ( int i = 0; i < empNumList.size() & z < monthDays.size(); i++) {
				Boolean empIdBoolean = inputEmpId.equals(empNumList.get(i));
				Boolean dateBoolean = monthDays.get(z).equals(dateList.get(i));

				if (empIdBoolean == true && dateBoolean == true) {
					z++;

					Date timeOutChoice = timeOutList.get(i);
					Date timeInChoice = timeInList.get(i);
					if (timeInChoice.getTime() < 0 ) {
						dailyHoursRendered = 0;
						totalHours += 0;
					}
					else if (timeInChoice.getTime() > 900000 ) {
						dailyHoursRendered = (timeOutChoice.getTime() - timeInChoice.getTime());
						if(dailyHoursRendered > (5*1000*60*60)){
							totalHours += dailyHoursRendered - (1*1000*60*60);
						}else {
							totalHours += dailyHoursRendered;
						}
					} else {
						dailyHoursRendered = (timeOutChoice.getTime() - timeInDefault.getTime());
						if(dailyHoursRendered > (5*1000*60*60)){
							totalHours += dailyHoursRendered - (1*1000*60*60);
						}else {
							totalHours += dailyHoursRendered;
						}
					}

					long minutesRendered = (dailyHoursRendered / (1000*60)) % 60;
					long hoursRendered = (dailyHoursRendered / (1000*60*60));
					String timeOutChoiceString = sdfTime.format(timeOutChoice);
					String timeInChoiceString = sdfTime.format(timeInChoice);


				} else {
					continue;
				}

			}
			z++;
			continue;
		} while (z < monthDays.size());

		minutes = (totalHours / (1000*60)) % 60;
		hours = (totalHours / (1000*60*60));
		String hourlyRate = salaryRate.getHourlyRate(inputEmpId);


		double hourlyRateDouble = Double.parseDouble(hourlyRate);
		double grossMonthlySalary = (hours + (minutes/60))*hourlyRateDouble;
		double sssContribution = govtDeductions.sss(grossMonthlySalary);
		double philHealthContribution = govtDeductions.philHealth(grossMonthlySalary);
		double pagIbigContribution = govtDeductions.pagIbig(grossMonthlySalary);
		double deductions = sssContribution + philHealthContribution + pagIbigContribution;
		double taxableIncome = grossMonthlySalary - (deductions);

		DecimalFormat formatter = new DecimalFormat("#,###.00");
		String FILE_PATH = "./data/MotorPH Employee Data.csv";
		int HEADER_ROWS = 1;

		String emp_ID = inputEmpId;
		int intEmpID = Integer.parseInt(emp_ID);
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
			for (int i = 0; i < HEADER_ROWS; i++) {
				reader.readLine();
			}
			String line_allowance;
			while ((line_allowance = reader.readLine()) != null) {
				String[] employeeData = line_allowance.split(",");
				if (Integer.parseInt(employeeData[0]) == intEmpID) {
					double basicSalary = Double.parseDouble(employeeData[13]);
					String formattedBasicSalary = String.format("%,.2f", basicSalary);
					System.out.println("\nBasic Salary(Base Computation only): "+ formattedBasicSalary);
					double riceSubsidy = Double.parseDouble(employeeData[14]);
					String formattedRiceSubsidy = String.format("%,.2f", riceSubsidy);
					System.out.println("\nRice Subsidy: " + formattedRiceSubsidy);
					double phoneAllowance = Double.parseDouble(employeeData[15]);
					String formattedPhoneAllowance = String.format("%,.2f", phoneAllowance);
					System.out.println("Phone Allowance: " + formattedPhoneAllowance);
					double clothingAllowance = Double.parseDouble(employeeData[16]);
					String formattedClothingAllowance = String.format("%,.2f", clothingAllowance);
					System.out.println("Clothing Allowance: " + formattedClothingAllowance);
				}
			}
		}
//Ssian's allowances code ends here

		System.out.println();
		System.out.println("Total Monthly Hours:            "+hours+"Hrs. "+minutes+"mins. ");
		System.out.println("Hourly Rate:                    "+formatter.format(hourlyRateDouble));
		System.out.println("                               -------------");
		System.out.println("Total Gross Monthly salary:     "+formatter.format(grossMonthlySalary));
		System.out.println();
		System.out.println("Less:");
		System.out.println("SSS:                "+formatter.format(sssContribution));
		System.out.println("PhilHealth:         "+formatter.format(philHealthContribution));
		System.out.println("PagIbig:            "+formatter.format(pagIbigContribution)+ "     ("+formatter.format(deductions)+")");
		System.out.println("                              -----------");
		System.out.println("Taxable Income:                "+formatter.format(taxableIncome));

//			Monthly Rate	              Tax Rate
//			20,832 and below	          No withholding tax
//			20,833 to below 33,333	      20% in excess of 20,833
//			33,333 to below 66,667	      2,500 plus 25% in excess of 33,333
//			66,667 to below 166,667	      10,833 plus 30% in excess of 66,667
//			166,667 to below 666,667	  40,833.33 plus 32% in excess over 166,667
//			666,667 and above	          200,833.33 plus 35% in excess of 666,667


//			withholding tax computation
		double withholdingTax;

		if (taxableIncome >= 666667) {
			withholdingTax = 200833.33 + ((taxableIncome - 666667) * .35);
		} else if (taxableIncome >= 166667) {
			withholdingTax = 40833.33 + ((taxableIncome - 166667) * .32);
		} else if (taxableIncome >= 66667) {
			withholdingTax = 10833 + ((taxableIncome - 66667) * .30);
		} else if (taxableIncome >= 33333) {
			withholdingTax = 2500 + ((taxableIncome - 33333) * .25);
		} else if (taxableIncome >= 20833) {
			withholdingTax = (taxableIncome - 20833) * .20;
		} else {
			withholdingTax = 0;
		}

		double netSalary = taxableIncome - withholdingTax;
		System.out.println();
		System.out.println("Less:");
		System.out.println("Withholding Tax:               ("+formatter.format(withholdingTax)+")");
		System.out.println("                              -----------");
		System.out.println("Net Salary:                    "+formatter.format(netSalary));
		System.out.println("                              ===========");
		System.out.println();
		System.out.println("==========================================");

		// check original raw data from camu. check dates in attendance record sheet

		System.out.println();
		System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
		System.out.println(".												  	              	.");
		System.out.println(".	Sub-Menu: 														.");
		System.out.println(".	1. View Basic Details											.");
		System.out.println(".	2. Compute for this employee's Weekly Hours & Weekly Salary		.");
		System.out.println(".	3. Compute for this employee's Monthly Hours					.");
		System.out.println(".	4. Go back to Main menu											.");
		System.out.println(".	5. Exit															.");
		System.out.println(".                                                                   .");
		System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
		System.out.println();
		System.out.print("Please enter your choice: ");
		String userInputSubOption = scanner.nextLine();
		if (userInputSubOption.equals("1") == true) {
			employeeDetails.getEmployeeDetails(inputEmpId);
		}else if (userInputSubOption.equals("2") == true) {
			weeklyHours.countWeeklyHours(inputEmpId);
		}else if (userInputSubOption.equals("3") == true) {
			monthlyHours.countMonthlyHours(inputEmpId);
		} else if (userInputSubOption.equals("4") == true) {
			Main.main(null);
		}else  if (userInputSubOption.equals("5") == true) {
			System.out.println();
			System.out.println();
			System.out.println("||===========================================||");
			System.out.println("||    Thank you for using MotorPh Portal!    ||");
			System.out.println("||===========================================||");

		}


		br.close();


	}
}
