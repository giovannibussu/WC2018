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

	<jsp:include page="includes/header.jsp" flush="true">
		<jsp:param name="titoloPagina" value="UEFA EURO 2020 | Registra Partita" />
	</jsp:include>
    <%
    String username = request.getParameter("username");
	String password = request.getParameter("password");
    
    SalvaRisultato salvaRisultato = new SalvaRisultato();
    boolean login = true; // salvaRisultato.login(username, password); 
    %>

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
	               <h3>Registra Pronostico</h3>
                </div>
                <div class="ec-fixture-list">
                	<ul>
                 		<li>
                         <div class="ec-cell">
		                	<form action="<%= context %>/inviaRisultato.jsp" method="post" enctype="multipart/form-data"> 
		                		<input type="hidden" name="username" value="<%=username%>" >
		                		<input type="hidden" name="password" value="<%=password%>">
							  	<div class="form-group">
								  	<div class="form-row">
	   									<div class="col-md-6">
								    		<label for="inputCasa">Giocatore</label>
								    		<input type="text" class="form-control" id="idGiocatore" aria-describedby="Giocatore" placeholder="Inserire Nome Giocatore" name="idGiocatore" >
								  		</div>
								  		<div class="col-md-6">
								    		<label for="inputTrasferta">Excel</label>
								    		<input type="file" class="form-control" id="filePronostico" aria-describedby="Pronostico" placeholder="Pronostico" name="filePronostico">
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
