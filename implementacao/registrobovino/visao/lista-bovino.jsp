<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="pt-br">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Registro Bovino v1.0</title>
		<link rel="stylesheet" href="estilos/tabela_bovinos.css">
		<script src="scripts/embarque.js"></script> 
	</head>
	<body>
		<form method="post" action="listabovino">
			<input type="submit" value="Exportar Embarcados (PDF)" target="_blank" onclick="return existeEmbarque()">
			<br>
			<br>
			<table>
				<thead>
					<tr>
						<th>Nº SisBov</th>
						<th>Data Nascimento</th>
						<th>Sexo</th>
						<th>Data Inclusão</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${bovinos}" var="bovino">
						<tr id="${bovino.sisbov}">
							<td>${bovino.sisbov}</td>
							<td>${bovino.dataMascimento}</td>
							<td>${bovino.sexo}</td>
							<td>${bovino.dataInclusao}</td>
							<td><input type="button" value="EMBARCAR" onclick="marcar(this.parentNode.parentNode)"></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</body>
</html>