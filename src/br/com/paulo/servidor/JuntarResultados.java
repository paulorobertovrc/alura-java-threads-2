package br.com.paulo.servidor;

import java.io.PrintStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JuntarResultados implements Runnable {

	private Future<Integer> futureWS;
	private Future<String> futureBD;
	private PrintStream saidaCliente;

	public JuntarResultados(Future<Integer> futureWS, Future<String> futureBD, PrintStream saidaCliente) {
		this.futureWS = futureWS;
		this.futureBD = futureBD;
		this.saidaCliente = saidaCliente;
	}

	@Override
	public void run() {
		System.out.println(ServidorTarefas.currentDateTime() + "Aguardando resultado do processamento");
		
		try {
			int retornoWS = this.futureWS.get(40, TimeUnit.SECONDS);
			String retornoBD = this.futureBD.get(40, TimeUnit.SECONDS);
			
			this.saidaCliente.println("[SERVIDOR]Resultado do comando c2: " + retornoWS + " | " + retornoBD);
			
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			saidaCliente.println("[SERVIDOR][C2]Tempo de execuçao excedido");
			System.out.println("Tempo de execuçao excedido no processamento do comando c2");
			
			this.futureWS.cancel(true);
			this.futureBD.cancel(true);
		}
		
		System.out.println(ServidorTarefas.currentDateTime() + "Processamento finalizado com sucesso");
	}

}
