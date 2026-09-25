package dataDrivenExcel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class dataDriven {

	// Identify test cases column by scanning entire row (first row)
	// once column is identified then scan entire testcase column to identify purchase test case
	// After you grab purchase test case row pull all the data of that row and feed into test
	
	public ArrayList<String> getData(String testcaseName) throws IOException {
		
        ArrayList<String> a = new ArrayList<>();
		
		
		FileInputStream fis = new FileInputStream("C://Users//10851440//eclipse-workspace//ExcelDriven//src//test//resources//demoData.xlsx");
		// you have to pass the object which have access the file (fis)
		// FileInputStream is class in java which have power to  read any file 
		
		XSSFWorkbook worbook = new XSSFWorkbook(fis);
		
		// Now workbook have access to fis object
		int sheets = worbook.getNumberOfSheets();
		
		for(int i = 0; i<sheets;i++) {
			if(worbook.getSheetName(i).equalsIgnoreCase("testdata")) {
			
				XSSFSheet sheet = worbook.getSheetAt(i);
				// Identify test cases column by scanning entire row (first row)
				
				Iterator<Row> rows = sheet.iterator();	// sheet of collection of rows
				Row firstrow = rows.next();
				Iterator <Cell> ce = firstrow.cellIterator(); // row is collection of cells
				ce.next();
			
				int k = 0;
				int column=0;
				
				while(ce.hasNext()) {
					
					Cell value = ce.next();
					if(value.getStringCellValue().equalsIgnoreCase("TestCases")) {
						
						column=k;
					}
					k++;
				}
				System.out.println(column);
				// scan all columns and identify where Purchase is present (which row)
				
				// once column is identified then scan entire testcase column to identify purchase test case
				while(rows.hasNext()) {
					Row r = rows.next();
					if(r.getCell(column).getStringCellValue().equalsIgnoreCase(testcaseName)) {
						// After you grab purchase test case row pull all the data of that row and feed into test
						
						// Write wrapper to check cell value is numeric or String
						Cell cell = sheet.getRow(column).getCell(i);
						
						
						Iterator<Cell> cv = r.cellIterator();
						
						while(cv.hasNext()) {
							Cell c = cv.next();
							if(c.getCellType() == CellType.STRING) {
							
								a.add(c.getStringCellValue());
							}else {
								//NumberToTextConverter.toText(c.getNumericCellValue());
								a.add(NumberToTextConverter.toText(c.getNumericCellValue()));
							}
							
						}
					}
				
				
				}
				System.out.println(a);
				
			
			}
			
		}
		return a;
	}
	
	public static void main(String[] args) throws IOException {
		
		
		
		
		
		
	}
}
