package worldcup.impl.converter;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class XlsxReader {

	public static void main(String[] args) throws Exception {

//		System.out.println(new Date());
		File myFile = new File("/home/giovanni/Scaricati/pronostico_Porta.xlsx");
		Workbook myWorkBook = WorkbookFactory.create(myFile);

		Sheet mySheet = myWorkBook.getSheet("Matches");
		
//		System.out.println(mySheet.getRow(47).getCell(16).getStringCellValue());
//		int j = 1;
//		while(j < 5) {
//				Sheet mySheet = myWorkBook.getSheetAt(j);
//				System.out.println(mySheet.getSheetName());
//				j++;
//			Sheet mySheet = myWorkBook.getSheet(name.getSheetName());
//		for(int i = 0; i < mySheet.getPhysicalNumberOfRows(); i++) {
//			Row row = mySheet.getRow(i);
//			
//			row.forEach(c -> {
//				switch(c.getCellType()) {
//				case BLANK:
//					break;
//				case BOOLEAN:
//					break;
//				case ERROR:
//					break;
//				case FORMULA:
//					try {
//						if(c.getStringCellValue().contains("I58"))
//							System.out.println("r["+c.getRowIndex()+"] c["+c.getColumnIndex()+"] " + c.getStringCellValue());
//					} catch(Exception e) {
//					}
//					break;
//				case NUMERIC:
//					break;
//				case STRING: //System.out.println("r["+c.getRowIndex()+"] c["+c.getColumnIndex()+"] " + c.getStringCellValue());
//					break;
//				case _NONE:
//					break;
//				default:
//					break;}
//			});
//
//		}
//		}
		for(int i = 6; i < 42; i++) {

			printStadio(mySheet, i);
		}
		System.out.println("------------------------");
		
		for(int i = 43; i < 58; i++) {
			printStadio(mySheet, i);
		}
//		System.out.println(new Date());
		

	}

	/**
	 * @param mySheet
	 * @param i
	 */
	private static void printStadio(Sheet mySheet, int i) {

		Row row = mySheet.getRow(i);
		
		String stadio = row.getCell(14).getStringCellValue();
		
		System.out.println("Stadio " + stadio);
	}

	private static void printIncontro(Sheet sheet, int rowNum) {

		Row row = sheet.getRow(rowNum);
		
		int incontro = (int) row.getCell(1).getNumericCellValue();
		int goalCasa = (int) row.getCell(8).getNumericCellValue();
		int goalTrasferta = (int) row.getCell(9).getNumericCellValue();
		
		System.out.println("Incontro " + incontro + ":" + goalCasa + "-"+goalTrasferta);
		
	}
}
