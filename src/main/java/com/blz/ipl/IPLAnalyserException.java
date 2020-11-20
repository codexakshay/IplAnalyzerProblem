package com.blz.ipl;

public class IPLAnalyserException extends Exception {

	enum IPLAnalyserExceptionType {
		CENSUS_FILE_PROBLEM, INCORRECT_TYPE, SOME_OTHER_ERRORS, PARSE_ERROR
	}

	IPLAnalyserExceptionType exceptionType;

	public IPLAnalyserException(String message, IPLAnalyserExceptionType exceptionType) {
		super(message);
		this.exceptionType = exceptionType;
	}

	public IPLAnalyserException(String message, IPLAnalyserExceptionType type, Throwable cause) {
		super(message, cause);
		this.exceptionType = type;
	}
}