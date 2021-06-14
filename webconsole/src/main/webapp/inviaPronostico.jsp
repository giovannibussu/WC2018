<%@page import="worldcup.clients.model.Giocatore"%>
<%@page import="worldcup.clients.model.Pronostico"%>
<%@page import="org.apache.commons.io.IOUtils"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.InputStream"%>
<%@page import="worldcup.core.SalvaRisultato"%>
<%@page import="worldcup.core.ProssimiIncontri"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">

	<jsp:include page="includes/header.jsp" flush="true">
		<jsp:param name="titoloPagina" value="UEFA EURO 2020 | Registra Pronostico Esito" />
	</jsp:include>
    <%
    String username = request.getParameter("username");
	String password = request.getParameter("password");
    String idGiocatore = request.getParameter("idGiocatore");
    String linkPronostico = request.getParameter("linkPronostico");
    String context = request.getContextPath();
    SalvaRisultato salvaRisultato = new SalvaRisultato();
    
    InputStream fs = request.getPart("filePronostico").getInputStream();
    
    File tmp = File.createTempFile("pronostico", idGiocatore);
    
    try(FileOutputStream fos = new FileOutputStream(tmp);){
    	IOUtils.copy(fs, fos);
    }
    
    Pronostico pronostico =	 null;
    Giocatore giocatore = null;
    String errore = null;
  	try{
    	pronostico = salvaRisultato.inviaPronostico(username, password, idGiocatore, tmp, linkPronostico);
    	giocatore = pronostico.getGiocatore();
  	}catch (Exception e){
  		errore = e.getMessage();
  	}
    %>

  <body>

	<jsp:include page="includes/navbar.jsp" flush="true"></jsp:include>

    <main role="main" class="container">
    	
  	<div class="starter-template">
        <h1>&nbsp;&nbsp;</h1>
     </div>
	<div class="album">
			<% if(pronostico != null) { %>
          <div class="row">
          	<div class="col-md-12">
	            <div class="ec-fancy-title">
	               <h3>Esito</h3>
                </div>
                <div class="ec-fixture-list">
                	 <ul>
                	 	<li>
                	 		<div class="ec-cell">
	                			<span>Pronostico <%=giocatore.getNome() %> Registrato</span>
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
