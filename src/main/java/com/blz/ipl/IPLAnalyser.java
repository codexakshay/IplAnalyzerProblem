package com.blz.ipl;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class IPLAnalyser {

	public static List<IPLBattingAnalysis> iplRunsCSVList;

	public int loadCSVData(String csvFilePath) throws IPLAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilderFactory csvBuilder = CSVBuilderFactory.createCsvBuilder();
			iplRunsCSVList = csvBuilder.getCSVFileList(reader, IPLBattingAnalysis.class);
			return iplRunsCSVList.size();
		} catch (IOException e) {
			throw new IPLAnalyserException(e.getMessage(),
					IPLAnalyserException.IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		} catch (RuntimeException e) {
			throw new IPLAnalyserException(e.getMessage(),
					IPLAnalyserException.IPLAnalyserExceptionType.SOME_OTHER_ERRORS);
		}
	}

	public String getTopBattingAverages() throws IPLAnalyserException {
		if (iplRunsCSVList == null || iplRunsCSVList.size() == 0) {
			throw new IPLAnalyserException("NO_CENSUS_DATA",
					IPLAnalyserException.IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		}
		Comparator<IPLBattingAnalysis> censusComparator = Comparator.comparing(census -> census.avg);
		this.sort(censusComparator);
		String sortedStateCensusJson = new Gson().toJson(this.iplRunsCSVList);
		return sortedStateCensusJson;
	}

	public String getTopStrikingRates() throws IPLAnalyserException {
		if (iplRunsCSVList == null || iplRunsCSVList.size() == 0) {
			throw new IPLAnalyserException("NO_CENSUS_DATA",
					IPLAnalyserException.IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		}
		Comparator<IPLBattingAnalysis> runsComparator = Comparator.comparing(census -> census.strikeRate);
		this.sort(runsComparator);
		String sortedMostRunsJson = new Gson().toJson(this.iplRunsCSVList);
		return sortedMostRunsJson;
	}

	public List<IPLBattingAnalysis> getTopFoursHittingBatsman(String csvFilePath)
			throws IOException, IPLAnalyserException {
		loadCSVData(csvFilePath);
		List<IPLBattingAnalysis> playerWithMax4s = iplRunsCSVList.stream()
				.sorted((player1, player2) -> Double.compare(player1.getFours(), player2.getFours()))
				.collect(Collectors.toList());
		Collections.reverse(playerWithMax4s);
		return playerWithMax4s;
	}

	public List<IPLBattingAnalysis> getTopSixesHittingBatsman(String csvFilePath)
			throws IOException, IPLAnalyserException {
		loadCSVData(csvFilePath);
		List<IPLBattingAnalysis> playerWithMax6s = iplRunsCSVList.stream()
				.sorted((player1, player2) -> Double.compare(player1.getSixes(), player2.getSixes()))
				.collect(Collectors.toList());
		Collections.reverse(playerWithMax6s);
		return playerWithMax6s;
	}

	public List<IPLBattingAnalysis> getBestStrikingRatesWithBoundaries(String csvFilePath)
			throws IOException, IPLAnalyserException {
		loadCSVData(csvFilePath);
		Integer playerWithMax6s4s = iplRunsCSVList.stream().map(player -> (player.getFours() + player.getSixes()))
				.max(Double::compare).get();
		List<IPLBattingAnalysis> playerWithBest6s4s = iplRunsCSVList.stream()
				.filter(player -> player.getFours() + player.getSixes() == playerWithMax6s4s)
				.collect(Collectors.toList());
		double playerWithBestStrikerate = playerWithBest6s4s.stream().map(IPLBattingAnalysis::getStrikeRate)
				.max(Double::compare).get();
		return playerWithBest6s4s.stream().filter(player -> player.getStrikeRate() == playerWithBestStrikerate)
				.collect(Collectors.toList());
	}

	public List<IPLBattingAnalysis> getGreatAveragesWithBestStrikingRates(String csvFilePath)
			throws IOException, IPLAnalyserException {
		loadCSVData(csvFilePath);
		double bestAvgs = iplRunsCSVList.stream().map(IPLBattingAnalysis::getAvg).max(Double::compare).get();
		List<IPLBattingAnalysis> playerWithBestAvgs = iplRunsCSVList.stream()
				.filter(player -> player.getAvg() == bestAvgs).collect(Collectors.toList());
		double playerWithBestStrikerate = playerWithBestAvgs.stream().map(IPLBattingAnalysis::getStrikeRate)
				.max(Double::compare).get();
		return playerWithBestAvgs.stream().filter(player -> player.getStrikeRate() == playerWithBestStrikerate)
				.collect(Collectors.toList());
	}

	public List<IPLBattingAnalysis> getMaxRunsWithBestAvg(String csvFilePath) throws IOException, IPLAnalyserException {
		loadCSVData(csvFilePath);
		int maxRuns = iplRunsCSVList.stream().map(IPLBattingAnalysis::getRuns).max(Integer::compare).get();
		List<IPLBattingAnalysis> playerWithMaxRuns = iplRunsCSVList.stream()
				.filter(player -> player.getRuns() == maxRuns).collect(Collectors.toList());
		double playerWithBestAvgs = playerWithMaxRuns.stream().map(IPLBattingAnalysis::getAvg).max(Double::compare)
				.get();
		return playerWithMaxRuns.stream().filter(player -> player.getAvg() == playerWithBestAvgs)
				.collect(Collectors.toList());
	}

	public void sort(Comparator<IPLBattingAnalysis> censusComparator) {
		for (int i = 0; i < iplRunsCSVList.size(); i++) {
			for (int j = 0; j < iplRunsCSVList.size() - i - 1; j++) {
				IPLBattingAnalysis census1 = iplRunsCSVList.get(j);
				IPLBattingAnalysis census2 = iplRunsCSVList.get(j + 1);
				if (censusComparator.compare(census1, census2) > 0) {
					iplRunsCSVList.set(j, census2);
					iplRunsCSVList.set(j + 1, census1);
				}
			}
		}
	}
}
