package j_collector;

public class TableCell {
	private String name;
	private int colSpan = 1;
	private int rowSpan = 1;
	
	public TableCell(String name){
		this.name = name;
	}
	
	public void setColSpan(int n){
		this.colSpan = n;
	}
	
	public void setRowSpan(int n){
		this.rowSpan = n;
	}
	
	public String getName(){
		return name;
	}
	
	public int getColSpan(){
		return colSpan;
	}
	
	public int getRowSpan(){
		return rowSpan;
	}
	
	@Override
	public String toString() {
		return "TableCell [name=" + name + ", colSpan=" + colSpan + ", rowSpan=" + rowSpan + "]";
	}
}
