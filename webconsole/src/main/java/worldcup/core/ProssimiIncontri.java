package worldcup.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import worldcup.bean.Partita;
import worldcup.bean.Squadra;
import worldcup.bean.Stadio;

public class ProssimiIncontri {
	
	private Gioco gioco = null;
	
	public ProssimiIncontri() {
	this.gioco = new Gioco();
	}
	
	public List<Match> getListProssimiIncontri(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			return this.gioco.matchPerData(sdf.parse("14-06-2018"), sdf.parse("15-06-2018"));
		} catch (ParseException e) {
			System.out.println(e.getMessage()); 
		}
		
		return new ArrayList<>();
	}

//	public List<Partita> getListProssimiIncontri(){
//		List<Partita> list = new ArrayList<>();
//		Partita p1 = new Partita();
//		p1.setData("14/06");
//		p1.setOra("18:00");
//		Squadra squadra1 = new Squadra();
//		squadra1.setId("RUS");
//		squadra1.setNome("Russia");
//		squadra1.setBandiera("images/russia.png");
//		p1.setSquadra1(squadra1 );
//		Squadra squadra2 = new Squadra();
//		squadra2.setId("ARS");
//		squadra2.setNome("Arabia Saudita");
//		squadra2.setBandiera("images/arabia_saudita.png");
//		p1.setSquadra2(squadra2 );
//		Stadio stadio = new Stadio();
//		stadio.setCitta("Mosca");
//		stadio.setId("1");
//		stadio.setNome("Moscow Stadium");
//		stadio.setLink("http://www.google.it");
//		p1.setStadio(stadio);
//		list.add(p1);
//		return list;
//	}
}
