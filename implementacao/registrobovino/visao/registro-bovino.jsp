<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Registro Bovino v1.0</title>
		<link rel="stylesheet" href="estilos/tabela_bovinos.css">
		<style>
			html {
				margin: 0 auto;
			}

			body {
				font-family: 'Source Sans Pro', sans-serif;
			}

			input[type=submit] {
				width: 100px;
				height: 20px;
				padding: 0;
				margin: 0;
			}
		</style>
	</head>
	<body>
		<form method="post" action="registrobovino" enctype="multipart/form-data">
			<fieldset>
				<p>
					Informe o arquivo PDF contendo as informações bovinas para embarcação.
					<br>
					<br>
					Segue abaixo um exemplo do modelo do arquivo que deve ser informado:
				</p>
				<img src="imagens/modelo.png" width="800px" height="400px">
				<br>
				<br>
				<p>
					<label>Selecione o arquivo:</label>
					<input required type="file" id="arquivo" name="arquivo" accept="application/pdf">
					<input type="submit" value="Enviar" size="50">
				</p>
			</fieldset>
		</form>
	</body>
</html>