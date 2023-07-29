package br.com.paulo.comandos;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

import br.com.paulo.servidor.ServidorTarefas;

public class ComandoC2 implements Comando, Callable<Integer> {
	
	private PrintStream saida;
	private String nome;

	public ComandoC2(PrintStream saida) {
		this.saida = saida;
		this.nome = "c2";
	}

	@Override
	public Integer call() throws Exception {
		saida.println("[SERVIDOR] Executando comando c2");
		
		ServidorTarefas.consumeThread();
		Thread.sleep(20000);
		
		int numeroAleatorio = new Random().nextInt(101);
		
		saida.println("[SERVIDOR] Comando c2 executado com sucesso");
		ServidorTarefas.releaseThread();

		return numeroAleatorio;
	}

	@Override
	public void consumir() {
		saida.println("[SERVIDOR][C2] Consumindo...");
	}

	@Override
	public String getNome() {
		return nome;
	}

}
