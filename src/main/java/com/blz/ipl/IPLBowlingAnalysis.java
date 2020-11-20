package com.blz.ipl;

import com.opencsv.bean.CsvBindByName;

public class IPLBowlingAnalysis {

	@CsvBindByName(column = "POS", required = true)
	public int position;

	@CsvBindByName(column = "PLAYER", required = true)
	public String playerName;

	@CsvBindByName(column = "Mat", required = true)
	public int matches;

	@CsvBindByName(column = "Inns", required = true)
	public int innings;

	@CsvBindByName(column = "Ov", required = true)
	public double overs;

	@CsvBindByName(column = "Runs", required = true)
	public int runs;

	@CsvBindByName(column = "Wkts", required = true)
	public int wickets;

	@CsvBindByName(column = "BBI", required = true)
	public int bbi;

	@CsvBindByName(column = "Avg", required = true)
	public double avg;

	@CsvBindByName(column = "Econ", required = true)
	public double economy;

	@CsvBindByName(column = "SR", required = true)
	public double strikeRate;

	@CsvBindByName(column = "4w", required = true)
	public int fourWkHaul;

	@CsvBindByName(column = "5w", required = true)
	public int fiveWkHaul;

	public double getEconomy() {
		return economy;
	}

	public double getStrikeRate() {
		return strikeRate;
	}

	public int getFourWkHaul() {
		return fourWkHaul;
	}

	public int getFiveWkHaul() {
		return fiveWkHaul;
	}

	@Override
	public String toString() {
		return "IPLBatting [position=" + position + ", player=" + playerName + ", match=" + matches + ", innings="
				+ innings + ", Overs=" + overs + ", runs=" + runs + ", wickets" + wickets + ", average=" + avg
				+ ", BBI=" + bbi + ", average=" + avg + ", economy=" + economy + " strikeRate=" + strikeRate
				+ ", 4Wk-Hauls" + fourWkHaul + ", 5Wk-Hauls " + fiveWkHaul + " " + "]";
	}
}
