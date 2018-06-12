<%@page import="worldcup.core.Team"%>
<%@page import="worldcup.core.Stadium"%>
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

    <title>WorldCup 2018 | Home</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/starter-template.css" rel="stylesheet">
    
    <!-- Bootstrap core CSS -->
    <link href="css/custom.css" rel="stylesheet">
    
    <link href="css/partite_google.css" rel="stylesheet">
    
    <%
    String context = request.getContextPath();
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
	<div class="album">
        <div class="container">

          <div class="row">
          	<div class="col-md-12">
	            <div class="ec-fancy-title">
	               <h3>Prossimi incontri</h3>
                </div>
                <div class="ec-fixture-list">
                    <ul>
                    <% for(int i = 0; i < listaPartite.size() ; i++){ 
                    	String liStyleClass= i % 2 == 0 ? "even" : "odd";
                    	Match match = listaPartite.get(i);
                    	int id = match.getStadium();
                    	Stadium s = Stadium.getStadiums().get(id);
		          		Team s1 = match.getHome();
		          		Team s2 = match.getAway();
		          		String descrizioneMatch = match.getDescrizione();
		          	%>
                        <li class="<%=liStyleClass %>">
                        	<div class="imspo_mt__mtc-no scheda_match" id="div_match_<%=match.getMatchId() %>">
                        		<input type="hidden" value="<%=context %>/incontro.jsp?idMatch=<%=match.getMatchId() %>" id="<%=match.getMatchId() %>"/>
								<table class="imspo_mt__mit">
									<tbody>
										<tr class="imspo_mt__tr-s">
											<td class="lc"></td>
											<td></td>
											<td class="imspo_mt__rg"></td>
											<td class="imspo_mt__vs-c" rowspan="999"></td>
											<td class="imspo_mt__sc imspo_mt__match-sc imspo_mt__wt" rowspan="999">
												<div class="imspo_mt__ms-w imspo_mt__ms-m">
													<div>
														<div class="imspo_mt__ns-pm-s">
															<div class="imspo_mt__pm-inf imspo_mt__date imso-medium-font stadium"><%=s.getNome() %></div>
															<div class="imspo_mt__pm-inf imspo_mt__date imso-medium-font"><%=match.getDataMatchAsString() %></div>
															<div class="imspo_mt__ndl-p imspo_mt__pm-inf imso-medium-font"><%=match.getOraMatchAsString() %></div>
														</div>
														<div style="display: none;"></div>
													</div>
													<div style="display: none;"></div>
													<div style="display: none;"></div>
													<div style="display: none;"></div>
												</div>
												<div style="display: none;"></div></td>
											<td class="imspo_mt__rpo-c" style="width: 0px"></td>
										</tr>
										<tr>
											<td class="imspo_mt__lg-st-c" colspan="3">
												<div class="imspo_mt__lg-st-co">
													<span style="display: none;"></span><span
														style="display: none;"> · </span>
														<span><%=descrizioneMatch %></span>
													<div style="display: none;"></div>
												</div></td>
										</tr>
										<tr class="imspo_mt__tr">
											<td class="lc"><jsl	jstcache="9">
												<img style="width: 48px; height: 48px;"
													src="<%=s1.getBandiera() %>"
													alt=""></jsl></td>
											<td class="tns-c imspo_mt__dt-t">
												<div style="display: none;"></div>
												<div class="imspo_mt__tt-w">
													<div class="ellipsisize">
														<span class="qRHjyd" style="display: none"></span>
															<span><%=s1.getNome() %></span>
														</div>
												</div></td>
											<td class="imspo_mt__rg"><svg style="display: none;" ></svg></td>
										</tr>
										<tr class="imspo_mt__bt-s"></tr>
										<tr class="imspo_mt__tr">
											<td class="lc"><jsl	jstcache="9">
												<img style="width: 48px; height: 48px;"
													src="<%=s2.getBandiera() %>"
													alt=""></jsl></td>
											<td class="tns-c imspo_mt__dt-t"><div style="display: none;"></div>
												<div class="imspo_mt__tt-w">
													<div class="ellipsisize">
														<span class="qRHjyd" style="display: none" ></span>
														<span><%=s2.getNome() %></span>
													</div>
												</div></td>
											<td class="imspo_mt__rg"><svg style="display: none;"></svg></td>
										</tr>
										<tr style="display: none;" ></tr>
										<tr style="display: none;" ></tr>
										<tr class="imspo_mt__br-s" ></tr>
									</tbody>
								</table>
							</div>
                        
<!--                             <div class="ec-cell text-centered"> -->
<%--                             	<span><%=match.getDataMatchAsString() %>&nbsp;&nbsp;&nbsp;<a title="<%=s.getNome() %>" href="<%=s.getLink() %>"><%=s.getNome() %></a></span> --%>
<!--                             </div> -->
<!--                             <div class="ec-cell"> -->
<%--                                	<span class="ec-fixture-flag"><img src="<%=s1.getBandiera() %>" alt=""> <%=s1.getNome() %></span> --%>
<%--                                 <a href="<%=context %>/incontro.jsp?idMatch=<%=match.getMatchId() %>" class="ec-fixture-vs" title="Vedi Pronostici"><small>vs</small></a> --%>
<%--                                 <span class="ec-fixture-flag"><img src="<%=s2.getBandiera() %>" alt=""> <%=s2.getNome() %></span> --%>
<!--                             </div> -->
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
    
   <script type="text/javascript">
   $('[id^=div_match_]')
       .click(function() {
//            $(this).data('title', $(this).attr('title'));
//            $(this).removeAttr('title');
			var val = $(this).children('input[type=hidden]').val();
			window.location = val;
       });

   </script>
  </body>
</html>
