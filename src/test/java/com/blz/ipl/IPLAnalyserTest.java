package com.blz.ipl;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

public class IPLAnalyserTest {

	private static final String RIGHT_MOST_RUNS_CSV = "F:\\BridgeLabz Fellowship Program\\practice\\IPLAnalyserProblem\\src\\test\\resources\\MostRuns.csv";
	private static final double DELTA = 1e-15;

	static IPLAnalyser iplbatting;

	@BeforeClass
	public static void obj() {
		iplbatting = new IPLAnalyser();
	}

	@Test
	public void givenMostRunsCSV_ShouldReturnNumberOfRecords() throws IPLAnalyserException {
		Assert.assertEquals(101, iplbatting.loadCSVData(RIGHT_MOST_RUNS_CSV));
	}

	@Test
	public void givenMostRunsCSV_ShouldReturnTopBattingAverages() {
		try {
			iplbatting.loadCSVData(RIGHT_MOST_RUNS_CSV);
			String sortedData = iplbatting.getTopBattingAverages();
			IPLBattingAnalysis[] censusCSV = new Gson().fromJson(sortedData, IPLBattingAnalysis[].class);
			Assert.assertEquals(83.2, censusCSV[censusCSV.length - 1].avg, DELTA);
		} catch (IPLAnalyserException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void givenMostRunsCSV_ShouldReturnTopStrikingRatesOfBatsman() {
		try {
			iplbatting.loadCSVData(RIGHT_MOST_RUNS_CSV);
			String sortedData = iplbatting.getTopStrikingRates();
			IPLBattingAnalysis[] mostRunsCSV = new Gson().fromJson(sortedData, IPLBattingAnalysis[].class);
			Assert.assertEquals(333.33, mostRunsCSV[mostRunsCSV.length - 1].avg, DELTA);
		} catch (IPLAnalyserException e) {
			e.printStackTrace();
		}
	}
}
