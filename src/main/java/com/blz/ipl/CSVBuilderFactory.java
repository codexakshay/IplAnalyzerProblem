package com.blz.ipl;

public class CSVBuilderFactory {

	public static ICSVBuilderFactory createCsvBuilder() {
		return new OpenCsvBuilder();
	}

}
