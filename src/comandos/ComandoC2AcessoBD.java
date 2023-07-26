package comandos;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import br.com.paulo.servidor.ServidorTarefas;

public class ComandoC2AcessoBD implements Callable<String> {
	
	private PrintStream saida;
	private int numero;

	public ComandoC2AcessoBD(PrintStream saida) {
		this.saida = saida;
	}

	@Override
	public String call() throws Exception {
		System.out.println("Acessando o banco de dados");
		saida.println("[SERVIDOR][C2]Acessando o banco de dados");
		
		ServidorTarefas.consumeThread();
		Thread.sleep(5000);
		
		saida.println("[SERVIDOR][C2]Fechando acesso ao banco de dados");
		ServidorTarefas.releaseThread();

		return this.parOuImpar(numero);
	}
	
	public void setNumero(Future<Integer> resultadoNumerico) throws InterruptedException, ExecutionException {
		this.numero = resultadoNumerico.get();
	}
	
	private String parOuImpar(int numero) {
		return numero % 2 == 0 ? "PAR" : "ÍMPAR"; 
	}

}
