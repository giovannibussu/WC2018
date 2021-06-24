<%@page import="java.text.DecimalFormat"%>
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
		<jsp:param name="titoloPagina" value="UEFA EURO 2020 | Storico Incontro" />
	</jsp:include>
    <%
    DecimalFormat dec = new DecimalFormat("#0.00");
    String context = request.getContextPath();
    String idMatch = request.getParameter("idMatch");
    ProssimiIncontri pi = new ProssimiIncontri();
    Partita match = pi.getMatch(idMatch);
    List<PronosticoPartita> listaPronosticiMatch = pi.getPronosticiPerMatch(idMatch);
    boolean visualizzaGrafici = false;
    %>
  <body>
  
   <script>
    	var graficoRisultatiEsatti = '<%= JsonSerializable.serializeToString(pi.distribuzionePronosticiPerMatchRisultatoEsatto(idMatch)) %>';
    </script>

	<jsp:include page="includes/navbar.jsp" flush="true">
		<jsp:param name="idPagina" value="3" />
	</jsp:include>

    <main role="main" class="container">
    	
  	<div class="starter-template">
        <h1>&nbsp;&nbsp;</h1>
     </div>
	<div class="album">
       	<div class="row">
         		<div class="col-md-12">
         			<div class="ec-fixture-list" style="margin-bottom: 44px;">
                   <ul>
                   <%  
                   	Stadio s = match.getStadio();
	          		Squadra s1 = match.getCasa();
	          		Squadra s2 = match.getTrasferta();
	          		String descrizioneMatch = match.getDescrizione();
	          		RisultatoPartita risultatoMatch = match.getRisultato();
	          		String risultatoUfficiale = PartitaUtils.getRisultatoEsatto(risultatoMatch);
	          		Risultato risultatoUfficialeEnum = PartitaUtils.getRisultato(risultatoMatch);
	          	%>
                       <li>
						<div class="imspo_mt__mtc-no" id="div_match_<%=match.getIdPartita() %>">
							<table class="imspo_mt__mit">
								<tbody>
									<tr class="imspo_mt__tr-s">
										<td class="lc"></td>
										<td></td>
										<td class="imspo_mt__rg"></td>
										<td class="imspo_mt__vs-c" rowspan="999"></td>
										<td class="imspo_mt__sc imspo_mt__match-sc imspo_mt__wt" rowspan="999">
											<div class="imspo_mt__ms-w imspo_mt__ms-m">
												<div>
													<div class="imspo_mt__ns-pm-s">
														<div class="imspo_mt__pm-inf imspo_mt__date imso-medium-font">
															<a class= "imspo_mt__pm-inf stadium" target="_blank" title="<%=s.getNome() %>" href="<%=s.getLink() %>"><%=s.getNome() %></a>
														</div>
														<div class="imspo_mt__pm-inf imspo_mt__date imso-medium-font"><%=Utilities.getDataMatchAsString(match.getData()) %></div>
														<div class="imspo_mt__ndl-p imspo_mt__pm-inf imso-medium-font"><%=Utilities.getOraMatchAsString(match.getData()) %></div>
													</div>
													<div style="display: none;"></div>
												</div>
												<div style="display: none;"></div>
												<div style="display: none;"></div>
												<div style="display: none;"></div>
											</div>
											<div style="display: none;"></div></td>
										<td class="imspo_mt__rpo-c" style="width: 0px"></td>
									</tr>
									<tr>
										<td class="imspo_mt__lg-st-c" colspan="3">
											<div class="imspo_mt__lg-st-co">
												<span style="display: none;"></span><span
													style="display: none;"> · </span>
													<span><%=descrizioneMatch %></span>
												<div style="display: none;"></div>
											</div></td>
									</tr>
									<tr class="imspo_mt__tr">
										<td class="lc"><jsl	jstcache="9">
											<img style="width: 48px; height: 48px;"
												src="<%=s1.getBandiera() %>"
												alt=""></jsl></td>
										<td class="tns-c imspo_mt__dt-t">
											<div class="imspo_mt__t-sc"><div class="imspo_mt__tt-w"><%=risultatoMatch.getGoalCasa() %></div></div>
											<div class="imspo_mt__tt-w">
												<div class="ellipsisize">
													<span class="qRHjyd" style="display: none"></span>
														<span><%=s1.getNome() %></span>
													</div>
											</div></td>
										<td class="imspo_mt__rg"><svg style="display: none;" ></svg></td>
									</tr>
									<tr class="imspo_mt__bt-s"></tr>
									<tr class="imspo_mt__tr">
										<td class="lc"><jsl	jstcache="9">
											<img style="width: 48px; height: 48px;"
												src="<%=s2.getBandiera() %>"
												alt=""></jsl></td>
										<td class="tns-c imspo_mt__dt-t">
											<div class="imspo_mt__t-sc"><div class="imspo_mt__tt-w"><%=risultatoMatch.getGoalTrasferta() %></div></div>
											<div class="imspo_mt__tt-w">
												<div class="ellipsisize">
													<span class="qRHjyd" style="display: none" ></span>
													<span><%=s2.getNome() %></span>
												</div>
											</div></td>
										<td class="imspo_mt__rg"><svg style="display: none;"></svg></td>
									</tr>
									<tr style="display: none;" ></tr>
									<tr style="display: none;" ></tr>
									<tr class="imspo_mt__br-s" ></tr>
								</tbody>
							</table>
						</div>
                       </li>
                   <%  %>       
                     </ul>
                 </div>
         		</div>
      		</div>
	 <% if(listaPronosticiMatch.size() > 0) { %>
	       <% if(visualizzaGrafici) { %>
			<div class="row">
	         	<div class="col-md-12" id="chartPanelGraficoRisultatiEsattiDiv">
	            <div class="ec-fancy-title">
	               <h3>Pronostici</h3>
	               </div>
	               <div class="ec-fixture-list">
	                   <ul>
	                   		<li class="">
	                   			<div id="graficoRisultatiEsattiDiv"></div>
	              			</li>
	           			</ul>
	       			</div>
	   			</div>
			</div>
			
			<% } %>
			
			<div class="row">
				<%
				int sommaOk = 0;
				int sommaEsatti = 0;
				int numeroPronostici = listaPronosticiMatch.size();
				
                for(PronosticoPartita pronostico: listaPronosticiMatch){
	               	 Giocatore p = pronostico.getGiocatore();
	               	PronosticoRisultato pronosticoRisultato = pronostico.getPronostico();
	                Partita m = pronostico.getPartita();
	                String risultato = (m.getRisultato() != null && PartitaUtils.getRisultatoEsatto(pronosticoRisultato, m, match) != null) ? PartitaUtils.getRisultatoEsatto(pronosticoRisultato, m, match) : ""; //TODO bussu ribaltare risultati
	                Risultato risultatoEnum = PartitaUtils.getRisultato(pronosticoRisultato);
	                
	                if(risultatoUfficiale.equals(risultato)){
	               	 sommaEsatti ++;
	                } else {
	               	 if(risultatoUfficialeEnum.equals(risultatoEnum)){
	               		sommaOk ++;
	               	 }
	                }
                }
                
                int numeroErrati = numeroPronostici - (sommaEsatti + sommaOk);
                
                String percOk = numeroPronostici != 0 ?  dec.format(((double) sommaOk * 100 / numeroPronostici)) : "0";
                String percEsatti = numeroPronostici != 0 ? dec.format(((double) sommaEsatti * 100 / numeroPronostici)) : "0";
                String percErrati = numeroPronostici != 0 ? dec.format(((double) numeroErrati * 100 / numeroPronostici)) : "0";
				%>
	         	<div class="col-md-12">
	            <div class="ec-fancy-title">
	               <h3>Statistiche Incontro</h3>
	               </div>
	               <div class="ec-fixture-list">
	                   <ul>
	                   		<li class="">
	                   			<div class="ec-cell"><span>Totale giocate:</span></div>
	                           <div class="ec-cell"><span><%=listaPronosticiMatch.size() %></span></div>
	              			</li>
	              			<li class="">
	                   			<div class="ec-cell"><span>Risultato:</span></div>
	                           <div class="ec-cell"><span><%=sommaOk %> (<%=percOk %>%)</span></div>
	              			</li>
	              			<li class="">
	                   			<div class="ec-cell"><span>Risultato esatto:</span></div>
	                           <div class="ec-cell"><span><%=sommaEsatti %> (<%=percEsatti %>%)</span></div>
	              			</li>
	              			<li class="">
	                   			<div class="ec-cell"><span>Errato:</span></div>
	                           <div class="ec-cell"><span><%=numeroErrati %> (<%=percErrati %>%)</span></div>
	              			</li>
	           			</ul>
	       			</div>
	   			</div>
			</div>
	
	         <div class="row">
	         	<div class="col-md-12">
	            <div class="ec-fancy-title">
	               <h3>Pronostici Giocatore</h3>
	               </div>
	               <div class="ec-fixture-list">
	                   <ul>
	                     <%
	                     int i = 0;
	                     for(PronosticoPartita pronostico: listaPronosticiMatch){
	                    	 Giocatore p = pronostico.getGiocatore();
		                   	 String liStyleClass= i % 2 == 0 ? "even" : "odd";
			                 i++;
			                 
			                 PronosticoRisultato pronosticoRisultato = pronostico.getPronostico();
			                 Partita m = pronostico.getPartita();
			                 String risultato = (m.getRisultato() != null && PartitaUtils.getRisultatoEsatto(pronosticoRisultato, m, match) != null) ? PartitaUtils.getRisultatoEsatto(pronosticoRisultato, m, match) : ""; //TODO bussu ribaltare risultati
			                 Risultato risultatoEnum = PartitaUtils.getRisultato(pronosticoRisultato);
			                 
			                 String classRisultato = "";
			                 
			                 if(risultatoUfficiale.equals(risultato)){
			                	 classRisultato = "giocatore-pronostico-esatto";
			                 } else {
			                	 if(risultatoUfficialeEnum.equals(risultatoEnum)){
			                		 classRisultato = "giocatore-pronostico-ok";
			                	 }
			                 }
				          	
				          	
				          	%>
	                       <li class="<%=liStyleClass %> li-<%=classRisultato %>">
	                           <div class="ec-cell ec-cell-<%=classRisultato %>"><span><%=p.getNome()%></span></div>
	                           <div class="ec-cell ec-cell-<%=classRisultato %>"><span><%=risultato %></span></div>
	                       </li>
	                   <% } %>       
	                     </ul>
	                 </div>
	             </div>
	         </div>
 		 <% } %>
     </div>

    </main><!-- /.container -->
   
    <jsp:include page="includes/footer.jsp" flush="true"></jsp:include>
    
     <% if(visualizzaGrafici) { %>
     <script src="js/d3.min.js"></script>
     <script src="js/c3.js"></script>
     <script src="js/ChartMap.js"></script>
    
	  <script type="text/javascript">
          //<![CDATA[
          var chart2Id = 'graficoRisultatiEsattiId';
          var chart2DivId = 'graficoRisultatiEsattiDiv';
          var chart2Width = jQuery('#chartPanelGraficoRisultatiEsattiDiv').width();
          var chart2Height = 650;
          // init 
          createChart(chart2DivId, graficoRisultatiEsatti, 'pie', chart2Width, chart2Height);
   //]]>  
  </script>
  <% } %>
  </body>
</html>
