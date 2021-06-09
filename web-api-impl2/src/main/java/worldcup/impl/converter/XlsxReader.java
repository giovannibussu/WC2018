package worldcup.impl.converter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import worldcup.orm.vo.DatiPartitaVO;
import worldcup.orm.vo.PronosticoVO;

public class XlsxReader {

	public static void main(String[] args) throws Exception {

		System.out.println(new Date());
		File myFile = new File("/home/bussu/Downloads/tabellone-euro2020_Porta.xlsx");
		Workbook myWorkBook = WorkbookFactory.create(myFile);

		Sheet mySheet = myWorkBook.getSheet("Matches");
		
		for(int i = 6; i < 42; i++) {

			printIncontro(mySheet, i);
		}
		System.out.println("------------------------");
		
		for(int i = 43; i < 58; i++) {
			printIncontro(mySheet, i);
		}
		System.out.println(new Date());
		

	}

	public PronosticoVO getPronostico(byte[] pronosticoRaw) throws EncryptedDocumentException, IOException {
		PronosticoVO p = new PronosticoVO();
		p.setPronosticoOriginale(pronosticoRaw);
		
		Workbook myWorkBook = WorkbookFactory.create(new ByteArrayInputStream(pronosticoRaw));
		

		Sheet mySheet = myWorkBook.getSheet("Matches");
		
		for(int i = 6; i < 58; i++) {
			setIncontro(p, mySheet, i);
		}
		return p;
	}
	private static void printIncontro(Sheet sheet, int rowNum) {

		Row row = sheet.getRow(rowNum);
		
		int incontro = (int) row.getCell(1).getNumericCellValue();
		int goalCasa = (int) row.getCell(8).getNumericCellValue();
		int goalTrasferta = (int) row.getCell(9).getNumericCellValue();
		
		System.out.println("Incontro " + incontro + ":" + goalCasa + "-"+goalTrasferta);
		
	}
	private static void setIncontro(PronosticoVO p, Sheet sheet, int rowNum) {

		Row row = sheet.getRow(rowNum);
		
		String incontro = row.getCell(1).getStringCellValue();
		int goalCasa = (int) row.getCell(8).getNumericCellValue();
		int goalTrasferta = (int) row.getCell(9).getNumericCellValue();
		
		DatiPartitaVO dp = new DatiPartitaVO();
		dp.setGoalCasa(goalCasa);
		dp.setGoalTrasferta(goalTrasferta);
		dp.setCodicePartita("" + incontro);
		p.getDatiPartite().add(dp);
	}
}
