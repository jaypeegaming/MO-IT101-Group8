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

	private List<Employee> employees = new ArrayList<>();

	public void loadEmployeeData(String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			// Skip the header
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				try {
					Employee employee = new Employee(
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
					employees.add(employee);
				} catch (NumberFormatException e) {
					System.err.println("Error parsing numeric value in line: " + line);
				}
			}
		} catch (IOException e) {
			System.err.println("Error loading employee data: " + e.getMessage());
		}
	}

	public Optional<Employee> findEmployeeByNumber(int employeeNumber) {
		return employees.stream()
				.filter(employee -> employee.getEmployeeNumber() == employeeNumber)
				.findFirst();
	}

	public static void main(String[] args) throws ParseException, IOException {
		Main payrollSystem = new Main();
		payrollSystem.loadEmployeeData("./data/MotorPH Employee Data.csv");

		Scanner scanner = new Scanner(System.in);
		GetEmployeeDetails employeeDetails = new GetEmployeeDetails();
		CountWeeklyHours weeklyHours = new CountWeeklyHours();
		ComputeMonthlySalary computeMonthlySalary = new ComputeMonthlySalary();
		CountMonthlyHours monthlyHours = new CountMonthlyHours();
		CheckInputEmpId checkInputEmpId = new CheckInputEmpId();
//		Main main = new Main();

		String answer = ""; // Initialize answer with an empty string


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
			if (userInput.equals("1") == true) {
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
				System.out.println();
				System.out.println("------------------------------------");
				System.out.println("Thank you for using MotorPh Portal!");
				break; // exit loop
			}

			System.out.printf("\n\nEnter Another Employee Number? Y/N: ");
			answer = scanner.nextLine().trim();
		} while (!answer.equals("5"));

		scanner.close(); // close scanner at the end
	}
}