package br.com.paulo.comandos;

import java.io.PrintStream;

import br.com.paulo.servidor.ServidorTarefas;

public class ComandoC1 implements Comando, Runnable {
	
	private PrintStream saida;
	private String nome;

	public ComandoC1(PrintStream saida) {
		this.saida = saida;
		this.nome = "c1";
	}

	@Override
	public void run() {
		saida.println("[SERVIDOR] Executando comando c1");
		
		try {
			ServidorTarefas.consumeThread();
			Thread.sleep(20000);
			
		} catch(InterruptedException e) {
			throw new RuntimeException(e);
		}
		
		saida.println("[SERVIDOR] Comando c1 executado com sucesso");
		ServidorTarefas.releaseThread();
	}

	@Override
	public void consumir() {
		saida.println("[SERVIDOR][C1] Consumindo...");
	}

	@Override
	public String getNome() {
		return nome;
	}

}
