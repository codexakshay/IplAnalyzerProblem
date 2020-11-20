package com.blz.ipl;

import java.io.Reader;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class OpenCsvBuilder<E> implements ICSVBuilderFactory {

	@Override
	public List<E> getCSVFileList(Reader reader, Class csvClass) throws IPLAnalyserException {
		return this.getCSVBean(reader, csvClass).parse();
	}

	private CsvToBean<E> getCSVBean(Reader reader, Class csvClass) throws IPLAnalyserException {
		try {
			CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			csvToBeanBuilder.withType(csvClass).withIgnoreLeadingWhiteSpace(true);
			return csvToBeanBuilder.build();
		} catch (RuntimeException e) {
			throw new IPLAnalyserException("Field Exception", IPLAnalyserException.IPLAnalyserExceptionType.SOME_OTHER_ERRORS);
		}
	}

}
