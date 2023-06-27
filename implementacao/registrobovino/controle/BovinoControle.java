package registrobovino.controle;

import java.util.List;
import java.util.Iterator;

import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItem;

public class BovinoControle extends HttpServlet {
	private String caminhoArquivo;
	private int tamanhoMaximoArquivo = 5000 * 1024;
	private int tamanhoMaximoMemoria = 400 * 1024;
	private File arquivo;

	public void init() throws ServletException {
		caminhoArquivo = getServletContext().getInitParameter("file-upload");
	}

	public void doGet(HttpServletRequest requisicaoHTTP, HttpServletResponse respostaHTTP) throws ServletException, IOException {
		RequestDispatcher despachoRequisicao = requisicaoHTTP.getRequestDispatcher("registro-bovino.jsp");
		despachoRequisicao.forward(requisicaoHTTP, respostaHTTP);
	}

	public void doPost(HttpServletRequest requisicaoHTTP, HttpServletResponse respostaHTTP) throws ServletException, IOException {
		respostaHTTP.setContentType("text/html");
		PrintWriter out = respostaHTTP.getWriter();
		DiskFileItemFactory fabricaArquivos = new DiskFileItemFactory();
		fabricaArquivos.setSizeThreshold(tamanhoMaximoMemoria);
		fabricaArquivos.setRepository(new File("/tmp/"));
		ServletFileUpload servletUploadArquivo = new ServletFileUpload(fabricaArquivos);
		servletUploadArquivo.setSizeMax(tamanhoMaximoArquivo);
		String nomeArquivo = "";
		try {
			List itensArquivo = servletUploadArquivo.parseRequest(requisicaoHTTP);
			Iterator iterador = itensArquivo.iterator();
			while (iterador.hasNext()) {
				FileItem itemArquivo = (FileItem) iterador.next();
				if (!itemArquivo.isFormField()) {
					nomeArquivo = itemArquivo.getName();
					arquivo = new File(caminhoArquivo + nomeArquivo);
					itemArquivo.write(arquivo);
				}
			}
			requisicaoHTTP.getSession().setAttribute("enderecoArquivo", caminhoArquivo + nomeArquivo);
			respostaHTTP.sendRedirect(requisicaoHTTP.getContextPath() + "/listabovino");
		}
		catch(Exception excecao) {
			excecao.printStackTrace(out);
		}
	}

	public void destroy() {
	}
}