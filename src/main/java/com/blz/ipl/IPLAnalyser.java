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
	public static List<IPLBowlingAnalysis> iplWktsCSVList;

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

	public int loadWktsCSVData(String CSVFilePath) throws IPLAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(CSVFilePath))) {
			ICSVBuilderFactory csvBuilder = CSVBuilderFactory.createCsvBuilder();
			iplWktsCSVList = csvBuilder.getCSVFileList(reader, IPLBowlingAnalysis.class);
			return iplWktsCSVList.size();
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

	public String getTopBowlingAverages(String csvFilePath) throws IPLAnalyserException {
		loadWktsCSVData(csvFilePath);
		if (iplWktsCSVList == null || iplWktsCSVList.size() == 0) {
			throw new IPLAnalyserException("NO_CENSUS_DATA",
					IPLAnalyserException.IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		}
		Comparator<IPLBowlingAnalysis> wktsComparator = Comparator.comparing(census -> census.avg);
		this.sort2(wktsComparator);
		String sortedWktsJson = new Gson().toJson(this.iplWktsCSVList);
		return sortedWktsJson;
	}

	public String getTopStrikingRatesOfBowler(String csvFilePath) throws IPLAnalyserException {
		loadWktsCSVData(csvFilePath);
		if (iplWktsCSVList == null || iplWktsCSVList.size() == 0) {
			throw new IPLAnalyserException("NO_CENSUS_DATA",
					IPLAnalyserException.IPLAnalyserExceptionType.CENSUS_FILE_PROBLEM);
		}
		Comparator<IPLBowlingAnalysis> wktsComparator = Comparator.comparing(census -> census.strikeRate);
		this.sort2(wktsComparator);
		String sortedWktsJson = new Gson().toJson(this.iplWktsCSVList);
		return sortedWktsJson;
	}

	public List<IPLBowlingAnalysis> getBowlersHadBestEconomy(String csvFilePath)
			throws IOException, IPLAnalyserException {
		loadWktsCSVData(csvFilePath);
		List<IPLBowlingAnalysis> playerWithBestEconomy = iplWktsCSVList.stream()
				.sorted(Comparator.comparingDouble(IPLBowlingAnalysis::getEconomy)).collect(Collectors.toList());
		Collections.reverse(playerWithBestEconomy);
		return playerWithBestEconomy;
	}

	public List<IPLBowlingAnalysis> getBestStrikingRatesWith5WAnd4W(String csvFilePath)
			throws IOException, IPLAnalyserException {
		loadWktsCSVData(csvFilePath);
		int playerWith4wAnd5w = iplWktsCSVList.stream().map(player -> (player.getFourWkHaul() + player.getFiveWkHaul()))
				.max(Integer::compare).get();
		List<IPLBowlingAnalysis> playerWithBest4wAnd5w = iplWktsCSVList.stream()
				.filter(player -> player.getFourWkHaul() + player.getFiveWkHaul() == playerWith4wAnd5w)
				.collect(Collectors.toList());
		double playerWithBestStrikeRate = playerWithBest4wAnd5w.stream().map(IPLBowlingAnalysis::getStrikeRate)
				.max(Double::compare).get();
		return playerWithBest4wAnd5w.stream().filter(player -> player.getStrikeRate() == playerWithBestStrikeRate)
				.collect(Collectors.toList());
	}

	public List<IPLBowlingAnalysis> getWhoHadBowlingAvgsWithBestStrikingRates(String csvFilePath)
			throws IOException, IPLAnalyserException {
		loadWktsCSVData(csvFilePath);
		double playerWithAvgs = iplWktsCSVList.stream().map(IPLBowlingAnalysis::getAvg).max(Double::compare).get();
		List<IPLBowlingAnalysis> playerWithBestAvgs = iplWktsCSVList.stream()
				.filter(player -> player.getAvg() == playerWithAvgs).collect(Collectors.toList());
		double playerWithBestStrikeRate = playerWithBestAvgs.stream().map(IPLBowlingAnalysis::getStrikeRate)
				.max(Double::compare).get();
		return playerWithBestAvgs.stream().filter(player -> player.getStrikeRate() == playerWithBestStrikeRate)
				.collect(Collectors.toList());
	}

	public List<IPLBowlingAnalysis> getBowlersMaximumWicketsWithBestBowlingAvgs(String csvFilePath)
			throws IOException, IPLAnalyserException {
		loadWktsCSVData(csvFilePath);
		int playerWithWkts = iplWktsCSVList.stream().map(IPLBowlingAnalysis::getWickets).max(Integer::compare).get();
		List<IPLBowlingAnalysis> playerWithMaxWkts = iplWktsCSVList.stream()
				.filter(player -> player.getWickets() == playerWithWkts).collect(Collectors.toList());
		double playerWithBestBowlingAvgs = playerWithMaxWkts.stream().map(IPLBowlingAnalysis::getAvg)
				.max(Double::compare).get();
		return playerWithMaxWkts.stream().filter(player -> player.getAvg() == playerWithBestBowlingAvgs)
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

	public void sort2(Comparator<IPLBowlingAnalysis> wktsComparator) {
		for (int i = 0; i < iplWktsCSVList.size(); i++) {
			for (int j = 0; j < iplWktsCSVList.size() - i - 1; j++) {
				IPLBowlingAnalysis wkt1 = iplWktsCSVList.get(j);
				IPLBowlingAnalysis wkt2 = iplWktsCSVList.get(j + 1);
				if (wktsComparator.compare(wkt1, wkt2) > 0) {
					iplWktsCSVList.set(j, wkt2);
					iplWktsCSVList.set(j + 1, wkt1);
				}
			}
		}
	}

}
