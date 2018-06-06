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

    <title>WorldCup 2018 | Home</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/starter-template.css" rel="stylesheet">
    
    <!-- Bootstrap core CSS -->
    <link href="css/custom.css" rel="stylesheet">
    
    <%
    String context = request.getContextPath();
    ProssimiIncontri pi = new ProssimiIncontri();
    List<Partita> listaPartite = pi.getListProssimiIncontri(); 
    
    %>
  </head>

  <body>

	<jsp:include page="includes/navbar.jsp" flush="true"></jsp:include>

    <main role="main" class="container">
    	
  	<div class="starter-template">
        <h1>&nbsp;&nbsp;</h1>
     </div>
	<div class="album py-5">
        <div class="container">

          <div class="row">
          	<div class="col-md-12">
	            <div class="ec-fancy-title">
	               <h2>Prossimi incontri</h2>
                </div>
                <div class="ec-fixture-list">
                    <ul>
                    <% for(int i = 0; i < listaPartite.size() ; i++){ 
		          		Partita p = listaPartite.get(i);
		          		Stadio s = p.getStadio();
		          		Squadra s1 = p.getSquadra1();
		          		Squadra s2 = p.getSquadra2();
		          	%>
                        <li>
                            <div class="ec-cell">
                            	<span><%=p.getData() %>&nbsp;&nbsp;ore&nbsp;<%=p.getOra() %></span>
                            	<br/>
                             	<span><a href="<%=s.getLink() %>"><%=s.getNome() %></a></span>
                            </div>
                            <div class="ec-cell">
                               	<span class="ec-fixture-flag"><img src="<%=s1.getBandiera() %>" alt=""> <%=s1.getNome() %></span>
                                <a href="<%=context %>/incontro.jsp?idMatch=<%=p.getId() %>" class="ec-fixture-vs" title="Vedi Pronostici"><small>vs</small></a>
                                <span class="ec-fixture-flag ec-next-flag"><img src="<%=s2.getBandiera() %>" alt=""> <%=s2.getNome() %></span>
                            </div>
                        </li>
                    <% } %>       
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
