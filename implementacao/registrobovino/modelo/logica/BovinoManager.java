package registrobovino.modelo.logica;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import registrobovino.modelo.Bovino;

public class BovinoManager {
	private static Map<String, Pattern> padroesTextuais;

	private List<String> sisbovs;
	private List<String> datasInclusao;
	private List<String> sexos;
	private List<String> datasNascimento;
	private List<Bovino> bovinos;
	private String enderecoArquivo;

	static {
		padroesTextuais = new LinkedHashMap<>();
		padroesTextuais.put("sisbov", Pattern.compile("\\d{15}"));
		padroesTextuais.put("data", Pattern.compile("\\d{2}/\\d{2}/\\d{4}"));
		padroesTextuais.put("sexo", Pattern.compile("(?:Macho|Fêmea)"));
	}

	public BovinoManager(String enderecoArquivo) {
		sisbovs = new ArrayList<>();
		datasInclusao = new ArrayList<>();
		sexos = new ArrayList<>();
		datasNascimento = new ArrayList<>();
		bovinos = new ArrayList<>();
		this.enderecoArquivo = enderecoArquivo;
	}

	public List<Bovino> listarBovinos() {
		return bovinos;
	}

	public void preencherCaracteristicas() throws IOException {
		try (PDDocument documento = Loader.loadPDF(new File(enderecoArquivo))) {
			for (PDPage pagina : documento.getPages()) {
				String texto = new BufferedReader(new InputStreamReader(pagina.getContents(), "ISO-8859-15")).lines().collect(Collectors.joining("\n"));
				String[] palavras = texto.split("\n");
				for (String padraoTextual : BovinoManager.padroesTextuais.keySet()) {
					for (String palavra : palavras) {
						if (palavra.contains("Total") || palavra.matches(".*(:.*){2}.*"))
							continue;
						Matcher combinador = BovinoManager.padroesTextuais.get(padraoTextual).matcher(palavra);						
						while (combinador.find()) {
							String palavraCombinada = combinador.group();
							switch (padraoTextual) {
								case "sisbov":
									sisbovs.add(palavraCombinada);
									break;
								case "data":
									if (datasNascimento.size() < sisbovs.size())
										datasNascimento.add(palavraCombinada);
									else
										datasInclusao.add(palavraCombinada);
									break;
								case "sexo":
									sexos.add(palavraCombinada);
									break;
							}
						}
					}
				}
			}
		}
		catch (IOException excecao) {
			throw excecao;
		}
	}

	public void criarBovinos() throws IOException {
		if (sisbovs.size() != datasNascimento.size() || sisbovs.size() != sexos.size() || sisbovs.size() != datasInclusao.size())
			throw new IOException("O arquivo está fora do padrão!\nFavor contactar a equipe de suporte."); 

		for (int i = 0; i < sisbovs.size(); i++)
			bovinos.add(new Bovino(sisbovs.get(i), datasNascimento.get(i), sexos.get(i), datasInclusao.get(i)));
	}
}