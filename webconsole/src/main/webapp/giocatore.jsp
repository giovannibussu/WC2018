<%@page import="worldcup.clients.model.Pronostico"%>
<%@page import="worldcup.core.ClassificaGenerale"%>
<%@page import="worldcup.clients.model.Risultato"%>
<%@page import="worldcup.clients.model.RisultatoPartita"%>
<%@page import="worldcup.core.utils.PartitaUtils"%>
<%@page import="worldcup.clients.model.PronosticoRisultato"%>
<%@page import="worldcup.clients.model.Giocatore"%>
<%@page import="worldcup.core.utils.Utilities"%>
<%@page import="worldcup.core.utils.JsonSerializable"%>
<%@page import="worldcup.clients.model.Squadra"%>
<%@page import="worldcup.clients.model.Stadio"%>
<%@page import="worldcup.clients.model.PronosticoPartita"%>
<%@page import="worldcup.clients.model.Partita"%>
<%@page import="java.util.Map"%>
<%@page import="worldcup.core.ProssimiIncontri"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">

	<jsp:include page="includes/header.jsp" flush="true">
		<jsp:param name="titoloPagina" value="UEFA EURO 2020 | Pronostico Giocatore" />
	</jsp:include>
    <%
    String context = request.getContextPath();
    String idGiocatore = request.getParameter("idGiocatore");
    
    ClassificaGenerale classificaGenerale = new ClassificaGenerale();
    Pronostico pronosticoUfficiale = classificaGenerale.getPronosticoUfficiale();
    
    Pronostico pronosticoGiocatore = classificaGenerale.getPronosticoGiocatore(idGiocatore);
    Giocatore giocatore = pronosticoGiocatore.getGiocatore();
    
    
//     String idMatch = request.getParameter("idMatch");
//     ProssimiIncontri pi = new ProssimiIncontri();
//     Partita match = pi.getMatch(idMatch);
//     List<PronosticoPartita> listaPronosticiMatch = pi.getPronosticiPerMatch(idMatch);
    %>
  <body>
	<jsp:include page="includes/navbar.jsp" flush="true">
		<jsp:param name="idPagina" value="3" />
	</jsp:include>

    <main role="main" class="container">
    	
  	<div class="starter-template">
        <h1>&nbsp;&nbsp;</h1>
     </div>
	<div class="album">
		<div class="row">
			<%
			int sommaOk = 0;
			int sommaEsatti = 0;
// 			int numeroPronostici = listaPronosticiMatch.size();
			
//                for(PronosticoPartita pronostico: listaPronosticiMatch){
//                	 Giocatore p = pronostico.getGiocatore();
//                	PronosticoRisultato pronosticoRisultato = pronostico.getPronostico();
//                 Partita m = pronostico.getPartita();
//                 String risultato = (m.getRisultato() != null && PartitaUtils.getRisultatoEsatto(pronosticoRisultato, m, match) != null) ? PartitaUtils.getRisultatoEsatto(pronosticoRisultato, m, match) : ""; //TODO bussu ribaltare risultati
//                 Risultato risultatoEnum = PartitaUtils.getRisultato(pronosticoRisultato);
                
//                 if(risultatoUfficiale.equals(risultato)){
//                	 sommaEsatti ++;
//                 } else {
//                	 if(risultatoUfficialeEnum.equals(risultatoEnum)){
//                		sommaOk ++;
//                	 }
//                 }
//                }
               
//                int numeroErrati = numeroPronostici - (sommaEsatti + sommaOk);
               
//                int percOk = numeroPronostici != 0 ? ((int) sommaOk * 100 / numeroPronostici) : 0;
//                int percEsatti = numeroPronostici != 0 ? ((int) sommaEsatti * 100 / numeroPronostici) : 0;
//                int percErrati = numeroPronostici != 0 ? ((int) numeroErrati * 100 / numeroPronostici) : 0;
			%>
		</div>

         <div class="row">
         	<div class="col-md-12">
            <div class="ec-fancy-title">
               <h3><a target="_blank" href="<%=pronosticoGiocatore.getLink() %>" title="Visualizza Excel"><%= giocatore.getNome() %></a></h3>
               </div>
               <div class="ec-fixture-list">
                   <ul>
                   	<li>
                         <div class="ec-cell ec-cell-giocatore-th"><span>&nbsp;</span></div>
                         <div class="ec-cell ec-cell-giocatore-th"><span>Pronostico (Risultato)</span></div>
                         <div class="ec-cell ec-cell-giocatore-th"><span>Pt: <%=pronosticoGiocatore.getPunti() %></span></div>
                     </li>
                     <%
                     for(int i = 0; i < pronosticoUfficiale.getPartite().size() ; i++){
                   		Partita partita = pronosticoUfficiale.getPartite().get(i);
	                   	String liStyleClass= i % 2 == 0 ? "even" : "odd";
                   		Squadra s1 = partita.getCasa();
	              		Squadra s2 = partita.getTrasferta();
	              		String descrizioneMatch = partita.getDescrizione();
	    				RisultatoPartita risultato = partita.getRisultato();
	    				String risultatoString = PartitaUtils.getRisultatoEsatto(risultato);
	    				
	    				Partita matchGiocatore = PartitaUtils.getPartitaFromPronostico(pronosticoGiocatore.getPartite(), partita);
	    				RisultatoPartita risultatoGiocatore = matchGiocatore != null ? matchGiocatore.getRisultato() : null;
	    				String risultatoGiocatoreString = PartitaUtils.getRisultatoEsatto(risultatoGiocatore, partita, matchGiocatore);
	    				if(risultatoGiocatoreString == null)
	    					risultatoGiocatoreString = "";
		                 
		                String classRisultato = PartitaUtils.getClasseCSS(partita, matchGiocatore);
		                 
						int puntiGiocatore = PartitaUtils.getPunti(partita, matchGiocatore);			          	
			          	
			          	%>
			          	<li class="<%=liStyleClass %>">
	                         <div class="ec-cell ec-cell-info-match-th"><span><%=descrizioneMatch %></span></div>
	                         <div class="ec-cell ec-cell-info-match-th"><span>&nbsp;</span></div>
	                         <div class="ec-cell ec-cell-info-match-th"><span>&nbsp;</span></div>
	                     </li>
                       <li class="<%=liStyleClass %> li-<%=classRisultato %>">
                       		<div class="ec-cell ec-cell-info-match ec-cell-<%=classRisultato %>"><span><%=s1.getNome() %> - <%=s2.getNome() %></span></div>
                          	<div class="ec-cell ec-cell-info-match ec-cell-<%=classRisultato %>"><span><%=risultatoGiocatoreString %> <p class="info-match-p">(<%=risultatoString %>)</p></span></div>
                          	<div class="ec-cell ec-cell-info-match ec-cell-<%=classRisultato %>"><span><%=puntiGiocatore %></span></div>
                       </li>
                   <% } %>       
                     </ul>
                 </div>
             </div>
         </div>
     </div>

    </main><!-- /.container -->
   
    <jsp:include page="includes/footer.jsp" flush="true"></jsp:include>
  </body>
</html>
