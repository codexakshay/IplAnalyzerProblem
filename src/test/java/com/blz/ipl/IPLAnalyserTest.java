package com.blz.ipl;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

public class IPLAnalyserTest {

	private static final String RIGHT_MOST_RUNS_CSV = "F:\\BridgeLabz Fellowship Program\\practice\\IPLAnalyserProblem\\src\\test\\resources\\MostRuns.csv";
	private static final String RIGHT_MOST_WICKETS_CSV = "F:\\BridgeLabz Fellowship Program\\practice\\IPLAnalyserProblem\\src\\test\\resources\\MostWkts.csv";
	private static final double DELTA = 1e-15;

	static IPLAnalyser iplanalyser;

	@BeforeClass
	public static void obj() {
		iplanalyser = new IPLAnalyser();
	}

	@Test
	public void givenMostRunsCSV_ShouldReturnNumberOfRecords() throws IPLAnalyserException {
		Assert.assertEquals(101, iplanalyser.loadCSVData(RIGHT_MOST_RUNS_CSV));
	}

	@Test
	public void givenMostRunsCSV_ShouldReturnTopBattingAverages() {
		try {
			iplanalyser.loadCSVData(RIGHT_MOST_RUNS_CSV);
			String sortedData = iplanalyser.getTopBattingAverages();
			IPLBattingAnalysis[] censusCSV = new Gson().fromJson(sortedData, IPLBattingAnalysis[].class);
			Assert.assertEquals(83.2, censusCSV[censusCSV.length - 1].avg, DELTA);
		} catch (IPLAnalyserException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void givenMostRunsCSV_ShouldReturnTopStrikingRatesOfBatsman() {
		try {
			iplanalyser.loadCSVData(RIGHT_MOST_RUNS_CSV);
			String sortedData = iplanalyser.getTopStrikingRates();
			IPLBattingAnalysis[] mostRunsCSV = new Gson().fromJson(sortedData, IPLBattingAnalysis[].class);
			Assert.assertEquals(333.33, mostRunsCSV[mostRunsCSV.length - 1].avg, DELTA);
		} catch (IPLAnalyserException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void givenMostRunsCSV_ShouldReturnMax4sCricketer() throws IOException, IPLAnalyserException {
		List<IPLBattingAnalysis> lst = new IPLAnalyser().getTopFoursHittingBatsman(RIGHT_MOST_RUNS_CSV);
		Assert.assertEquals("Shikhar Dhawan",
				new IPLAnalyser().getTopFoursHittingBatsman(RIGHT_MOST_RUNS_CSV).get(0).playerName);
	}

	@Test
	public void givenMostRunsCSV_ShouldReturnMax6sCricketer() throws IOException, IPLAnalyserException {
		List<IPLBattingAnalysis> lst = new IPLAnalyser().getTopSixesHittingBatsman(RIGHT_MOST_RUNS_CSV);
		Assert.assertEquals("Andre Russell",
				new IPLAnalyser().getTopSixesHittingBatsman(RIGHT_MOST_RUNS_CSV).get(0).playerName);
	}

	@Test
	public void givenMostRunsCSV_ShouldReturnBestStrikingRates_With6sAnd4s() throws IOException, IPLAnalyserException {
		Assert.assertEquals("Andre Russell",
				new IPLAnalyser().getBestStrikingRatesWithBoundaries(RIGHT_MOST_RUNS_CSV).get(0).playerName);
	}

	@Test
	public void givenMostRunsCSV_ShouldReturnbestStrikingRatesWithBestAverages()
			throws IOException, IPLAnalyserException {
		Assert.assertEquals("MS Dhoni",
				new IPLAnalyser().getGreatAveragesWithBestStrikingRates(RIGHT_MOST_RUNS_CSV).get(0).playerName);
	}

	@Test
	public void givenMostRunsCSV_ShouldReturnWhoHitMaxRuns_WithBestAvgs() throws IOException, IPLAnalyserException {
		Assert.assertEquals("David Warner ",
				new IPLAnalyser().getMaxRunsWithBestAvg(RIGHT_MOST_RUNS_CSV).get(0).playerName);
	}

	@Test
	public void givenMostWktsCSV_ShouldReturnNumberOfWktsRecords() throws IPLAnalyserException {
		Assert.assertEquals(101, iplanalyser.loadWktsCSVData(RIGHT_MOST_WICKETS_CSV));
	}

	public void givenMostWktsCSV_ShouldReturnTopBowlingAverages() {
		try {
			iplanalyser.loadCSVData(RIGHT_MOST_WICKETS_CSV);
			String sortedData = iplanalyser.getTopBattingAverages();
			IPLBowlingAnalysis[] wktsCSV = new Gson().fromJson(sortedData, IPLBowlingAnalysis[].class);
			Assert.assertEquals(166, wktsCSV[wktsCSV.length - 1].avg, DELTA);
		} catch (IPLAnalyserException e) {
			e.printStackTrace();
		}
	}
}
