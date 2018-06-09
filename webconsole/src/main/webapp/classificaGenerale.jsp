<%@page import="java.util.Map"%>
<%@page import="worldcup.core.Pronostico"%>
<%@page import="worldcup.core.ClassificaGenerale"%>
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

    <title>WorldCup 2018 | Classifica Generale</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/starter-template.css" rel="stylesheet">
    
    <!-- Bootstrap core CSS -->
    <link href="css/custom.css" rel="stylesheet">
    
    <%
    String categoria = request.getParameter("cat");
    ClassificaGenerale classificaGenerale = new ClassificaGenerale();
    Map<Pronostico,Integer> classifica = classificaGenerale.getClassificaGenerale(categoria);
    %>
  </head>

  <body>

	<jsp:include page="includes/navbar.jsp" flush="true"></jsp:include>

    <main role="main" class="container">
    	
  	<div class="starter-template">
        <h1>&nbsp;&nbsp;</h1>
     </div>
	<div class="album">
        <div class="container">

          <div class="row">
          	<div class="col-md-12">
	            <div class="ec-fancy-title">
	               <h3>Classifica Generale</h3>
                </div>
                <div class="ec-fixture-list">
                    <ul>
                    	<li>
                            <div class="ec-cell"><span>Giocatore</span></div>
                            <div class="ec-cell"><span>Punti</span></div>
                            <div class="ec-cell"><span>Squadra Vincente</span></div>
                        </li>
	                    <% 
	                    	int i = 1;
	                    	for(Pronostico pronostico: classifica.keySet()){
	                    		String liStyleClass= i % 2 == 0 ? "even" : "odd";
	                    		Integer punteggioGiocatore = classifica.get(pronostico);
			          	%>
	                        <li class="<%=liStyleClass %>">
	                            <div class="ec-cell"><span><%=pronostico.getPlayer().getNome() %></span></div>
	                            <div class="ec-cell"><span><%=punteggioGiocatore %></span></div>
	                            <div class="ec-cell"><span><%=pronostico.getTorneo().getWinner().getNome() %></span></div>
	                        </li>
	                    <% 
	                    	i++;
	                    	} 
	                    %>       
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
