package worldcup.impl.converter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.Resource;

import worldcup.model.Pronostico;
import worldcup.model.PronosticoRisultato;
import worldcup.orm.vo.DatiPartitaVO;
import worldcup.orm.vo.GiocatoreVO;
import worldcup.orm.vo.PronosticoVO;
import worldcup.orm.vo.TorneoVO;

public class PronosticoConverter {

	public static Pronostico toRsModel(PronosticoVO dto, Integer punti) {
		Pronostico rsModel = new Pronostico();
		
		rsModel.setGiocatore(GiocatoreConverter.toRsModel(dto.getGiocatore()));
		rsModel.setPunti(punti);
//		rsModel.setSquadraVincente(SquadraConverter.toRsModel(dto.getTorneo().getPronosticoUfficiale())); //TODO SQUADRA VINCENTE
		
		return rsModel;
	}
	
	public static PronosticoRisultato toRsModel(DatiPartitaVO dto) {
		PronosticoRisultato rsModel = new PronosticoRisultato();
		
		rsModel.setGoalAway(dto.getGoalTrasferta());
		rsModel.setGoalHome(dto.getGoalCasa());
		
		return rsModel;
	}

	public static PronosticoVO toPronosticoVO(TorneoVO torneo, GiocatoreVO giocatore, Resource body) throws IOException {
		byte[] b = body.getInputStream().readAllBytes();
		
		PronosticoVO p = getPronostico(b);

		p.setGiocatore(giocatore);
		p.setIdPronostico(giocatore.getNome() + "_"  +torneo.getNome());
		p.setTorneo(torneo);
		return p;
		
	}
	
	
//	public static void main(String[] args) throws IOException {
//		byte[] pr = Files.readAllBytes(Paths.get("/home/bussu/Downloads/tabellone-euro2020_Porta.xlsx"));
//		
//		PronosticoVO p = getPronostico(pr);
//		
//		p.getDatiPartite().stream().sorted(new Comparator<DatiPartitaVO>() {
//
//			@Override
//			public int compare(DatiPartitaVO o1, DatiPartitaVO o2) {
//				return (Integer.parseInt(o1.getCodicePartita()) - (Integer.parseInt(o2.getCodicePartita())));
//			}
//		}).forEach(dp -> {
////		for(DatiPartitaVO dp: p.getDatiPartite()) {
//			System.out.println("Incontro " + dp.getCodicePartita() + ":" + dp.getGoalCasa() + "-"+dp.getGoalTrasferta());
//		});
//	}
	public static PronosticoVO getPronostico(byte[] pronosticoRaw) throws EncryptedDocumentException, IOException {
		PronosticoVO p = new PronosticoVO();
		p.setPronosticoOriginale(pronosticoRaw);
		
		Workbook myWorkBook = WorkbookFactory.create(new ByteArrayInputStream(pronosticoRaw));
		

		Sheet mySheet = myWorkBook.getSheet("Matches");
		
		for(int i = 6; i < 58; i++) {
			if(i != 42) {
				setIncontro(p, mySheet, i);
			}
		}
		return p;
	}
	private static void setIncontro(PronosticoVO p, Sheet sheet, int rowNum) {

		Row row = sheet.getRow(rowNum);
		
		String incontro = getStringValue(row.getCell(1));
		int goalCasa = Integer.parseInt(getStringValue(row.getCell(8)));
		int goalTrasferta = Integer.parseInt(getStringValue(row.getCell(9)));
		
		DatiPartitaVO dp = new DatiPartitaVO();
		dp.setGoalCasa(goalCasa);
		dp.setGoalTrasferta(goalTrasferta);
		dp.setCodicePartita(incontro);
		dp.setPronostico(p);
		p.getDatiPartite().add(dp);
	}
	
	private static String getStringValue(Cell cell) {
		switch(cell.getCellType()) {
		case BLANK:
		case BOOLEAN:
		case ERROR:
		case _NONE:
		case FORMULA:
			break;
		case NUMERIC:
			return (int) (cell.getNumericCellValue()) + "";
		case STRING:
			return cell.getStringCellValue();
		default:
			break;
		
		}

		throw new RuntimeException("Cell type: " + cell.getCellType());
	}

}
