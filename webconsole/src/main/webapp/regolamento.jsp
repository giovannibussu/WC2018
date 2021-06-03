<%@page import="worldcup.core.model.Match"%>
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

    <title>WorldCup 2018 | Regolamento</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/starter-template.css" rel="stylesheet">
    
    <!-- Bootstrap core CSS -->
    <link href="css/custom.css" rel="stylesheet">
    
     <link href="css/roboto/roboto-fontface.css" rel="stylesheet" type="text/css">
    <%
    String context = request.getContextPath();
    ProssimiIncontri pi = new ProssimiIncontri();
//     List<Match> listaPartite = pi.getListProssimiIncontri(); 
    
    %>
    <link rel="icon" href="<%= context %>/favicon.png">
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
	               <h3>Regolamento</h3>
                </div>
                <div class="ec-fixture-list">
                    <ul>
                        <li>
                            <div class="ec-cell">
                            	<span><b>Metodo di Calcolo dei punteggi</b></span>
                            	<br/>
                            	<p>
Le partite sono suddivise in 2 fasi:
<br/>- Prima fase con organizzazione a gironi
<br/>- Seconda fase ad eliminazione diretta
<br/><br/>
Nella prima fase i pronostici saranno valutati sulla base delle partite stabilite dal calendario.
<br/>Nella seconda fase i pronostici relativi al risultato della partita saranno valutati se entrambe le squadre fanno parte del pronostico :
<br/>- "SquadraA vs SquadraB" è equivalente a "SquadraB vs Squadra A"
<br/>- un pronostico nella seconda fase è valido solo per il turno in cui è stato inserito (se il pronostico si riferisce agli ottavi e le 2 squadre giocano la finale non si otterranno punti)
<br/><br/>
I pronostici saranno valutati totalizzando i punteggi come segue:
<br/><br/>
<b>Prima fase</b>
   <br/>Risultato (1X2): 1
   <br/>Risultato Esatto: 3
   <br/>Passaggio agli Ottavi: 5
   <br/>Posizione in Classifica: 3  
    <br/><br/>
<b>Ottavi</b>
   <br/>Risultato (1X2): 3
   <br/>Risultato Esatto: 7
   <br/>Passaggio ai Quarti: 6
   <br/>Posizione Tabellone Quarti: 4
<br/><br/>
<b>Quarti</b>    
   <br/>Risultato (1X2): 5
   <br/>Risultato Esatto: 11
   <br/>Passaggio in Semifinale: 7
   <br/>Posizione Tabellone Semifinale: 5
<br/><br/>
<b>Semifinali</b>
   <br/>Risultato (1X2): 6
   <br/>Risultato Esatto: 15
   <br/>Passaggio in Finale: 8
   <br/>Posizione Tabellone Finale: 6
<br/><br/>
<b>Finale</b>
   <br/>Risultato (1X2): 7
   <br/>Risultato Esatto: 21
<br/><br/><br/>
Nei turni successivi alla prima fase non sarà consentito pronosticare pareggi; in caso di gare terminate in parità al 90-esimo sarà considerato valido il risultato al termine dei tempi supplementari; in caso di partite decise ai calci di rigore si assegnerà un gol aggiuntivo alla squadra vincente.
<br/><br/>
Esempi (validi per ottavi, quarti semifinali e finale)
<br/>
    ESEMPIO1 : "SquadraA vs SquadraB" 0-0 al 90-esimo , 2-1 dopo i supplementari: il pronostico esatto è vittoria SquadraA con il risultato di 2-1
    <br/>ESEMPIO2: "SquadraA vs SquadraB" 1-1 al 90-esimo, 2-2 dopo i supplementari, vince SquadraB ai rigori: il pronostico esatto è vittoria SquadraB con il risultato di 2-3
    							</p>
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
