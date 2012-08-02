<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Freemarker test</title>
</head>
<body>
	<table border="1">
		<tr>
			<th>id</th>
			<th>name</th>
			<th>type</th>
			<th>owner</th>
		</tr>
		<#list instances as xx>
			<tr>
				<td>${xx.id}</td>
				<td>${xx.name}</td>
				<td>${xx.type}</td>
				<td>${xx.owner}</td>
			</tr>
		</#list>

	</table>
</body>
</html>