package br.com.lima.souza.caio.cm;

import br.com.lima.souza.caio.cm.model.Tabuleiro;
import br.com.lima.souza.caio.cm.vision.TabuleiroConsole;

public class Application {

	public static void main(String[] args) {
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
		new TabuleiroConsole(tabuleiro);
	}
}
