package doubler.main;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class Main {
	static Connection conn = null;
	static PreparedStatement pstmt = null;
	private static List<String[]> allRowsData = new ArrayList<String[]>();
     
	public static void main(String[]args) throws Exception{
		Main main = new Main();
		
		conn = main.getConnection();
		main.processAllSheets("src/doubler/main/crc_category.xlsx");
	}
	
	public Connection getConnection(){
		Connection conn = null;
		
		try {
			String user = "userName";
			String pw = "userPassword";
			String url = "URL";

			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pw);
			System.out.println("Database에 연결되었습니다.\n");
		} 
		
		catch (ClassNotFoundException cnfe) {
			System.out.println("DB 드라이버 로딩 실패 :" + cnfe.toString());
		} 
		
		catch (SQLException sqle) {
			System.out.println("DB 접속실패 : " + sqle.toString());
		} 
		
		catch (Exception e) {
			System.out.println("Unkonwn error");
			e.printStackTrace();
		}
		return conn;
	}
	
	
	public static void insertDB() throws SQLException{
		
		String query = "INSERT INTO CPC_TB VALUES(?, ?, ?)";
		int size = allRowsData.size();
		String[]rowData = null;
		
		conn.setAutoCommit(false);
		pstmt = conn.prepareStatement(query);
		System.out.println(allRowsData.size());
		
		
		for(int i = 0; i < size; i++){
			rowData = allRowsData.get(i);
			
			if(rowData[0].equals("코드") || rowData[0].equals("") || rowData[0] == null || rowData[1].equals("원문") || rowData[2].equals("번역")){
				continue;
			}
			
			try {
				pstmt.setString(1, rowData[0]);
				pstmt.setString(2, rowData[1]);
				pstmt.setString(3, rowData[2]);
				pstmt.addBatch();
				
				if(i % 10000 == 0){
					pstmt.executeBatch();
					conn.commit();
				}
			} 
			catch (SQLException e) {
				e.printStackTrace();
				
				System.out.println(rowData[0]);
				System.out.println(rowData[1]);
				System.out.println(rowData[2]);
				
				System.exit(1);
			}
		}// for
		
		try{
			pstmt.executeBatch();
			conn.commit();
			
			pstmt.close();
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/*********************************************************
	 * 
	 * 				 [ 대용량 엑셀 파일 읽어들이는 소스  ]
	 * 
	 * 데이터의 건수는 26만건 된다. 기존 POI 라이브러리를 이용한다면 아예 읽혀지지 않는다.
	 * 그렇기 때문에 아래의 소스를 복붙하였고 기존의 내용을 수정하였다. 내부 클래스로 작성되었기 
	 * 때문에 따로 외부 클래스의 멤버필드를 static으로 뺴두었고 많은 건수를 처리하기 위해 Collection 객체와
	 * 불변한 성질을 지닌 String 클래스를 이용하였다. 
	 * 
	 * 그리고 PK 내용은 NULL 이 있는 경우가 있기 때문에 
	 * 상단의 insertDB() 메소드에서 따로 조건처리를 해주어서 제외시켜주었다.
	 * 
	 * reference : http://svn.apache.org/repos/asf/poi/trunk/src/examples/src/org/apache/poi/xssf/eventusermodel/examples/FromHowTo.java
	 * 
	 * @param filename
	 * @throws Exception
	 *********************************************************/
	public void processAllSheets(String filename) throws Exception {
        try (OPCPackage pkg = OPCPackage.open(filename, PackageAccess.READ)) {
            XSSFReader r = new XSSFReader(pkg);
            SharedStringsTable sst = r.getSharedStringsTable();

            XMLReader parser = fetchSheetParser(sst);

            Iterator<InputStream> sheets = r.getSheetsData();
            while (sheets.hasNext()) {
                System.out.println("Processing new sheet:\n");
                try (InputStream sheet = sheets.next()) {
					InputSource sheetSource = new InputSource(sheet);
					parser.parse(sheetSource);
				}
                System.out.println("");
            }
            
            insertDB();
        }
    }
	
	 public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException, ParserConfigurationException {
        XMLReader parser = SAXHelper.newXMLReader();
        ContentHandler handler = new SheetHandler(sst);
        parser.setContentHandler(handler);
        return parser;
    }
	 
	 private static class SheetHandler extends DefaultHandler {
        private final SharedStringsTable sst;
        private String lastContents;
        private boolean nextIsString;
        private boolean inlineStr;
        private final LruCache<Integer,String> lruCache = new LruCache<>(50);
        private String cellPosition = null;
        
        private String data1 = null;
        private String data2 = null;
        private String data3 = null;
        
        private static class LruCache<A,B> extends LinkedHashMap<A, B> {
            private final int maxEntries;

            public LruCache(final int maxEntries) {
                super(maxEntries + 1, 1.0f, true);
                this.maxEntries = maxEntries;
            }

            @Override
            protected boolean removeEldestEntry(final Map.Entry<A, B> eldest) {
                return super.size() > maxEntries;
            }
        }

        private SheetHandler(SharedStringsTable sst) {
            this.sst = sst;
        }

		@Override
		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
			// c => cell
			if (name.equals("c")) {
				// Print the cell reference
//				System.out.print(attributes.getValue("r") + " - ");
				
				cellPosition = new String(attributes.getValue("r"));
//				if(cellPosition.toString().contains("A") || cellPosition.toString().contains("C") || cellPosition.toString().contains("D"))
//            		System.out.println(cellPosition);
            	
				// Figure out if the value is an index in the SST
                String cellType = attributes.getValue("t");
                
                nextIsString = cellType != null && cellType.equals("s");
                inlineStr = cellType != null && cellType.equals("inlineStr");
            }
			
            // Clear contents cache
            lastContents = "";
        }

        @Override
        public void endElement(String uri, String localName, String name)
                throws SAXException {
            // Process the last contents as required.
            // Do now, as characters() may be called more than once
            if(nextIsString) {
                Integer idx = Integer.valueOf(lastContents);
                lastContents = lruCache.get(idx);
                if (lastContents == null && !lruCache.containsKey(idx)) {
                    lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
                    lruCache.put(idx, lastContents);
                }
                nextIsString = false;
            }

            // v => contents of a cell
            // Output after we've seen the string contents
            if(name.equals("v") || (inlineStr && name.equals("c"))) {
            	if(cellPosition.toString().contains("A"))
            		data1 = new String(lastContents);
            	if(cellPosition.toString().contains("C"))
            		data2 = new String(lastContents);
            	if(cellPosition.toString().contains("D")){
            		data3 = new String(lastContents);
            		String[]datas = {data1, data2, data3};
            		
            		allRowsData.add(datas);
            	}
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException { // NOSONAR
            lastContents += new String(ch, start, length);
        }
    }
}