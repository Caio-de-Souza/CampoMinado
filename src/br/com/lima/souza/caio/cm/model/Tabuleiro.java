package br.com.lima.souza.caio.cm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.lima.souza.caio.cm.exception.ExplosaoException;

public class Tabuleiro {
	private int linhas;
	private int colunas;
	private int minas;
	
	private static final List<Campo> CAMPOS = new ArrayList<>();
	
	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		this.gerarCampos();
		this.associarVizinhos();
		this.sortearMinas();
	}

	private void gerarCampos() {
		for (int i = 0; i < this.linhas; i++) {
			for (int j = 0; j < this.colunas; j++) {
				CAMPOS.add(new Campo(i, j));
			}
		}
	}
	
	private void associarVizinhos() {
		CAMPOS.forEach(campo1 -> CAMPOS.forEach(campo2 -> campo2.adicionarVizinho(campo1)));
	}

	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = Campo::isMinado;
		do {			
			int random = (int) (Math.random()* CAMPOS.size()); //NOSONAR
			CAMPOS.get(random).minar();
			minasArmadas = CAMPOS.stream().filter(minado).count();
		}while(minasArmadas < this.minas);
	}
	
	public boolean objAlcancado() {
		return CAMPOS.stream().allMatch(Campo::objAlcancado);
	}
	
	public void reiniciar() {
		CAMPOS.stream().forEach(Campo::reiniciar);
		this.sortearMinas();
	}
	
	public void abrir(int linha, int coluna) {
		try {
			CAMPOS.parallelStream()
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst().ifPresent(Campo::abrir);
		}catch(ExplosaoException ex) {
			CAMPOS.forEach(c -> c.setAberto(true));
			throw ex;
		}
	}
	
	public void alternarMarcacao(int linha, int coluna) {
		CAMPOS.parallelStream()
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst().ifPresent(Campo::alterarMarcacao);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("  ");
		for (int c = 0; c < this.colunas; c++) {
			sb.append("| ");
			sb.append(c);
			sb.append(" ");
		}
		sb.append("|\n");
		sb.append("  ");
		for (int c = 0; c < this.colunas; c++) {
			sb.append("----");
		}
		sb.append("\n");
		
		int aux = 0;
		for (int l = 0; l < this.linhas; l++) {
			sb.append(l);
			sb.append(" ");
			sb.append("|");
			for (int c = 0; c < this.colunas; c++) {
				if(c > 0) {
					sb.append("|");
				}
				sb.append(" ");
				sb.append(CAMPOS.get(aux));
				sb.append(" ");
				aux++;
			}
			sb.append("|\n");
		}
		return sb.toString();
	}
}
