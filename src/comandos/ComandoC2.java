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
		saida.println("[SERVIDOR] Executando comando c2");
		
		try {
			ServidorTarefas.consumeThread();
			Thread.sleep(20000);
			
		} catch(InterruptedException e) {
			throw new RuntimeException(e);
		}
		
		throw new RuntimeException("Exceção lançada pelo comando c2");
		
//		saida.println("[SERVIDOR] Comando c1 executado com sucesso");
//		ServidorTarefas.releaseThread();
	}

}
