package Collector;

import org.jsoup.nodes.Element;

public interface CallbackDrill {
	public String drillTag(Element usefulElement, StringBuilder childSb);
}
