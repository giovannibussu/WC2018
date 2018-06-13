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

    <title>WorldCup 2018 | Registra Partita</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/starter-template.css" rel="stylesheet">
    
    <!-- Bootstrap core CSS -->
    <link href="css/custom.css" rel="stylesheet">
    
    <link href="css/partite_google.css" rel="stylesheet">
    
    <%
    String username = request.getParameter("username");
	String password = request.getParameter("password");
    String context = request.getContextPath();
    ProssimiIncontri pi = new ProssimiIncontri();
    List<Match> listaPartite = pi.getListaMatch(); 
    
    %>
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
	            <div class="ec-fancy-title">
	               <h3>Registra Partita</h3>
                </div>
                <div class="ec-fixture-list">
                	<form action="<%= context %>/inviaRisultato.jsp?username=<%=username%>&password=<%=password%>" method="get"> 
                		<div class="form-group">
						    <label for="listaMatch">Match</label>
						    <select class="form-control" id="listaMatch">
						    	<% for(int i = 0 ; i < listaPartite.size() ; i++){ 
						    			Match match =listaPartite.get(i);
						    	%>
						    		<option value="<%= match.getMatchId() %>"><%=match.getMatchLabel() %></option>
						    	<% }%>
						    </select>
						  </div>
					  <div class="form-group">
					    <label for="inputCasa">Punteggio Casa</label>
					    <input type="number" class="form-control" id="inputCasa" aria-describedby="Punteggio Casa" placeholder="Inserire Punteggio Suqadra in casa">
					    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
					  </div>
					  <div class="form-group">
					    <label for="inputTrasferta">Punteggio Trasferta</label>
					    <input type="number" class="form-control" id="inputTrasferta" aria-describedby="Punteggio Trasferta" placeholder="Inserire Punteggio Suqadra in trasferta">
					  </div>
					  <button type="submit" class="btn btn-primary">Salva</button>
					</form>
                  </div>
              </div>
          </div>
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
