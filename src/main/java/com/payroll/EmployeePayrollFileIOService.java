package com.payroll;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollFileIOService {
	public static final String PAYROLL_FILE_NAME = "src/main/java/com/payroll/data.txt";

	/**
	 * method to Write Employee Payroll to a File
	 */
	public void writeData(List<EmployeePayrollData> employeePayrollList) {
		StringBuffer empBuffer = new StringBuffer();
		employeePayrollList.forEach(employee -> {
			String employeeDataString = employee.toString().concat("\n");
			empBuffer.append(employeeDataString);
		});
		try {
			Files.write(Paths.get(PAYROLL_FILE_NAME), empBuffer.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * Method to get count of payroll
	 */
	public long countEntries() {
		long entries = 0;
		try {
			entries = Files.lines(new File(PAYROLL_FILE_NAME).toPath()).count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return entries;
	}
	
	/**
	 * Method to print the data of the payroll
	 * @return each payroll
	 */
	public long printData() {
		long entries = 0;
		try {
			entries = Files.lines(new File(PAYROLL_FILE_NAME).toPath()).count();
			Files.lines(new File(PAYROLL_FILE_NAME).toPath()).forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return entries;
	}
	
	public List<EmployeePayrollData> readData() {
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		try {
			Files.lines((new File(PAYROLL_FILE_NAME).toPath())).map(line -> line.trim()).forEach(line -> {
				String[] s = line.split(",");
				employeePayrollList.add(new EmployeePayrollData(Integer.parseInt(s[0]), s[1], Double.parseDouble(s[2])));
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}
}
