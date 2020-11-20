package com.blz.ipl;

import java.io.Reader;
import java.util.List;

public interface ICSVBuilderFactory<E>{

	List<E> getCSVFileList(Reader reader, Class<E> csvClass) throws IPLAnalyserException;
}
