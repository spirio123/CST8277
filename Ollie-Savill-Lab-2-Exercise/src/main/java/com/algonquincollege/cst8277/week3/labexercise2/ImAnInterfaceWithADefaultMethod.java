/*****************************************************************
 * Course materials (23W) CST 8277
 * 
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 */
package com.algonquincollege.cst8277.week3.labexercise2;

public interface ImAnInterfaceWithADefaultMethod {

	// All Java classes that implement ImAnInterfaceWithNewDefaultMethods
	// must provide code for generateReport
	/* public abstract */ String generateReport(ReportType reportType, String reportData);

	// All Java classes that implement ImAnInterfaceWithNewDefaultMethods
	// get the following default implementation of doReport
	default /* public */ String doReport(String reportCode, String rawReportData) {
		if (null == reportCode) {
			throw new NullPointerException("report type cannot be null");
		}
		// By default, reports are generated from cached data
		ReportType reportType = ReportType.Cached_Report;
		return generateReport(reportType, rawReportData);
	}
}