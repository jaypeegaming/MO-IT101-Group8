package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

	private List<Employee> employees = new ArrayList<>();// List to store all employee information

	// Method to load employee data from a file
	public void loadEmployeeData(String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;

			br.readLine();// Skip the header of the CSV file
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				try {
					Employee employee = new Employee(// Creating a new Employee object
							// Parsing and setting respective fields for the Employee object
							Integer.parseInt(values[0].trim()),
							values[1].trim(),
							values[2].trim(),
							values[3].trim(),
							values[4].trim(),
							values[5].trim(),
							values[10].trim(),
							values[8].trim(),
							values[6].trim(),
							values[7].trim(),
							values[9].trim(),
							values[11].trim(),
							values[12].trim(),
							new BigDecimal(values[13].trim()),
							new BigDecimal(values[14].trim()),
							new BigDecimal(values[15].trim()),
							new BigDecimal(values[16].trim()),
							new BigDecimal(values[17].trim()),
							new BigDecimal(values[18].trim())
					);
					employees.add(employee);// Adding the Employee object to the list
				} catch (NumberFormatException e) {
					System.err.println("Error parsing numeric value in line: " + line);
				}
			}
		} catch (IOException e) {
			System.err.println("Error loading employee data: " + e.getMessage());
		}
	}
	// Method to find and return an employee by their employee number
	public Optional<Employee> findEmployeeByNumber(int employeeNumber) {
		Optional<Employee> foundEmployee = employees.stream()
				.filter(employee -> employee.getEmployeeNumber() == employeeNumber)
				.findFirst();

		if (!foundEmployee.isPresent()) {
			System.out.println("Employee Number Does not Exist.");
		}

		return foundEmployee;
	}


	public static void main(String[] args) throws ParseException, IOException {
		Main payrollSystem = new Main();
		payrollSystem.loadEmployeeData("./data/MotorPH Employee Data.csv");// Load employee data

		Scanner scanner = new Scanner(System.in);// Initialize Scanner for user input
		CountWeeklyHours weeklyHours = new CountWeeklyHours();
		// Instantiate a CountWeeklyHours object
		ComputeMonthlySalary computeMonthlySalary = new ComputeMonthlySalary();
		// Instantiate a ComputeMonthlySalary object
		CountMonthlyHours monthlyHours = new CountMonthlyHours();
		// Instantiate a CountMonthlyHours object
		CheckInputEmpId checkInputEmpId = new CheckInputEmpId();
//		Main main = new Main(); Instantiate a CheckInputEmpId object

		String answer = ""; // Initialize answer with an empty string
// Main loop for user interface
		do {
			System.out.println();
			System.out.println("||===========================================||");
			System.out.println("||    Welcome to MotorPh Employee Portal!    ||");
			System.out.println("||===========================================||");
			System.out.println();
			System.out.println("Main Menu:");
			System.out.println("1. View Full Employee Details");
			System.out.println("2. Get Weekly Hours & Weekly Salary");
			System.out.println("3. Get Monthly Hours");
			System.out.println("4. Compute Net Monthly Salary");
			System.out.println("5. None");
			System.out.println();
			System.out.print("Please enter your choice: ");
			String userInput = scanner.nextLine();


			do {
				// Actions based on user input
				if (userInput.equals("1") == true) {
					// Action for option 1 (View Full Employee Details)
					// Get employee number, find the employee and print details
					System.out.println();
					System.out.println("------------------------------------");
					System.out.println(">>> View Full Employee Details <<<");
					System.out.println();
					int employeeNumber = 0;
					boolean validEmployeeNumber = false;

					// Loop until a valid employee number is entered
					while (!validEmployeeNumber) {
						System.out.printf("Please Enter Your Employee Number: ");
						try {
							employeeNumber = Integer.parseInt(scanner.nextLine().trim());
							validEmployeeNumber = true; // Mark the employee number as valid
						} catch (NumberFormatException e) {
							System.out.println("\nInvalid Employee Number. Please enter a valid  employee number.");
						}
					}
					Optional<Employee> employee = payrollSystem.findEmployeeByNumber(employeeNumber);
					if (employee.isPresent()) {
						System.out.println(employee.get());

					}


				} else if (userInput.equals("2") == true) {
					// Action for option 2 (Get Weekly Hours & Weekly Salary)
					// Get employee number and calculate weekly hours and salary
					System.out.println();
					System.out.println("------------------------------------");
					System.out.println(">>> Get Weekly Hours & Weekly Salary <<<");
					System.out.println();
					System.out.print("Enter Employee Number: ");
					String inputEmpId = scanner.nextLine();
					String checkedInput = checkInputEmpId.checkInputEmpId(inputEmpId);
					inputEmpId = checkedInput;
					weeklyHours.countWeeklyHours(inputEmpId);
				} else if (userInput.equals("3") == true) {
					// Action for option 3 (Get Monthly Hours)
					// Get employee number and calculate monthly hours
					System.out.println();
					System.out.println("------------------------------------");
					System.out.println(">>> Get Monthly Hours <<<");
					System.out.println();
					System.out.print("Enter Employee Number: ");
					String inputEmpId = scanner.nextLine();
					String checkedInput = checkInputEmpId.checkInputEmpId(inputEmpId);
					inputEmpId = checkedInput;
					monthlyHours.countMonthlyHours(inputEmpId);
				} else if (userInput.equals("4") == true) {
					// Action for option 4 (Compute Monthly Salary)
					// Get employee number and calculate net monthly salary
					System.out.println();
					System.out.println("------------------------------------");
					System.out.println(">>> Compute Monthly Salary <<<");
					System.out.println();
					System.out.print("Enter Employee Number: ");
					String inputEmpId = scanner.nextLine();
					String checkedInput = checkInputEmpId.checkInputEmpId(inputEmpId);
					inputEmpId = checkedInput;
					computeMonthlySalary.computeMonthlySalary(inputEmpId);
				} else if (userInput.equals("5") == true) {
					// Action for option 5 (None)
					// Do nothing (Exit the loop)
					System.out.println();


				}

				System.out.printf("\n\nEnter Another Employee Number? Y/N: ");
				answer = scanner.nextLine().trim();
			} while (!answer.equalsIgnoreCase("N"));
			System.out.println();
			System.out.println("||===========================================||");
			System.out.println("||    Thank you for using MotorPh Portal!    ||");
			System.out.println("||===========================================||");

			System.out.print("\n\nDo you want to return to the Main Menu? Y/N: ");
			answer = scanner.nextLine().trim();
		} while (answer.equalsIgnoreCase("Y"));

	} // End of Main class

}
