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

    <title>WorldCup 2018 | Pronostici</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/starter-template.css" rel="stylesheet">
    
    <!-- Bootstrap core CSS -->
    <link href="css/custom.css" rel="stylesheet">
    
    <%
    
    ProssimiIncontri pi = new ProssimiIncontri();
    List<Match> listaPartite = pi.getListProssimiIncontri(); 
    
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
		          	%>
                        <li>
                            <div class="ec-cell"><span><a href="#">Giocatore</a></span></div>
                            <div class="ec-cell"><span>Germania</span></div>
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