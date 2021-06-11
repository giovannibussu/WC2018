<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="worldcup.core.ClassificaGenerale"%>
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
    <%
    String idGiocatore = request.getParameter("idGiocatore");
    String context = request.getContextPath();

    ClassificaGenerale classificaGenerale = new ClassificaGenerale();
    
    File pronostico = null;
    String errore = null;
    try{
      pronostico = classificaGenerale.getPronostico(idGiocatore);
    }catch (Exception e){
  		errore = e.getMessage();
  	}
    
    
    if(pronostico != null){
    	String fileName = "pronostico_" + idGiocatore + ".xlsx";
    	response.setContentType("APPLICATION/OCTET-STREAM");   
    	response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\"");   
    	response.setStatus(200);
    	
    	FileInputStream fis = new FileInputStream(pronostico);
    	
    	IOUtils.copy(fis, out);
    	
    	fis.close();
    	out.flush();
    	
    	
    } else {
    	%>
    	<!doctype html>
		<html lang="en">

		<jsp:include page="includes/header.jsp" flush="true">
			<jsp:param name="titoloPagina" value="UEFA EURO 2020 | Scarica Pronostico" />
		</jsp:include>
    	
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
		     </div>
		
		    </main><!-- /.container -->
		   
		    <jsp:include page="includes/footer.jsp" flush="true"></jsp:include>
		    
		  </body>
		</html>
    	<%
    }
    %>
