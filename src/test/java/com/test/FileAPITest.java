package com.test;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import org.junit.*;
import org.junit.jupiter.api.Test;

import com.payroll.EmployeePayrollData;
import com.payroll.EmployeePayrollService;
import com.payroll.EmployeePayrollService.IOService;
import com.payroll.FileUtils;
import com.payroll.Java8WatchService;

public class FileAPITest {
	private static String HOME = System.getProperty("user.home");
	private static String PLAY_WITH_NIO = "TempPlayGround";

	@Test
	public void givenPathWhenCheckedThenConfirm() throws IOException {
		// Check File exists
		Path homePath = Paths.get(HOME);
		Assert.assertTrue(Files.exists(homePath));

		// Delete File and check file not exists
		Path playPath = Paths.get(HOME + "/" + PLAY_WITH_NIO);
		if (Files.exists(playPath))
			FileUtils.deleteFiles(playPath.toFile());
		Assert.assertTrue(Files.notExists(playPath));

		// Create Directory
		Files.createDirectory(playPath);
		Assert.assertTrue(Files.exists(playPath));

		// Create File
		IntStream.range(1, 10).forEach(cntr -> {
			Path tempFile = Paths.get(playPath + "/temp" + cntr);
			try {
				Files.createFile(tempFile);
			} catch (IOException E) {
			}
			Assert.assertTrue(Files.exists(tempFile));
		});
		// listing of files
		Files.list(playPath).filter(Files::isRegularFile).forEach(System.out::println);
		System.out.println("----------------------------");
		Files.newDirectoryStream(playPath).forEach(System.out::println);
		System.out.println("----------------------------");
		Files.newDirectoryStream(playPath, path -> path.toFile().isFile() && path.toString().startsWith("temp"))
		.forEach(System.out::println);
	}

	@Test
	public void givenADirectoryWhenWatchedListsAllTheActivities() throws IOException {
		Path dir = Paths.get("src/main/java/com/payroll");
		Files.list(dir).filter(Files::isRegularFile).forEach(System.out::println);
		new Java8WatchService(dir).processEvents();
	}

	@Test
	public void given4EmployeesWhenWrittenToFileShouldMatchEmployeeEntries() {
		EmployeePayrollData[] arrayOfEmps = { new EmployeePayrollData(1, " Jeff Bezos", 100000.0),
				new EmployeePayrollData(2, "Bill Gates", 200000.0),
				new EmployeePayrollData(3, "Mark Zuckerberg", 300000.0),
				new EmployeePayrollData(4, "Elon Musk ", 4500000.0) };

		EmployeePayrollService employeePayrollService;
		employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
		employeePayrollService.writeEmployeePayrollData(IOService.FILE_IO);
		long entries = employeePayrollService.countEntries();
		assertEquals(4, entries);
	}

	@Test
	public void given4EmployeesWhenPrintedToConsoleShouldMatchEmployeeEntries() {

		EmployeePayrollService employeePayrollService = new EmployeePayrollService();

		assertEquals(4, employeePayrollService.printData());
	}

	@Test
	public void given4EmployeesWhenReadFromFileShouldMatchEmployeeEntries() {

		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		assertEquals(4, employeePayrollService.readFromFile());
	}
}
