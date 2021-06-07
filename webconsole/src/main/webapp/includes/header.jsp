<%@ page contentType="text/html; charset=UTF-8" %>

  <head>
   <%
    String context = request.getContextPath();
    String titoloPagina = request.getParameter("titoloPagina");
    %>
    
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><%=titoloPagina %></title>
    
    <link href="css/roboto/roboto-fontface.css" rel="stylesheet" type="text/css">

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/starter-template.css" rel="stylesheet">
    
    <!-- Bootstrap core CSS -->
    <link href="css/custom.css" rel="stylesheet">
    
    <link href="css/partite_google.css" rel="stylesheet">
    
     
   
    <link rel="icon" href="<%= context %>/euro2020.ico">

  </head>