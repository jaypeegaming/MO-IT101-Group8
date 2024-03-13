package main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class calendarTest {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the date (ex. 01/09/2022): ");
		String inputDate = scanner.nextLine();

		// Get the list of dates of the month and week for the input date
		ArrayList<String> monthDates = getDatesOfTheMonth(inputDate);
		ArrayList<String> weekDates = getDatesOfTheWeek(inputDate);

		// Print the lists of dates
		System.out.println(monthDates);
		System.out.println(weekDates);

		scanner.close();
	}


	// Method to get the list of dates of the month for a given input date
	public static ArrayList<String> getDatesOfTheMonth(String inputDate) {
		ArrayList<String> daysOfTheMonthList = new ArrayList<String>();

		// Define date format and initialize calendar
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();

		// Extract year and month from the input date
		int year = Integer.parseInt((inputDate).substring(6, 10));
		int month = Integer.parseInt((inputDate).substring(3, 5));

		// Clear calendar and set to the first day of the month
		cal.clear();
		cal.set(year, month - 1, 1);

		// Get the number of days in the month and add each day to the list
		int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 0; i < daysInMonth; i++) {
			daysOfTheMonthList.add(fmt.format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}

		System.out.print(daysOfTheMonthList);

		return daysOfTheMonthList;
	}


	// Method to get the list of dates of the week for a given input date
	public static ArrayList<String> getDatesOfTheWeek(String inputDate) {
		ArrayList<String> daysOfTheWeekList = new ArrayList<String>();
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

		// Create calendar instance and set the input date
		Calendar calendar = Calendar.getInstance();
		int year = Integer.parseInt((inputDate).substring(6, 10));
		int month = Integer.parseInt((inputDate).substring(3, 5));
		int day = Integer.parseInt((inputDate).substring(0, 2));
		calendar.set(year, month - 1, day);

		// Get the week of the year, set to Sunday, and loop through each day of the week
		int weekYear = calendar.get(Calendar.YEAR);
		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		calendar.setWeekDate(weekYear, weekOfYear, 1); // Set to Sunday
		for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
			calendar.set(Calendar.DAY_OF_WEEK, i);
			daysOfTheWeekList.add(fmt.format(calendar.getTime()));
		}

		System.out.print(daysOfTheWeekList);
		return daysOfTheWeekList;
	}
}
