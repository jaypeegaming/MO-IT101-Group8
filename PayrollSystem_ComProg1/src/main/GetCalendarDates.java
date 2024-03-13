package main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class GetCalendarDates {
	//getDatesOfTheWeek -weekdays should only be monday to friday!!!
	//or just call element in countWeeklyHours index 1-5 only

	public ArrayList<String> getDatesOfTheMonth(String inputDate) {

		ArrayList<String> daysOfTheMonthList = new ArrayList<String>();

		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		int year = Integer.parseInt((inputDate).substring(6, 10));
		int month = Integer.parseInt((inputDate).substring(3, 5));
		cal.clear();
		cal.set(year, month - 1, 1);
		int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 0; i < daysInMonth; i++) {
			daysOfTheMonthList.add(fmt.format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, 1);

		}
//	    System.out.print(daysOfTheMonthList);

		return daysOfTheMonthList;
	}


	public ArrayList<String> getDatesOfTheWeek(String inputDate) {

		ArrayList<String> daysOfTheWeekList = new ArrayList<String>();
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
//		SimpleDateFormat fmt = new SimpleDateFormat("E, dd/MM/yyyy"); //add E to show the days of the week

		Calendar calendar = Calendar.getInstance();
		// by using getTime method geting the Todays Time

		int year = Integer.parseInt((inputDate).substring(6, 10));
		int month = Integer.parseInt((inputDate).substring(3, 5));
		int day = Integer.parseInt((inputDate).substring(0, 2));
		calendar.set(year, month-1, day);
		int weekYear = calendar.get(Calendar.YEAR); //get the year from input (here: calendar)
		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);  //get pang-ilang week andun yung date
//        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);  //disregarded, but this is used to get kung pang ilang araw sya sa isang linggo (ex: 1-sunday, 2-monday, and so on)

		// setting the weekyear , weekofyear and dayofweek using setWeekDate
		calendar.setWeekDate(weekYear , weekOfYear , 1); //1 was used as DOW so we get the Sunday of this week

		for(int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
			calendar.set(Calendar.DAY_OF_WEEK, i);
			daysOfTheWeekList.add(fmt.format(calendar.getTime()));
		}

//         System.out.print(daysOfTheWeekList);
		return daysOfTheWeekList;
	}

}
