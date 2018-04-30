package edu.doubler.log_process;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public interface BufferedReaderCallback {
	Hashtable<String, Integer> doSomethingWithReader(File file) throws IOException;
}
