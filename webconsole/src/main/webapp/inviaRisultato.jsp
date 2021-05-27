<%@page import="worldcup.core.model.Team"%>
<%@page import="worldcup.core.model.Stadium"%>
<%@page import="worldcup.core.model.Match"%>
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

    <title>WorldCup 2018 | Registra Partita Esito</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/starter-template.css" rel="stylesheet">
    
    <!-- Bootstrap core CSS -->
    <link href="css/custom.css" rel="stylesheet">
    
    <link href="css/partite_google.css" rel="stylesheet">
    
     <link href="css/roboto/roboto-fontface.css" rel="stylesheet" type="text/css">
    <%
    String username = request.getParameter("username");
	String password = request.getParameter("password");
    String idMatch = request.getParameter("matchSelezionato");
	String inputCasa = request.getParameter("inputCasa");
    String inputTrasferta = request.getParameter("inputTrasferta");
    String context = request.getContextPath();
    ProssimiIncontri pi = new ProssimiIncontri();
    
    boolean login = pi.login(username, password);
    	
    if(login) {
    	Match match = pi.getMatch(idMatch);
    	pi.setResult(match, Integer.parseInt(inputCasa), Integer.parseInt(inputTrasferta)); 
    }
    %>
    <link rel="icon" href="<%= context %>/favicon.png">
  </head>

  <body>

	<jsp:include page="includes/navbar.jsp" flush="true"></jsp:include>

    <main role="main" class="container">
    	
  	<div class="starter-template">
        <h1>&nbsp;&nbsp;</h1>
     </div>
	<div class="album">
			<% if(login) { %>
          <div class="row">
          	<div class="col-md-12">
	            <div class="ec-fancy-title">
	               <h3>Esito</h3>
                </div>
                <div class="ec-fixture-list">
                	 <ul>
                	 	<li>
                	 		<div class="ec-cell">
	                			<span>Risultato Registrato</span>
                			</div>
                		</li>
               		</ul>
                  </div>
              </div>
          </div>
          <% } else { %>
           <div class="row">
          	<div class="col-md-12">
	            <div class="ec-fancy-title">
	               <h3>Errore</h3>
                </div>
                <div class="ec-fixture-list">
                	 <ul>
                	 	<li>
                	 		<div class="ec-cell">
	                			<span>Utente non autorizzato</span>
                			</div>
                		</li>
               		</ul>
                  </div>
              </div>
          </div>
          <% } %>
     </div>

    </main><!-- /.container -->
   
    <jsp:include page="includes/footer.jsp" flush="true"></jsp:include>
    
  </body>
</html>
