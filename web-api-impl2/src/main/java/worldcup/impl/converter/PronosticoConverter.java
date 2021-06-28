package worldcup.impl.converter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.joda.time.DateTime;
import org.springframework.core.io.Resource;
import org.springframework.format.Formatter;

import worldcup.BadRequestException;
import worldcup.business.TorneoBD;
import worldcup.business.calculator.Classifica;
import worldcup.business.calculator.GironePerformance;
import worldcup.business.calculator.GironeResult;
import worldcup.business.calculator.TorneoUtils;
import worldcup.business.calculator.WrapperDatiPartita;
import worldcup.model.Girone;
import worldcup.model.Partita;
import worldcup.model.Posizione;
import worldcup.model.Pronostico;
import worldcup.model.PronosticoRisultato;
import worldcup.orm.vo.DatiPartitaVO;
import worldcup.orm.vo.GiocatoreVO;
import worldcup.orm.vo.PartitaVO;
import worldcup.orm.vo.PronosticoVO;
import worldcup.orm.vo.SubdivisionVO;
import worldcup.orm.vo.TorneoVO;
import worldcup.orm.vo.SubdivisionVO.TIPO;

public class PronosticoConverter {

	public static Pronostico toRsModel(PronosticoVO dto, Integer punti, Formatter<DateTime> formatter, boolean partiteOn) {
		Pronostico rsModel = new Pronostico();
		
		rsModel.setGiocatore(GiocatoreConverter.toRsModel(dto.getGiocatore()));
		rsModel.setPunti(punti);
		if(dto.getVincente()!=null) {
			rsModel.setSquadraVincente(SquadraConverter.toRsModel(dto.getVincente()));
		}
		rsModel.setLink(dto.getLink());
		if(partiteOn) {
			
			TorneoVO t = TorneoUtils.getTorneoPronosticato(dto);
			List<Partita> partite = new ArrayList<>();
			
			for(SubdivisionVO s: t.getSubdivisions()) {
				for(PartitaVO p: s.getPartite()) {
					WrapperDatiPartita w = TorneoUtils.getDatiPartitaEqui(p, t);
					if(w.getDatiPartita().isPresent()) {
						partite.add(PartitaConverter.toRsModel(p, w.getDatiPartita(), formatter, w.isReverse()));
					}
				}
			}
			
			rsModel.setPartite(partite);
			
			List<Girone> g = new ArrayList<>();

			Collection<SubdivisionVO> subdivisionsPron = TorneoUtils.getSubdivisions(t, TIPO.GIRONE);
			GironeResult resultPron = TorneoUtils.getGironeResult(t.getPronosticoUfficiale(), subdivisionsPron);
			
			for(SubdivisionVO s: subdivisionsPron) {

				Girone girone = new Girone();
				girone.setNome(s.getNome());
				List<Posizione> posizioni = new ArrayList<>();

				Classifica cPron = resultPron.getClassificaVerticale(s.getNome());
				
				for(Entry<Integer, GironePerformance> e : cPron.getSquadre().entrySet()) {

					Posizione posizione = new Posizione();
					posizione.setPosizione(BigDecimal.valueOf(e.getKey()));
					posizione.setSquadra(e.getValue().getSquadra().getNome());
					
					posizioni.add(posizione);
				}

				girone.setPosizioni(posizioni);
				g.add(girone);

				Girone miglioriTerze = new Girone();
				miglioriTerze.setNome("Lucky3");
				List<Posizione> posizioni3 = new ArrayList<>();

				Classifica cTerze = resultPron.getClassificaOrizzontale(3);
				for(Entry<Integer, GironePerformance> e : cTerze.getSquadre().entrySet()) {
	
					Posizione posizione = new Posizione();
					posizione.setPosizione(BigDecimal.valueOf(e.getKey()));
					posizione.setSquadra(e.getValue().getSquadra().getNome());
					
					posizioni3.add(posizione);
				}
	
				miglioriTerze.setPosizioni(posizioni3);
				g.add(miglioriTerze);
			}

			rsModel.setGironi(g );

		}
		
		return rsModel;
	}
	
	public static PronosticoRisultato toRsModel(DatiPartitaVO dto, boolean isReverse) {
		PronosticoRisultato rsModel = new PronosticoRisultato();
		
		rsModel.setGoalHome(TorneoUtils.getGoalCasa(dto, isReverse));
		rsModel.setGoalAway(TorneoUtils.getGoalTrasferta(dto, isReverse));
		
		return rsModel;
	}

	public static PronosticoVO toPronosticoVO(TorneoVO torneo, GiocatoreVO giocatore, Resource body, String link, TorneoBD torneoBD) throws IOException {
		byte[] b = body.getInputStream().readAllBytes();
		
		PronosticoVO p = getPronostico(b, torneoBD);

		p.setGiocatore(giocatore);
		p.setIdPronostico(giocatore.getNome() + "_"  +torneo.getNome());
		p.setTorneo(torneo);
		p.setLink(link);
		return p;
		
	}
	
	
	public static PronosticoVO getPronostico(byte[] pronosticoRaw, TorneoBD torneoBD) throws EncryptedDocumentException, IOException {
		PronosticoVO p = new PronosticoVO();
//		p.setPronosticoOriginale(pronosticoRaw);
		
		Workbook myWorkBook = WorkbookFactory.create(new ByteArrayInputStream(pronosticoRaw));
		

		Sheet mySheet = myWorkBook.getSheet("Matches");
		
		for(int i = 6; i < 42; i++) {
			setIncontro(p, mySheet, i, true);
		}
		for(int i = 43; i < 58; i++) {
			setIncontro(p, mySheet, i, false);
		}
		
		p.setVincente(torneoBD.getSquadra(mySheet.getRow(47).getCell(16).getStringCellValue()));
		return p;
	}
	private static void setIncontro(PronosticoVO p, Sheet sheet, int rowNum, boolean pareggioAmmesso) {

		Row row = sheet.getRow(rowNum);
		
		String incontro = getStringValue(row.getCell(1));
		int goalCasa = Integer.parseInt(getStringValue(row.getCell(8)));
		int goalTrasferta = Integer.parseInt(getStringValue(row.getCell(9)));
		
		if(goalCasa < 0) {
			throw new BadRequestException("La squadra in casa deve fare almeno 0 goal, inseriti ["+goalCasa+"]");
		}
		if(goalTrasferta < 0) {
			throw new BadRequestException("La squadra in trasferta deve fare almeno 0 goal, inseriti ["+goalTrasferta+"]");
		}
		
		if(!pareggioAmmesso && goalCasa == goalTrasferta) {
			goalCasa = Integer.parseInt(getStringValue(row.getCell(12)));
			goalTrasferta = Integer.parseInt(getStringValue(row.getCell(13)));
			
//			throw new BadRequestException("Pareggio ["+goalCasa+"-"+goalTrasferta+"] non ammesso per la partita ["+incontro+"]");
		}

		
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
