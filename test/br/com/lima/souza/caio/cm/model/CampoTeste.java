package br.com.lima.souza.caio.cm.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.lima.souza.caio.cm.exception.ExplosaoException;

public class CampoTeste {
	
	private Campo campo;
	
	@BeforeEach
	void inicializarCampo() {
		campo = new Campo(3, 3);
	}
	
	@Test
	void testeVizinhoRealDistEsq() {
		Campo vizinho = new Campo(3, 2);
		boolean isVizinho = campo.adicionarVizinho(vizinho);
		assertTrue(isVizinho);
	}

	@Test
	void testeVizinhoRealDistDir() {
		Campo vizinho = new Campo(3, 4);
		boolean isVizinho = campo.adicionarVizinho(vizinho);
		assertTrue(isVizinho);
	}
	
	@Test
	void testeVizinhoRealDistCima() {
		Campo vizinho = new Campo(2, 3);
		boolean isVizinho = campo.adicionarVizinho(vizinho);
		assertTrue(isVizinho);
	}
	
	@Test
	void testeVizinhoRealDistBaixo() {
		Campo vizinho = new Campo(4, 3);
		boolean isVizinho = campo.adicionarVizinho(vizinho);
		assertTrue(isVizinho);
	}
	
	@Test
	void testeVizinhoRealDistDiagonal() {
		Campo vizinho = new Campo(2, 2);
		boolean isVizinho = campo.adicionarVizinho(vizinho);
		assertTrue(isVizinho);
	}
	
	@Test
	void testeVizinhoFalso() {
		Campo vizinho = new Campo(1, 1);
		boolean isVizinho = campo.adicionarVizinho(vizinho);
		assertFalse(isVizinho);
	}
	
	@Test
	void testeDefaultAlternarMarcacao() {
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacaoDuasChamadas() {
		campo.alterarMarcacao();
		campo.alterarMarcacao();
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeAbrirNaoMinadoNaoMarcado() {
		assertTrue(campo.abrir());
	}
	
	@Test
	void testeAbrirNaoMinadoMarcado() {
		campo.alterarMarcacao();
		assertFalse(campo.abrir());
	}
	
	@Test
	void testeAbrirMinadoMarcado() {
		campo.alterarMarcacao();
		campo.minar();
		assertFalse(campo.abrir());
	}
	
	@Test
	void testeAbrirMinadoNaoMarcado() {
		campo.minar();
		assertThrows(ExplosaoException.class, () -> campo.abrir());
	}
	
	@Test
	void testeAbrirComVizinhos() {
		Campo campo11 = new Campo(1, 1);
		Campo campo22 = new Campo(2, 2);

		campo22.adicionarVizinho(campo11);
		campo.adicionarVizinho(campo22);
		campo.abrir();
		assertTrue(campo22.isAberto() && campo11.isAberto());
	}
	
	@Test
	void testeAbrirComVizinhos2() {
		Campo campo11 = new Campo(1, 1);
		Campo campo12 = new Campo(1, 2);
		Campo campo22 = new Campo(2, 2);
		campo12.minar();

		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);
		
		campo.adicionarVizinho(campo22);
		campo.abrir();
		assertTrue(campo22.isAberto() && !campo11.isAberto());
	}
}
