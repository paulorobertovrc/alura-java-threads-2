package comandos;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

import br.com.paulo.servidor.ServidorTarefas;

public class ComandoC2 implements Callable<Integer> {
	
	private PrintStream saida;

	public ComandoC2(PrintStream saida) {
		this.saida = saida;
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

}
