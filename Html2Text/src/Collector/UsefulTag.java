package Collector;

public enum UsefulTag {
	USEFUL_BR("<br>"),
	USEFUL_START_P("<p>"),
	USEFUL_END_P("</p>"),
	USEFUL_START_TABLE("<table>"),
	USEFUL_END_TABLE("</table>"),
	USEFUL_TABLE_TH("<th>"),
	USEFUL_TABLE_TR("<tr>"),
	USEFUL_TABLE_TD("<td>"),
	USEFUL_TABLE_THEAD("<thead>"),
	USEFUL_TABLE_TBODY("<tbody>");
	
	private String tagName;
	
	private UsefulTag(String tagName){
		this.tagName = tagName;
	}
	
	public String getTagName(){
		return tagName;
	}
}
