package br.com.lima.souza.caio.cm.vision;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.lima.souza.caio.cm.exception.ExitException;
import br.com.lima.souza.caio.cm.exception.ExplosaoException;
import br.com.lima.souza.caio.cm.model.Tabuleiro;

public class TabuleiroConsole {

	private Tabuleiro tabuleiro;
	private Scanner entrada = new Scanner(System.in);

	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		
		this.executarJogo();
	}

	private void executarJogo() {
		try {
			boolean continuar = true;
			
			while(continuar) {
				exibirTabuleiro();
				
				System.out.println("Outra partida? (S/n)");
				String resp = entrada.nextLine();
				if(resp.equalsIgnoreCase("n")) {
					continuar = false;
				}else {
					tabuleiro.reiniciar();
				}
			}
		}catch(ExitException ex) {
			System.out.println("Tchau!!");
		}finally {
			entrada.close();
		}
	}

	private void exibirTabuleiro() {
		try {
			
			while(!this.tabuleiro.objAlcancado()) {
				System.out.println(this.tabuleiro);
				
				String dig = this.capturarValorDigitado("Digite (x,y): ");
				Iterator<Integer> xy = Arrays.stream(
						dig.split(","))
						.map(e -> Integer.parseInt(e.trim()))
						.iterator();
				
				dig = capturarValorDigitado("1 - Abrir, 2 - (Des)marcar: ");
				if(dig.equals("1")) {
					this.tabuleiro.abrir(xy.next(), xy.next());
				} else if(dig.equals("2")) {
					this.tabuleiro.alternarMarcacao(xy.next(), xy.next());
				} else{
					System.err.println("Insira uma opção válida!");
				}
				
			}
			System.out.println(this.tabuleiro);
			System.out.println("Você ganhou!!");
		}catch(ExplosaoException ex) {
			System.out.println(this.tabuleiro);
			System.out.println("Você perdeu!");
		}
	}
	
	private String capturarValorDigitado(String texto) {
		System.out.print(texto);
		String dig = entrada.nextLine();
		if(dig.equalsIgnoreCase("Sair")) {
			throw new ExitException();
		}
		return dig;
	}
}
