<%@page import="worldcup.clients.model.RisultatoPartita"%>
<%@page import="worldcup.clients.model.Squadra"%>
<%@page import="worldcup.clients.model.Partita"%>
<%@page import="worldcup.core.SalvaRisultato"%>
<%@page import="worldcup.core.ProssimiIncontri"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">

	<jsp:include page="includes/header.jsp" flush="true">
		<jsp:param name="titoloPagina" value="UEFA EURO 2020 | Registra Partita Esito" />
	</jsp:include>
    <%
    String username = request.getParameter("username");
	String password = request.getParameter("password");
    String idMatch = request.getParameter("matchSelezionato");
	String inputCasa = request.getParameter("inputCasa");
    String inputTrasferta = request.getParameter("inputTrasferta");
    String azione = request.getParameter("azione");
    String context = request.getContextPath();
    SalvaRisultato salvaRisultato = new SalvaRisultato();
    
    Partita partita = null;
    String errore = null;
    if(azione != null && azione.equals("cancella")){
    	try{
	    	partita = salvaRisultato.cancellaRisultato(username, password, idMatch);
		}catch (Exception e){
	  		errore = e.getMessage();
	  	}
    } else {
	  	try{
	    	partita = salvaRisultato.setResult(username, password, idMatch, Integer.parseInt(inputCasa), Integer.parseInt(inputTrasferta)); 
		}catch (Exception e){
	  		errore = e.getMessage();
	  	}
    }
    %>

  <body>

	<jsp:include page="includes/navbar.jsp" flush="true"></jsp:include>

    <main role="main" class="container">
    	
  	<div class="starter-template">
        <h1>&nbsp;&nbsp;</h1>
     </div>
	<div class="album">
			<% if(partita != null) { 
			
				Squadra s1 = partita.getCasa();
          		Squadra s2 = partita.getTrasferta();
          		String descrizioneMatch = partita.getDescrizione();
				RisultatoPartita risultato = partita.getRisultato();
			%>
          <div class="row">
          	<div class="col-md-12">
	            <div class="ec-fancy-title">
	               <h3>Esito</h3>
                </div>
                <div class="ec-fixture-list">
                	 <ul>
                	 	<li>
                	 		<div class="ec-cell">
	                			<span>
	                			
	                			<p><%=descrizioneMatch %></p>
	                			
	                			<p><%=s1.getNome() %> - <%=s2.getNome() %></p>
	                			<% if(azione != null && azione.equals("cancella")){ %>
	                				<p>Risultato Cancellato</p>
	                			<% } else { %>
	                				<p>Risultato Registrato: <%=risultato.getGoalCasa() %>-<%=risultato.getGoalTrasferta() %></p>
	                			<% } %>
	                			</span>
	                			
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
	                			<span><%=errore %></span>
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
