package com.blz.ipl;

import com.opencsv.bean.CsvBindByName;

public class IPLBattingAnalysis {

	@CsvBindByName(column = "POS", required = true)
	public int position;

	@CsvBindByName(column = "PLAYER", required = true)
	public String playerName;

	@CsvBindByName(column = "Mat", required = true)
	public int matches;

	@CsvBindByName(column = "Inns", required = true)
	public int innings;

	@CsvBindByName(column = "NO", required = true)
	public int notOuts;

	@CsvBindByName(column = "Runs", required = true)
	public int runs;

	@CsvBindByName(column = "HS", required = true)
	public int highestScore;

	@CsvBindByName(column = "Avg", required = true)
	public double avg;

	@CsvBindByName(column = "BF", required = true)
	public int ballsFaced;

	@CsvBindByName(column = "SR", required = true)
	public int strikeRate;

	@CsvBindByName(column = "100", required = true)
	public int centuries;

	@CsvBindByName(column = "50", required = true)
	public int fifties;

	@CsvBindByName(column = "4s", required = true)
	public int fours;

	@CsvBindByName(column = "6s", required = true)
	public int sixes;

	public int getRuns() {
		return runs;
	}

	public double getAvg() {
		return avg;
	}

	public int getStrikeRate() {
		return strikeRate;
	}

	public int getCenturies() {
		return centuries;
	}

	public int getFifties() {
		return fifties;
	}

	public int getFours() {
		return fours;
	}

	public int getSixes() {
		return sixes;
	}

	@Override
	public String toString() {
		return "IPLBatting [position=" + position + ", player=" + playerName + ", match=" + matches + ", innings="
				+ innings + ", notOut=" + notOuts + ", runs=" + runs + ", highestScore=" + highestScore + ", average="
				+ avg + ", ballFaced=" + ballsFaced + ", strikeRate=" + strikeRate + ", centuries=" + centuries
				+ ", halfCenturies=" + fifties + ", fours=" + fours + ", sixes=" + sixes + "]";
	}
}
