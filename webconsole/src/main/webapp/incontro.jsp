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
    <link rel="icon" href="/favicon.ico">

    <title>WorldCup 2018</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/starter-template.css" rel="stylesheet">
    
    <!-- Bootstrap core CSS -->
    <link href="css/custom.css" rel="stylesheet">
    
    <%
    String idMatch = request.getParameter("idMatch");
    ProssimiIncontri pi = new ProssimiIncontri();
    Match match = pi.getMatch(idMatch);
    Map<Player, Match> listaPronosticiMatch = pi.getPronosticiPerMatch(match);
    
    %>
  </head>

  <body>

	<jsp:include page="includes/navbar.jsp" flush="true"></jsp:include>

    <main role="main" class="container">
    	
  	<div class="starter-template">
        <h1>&nbsp;&nbsp;</h1>
     </div>
	<div class="album py-5">
        <div class="container">
        	<div class="row">
          		<div class="col-md-12">
          			<div class="ec-fixture-list">
                    <ul>
                    <%  
                    	int id = match.getStadium();
                    	Stadium s = Stadium.getStadiums().get(id);
		          		Team s1 = match.getHome();
		          		Team s2 = match.getAway();
		          		
		          	%>
                        <li>
                            <div class="ec-cell text-centered">
                            	<span><%=match.getDataMatchAsString() %>&nbsp;&nbsp;&nbsp;<a href="<%=s.getLink() %>"><%=s.getNome() %></a></span>
                            </div>
                            <div class="ec-cell">
                               	<span class="ec-fixture-flag"><img src="<%=s1.getBandiera() %>" alt=""> <%=s1.getNome() %></span>
                                <span class="ec-fixture-vs"><small>vs</small></span>
                                <span class="ec-fixture-flag"><img src="<%=s2.getBandiera() %>" alt=""> <%=s2.getNome() %></span>
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
	               <h2>Pronostici per il match</h2>
                </div>
                <div class="ec-fixture-list">
                    <ul>
<%--                     <% for(int i = 0; i < listaPronosticiMatch.size() ; i++){  --%>
                      <%
                      int i = 0;
                      for(Player p: listaPronosticiMatch.keySet()){
                    	String liStyleClass= i % 2 == 0 ? "" : "";
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
       </div>
     </div>

    </main><!-- /.container -->
   
    <jsp:include page="includes/footer.jsp" flush="true"></jsp:include>
  </body>
</html>
