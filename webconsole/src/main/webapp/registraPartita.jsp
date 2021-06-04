<%@page import="worldcup.core.SalvaRisultato"%>
<%@page import="worldcup.core.utils.Utilities"%>
<%@page import="worldcup.clients.model.Partita"%>
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
    

    <title>UEFA EURO 2020 | Registra Partita</title>

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
    String context = request.getContextPath();
    ProssimiIncontri pi = new ProssimiIncontri();
    List<Partita> listaPartite = pi.getListaMatch(); 
    
    SalvaRisultato salvaRisultato = new SalvaRisultato();
    boolean login = salvaRisultato.login(username, password); 
    		
    %>
    <link rel="icon" href="<%= context %>/euro2020.ico">
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
	               <h3>Registra Partita</h3>
                </div>
                <div class="ec-fixture-list">
                	<ul>
                 		<li>
                         <div class="ec-cell">
		                	<form action="<%= context %>/inviaRisultato.jsp" method="get"> 
		                		<input type="hidden" name="username" value="<%=username%>" >
		                		<input type="hidden" name="password" value="<%=password%>">
		                		<div class="form-group">
								    <label for="listaMatch">Match</label>
								    <select class="form-control" id="listaMatch" name="matchSelezionato">
								    	<% for(int i = 0 ; i < listaPartite.size() ; i++){ 
								    		Partita match =listaPartite.get(i);
								    	%>
								    		<option value="<%= match.getIdPartita() %>"><%=Utilities.getMatchLabel(match) %></option>
								    	<% }%>
								    </select>
							  </div>
							  <div class="form-group">
							  	<div class="form-row">
   									<div class="col-md-6">
							    		<label for="inputCasa">Punteggio Casa</label>
							    		<input type="number" class="form-control" id="inputCasa" aria-describedby="Punteggio Casa" placeholder="Inserire Punteggio Squadra in casa" min="0" name="inputCasa" >
							  		</div>
							  		<div class="col-md-6">
							    		<label for="inputTrasferta">Punteggio Trasferta</label>
							    		<input type="number" class="form-control" id="inputTrasferta" aria-describedby="Punteggio Trasferta" placeholder="Inserire Punteggio Squadra in trasferta" min="0" name="inputTrasferta">
							  		</div>
						  		</div>
					  		</div>
					  		<div class="form-group">
						  		<div class="form-row" style="margin: auto;">
							  	<button type="submit" class="btn btn-backtotop">Salva</button>
							  	</div>
						  	</div>
							</form>
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
    
   <script type="text/javascript">
   $('[id^=div_match_]')
       .click(function() {
//            $(this).data('title', $(this).attr('title'));
//            $(this).removeAttr('title');
			var val = $(this).children('input[type=hidden]').val();
			window.location = val;
       });

   </script>
  </body>
</html>
