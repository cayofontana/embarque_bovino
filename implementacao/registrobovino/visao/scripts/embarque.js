function marcar(linha) {
	if (window.confirm("Confirma a embarcação do boi " + linha.id + "?") == true) {
		let corAtual = window.getComputedStyle(linha).getPropertyValue('background-color')
		linha.style.backgroundColor = "red"
		let celulas = linha.querySelectorAll("td")
		celulaBotao = celulas[celulas.length - 1]
		celulaBotao.innerHTML = "<input type=\"button\" value=\"Desfazer\" onclick=\"desmarcar(this.parentNode.parentNode, '" + corAtual + "')\"><input type=\"hidden\" name=\"embarcados\" value='" + linha.id + "'>"
	}
}

function desmarcar(linha, cor) {
	let celulas = linha.querySelectorAll("td")
	linha.style.backgroundColor = cor
	celulaBotao = celulas[celulas.length - 1]
	celulaBotao.innerHTML = "<input type=\"button\" value=\"EMBARCAR\" onclick=\"marcar(this.parentNode.parentNode)\">"
}

function existeEmbarque() {
	if (document.querySelectorAll("input[type=hidden]").length > 0)
		return true
	window.alert("Nenhum bovino foi selecionado para embarque.")
	return false
}