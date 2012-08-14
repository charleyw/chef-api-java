<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
table {
	margin: 0 0 1em;
	background: #FFF;
	border-collapse: collapse;
	border-top: 1px solid #363;
	border-bottom: 2px solid #363;
}

/* caption = table title/heading */
caption {
	text-align: left;
	font: bold small-caps 120%/1.3 "trebuchet ms", Helvetica, Arial,
		Sans-Serif;
	color: #363;
	margin: .3em 0;
}

/* reduced font size to save space */
tr {
	font-size: 90%;
}
/* prevent nested tables reducing font size further */
tr tr {
	font-size: 100%;
}

/* tinted rows */
/* in CSS3 selectors: tbody tr:even or tbody tr:nth-child(2n) */
tr.odd {
	background: #DFD;
}

/* table cells */
th,td {
	font-weight: normal;
	padding: .3em .7em;
	text-align: left;
	vertical-align: top;
}

/* borders to separate body sections */
tbody tr:first-child th,tbody tr:first-child td,tfoot tr:first-child th,tfoot tr:first-child td
	{
	border-top: 1px solid #363;
}

/* tints for column headings */
thead {
	background: #9C9;
	white-space: nowrap;
}

/* tints for totals */
tfoot {
	background: #ADA;
}

/* bold text for totals */
tfoot th,tfoot td {
	font-weight: bold;
}

.table-striped tbody tr:nth-child(odd) td,.table-striped tbody tr:nth-child(odd) th
	{
	background-color: #DFD;
}

div#maincontent div {
	width: 607px;
	margin: 0 0 0 15px;
	padding: 20px 0 20px 0;
	border: 0;
	border-top: 1px solid #AAA;
	border-bottom: 1px solid #AAA;
}
</style>
</head>
<body>
	<div id="list">
		<table class="table-striped">
			<caption>All instances</caption>

			<!-- Table Header-->
			<thead>
				<tr>
					<th>id</th>
					<th>hostname</th>
					<th>ip address</th>
					<th>vnc</th>
				</tr>
			</thead>

			<!-- Table Body-->
			<tbody>
				<#list servers as ss>
					<tr>
						<td>${ss.id}</a></td>
						<td>${ss.hostname}</td>
						<td>${ss.ipaddress}</td>
						<td><a href="http://macloud.dnsdynamic.com/nova/instances_and_volumes/instances/${ss.id}/detail?tab=instance_details__vnc">vnc</a></td>
					</tr>
				</#list>
			</tbody>
		</table>
	</div>
	<div id="new">
			<table class="table-striped">
				<caption>Create new instance</caption>

				<!-- Table Body-->
				<tbody>
					<tr>
						<td>time</td>
						<td>message</td>
					</tr>
					<#list logs as log>
						<tr>
							<td>${log.logTime}</td>
							<td>${log.logMsg}</td>
						</tr>
					</#list>
				</tbody>
			</table>
	</div>
</body>
<p>
</html>