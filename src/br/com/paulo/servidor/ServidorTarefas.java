package br.com.paulo.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTarefas {

	public static void main(String[] args) throws IOException {
		System.out.println("[ *** INICIANDO O SERVIDOR *** ]");
		ServerSocket server = new ServerSocket(12345);
		
		ExecutorService threadPool = Executors.newCachedThreadPool();
		
		while (true) {
			Socket socket = server.accept();
			System.out.println("Aceitando novo cliente na porta " + socket.getPort());
			
			DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket);
			threadPool.execute(distribuirTarefas);
		}
	}
	
}
