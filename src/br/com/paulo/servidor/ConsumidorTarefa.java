package br.com.paulo.servidor;

import java.util.concurrent.BlockingQueue;

import br.com.paulo.comandos.Comando;

public class ConsumidorTarefa implements Runnable {
	
	private BlockingQueue<Comando> filaComandos;
	
	public ConsumidorTarefa(BlockingQueue<Comando> filaComandos) {
		this.filaComandos = filaComandos;
	}

	@Override
	public void run() {
		
		try {
			Comando comando = null;
			
			do {
				comando = filaComandos.take();
				System.out.println(ServidorTarefas.currentDateTime() + "[CONSUMIDOR]Consumindo comando " + comando.getNome());
				comando.consumir();	
			} while (comando != null);
						
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
