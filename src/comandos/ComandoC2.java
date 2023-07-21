package comandos;

import java.io.PrintStream;

import br.com.paulo.servidor.ServidorTarefas;

public class ComandoC2 implements Runnable {
	
	private PrintStream saida;

	public ComandoC2(PrintStream saida) {
		this.saida = saida;
	}

	@Override
	public void run() {
		System.out.println("Executando comando c2");
		
		try {
			System.out.println(ServidorTarefas.currentDateTime());
			ServidorTarefas.consumeThread();
			Thread.sleep(20000);
		} catch(InterruptedException e) {
			throw new RuntimeException(e);
		}
		
		saida.println("[SERVIDOR] Comando c2 executado com sucesso");
		ServidorTarefas.releaseThread();
		ServidorTarefas.currentCapacity();
	}

}
