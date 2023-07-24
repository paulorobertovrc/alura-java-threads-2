package br.com.paulo.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import br.com.paulo.cliente.Cliente;

public class ServidorTarefas {
	
	private ServerSocket server;
	private ExecutorService threadPool;
	private AtomicBoolean isRunning;
	private static final int poolSize = 3;
	private static int usedThreads;
	
	private ArrayList<Cliente> clientes = new ArrayList<>();
	private int clientesConectados;

	public ServidorTarefas() throws IOException {
		this.server = new ServerSocket(12345);
//		this.threadPool = Executors.newCachedThreadPool();
		ServidorTarefas.usedThreads = 0;
		this.threadPool = Executors.newFixedThreadPool(poolSize, new ThreadFactory());
		this.isRunning = new AtomicBoolean(true);
		
		this.clientesConectados = 0;
		
		System.out.println("[ *** 	INICIANDO O SERVIDOR 	*** ]");
		System.out.println("[ *** 	isRunning: " + isRunning + " 	*** ]");
		System.out.println("[ *** 	Pool size: " + poolSize + "  		*** ]");
		System.out.println("[ ********************************* ]");
	}
	
	private void run() throws IOException {
		while (this.isRunning.get() && !this.isFull()) {
			try {				
				if (usedThreads < poolSize) {
					Socket socket = server.accept();
					usedThreads++;
					
					clientesConectados++;
					
					Cliente cliente = new Cliente(clientesConectados, socket.getInputStream(), socket.getOutputStream());
					clientes.add(cliente);
					
					System.out.println(ServidorTarefas.currentDateTime() + "Aceitando novo cliente na porta " + socket.getPort());
					System.out.println("Nome do cliente: " + clientes.get(clientesConectados - 1).getNome());
					
					if (isFull()) {
						System.out.println("Aguardando liberação de thread para distribuir tarefa ao cliente");
					} else {
						System.out.println(ServidorTarefas.currentCapacity());						
					}
										
					DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket, this);
					threadPool.execute(distribuirTarefas);
				} else {
					System.out.println(ServidorTarefas.currentDateTime() + "O número máximo de conexões já foi alcançado. Por favor, aguarde.");
				}
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
	
	public boolean isFull() {
		return ServidorTarefas.usedThreads >= ServidorTarefas.poolSize;
	}
	
	public static String currentDateTime() {
		return "[" + LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString() + "]";
	}
	
	public static String currentCapacity() {
		return "Tamanho do pool: " + ServidorTarefas.poolSize + " | Threads em uso: " + ServidorTarefas.usedThreads;
	}
	
	public Cliente getNovoCliente() {
		return this.clientes.get(clientesConectados - 1);
	}
	
	public ExecutorService getThreadPool() {
		return this.threadPool;
	}
	
	public int getPoolSize() {
		return ServidorTarefas.poolSize;
	}
	
	public int getUsedThreads() {
		return ServidorTarefas.usedThreads;
	}
	
	public static void consumeThread() {
		ServidorTarefas.usedThreads++;
	}
	
	public static void releaseThread() {
		ServidorTarefas.usedThreads--;
	}

	public static void main(String[] args) throws IOException {		
		ServidorTarefas server = new ServidorTarefas();
		server.run();
		server.shutdown();
	}
	
}
