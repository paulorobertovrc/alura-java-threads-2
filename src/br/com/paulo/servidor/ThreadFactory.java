package br.com.paulo.servidor;

public class ThreadFactory implements java.util.concurrent.ThreadFactory {

	private static int numero = 1;
	
	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r, "Thread-ServidorTarefas-" + numero);
		numero++;

		thread.setUncaughtExceptionHandler(new ExceptionHandler());
		
		return thread;
	}

}
