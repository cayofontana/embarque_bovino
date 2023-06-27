package registrobovino.controle;

import java.util.List;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import registrobovino.modelo.Bovino;
import registrobovino.modelo.logica.BovinoManager;

public class ListaBovinoControle extends HttpServlet {
	private String caminhoArquivo;

	public void init() throws ServletException {
		caminhoArquivo = getServletContext().getInitParameter("file-upload");
	}

	public void doGet(HttpServletRequest requisicaoHTTP, HttpServletResponse respostaHTTP) throws ServletException, IOException {
		HttpSession sessao = requisicaoHTTP.getSession();
		String enderecoArquivo = (String) sessao.getAttribute("enderecoArquivo");
		BovinoManager bovinoManager = new BovinoManager(enderecoArquivo);
		bovinoManager.preencherCaracteristicas();
		bovinoManager.criarBovinos();
		List<Bovino> bovinos = bovinoManager.listarBovinos();
		requisicaoHTTP.setAttribute("bovinos", bovinos);
		
		RequestDispatcher despachoRequisicao = requisicaoHTTP.getRequestDispatcher("lista-bovino.jsp");
		despachoRequisicao.forward(requisicaoHTTP, respostaHTTP);
	}

	public void doPost(HttpServletRequest requisicaoHTTP, HttpServletResponse respostaHTTP) throws ServletException, IOException {
		String[] embarcados = requisicaoHTTP.getParameterValues("embarcados");

		if (embarcados != null && embarcados.length > 0) {
			PDDocument documento = new PDDocument();
			int inicioPagina = 750;
			int alturaLinha = 15;
			int indiceAtualEmbarcados = 0;

			for (int quantidade_paginas = alturaLinha * embarcados.length / inicioPagina + 1; quantidade_paginas > 0; quantidade_paginas--) {
				PDPage pagina = new PDPage();
				PDPageContentStream conteudo = new PDPageContentStream(documento, pagina);

				conteudo.beginText();
				conteudo.newLineAtOffset(100, 770);

				if (indiceAtualEmbarcados == 0) {
					conteudo.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
					conteudo.showText("Lista de bovinos (sisbov) embarcados (total de " + embarcados.length + " animais):");
					conteudo.newLineAtOffset(0, -20);
				}
				conteudo.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);

				int limiteRestantePagina = inicioPagina;
				while (indiceAtualEmbarcados < embarcados.length) {
					conteudo.showText(embarcados[indiceAtualEmbarcados++]);
					conteudo.newLineAtOffset(0, -alturaLinha);
					limiteRestantePagina -= alturaLinha;
					if (inicioPagina - limiteRestantePagina > 730)
						break;
				}

				conteudo.endText();
				conteudo.close();

				documento.addPage(pagina);
			}

			String nomeArquivo = "bovinos_embarcados.pdf";
			documento.save(respostaHTTP.getOutputStream());
			documento.close();

			respostaHTTP.setCharacterEncoding("UTF-8");
			respostaHTTP.setContentType("application/pdf");
			respostaHTTP.setHeader("Content-Disposition","attachment; filename=\"" + nomeArquivo + "\"");
		}
	}

	public void destroy() {
	}
}