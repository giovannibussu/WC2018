<%@ page contentType="text/html; charset=UTF-8" %>
  <%
  String context = request.getContextPath();
  
  String active = "home";
  String activeClass = "active";
  %>
  
    <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
      <a class="navbar-brand" href="#">WorldCup 2018</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item <%= activeClass%>">
            <a class="nav-link" href="<%=context %>/index.jsp">Home <span class="sr-only">(current)</span></a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<%=context %>/classificaGenerale.jsp">Classifica Generale</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<%=context %>/pronostici.jsp">Pronostici</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<%=context %>/regolamento.jsp">Regolamento</a>
          </li>
        </ul>
      </div>
    </nav>