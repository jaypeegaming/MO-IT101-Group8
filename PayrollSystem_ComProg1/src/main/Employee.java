package main;
import java.math.BigDecimal;




public class Employee {


    // Member variables representing employee information

    /**
     * Constructor to initialize an Employee object with provided CSV data.
     *
     * @author Group 8
     *
     *
     * @param employeeNumber      The unique identifier for the employee.
     * @param lastName            The last name of the employee.
     * @param firstName           The first name of the employee.
     * @param birthday            The birthday of the employee.
     * @param address             The address of the employee.
     * @param phoneNumber         The Phone number of the employee
     * @param status              The employment status of the employee.
     * @param tin                 The Tax Identification Number (TIN) of the employee.
     * @param sss                 The Social Security System (SSS) number of the employee.
     * @param philhealth          The PhilHealth number of the employee.
     * @param pagIbig             The Pag-IBIG Fund (HDMF) number of the employee.
     * @param position            The position or job title of the employee.
     * @param immediateSupervisor The immediate supervisor of the employee.
     * @param basicSalary         The basic salary of the employee.
     * @param riceSubsidy         The rice subsidy received by the employee.
     * @param phoneAllowance      The phone allowance received by the employee.
     * @param clothingAllowance   The clothing allowance received by the employee.
     * @param grossSemiMonthlyRate The gross semi-monthly rate of the employee.
     * @param hourlyRate          The hourly rate of the employee.
     */

    private int employeeNumber;
    private String lastName;
    private String firstName;
    private String birthday;
    private String address;
    private String phoneNumber;
    private String status;
    private String tin;
    private String sss;
    private String philhealth;
    private String pagIbig;
    private String position;
    private String immediateSupervisor;
    private BigDecimal basicSalary;
    private BigDecimal riceSubsidy;
    private BigDecimal phoneAllowance;
    private BigDecimal clothingAllowance;
    private BigDecimal grossSemiMonthlyRate;
    private BigDecimal hourlyRate;

    public Employee(int employeeNumber, String lastName, String firstName, String birthday, String address,String phoneNumber, String status, String tin, String sss, String philhealth, String pagIbig, String position, String immediateSupervisor, BigDecimal basicSalary, BigDecimal riceSubsidy, BigDecimal phoneAllowance, BigDecimal clothingAllowance, BigDecimal grossSemiMonthlyRate, BigDecimal hourlyRate) {
        this.employeeNumber = employeeNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.tin = tin;
        this.sss = sss;
        this.philhealth = philhealth;
        this.pagIbig = pagIbig;
        this.position = position;
        this.immediateSupervisor = immediateSupervisor;
        this.basicSalary = basicSalary;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.grossSemiMonthlyRate = grossSemiMonthlyRate;
        this.hourlyRate = hourlyRate;
    }

    public Employee() {

    }


    //Getters
    public int getEmployeeNumber() {
        return employeeNumber;
    }
    /**
     * Retrieves the full name of the employee.
     *
     * @return The full name of the employee in the format "lastName, firstName".
     */
    public String getFullName() {
        return lastName + ", " + firstName;
    }

    public String getBirthday() {
        return birthday;
    }




    @Override
    public String toString() {




        return String.format("%-40s%-40s%-40s\n",
                "\nEmployee Information:",
                "Government Mandatory Benefits:",
                "Salary Information:") +
                String.format("%-40s%-40s%-40s\n",
                        "Employee Number: " + employeeNumber,
                        "TIN: " + tin,
                        "Gross Semi-monthly Rate: " + grossSemiMonthlyRate) +
                String.format("%-40s%-40s%-40s\n",
                        "Name: " + getFullName() ,
                        "SSS: " + sss,
                        "Hourly Rate: " + hourlyRate) +
                String.format("%-40s%-40s%-40s\n",
                        "Birthday: " + birthday,
                        "Philhealth: " + philhealth,
                        "Basic Salary: " + basicSalary) +
                String.format("%-40s%-40s%-40s\n",
                        "Position:" + position,
                        "Pag-Ibig: " + pagIbig,
                        "Rice Subsidy: " + riceSubsidy) +
                String.format("%-40s%-40s%-40s\n",
                        "Status: " + status,
                        "",
                        "Clothing Allowance: " + clothingAllowance) +
                String.format("%-40s%-40s%-40s",
                        "Contact Information: ",
                        "",
                        "Phone Allowance: " + phoneAllowance) +
                String.format("%-40s%-40s%-40s\n",
                        "\nHome Address: " + address,
                        "\nPhone Number: " + phoneNumber,
                        "",
                        "") +
                String.format("%-40s%-40s%-40s\n",
                        "Immediate Supervisor: " + immediateSupervisor,
                        "",
                        "");




    }
}
