package comandos;

import java.io.PrintStream;

import br.com.paulo.servidor.ServidorTarefas;

public class ComandoC1 implements Runnable {
	
	private PrintStream saida;

	public ComandoC1(PrintStream saida) {
		this.saida = saida;
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

}
