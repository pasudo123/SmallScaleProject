package edu.doubler.relevant.keyword;

import java.util.ArrayList;
import java.util.HashMap;

import edu.doubler.log_process.domain.KeywordMentionMapper;

public interface KeywordMentionCorrelat {
	public HashMap<String, ArrayList<KeywordMentionMapper>> getKeywordMention(String path);
}
