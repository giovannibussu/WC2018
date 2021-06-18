<%@ page contentType="text/html; charset=UTF-8" %>
  <%
  String context = request.getContextPath();
  
  String idPaginaS = request.getParameter("idPagina");
  
  int idPagina = 1;
  
  try {
	  idPagina = Integer.parseInt(idPaginaS);
  } catch (Exception e){
	  
  }
  
  String active = "home";
  String activeClass = "active";
  %>
  
    <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
      <a class="navbar-brand navbar-brand-button" href="<%=context %>/index.jsp" title="UEFA EURO 2020"></a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item <%= idPagina == 1 ? activeClass : ""%>">
            <a class="nav-link" href="<%=context %>/index.jsp">Prossimi Incontri <span class="sr-only">(current)</span></a>
          </li>
          <li class="nav-item <%= idPagina == 2 ? activeClass : ""%>">
            <a class="nav-link" href="<%=context %>/classificaGenerale.jsp">Classifica Generale</a>
          </li>
          <li class="nav-item <%= idPagina == 3 ? activeClass : ""%>">
            <a class="nav-link" href="<%=context %>/storicoIncontri.jsp">Storico Incontri</a>
          </li>
          <li class="nav-item <%= idPagina == 4 ? activeClass : ""%>">
            <a class="nav-link" href="<%=context %>/regolamento.jsp">Regolamento</a>
          </li>
        </ul>
      </div>
    </nav>