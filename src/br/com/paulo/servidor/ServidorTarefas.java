package br.com.paulo.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTarefas {

	public static void main(String[] args) throws IOException {
		System.out.println("[ *** INICIANDO O SERVIDOR *** ]");
		ServerSocket server = new ServerSocket(12345);
		
		while (true) {
			Socket socket = server.accept();
			System.out.println("Aceitando novo cliente na porta " + socket.getPort());
			
			DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket);
			Thread threadCliente = new Thread(distribuirTarefas);
			threadCliente.start();
		}
	}
	
}
