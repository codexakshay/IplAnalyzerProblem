package com.blz.ipl;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

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
