package br.com.lima.souza.caio.cm.model;

import java.util.ArrayList;
import java.util.List;

import br.com.lima.souza.caio.cm.exception.ExplosaoException;

public class Campo {

	private final int linha;
	private final int coluna;
	
	private boolean minado = false;
	private boolean aberto = false;
	private boolean marcado = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	public Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	public boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = linha != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaLinha + deltaColuna;

		boolean isVizinho = (deltaGeral == 1 && !diagonal) || (deltaGeral == 2 && diagonal);
		
		if(isVizinho) {
			this.vizinhos.add(vizinho);
		}
		
		return isVizinho;
	}

	void alterarMarcacao() {
		if(!this.aberto) {
			this.marcado = !this.marcado;
		}
	}
	
	boolean abrir() {
		if(!this.aberto && !this.marcado) {
			this.aberto = true;
			
			if(this.minado) {
				throw new ExplosaoException();
			}
			
			if(this.vizinhancaSegura()) {
				this.vizinhos.forEach(v -> v.abrir());
			}
			return true;
		}
		return false;
	}
	
	boolean vizinhancaSegura(){
		return this.vizinhos.stream().noneMatch(v -> v.minado);
	}

	public int getLinha() {
		return this.linha;
	}

	public int getColuna() {
		return this.coluna;
	}

	public boolean isMinado() {
		return this.minado;
	}

	public boolean isAberto() {
		return this.aberto;
	}

	public boolean isMarcado() {
		return this.marcado;
	}

	public List<Campo> getVizinhos() {
		return this.vizinhos;
	}
	
	
	void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	void minar() {
		this.minado = true;
	}
	
	boolean objAlcancado() {
		boolean desvendado = !this.minado && this.aberto;
		boolean protegido = this.minado && this.marcado;
		return desvendado || protegido;
	}
	
	long minasNaVizinhanca() {
		return this.vizinhos.stream().filter(v -> v.minado).count();
	}
	
	void reiniciar() {
		this.aberto = false;
		this.minado = false;
		this.marcado = false;
	}
	
	@Override
	public String toString() {
		String icon = "?";
		
		if(marcado) {
			icon = "x";
		} else if (this.aberto) {
			if(this.minado) {
				icon = "*";
			}else if(this.minasNaVizinhanca() > 0) {
				icon = Long.toString(this.minasNaVizinhanca());
			}else {
				icon = " ";
			}
		}
		return icon;
	}
	
}
