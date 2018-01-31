<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html>
<html>
	<head>
	 <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/static/images/favicon.ico"/>"/>
		<link rel="icon" type="image/ico" href="<c:url value="/static/images/favicon.ico"/>"/>
	     <title><spring:message code="title.page" text="!"/></title>
	     <link href="https://fonts.googleapis.com/css?family=Maven+Pro|Quicksand" rel="stylesheet">
	
	    <link href="<c:url value="/static/inspinia/css/bootstrap.min.css"/>" rel="stylesheet">
	    <link href="<c:url value="/static/inspinia/font-awesome/css/font-awesome.css"/>" rel="stylesheet">
	    <link href="<c:url value="/static/inspinia/css/animate.css"/>" rel="stylesheet">
	    <link href="<c:url value="/static/inspinia/css/style.css"/>" rel="stylesheet">
	    <link href="<c:url value="/static/css/loginstyle.css"/>" rel="stylesheet">
	  
	  	<style>
			#clear{
				clear:both
			}
			#logo
			{
				margin: auto;
				text-align: center;
				margin-bottom: 20px
			}
		</style>
	</head>
	
	<script type="text/javascript">
// 		function doSubmitForm(){
// 			document.forms[0].elements['password'].value = document.forms[0].elements['passwordFlag'].value + "," + getParameterByName('cldw', window.location);
// // 			document.forms[0].elements['password'].value = document.forms[0].elements['passwordFlag'].value + "," + ${cldw};
// 			document.forms[0].submit();
// 		}
		
		function getParameterByName(name, url) {
		    if (!url) {
		      url = window.location.href;
		    }
		    name = name.replace(/[\[\]]/g, "\\$&");
		    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
		        results = regex.exec(url);
		    if (!results) return null;
		    if (!results[2]) return '';
		    return decodeURIComponent(results[2].replace(/\+/g, " "));
		}
	</script>
	
	<body class="gray-bg pull-center">		
		<div class="loginColumns">
			<img id="logo" src="<c:url value='/static/images/logoDW.png' />"/>
			<div class="ibox-content">				
				<h4 class="text-upper text-center"><spring:message code="title.platform" text="!"/></h4>
				<c:url var="loginURL" value="/j_spring_security_check"/>
				<form:form action="${loginURL}" id="form" method="post">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<label style="color: red;">${messageError}<spring:message htmlEscape="false" code="${SPRING_SECURITY_LAST_EXCEPTION.message}" arguments=" " text="${SPRING_SECURITY_LAST_EXCEPTION.message}"/></label> 
					<div class="form-group">
						<div class="input-group m-b">
							<span class="input-group-addon"><i class="fa fa-user"></i></span>
							<input type="text" name="username" autocomplete="off" placeholder="<spring:message code="title.user" text="!"/>" class="form-control"/>
						</div>
					</div>
					<div class="form-group">
						<div class="input-group m-b">
							<span class="input-group-addon"><i class="fa fa-lock"></i></span> 
							<input type="password" name="password" autocomplete="off" placeholder="<spring:message code="title.psw" text="!"/>" class="form-control"/>
						</div>
					</div>	
					<div class="form-group">
						<button type="submit" class="btn block full-width m-b"><spring:message code="title.login" text="!"/></button>
						<p id="copyright"><i class="fa fa-copyright"></i>&nbsp;2017 <a id="data-wings" href="http://www.data-wings.com/" target="_blank">DataWings</a></p>
					</div>
				</form:form>
			</div>
		</div>
	</body>
</html>