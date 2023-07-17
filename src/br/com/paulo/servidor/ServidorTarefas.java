package br.com.paulo.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {
	
	private ServerSocket server;
	private ExecutorService threadPool;
	private AtomicBoolean isRunning;

	public ServidorTarefas() throws IOException {
		this.server = new ServerSocket(12345);
		this.threadPool = Executors.newCachedThreadPool();
		this.isRunning = new AtomicBoolean(true);
		
		System.out.println("[SERVIDOR][ *** INICIANDO O SERVIDOR *** ]");
		System.out.println("[SERVIDOR][ *** isRunning: " + this.isRunning + " *** ]");
	}
	
	private void run() throws IOException {
		while (this.isRunning.get()) {
			try {
				Socket socket = server.accept();
				String clientName = Integer.toOctalString(socket.getPort()).substring(4);
				
				System.out.println("[SERVIDOR] Aceitando novo cliente na porta " + socket.getPort());
				System.out.println("[SERVIDOR] Nome do cliente: " + clientName);
				
				
				DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket, clientName, this);
				threadPool.execute(distribuirTarefas);
			} catch (SocketException e) {
				System.out.println("[ERROR] Socket Exception");
				System.out.println("[STATUS : SERVER RUNNING] " + this.isRunning);
				System.out.println("[ERROR] Aguarde liberação pelo último cliente conectado.");
			}
		}
	}
	
	public void rotinaDesligamento() throws IOException {
		System.out.println("[SERVIDOR][ *** Rotina de desligamento inicializada a pedido do cliente *** ]");
		this.shutdown();
	}
	
	public void shutdown() throws IOException {
		this.isRunning.set(false);
		server.close();
		threadPool.shutdown();
	}

	public static void main(String[] args) throws IOException {		
		ServidorTarefas server = new ServidorTarefas();
		server.run();
		server.shutdown();
	}
	
}
