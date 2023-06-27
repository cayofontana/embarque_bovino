package registrobovino.modelo;

public class Bovino {
	private String sisbov;
	private String dataNascimento;
	private String sexo;
	private String dataInclusao;

	public Bovino(String sisbov, String dataInclusao, String sexo, String dataNascimento) {
		this.sisbov = sisbov;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.dataInclusao = dataInclusao;
	}

	public String getSisbov() {
		return sisbov;
	}

	public String getDataMascimento() {
		return dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public String getDataInclusao() {
		return dataInclusao;
	}

	public String imprimir() {
		return (sisbov + "\t" + dataNascimento + "\t" + sexo + "\t" + dataInclusao);
	}
}