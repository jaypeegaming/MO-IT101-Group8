package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class GetEmployeeDetails {
	public void getEmployeeDetails(String inputEmpId) throws IOException, ParseException {
		FileReader fr = new FileReader("./data/MotorPH Employee Data.csv");
		BufferedReader br = new BufferedReader(fr);
		CountWeeklyHours weeklyHours = new CountWeeklyHours();
		ComputeMonthlySalary monthlySalary = new ComputeMonthlySalary();
		CountMonthlyHours monthlyHours = new CountMonthlyHours();
		String line;

		//instantiate lists
		ArrayList<String> empNumList = new ArrayList<String>();
		ArrayList<String> nameList = new ArrayList<String>();
		ArrayList<String> birthdayList = new ArrayList<String>();
//		ArrayList<String> sssList = new ArrayList<String>();
//		ArrayList<String> philHealthList = new ArrayList<String>();
//		ArrayList<String> pagIbigList = new ArrayList<String>();
//		ArrayList<String> tinList = new ArrayList<String>();

		// if line is not null, split each row element by comma
		while ((line = br.readLine()) != null) {
			//comma as separators
			String[] cols = line.split(",");
			//System.out.println("Employee Number: " + cols[0]+" ; "+"Employee Name: "+cols[1]+", "+cols[2]+" ; "+"Birthday: "+cols[3]);

			//place all employee number in one list
			String empNum = cols[0];
			empNumList.add(empNum.trim());
			//place all employee name in one list
			String name = (cols[1] + ", " + cols[2]);
			nameList.add(name.trim());
			//place all birthday in one list
			String birthday = cols[3];
			birthdayList.add(birthday.trim());
//			//place all sss number in one list
//			String sssNum = cols[6];
//			sssList.add(sssNum.trim());
//			//place all philHealth number in one list
//			String philHealthNum = cols[7];
//			philHealthList.add(philHealthNum.trim());
//			//place all pagIbig number in one list
//			String pagIbigNum = cols[9];
//			pagIbigList.add(pagIbigNum.trim());
//			//place all tin in one list
//			String tinNum = cols[8];
//			tinList.add(tinNum.trim());


		}
		//to show output of each list
		//System.out.println(empNumList);
		//System.out.println(nameList);
		//System.out.println(birthdayList);

		//search index of user input's employee number in the empNumList

		// ask for user input
		Scanner scanner = new Scanner(System.in);

		int empNumIndex = empNumList.indexOf(inputEmpId);
		System.out.println();
		System.out.println("------------------------------------");
		System.out.println();
		System.out.println("Employee Number: " + empNumList.get(empNumIndex));
		System.out.println("Employee Name: " + nameList.get(empNumIndex));
		System.out.println("Employee Birthday: " + birthdayList.get(empNumIndex));
		System.out.println();
		System.out.println(". . . . . . . . . . . . . . . . . . .");
		System.out.println();
		System.out.println("Sub-Menu: ");
		System.out.println("1. Compute for this employee's Weekly Hours & Weekly Salary");
		System.out.println("2. Compute for this employee's Monthly Hours");
		System.out.println("3. Compute Net Monthly Salary");
		System.out.println("4. Go back to Main menu");
		System.out.println("5. None");
		System.out.println();
		System.out.print("Please enter your choice: ");
		String userInputSubOption = scanner.nextLine();
		if (userInputSubOption.equals("1") == true) {
			weeklyHours.countWeeklyHours(inputEmpId);
		} else if (userInputSubOption.equals("2") == true) {
			monthlyHours.countMonthlyHours(inputEmpId);
		} else if (userInputSubOption.equals("3") == true) {
			monthlySalary.computeMonthlySalary(inputEmpId);
		} else if (userInputSubOption.equals("4") == true) {
			Main.main(null);
		} else if (userInputSubOption.equals("5")) {
			System.out.println();
			System.out.println("------------------------------------");
			System.out.println("Thank you for using MotorPh Payroll!");

//		System.out.println("SSS Number: " + sssList.get(empNumIndex));
//		System.out.println("PhilHealth Number: " + philHealthList.get(empNumIndex));
//		System.out.println("Pag-Ibig Number: " + pagIbigList.get(empNumIndex));
//		System.out.println("Tin Number: " + tinList.get(empNumIndex));


			scanner.close();
			br.close();

		}

	}
}