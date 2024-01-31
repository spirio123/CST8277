/*****************************************************************
 * Course materials (23W) CST 8277
 * 
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 */
package com.algonquincollege.cst8277.week3.labexercise2;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * <b>Description</b></br>
 * </br>
 * Helper class that demonstrates some New features from Java 9
 *
 */
public class WhatIsNewJava9 implements ImAnInterfaceWithNewDefaultMethods {
	protected static final Class<?> MY_KLASSNAME = MethodHandles.lookup().lookupClass();
	private static final Logger logger = LoggerFactory.getLogger(MY_KLASSNAME);

	public static void main(String[] args) {

		ProcessHandle processHandle = ProcessHandle.current();
		long pid = processHandle.pid();
		// What are the public APIs available on ProcessHandle.Info? (Hint - there are six (6))
//			arguments()
//			command()
//			commandLine()
//			startInstant()
//			totalCpuDuration()
//			user()
		ProcessHandle.Info processHandleInfo = processHandle.info();
		// TODO - Change all info's to be initialized from processHandleInfo APIs
//		Optional<String[]> info1 = processHandleInfo.arguments();
//		Optional<String> info2 = Optional.of("Command");
//		Optional<String> info3 = Optional.of("CommandLine");
//		Optional<Instant> info4 = Optional.of(Instant.now());
//		Optional<Duration> info5 = Optional.of(Duration.ofMillis(1));
//		Optional<String> info6 = Optional.of("User");
		
		Optional<String[]> info1 = processHandleInfo.arguments();
		Optional<String> info2 = processHandleInfo.command();
		Optional<String> info3 = processHandleInfo.commandLine();
		Optional<Instant> info4 = processHandleInfo.startInstant();
		Optional<Duration> info5 = processHandleInfo.totalCpuDuration();
		Optional<String> info6 = processHandleInfo.user();


		DateTimeFormatter instantFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
				.withZone(ZoneId.systemDefault());
		String info4Formatted = instantFormatter.format(info4.get());

		// TODO - Use 'get' method on Optional info variables; use 'orElse' method on Optional if it could be empty 
		logger.info("pid = {}", pid);
		logger.info("info1 = {}", Arrays.deepToString(info1.orElse(new String[]{"no", "arguments" })));
		logger.info("info2 = {}", info2.orElse(""));
		logger.info("info3 = {}", info3.orElse(""));
		//TODO - Use instantFormatter to format your date
		logger.info("info4 = {}", instantFormatter.format(info4.orElse(Instant.now())));
		logger.info("info5 = {} ms", info5.orElse(Duration.ofMillis(0)).toMillis());
		logger.info("info6 = {}", info6.orElse(""));

		//TODO - Change to use the new private static interface method.
		//Do this by changing the interface to ImAnInterfaceWithNewDefaultMethods.
		WhatIsNewJava9 newJava9 = new WhatIsNewJava9();
		String report = newJava9.doReport("cache", " CST8277 is Awesome");
		logger.info("report = -->{}<--", report);
	}

	@Override
	public String generateReport(ReportType reportType, String reportData) {
		return reportData;
	}

}