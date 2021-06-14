<%@page import="java.util.Map"%>
<%@page import="worldcup.clients.model.Pronostico"%>
<%@page import="worldcup.core.ClassificaGenerale"%>
<%@page import="worldcup.core.ProssimiIncontri"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
	
	<jsp:include page="includes/header.jsp" flush="true">
		<jsp:param name="titoloPagina" value="UEFA EURO 2020 | Classifica Generale" />
	</jsp:include>
	<%
    String context = request.getContextPath();
    String categoria = request.getParameter("cat");
    ClassificaGenerale classificaGenerale = new ClassificaGenerale();
    List<Pronostico> classifica = classificaGenerale.getClassificaGenerale(categoria);
    %>
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
             <h3>Classifica Generale</h3>
             </div>
             <div class="ec-fixture-list">
                 <ul>
                 	<li>
                         <div class="ec-cell ec-cell-th"><span>Giocatore</span></div>
                         <div class="ec-cell ec-cell-th"><span>Pt</span></div>
                         <div class="ec-cell ec-cell-th"><span>Squadra Vincente</span></div>
                     </li>
                  <% 
                  	int i = 1;
                  	for(Pronostico pronostico: classifica){
                  		String liStyleClass= i % 2 == 0 ? "even" : "odd";
                  		Integer punteggioGiocatore = pronostico.getPunti();
          	%>
                      <li class="<%=liStyleClass %>">
                          <div class="ec-cell"><span><a target="_blank" href="<%=pronostico.getLink() %>"><%=pronostico.getGiocatore().getNome() %></a></span></div>
                          <div class="ec-cell"><span><%=punteggioGiocatore %></span></div>
                          <div class="ec-cell">
                          <span class="ec-cell-bandiera"><img style="width: 24px; height: 24px; margin-right:8px;" src="<%=pronostico.getSquadraVincente().getBandiera() %>" alt=""/></span>
                          <span><%=pronostico.getSquadraVincente().getNome() %></span></div>
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

    </main><!-- /.container -->
   
    <jsp:include page="includes/footer.jsp" flush="true"></jsp:include>
  </body>
</html>
