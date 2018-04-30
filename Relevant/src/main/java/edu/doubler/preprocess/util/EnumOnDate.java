package edu.doubler.preprocess.util;

public enum EnumOnDate{
	YEAR(2014, "2014"),
	
	JANUARY(1, "01", "JANUARY"){public String toString(){return YEAR + getDateValue();}}, 
	FEBUARY(2, "02","FEBUARY"){public String toString(){return YEAR + getDateValue();}}, 
	MARCH(3, "03", "MARCH"){public String toString(){return YEAR + getDateValue();}}, 
	APRIL(4, "04", "APRIL"){public String toString(){return YEAR + getDateValue();}}, 
	MAY(5, "05", "MAY"){public String toString(){return YEAR + getDateValue();}}, 
	JUNE(6, "06", "JUNE"){public String toString(){return YEAR + getDateValue();}},
	JULY(7, "07", "JULY"){public String toString(){return YEAR + getDateValue();}},
	AUGUST(8, "08", "AUGUST"){public String toString(){return YEAR + getDateValue();}},
	SEPTEMBER(9, "09","SEPTEMBER"){public String toString(){return YEAR + getDateValue();}}, 
	OCTOBER(10, "10","OCTOBER"){public String toString(){return YEAR + getDateValue();}},
	NOVEMBER(11, "11","NOVEMBER"){public String toString(){return YEAR + getDateValue();}}, 
	DECEMBER(12, "12","DECEMBER"){public String toString(){return YEAR + getDateValue();}};
	
	private int number = 0;
	private String dateValue = null;
	private String dateEngName = null;
	
	private EnumOnDate(int number){
		this.number = number;
	}
	
	private EnumOnDate(int number, String dateValue){
		this(number);
		this.dateValue = dateValue;
	}
	
	private EnumOnDate(int number, String dateValue, String dateEngName){
		this(number, dateValue);
		this.dateEngName = dateEngName;
	}
	
	public int getNumber(){
		return number;
	}
	
	public String getDateValue(){
		return dateValue;
	}
	
	public String getDateEngName(){
		return dateEngName;
	}
	
	@Override
	public String toString(){
		return dateValue;
	}
}
