<%@page import="worldcup.bean.Squadra"%>
<%@page import="worldcup.bean.Stadio"%>
<%@page import="worldcup.core.ProssimiIncontri"%>
<%@page import="worldcup.bean.Partita"%>
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
    
    ProssimiIncontri pi = new ProssimiIncontri();
    List<Partita> listaPartite = pi.getListProssimiIncontri(); 
    
    %>
  </head>

  <body>

	<jsp:include page="includes/navbar.jsp" flush="true"></jsp:include>

    <main role="main" class="container">
    	
	<section class="jumbotron text-center">
		<div class="container">
	 	   <h1 class="jumbotron-heading">World Cup 2018</h1>
	    	<p class="lead text-muted">Prossimi incontri</p>
		</div>
	</section>
	
	<div class="album py-5 bg-light">
        <div class="container">

          <div class="row">
          	<% for(int i = 0; i < listaPartite.size() ; i++){ 
          		Partita p = listaPartite.get(i);
          		Stadio s = p.getStadio();
          		Squadra s1 = p.getSquadra1();
          		Squadra s2 = p.getSquadra2();
          	%>
            <div class="col-md-4">
              <div class="card mb-4 box-shadow">
                <img class="card-img-top" data-src="holder.js/100px225?theme=thumb&bg=55595c&fg=eceeef&text=Thumbnail" alt="Card image cap">
                <div class="card-body">
                <p class="card-text"><%=p.getData() %></p>
                  <p class="card-text"><%=s.getCitta() %></p>
                  <p class="card-text"><a href="<%=s.getLink() %>"><%=s.getNome() %></a></p>
                  <p class="card-text"><%=s1.getNome() %> - <%=s2.getNome() %></p>
                  <div class="d-flex justify-content-between align-items-center">
                    <div class="btn-group">
                      <button type="button" class="btn btn-sm btn-outline-secondary" title="Visualizza Pronostici">Vedi</button>
                    </div>
                    <small class="text-muted"><%=p.getOra() %></small>
                  </div>
                </div>
              </div>
            </div>
            <% } %>
          </div>
       </div>
     </div>

    </main><!-- /.container -->
   
    <jsp:include page="includes/footer.jsp" flush="true"></jsp:include>
  </body>
</html>
