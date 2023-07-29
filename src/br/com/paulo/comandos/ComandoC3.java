package br.com.paulo.comandos;

import java.io.PrintStream;
import java.util.concurrent.BlockingQueue;

import br.com.paulo.servidor.ServidorTarefas;

public class ComandoC3 implements Comando, Runnable {
	
	private PrintStream saida;
	private String nome;
	private BlockingQueue<Comando> filaComandos;

	public ComandoC3(PrintStream saida, BlockingQueue<Comando> filaComandos) {
		this.saida = saida;
		this.nome = "c3";
		this.filaComandos = filaComandos;
	}

	@Override
	public void run() {
		saida.println("[SERVIDOR]Executando comando c3");
		
		try {
			saida.println("Tamanho da fila: " + filaComandos.size());
			
			ServidorTarefas.consumeThread();
			consumir();
			Thread.sleep(5000);
			
		} catch(InterruptedException e) {
			throw new RuntimeException(e);
		}
		
		saida.println("[SERVIDOR]Comando c3 executado com sucesso");
		ServidorTarefas.releaseThread();
	}

	@Override
	public void consumir() {
		saida.println("[SERVIDOR][C3] Consumindo...");
	}

	@Override
	public String getNome() {
		return nome;
	}
	
}
