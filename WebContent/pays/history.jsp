<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="ua.nure.fiedietsov.i18n.text" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>History</title>


<script type="text/javascript" charset="utf-8" src="./CSS/FileSaver.min.js"></script>
<script type="text/javascript" charset="utf-8" src="./CSS/jspdf.js"></script>
<script type="text/javascript" charset="utf-8" src="./CSS/jspdf.plugin.autotable.js"></script>
<script type="text/javascript" charset="utf-8" src="./CSS/split_text_to_size.js"></script>
<script type="text/javascript" charset="utf-8" src="./CSS/cell.js"></script>
<script type="text/javascript" charset="utf-8" src="./CSS/from_html.js"></script>
<script type="text/javascript" charset="utf-8" src="./CSS/standard_fonts_metrics.js"></script>
<script type="text/javascript" charset="utf-8" src="./CSS/topdf.js"></script>

<link href="./CSS/style.css" rel="stylesheet">
<script type="text/javascript" charset="utf-8"  src="./CSS/script.js"></script>
<script type="text/javascript">
		function toPDF() {

			  var doc = new jsPDF('p', 'pt');


			  var res = doc.autoTableHtmlToJson(document.getElementById("history"));
			  //doc.autoTable(res.columns, res.data, {margin: {top: 80}});

			  var header = function(data) {
			    doc.setFontSize(20);
			    doc.setTextColor(40);
			    doc.setFontStyle('normal');
			    //doc.addImage(headerImgData, 'JPEG', data.settings.margin.left, 20, 50, 50);
			    doc.text("History payments", data.settings.margin.left, 50);
			  };
			
			  var options = {
				overflow: 'linebreak',
			    beforePageContent: header,
			    margin: {
			      top: 80
			    },
			    startY: doc.autoTableEndPosY() + 80
			  };

			  doc.autoTable(res.columns, res.data, options);

			  //doc.save("history.pdf");
			  doc.output('dataurlnewwindow');
			}
</script>
</head>
<body >
	<c:set var="noCards" value="у вас нет добавленных карт" />
	<c:set var="success" value="ваш платеж осуществлён успешно" />
		<c:if test="${not empty sessionScope.success }">
		<c:remove var="success"/>
	</c:if>
	<fmt:message key="login.label.logged" var="textLogged" />	
	<div align="right">
		${textLogged}: ${pageContext.request.userPrincipal.name}
		<a href="logout">logout</a>
	</div>
	
		<%-- HEADER --%>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>		
	<br><br>		
	<div>
		<form action="../pays/gettransactions" method="POST">
			<select name="selectAcc">
				<c:if test="${empty sessionScope.accounts}">
				
					<option >${noCards}</option>
				
				</c:if>
				
	
	
				<c:forEach var="item" items="${sessionScope.accounts}">
					<option value="${item.account}">${item.account}</option>
				</c:forEach>
	
			</select> 
		<fmt:message key="mypays.button.viewHistory" var="buttonViewHistory" />	
		 <input type="submit" value="${buttonViewHistory}"> <button onclick="javascript:toPDF()">download pdf</button>
		</form>
	</div>
	<c:if test="${not empty sessionScope.trancs }">
		<br>
		<div id="render_me">
		<table class="spc" id="history" border="0" cellspacing="2" cellpadding="2" width="98%">
			<tr>
	            <td class="thd cur" onclick="sort(this)" title="Нажмите на заголовок, чтобы отсортировать колонку">Data</td>
	            <td class="thd cur" onclick="sort(this)" title="Нажмите на заголовок, чтобы отсортировать колонку">Account</td>
	            <td class="thd cur" onclick="sort(this)" title="Нажмите на заголовок, чтобы отсортировать колонку">Summary</td>
	            <td class="thd cur" onclick="sort(this)" title="Нажмите на заголовок, чтобы отсортировать колонку">Summa</td>
	            <td class="thd cur" onclick="sort(this)" title="Нажмите на заголовок, чтобы отсортировать колонку">Balance</td>
	          </tr>
			
			<c:forEach var="item" items="${sessionScope.trancs}">
				
				<tr><td>${item.dtime}</td><td>${item.account}</td>
				<td>${item.summary}</td><td>${item.total}</td>
				<td>${item.balance}</td>
				
				</tr>
		
		
			</c:forEach>
		
			</table>	
		</div>
	</c:if>
		<div class="footer">	
	    <form>
	      <select id="language" name="language" onchange="submit()">
	          <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
	          <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
	      </select>
	    </form>	
	</div>	
	<script type="text/javascript">
</script>

	

</body>
</html>