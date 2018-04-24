package edu.doubler.preprocess.util;

public enum EnumOnDate{
	YEAR(2014, "2014"),
	
	JANUARY(1, "01"){public String toString(){return YEAR + getDateValue();}}, 
	FEBUARY(2, "02"){public String toString(){return YEAR + getDateValue();}}, 
	MARCH(3, "03"){public String toString(){return YEAR + getDateValue();}}, 
	APRIL(4, "04"){public String toString(){return YEAR + getDateValue();}}, 
	MAY(5, "05"){public String toString(){return YEAR + getDateValue();}}, 
	JUNE(6, "06"){public String toString(){return YEAR + getDateValue();}},
	JULY(7, "07"){public String toString(){return YEAR + getDateValue();}},
	AUGUST(8, "08"){public String toString(){return YEAR + getDateValue();}},
	SEPTEMBER(9, "09"){public String toString(){return YEAR + getDateValue();}}, 
	OCTOBER(10, "10"){public String toString(){return YEAR + getDateValue();}},
	NOVEMBER(11, "11"){public String toString(){return YEAR + getDateValue();}}, 
	DECEMBER(12, "12"){public String toString(){return YEAR + getDateValue();}};
	
	private int number = 0;
	private String dateValue = null;
	
	private EnumOnDate(String dateValue){
		this.dateValue = dateValue;
	}
	
	private EnumOnDate(int number, String dateValue){
		this.number = number;
		this.dateValue = dateValue;
	}
	
	public int getNumber(){
		return number;
	}
	
	public String getDateValue(){
		return dateValue;
	}
	
	@Override
	public String toString(){
		return dateValue;
	}
}
