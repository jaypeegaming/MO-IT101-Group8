package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class CheckInputEmpId {
	
	public String checkInputEmpId (String inputEmpId) throws IOException {
	
		FileReader fr = new FileReader ("./data/MotorPH Employee Data.csv");
		BufferedReader br = new BufferedReader (fr);
		CountWeeklyHours weeklyHours = new CountWeeklyHours();
		String line;
	
		//instantiate lists
		ArrayList<String> empNumList = new ArrayList<String>();
		
		// if line is not null, split each row element by comma
		while ((line = br.readLine()) != null) {
			//comma as separators
			String[] cols = line.split(",");
			//System.out.println("Employee Number: " + cols[0]+" ; "+"Employee Name: "+cols[1]+", "+cols[2]+" ; "+"Birthday: "+cols[3]);
	
			//place all employee number in one list
			String empNum = cols[0];
			empNumList.add(empNum.trim());
			
		}
		//to show output of each list
		//System.out.println(empNumList);
		
		//search index of user input's employee number in the empNumList
	
//		// ask for user input
		Scanner scanner = new Scanner (System.in);
//		System.out.print("Please enter the Employee Number: ");
		String userInput = inputEmpId;
		userInput = userInput.replaceAll("[^\\d.]", "0");
		int inputInt = Integer.parseInt(userInput);
		
		// firstEmpNum and lastEmpNum is created only for the sysout "Choose from.." part
		String firstEmpNum = empNumList.get(1);
		int firstEmpNumInt = Integer.parseInt(firstEmpNum);
	//	System.out.println(firstEmpNumInt);
		String lastEmpNum = empNumList.get(empNumList.size()-1);
		int lastEmpNumInt = Integer.parseInt(lastEmpNum);
	//	System.out.println(lastEmpNumInt);
		
		if (userInput.length() != 5) {
			while (userInput.length() != 5) {
				System.out.println();
				System.out.print("Please enter a five digit Employee number\n(Choose from: " + firstEmpNum+"-"+ lastEmpNum+"): ");
				userInput = scanner.nextLine();
				userInput = userInput.replaceAll("[^\\d.]", "0");
				inputInt = Integer.parseInt(userInput);
			} 
			
		} 
		
		if (userInput.length() == 5) {
			while ((inputInt < firstEmpNumInt) || inputInt > lastEmpNumInt) {
				System.out.println();
				System.out.print("Input is not a valid Employee Number\n(Choose from: " + firstEmpNum+"-"+ lastEmpNum+"): ");
				userInput = scanner.nextLine();
				userInput = userInput.replaceAll("[^\\d.]", "0");
				inputInt = Integer.parseInt(userInput);
			}
			
		}
		
	
		int empNumIndex = empNumList.indexOf(userInput);
		String checkedInputEmpId = empNumList.get(empNumIndex);
		
		return checkedInputEmpId;
		
	
	}

}



