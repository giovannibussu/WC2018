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

    <title>WorldCup 2018 | Pronostici</title>

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
    	
  	<div class="starter-template">
        <h1>&nbsp;&nbsp;</h1>
     </div>
	<div class="album py-5">
        <div class="container">

          <div class="row">
          	<div class="col-md-12">
	            <div class="ec-fancy-title">
	               <h2>Pronostici</h2>
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
                            <div class="ec-cell"><span><%=p.getData() %>&nbsp;&nbsp;<%=p.getOra() %></span></div>
                            <div class="ec-cell"><span><a href="<%=s.getLink() %>"><%=s.getNome() %></a></span></div>
                            <div class="ec-cell">
                                <a href="#" class="ec-fixture-flag"><img src="<%=s1.getBandiera() %>" alt=""> <%=s1.getNome() %></a>
                                <span class="ec-fixture-vs"><small>vs</small></span>
                                <a href="#" class="ec-fixture-flag ec-next-flag"><img src="<%=s2.getBandiera() %>" alt=""> <%=s2.getNome() %></a>
                            </div>
                        </li>
                    <% } %>       
                      </ul>
                  </div>
              </div>
          </div>
<!--             <div class="col-md-4"> -->
<!--               <div class="card mb-4 box-shadow"> -->
<!--                 <img class="card-img-top" data-src="holder.js/100px225?theme=thumb&bg=55595c&fg=eceeef&text=Thumbnail" alt="Card image cap"> -->
<!--                 <div class="card-body"> -->
<%--                 <p class="card-text"><%=p.getData() %></p> --%>
<%--                   <p class="card-text"><%=s.getCitta() %></p> --%>
<%--                   <p class="card-text"><a href="<%=s.getLink() %>"><%=s.getNome() %></a></p> --%>
<%--                   <p class="card-text"><%=s1.getNome() %> - <%=s2.getNome() %></p> --%>
<!--                   <div class="d-flex justify-content-between align-items-center"> -->
<!--                     <div class="btn-group"> -->
<!--                       <button type="button" class="btn btn-sm btn-outline-secondary" title="Visualizza Pronostici">Vedi</button> -->
<!--                     </div> -->
<%--                     <small class="text-muted"><%=p.getOra() %></small> --%>
<!--                   </div> -->
<!--                 </div> -->
<!--               </div> -->
<!--             </div> -->
           
       </div>
     </div>

    </main><!-- /.container -->
   
    <jsp:include page="includes/footer.jsp" flush="true"></jsp:include>
  </body>
</html>
