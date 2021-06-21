//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import worldcup.business.calculator.GironeCalculator;
//import worldcup.business.calculator.GironePerformance;
//import worldcup.business.calculator.GironeResult;
//import worldcup.business.calculator.GoalDifferenceGironePerformanceEvaluator;
//import worldcup.business.calculator.GoalsDoneGironePerformanceEvaluator;
//import worldcup.business.calculator.IPerformanceEvaluator;
//import worldcup.business.calculator.PuntiGironePerformanceEvaluator;
//import worldcup.business.calculator.Regole;
//import worldcup.business.calculator.RegoleGirone;
//import worldcup.orm.vo.DatiPartitaVO;
//import worldcup.orm.vo.GiocatoreVO;
//import worldcup.orm.vo.PartitaVO;
//import worldcup.orm.vo.PronosticoVO;
//import worldcup.orm.vo.SquadraVO;
//import worldcup.orm.vo.SubdivisionVO;
//import worldcup.orm.vo.SubdivisionVO.TIPO;
//
//public class TestPassaggiTurno {
//
//	public void testPassaggioTurno() throws IOException {
//		InputStream is = TestPassaggiTurno.class.getResourceAsStream("/gironiEuro2020.csv");
//		
//		String gironi = new String(is.readAllBytes());
//
//		String[] gironiLines = gironi.split("\n");
//		Map<String, SubdivisionVO> gironiMap = new HashMap<>();	
//		for(String partita: gironiLines) {
//			String[] partitaFields = partita.split(";");
//			
//			String gironeKey = partitaFields[1];
//			String codicePartita = partitaFields[0];
//			String nomeSquadraCasa = partitaFields[3];
//			String nomeSquadraTrasferta = partitaFields[4];
//			
//			SubdivisionVO girone = null;
//			if(!gironiMap.containsKey(gironeKey)) {
//				girone = new SubdivisionVO();
//				girone.setNome(gironeKey);
//				girone.setTipo(TIPO.GIRONE);
//				gironiMap.put(gironeKey, girone);
//			} else {
//				girone = gironiMap.get(gironeKey);
//			}
//			
//			
//			PartitaVO partitaVO = new PartitaVO();
//			partitaVO.setCodicePartita(codicePartita);
//			SquadraVO squadraCasa = new SquadraVO();
//			squadraCasa.setNome(nomeSquadraCasa);
//			partitaVO.setCasa(squadraCasa);
//			SquadraVO squadraTrasferta = new SquadraVO();
//			squadraTrasferta.setNome(nomeSquadraTrasferta);
//			partitaVO.setTrasferta(squadraTrasferta);
//			girone.getPartite().add(partitaVO);
//		}
//		
//		for(SubdivisionVO girone: gironiMap.values()) {
//			Map<String, SquadraVO> squadre = new HashMap<>();
//			for(PartitaVO p: girone.getPartite()) {
//				squadre.put(p.getCasa().getNome(), p.getCasa());
//				squadre.put(p.getTrasferta().getNome(), p.getTrasferta());
//			}
//			
//			girone.setSquadre(squadre.values().stream().collect(Collectors.toSet()));
//		}
//		
////		gironiMap.keySet().stream().sorted().forEach(k -> {
////			SubdivisionVO s = gironiMap.get(k);
////			System.out.println("Girone: " + s.getNome());
////			System.out.println("Partite: ");
////			s.getPartite().stream().sorted(new Comparator<PartitaVO>() {
////
////	@Override
////	public int compare(PartitaVO o1, PartitaVO o2) {
////		return Integer.parseInt(o1.getCodicePartita()) - Integer.parseInt(o2.getCodicePartita());
////	}
////			}).forEach(partita -> {
//////			for(PartitaVO partita: s.getPartite()) {
////				System.out.println(partita.getCodicePartita()+":"+partita.getDatiUfficiali().getCasa().getNome()+"-"+partita.getDatiUfficiali().getTrasferta().getNome());
////			});
////		});
//		
//		
//		GironeCalculator cal = new GironeCalculator();
//		
//		RegoleGirone conf = new RegoleGirone();
//		Regole regoleVerticali = new Regole();
//		List<IPerformanceEvaluator<GironePerformance>> regoleVerticaliLst = new ArrayList<IPerformanceEvaluator<GironePerformance>>();
//		
//		regoleVerticaliLst.add(new PuntiGironePerformanceEvaluator());
//		regoleVerticaliLst.add(new GoalsDoneGironePerformanceEvaluator());
//		regoleVerticaliLst.add(new GoalDifferenceGironePerformanceEvaluator());
//		regoleVerticali.setRegole(regoleVerticaliLst);
//		conf.setRegoleVerticali(regoleVerticali );
//		conf.setRegoleOrizzontali(regoleVerticali );
//		
//		
//		PronosticoVO pronostico = new PronosticoVO();
//		GiocatoreVO giocatore = new GiocatoreVO();
//		giocatore.setNome("bussu");
//		pronostico.setGiocatore(giocatore);
//		pronostico.setIdPronostico("euro-bussu");
//		Set<DatiPartitaVO> datiPartite = new HashSet<>();
//
//		for(SubdivisionVO girone: gironiMap.values()) {
//			for(PartitaVO part: girone.getPartite()) {
//				DatiPartitaVO dati = new DatiPartitaVO();
//				dati.setCodicePartita(part.getCodicePartita());
//				dati.setGoalCasa(new Random().nextInt(4));
//				dati.setGoalTrasferta(0);
//				datiPartite.add(dati);
//			}
//		}
//		pronostico.setDatiPartite(datiPartite);
////		pronostico.setTorneo(torneo);
//		GironeResult result = cal.getResult(gironiMap.values(), pronostico, conf);
//		
//		System.out.println("Squadra\t\tPunti\tGD\tGF");
//		result.getClassificaOrizzontale(3).getSquadre().entrySet().stream().forEach(s ->{
//			System.out.println(s.getValue().getSquadra().getNome() +"\t\t"+ s.getValue().getPunti() +"\t"+ s.getValue().getDifferenzaReti() +"\t"+ s.getValue().getGoalFatti());
//		});
////
////		ComposizioneOttavi ott = new ComposizioneOttavi();
////
////		TiebreakConfig config = new TiebreakConfig();
////		
////		
////		InputStream isTerze = TestPassaggiTurno.class.getResourceAsStream("/TERZE_EURO.csv");
////		
////		String terze = new String(IOUtils.toByteArray(isTerze));
////
////		Map<String, String> algoritmoTerze = new HashMap<>();
////		
////		String[] terzeList = terze.split("\n");
////		
////		for(String terza: terzeList) {
////			String[] nameValue = terza.split(",");
////			algoritmoTerze.put(nameValue[0], nameValue[1]);
////		}
////
//////		algoritmoTerze.keySet().stream().sorted().forEach( k -> {
//////			System.out.println(k+"->"+algoritmoTerze.get(k));
//////		});
////		config.setAlgoritmoTerze(algoritmoTerze);
////		
////		Map<String, Integer> opponentsTerze = new HashMap<>();
////		InputStream isOpponentTerze = TestPassaggiTurno.class.getResourceAsStream("/OPPONENTS_TERZE.csv");
////		
////		String opponentTerze = new String(IOUtils.toByteArray(isOpponentTerze));
////
////		String[] opponentsTerzeList = opponentTerze.split("\n");
////
////		for(String terza: opponentsTerzeList) {
////			String[] nameValue = terza.split(",");
////			opponentsTerze.put(nameValue[0], Integer.parseInt(nameValue[1]));
////		}
////
//////		opponentsTerze.keySet().stream().sorted().forEach( k -> {
//////			System.out.println(k+"->"+opponentsTerze.get(k));
//////		});
////
////		
////		
////		config.setOpponentsTerze(opponentsTerze);
////		
////		
////		InputStream isListaPartite = TestPassaggiTurno.class.getResourceAsStream("/EURO_OTTAVI.csv");
////			
////		String listaPartiteString = new String(IOUtils.toByteArray(isListaPartite));
////
////		String[] listaPartiteList = listaPartiteString.split("\n");
////
////
////		List<PartitaInput> listaPartite = new ArrayList<>();
////		
////		for(String partita: listaPartiteList) {
////			String[] partitaList = partita.split(",");
////			
////			PartitaInput partitaInput = new PartitaInput();
////
////			partitaInput.setCodicePartita(partitaList[0]);
////			partitaInput.setCodiceCasa(partitaList[1]);
////			partitaInput.setCodiceTrasferta(partitaList[2]);
////			
////			listaPartite.add(partitaInput);
////		}
////		
////		config.setListaPartite(listaPartite);
////		
//////		ObjectMapper om = new ObjectMapper();
//////		
//////		om.writer().writeValue(System.out, gironiMap.values());
////		SubdivisionVO ottavi = ott.getPartite(result, config );
////		
////		ottavi.getPartite().stream().sorted(new Comparator<PartitaVO>() {
////
////			@Override
////			public int compare(PartitaVO o1, PartitaVO o2) {
////				return o1.getCodicePartita().compareTo(o2.getCodicePartita());
////			}
////		}).forEach(p -> {
////			System.out.println(p.getCodicePartita() + p.getCasa().getNome() + p.getTrasferta().getNome());
////		}
////		);
////		
//		
//	}
//}
