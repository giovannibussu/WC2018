<%@page import="worldcup.core.Player"%>
<%@page import="java.util.Map"%>
<%@page import="worldcup.core.Team"%>
<%@page import="worldcup.core.Stadium"%>
<%@page import="worldcup.core.Match"%>
<%@page import="worldcup.core.ProssimiIncontri"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>WorldCup 2018</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/starter-template.css" rel="stylesheet">
    
    <!-- Bootstrap core CSS -->
    <link href="css/custom.css" rel="stylesheet">
    
    <link href="css/partite_google.css" rel="stylesheet">
    
    <link href="css/c3.min.css" rel="stylesheet" type="text/css">
    <link href="css/ChartMap.css" rel="stylesheet" type="text/css">
    
    <link href="css/roboto/roboto-fontface.css" rel="stylesheet" type="text/css">
    <%
    String context = request.getContextPath();
    String idMatch = request.getParameter("idMatch");
    ProssimiIncontri pi = new ProssimiIncontri();
    Match match = pi.getMatch(idMatch);
    Map<Player, Match> listaPronosticiMatch = pi.getPronosticiPerMatch(match);
    boolean visualizzaGrafici = true;
    %>
    <link rel="icon" href="<%= context %>/favicon.png">
    
    <script>
    	var graficoRisultatiEsatti = '<%= pi.distribuzionePronosticiPerMatchRisultatoEsatto(match).serializeToString()%>';
    </script>
    
  </head>

  <body>

	<jsp:include page="includes/navbar.jsp" flush="true"></jsp:include>

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
                   	int id = match.getStadium();
                   	Stadium s = Stadium.getStadiums().get(id);
	          		Team s1 = match.getHome();
	          		Team s2 = match.getAway();
	          		String descrizioneMatch = match.getDescrizione();
	          	%>
                       <li>
						<div class="imspo_mt__mtc-no" id="div_match_<%=match.getMatchId() %>">
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
														<div class="imspo_mt__pm-inf imspo_mt__date imso-medium-font"><%=match.getDataMatchAsString() %></div>
														<div class="imspo_mt__ndl-p imspo_mt__pm-inf imso-medium-font"><%=match.getOraMatchAsString() %></div>
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
											<div style="display: none;"></div>
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
										<td class="tns-c imspo_mt__dt-t"><div style="display: none;"></div>
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

         <div class="row">
         	<div class="col-md-12">
            <div class="ec-fancy-title">
               <h3>Pronostici per il match</h3>
               </div>
               <div class="ec-fixture-list">
                   <ul>
                     <%
                     int i = 0;
                     for(Player p: listaPronosticiMatch.keySet()){
                   	String liStyleClass= i % 2 == 0 ? "even" : "odd";
                   	i++;
                   	Match m = listaPronosticiMatch.get(p);
                   	String risultato = (m.getResult() != null && m.getResult().getRisultatoEsatto() != null) ? m.getResult().getRisultatoEsatto() : "";
	          	%>
                       <li class="<%=liStyleClass %>">
                           <div class="ec-cell"><span><%=p.getNome()%></span></div>
                           <div class="ec-cell"><span><%=risultato %></span></div>
                       </li>
                   <% } %>       
                     </ul>
                 </div>
             </div>
         </div>
         
       <% if(visualizzaGrafici) { %>
		<div class="row">
         	<div class="col-md-12" id="chartPanelGraficoRisultatiEsattiDiv">
            <div class="ec-fancy-title">
               <h3>Pillole Statistiche</h3>
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
