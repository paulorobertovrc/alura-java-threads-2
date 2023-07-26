package br.com.paulo.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import br.com.paulo.cliente.Cliente;
import comandos.ComandoC1;
import comandos.ComandoC2;
import comandos.ComandoC2AcessoBD;
import comandos.Comandos;

public class DistribuirTarefas implements Runnable {

	private Socket socket;
	private Cliente client;
	private ServidorTarefas server;
	private ExecutorService threadPool;

	public DistribuirTarefas(Socket socket, ServidorTarefas server) {
		this.socket = socket;
		this.server = server;
		this.client = server.getNovoCliente();
		this.threadPool = server.getThreadPool();
	}

	@Override
	public void run() {		
		try {
			System.out.println(ServidorTarefas.currentDateTime() + "Distribuindo tarefas para " + socket);
			System.out.println();
			
			Scanner entradaCliente = client.getEntrada();
			PrintStream saidaCliente = client.getSaida();
			
			while(entradaCliente.hasNextLine()) {
				String comandoCliente = entradaCliente.nextLine();
				String validoOuInvalido; 

				if (isValidCommand(comandoCliente)) {
					validoOuInvalido = "VÁLIDO";
				} else {
					validoOuInvalido = "INVÁLIDO";
				}
				
				System.out.println(ServidorTarefas.currentDateTime() + "Comando " + validoOuInvalido + " recebido do cliente " + client.getNome() + ": " + comandoCliente);
				if (server.isFull()) {
					System.out.println(ServidorTarefas.currentDateTime() + "Comando não executado");
					System.out.println(ServidorTarefas.currentDateTime() + ServidorTarefas.currentCapacity());
				}
				
				switch (comandoCliente.toLowerCase()) {
				case "c1":
					saidaCliente.println("[SERVIDOR] Confirmação de comando c1 recebido");
					
					if (!server.isFull()) {
						ComandoC1 c1 = new ComandoC1(saidaCliente);
						System.out.println(ServidorTarefas.currentDateTime() + "Executando comando c1 a pedido do cliente " + client.getNome());
						this.threadPool.execute(c1);
					} else {
						saidaCliente.println("[SERVIDOR] Não foi possível executar o comando porque a capacidade máxima do servidor já foi atingida");
					}
					
					break;
				case "c2":
					saidaCliente.println("[SERVIDOR] Confirmação de comando c2 recebido");
					
					if (!server.isFull()) {
						System.out.println(ServidorTarefas.currentDateTime() + "Executando comando c2 a pedido do cliente " + client.getNome());
						
						ComandoC2 c2 = new ComandoC2(saidaCliente);
						Future<Integer> futureC2 = this.threadPool.submit(c2);

						ComandoC2AcessoBD c2Bd = new ComandoC2AcessoBD(saidaCliente);
						c2Bd.setNumero(futureC2);
						Future<String> futureC2Bd = this.threadPool.submit(c2Bd);
						
						this.threadPool.submit(new JuntarResultados(futureC2, futureC2Bd, saidaCliente));
						
					} else {
						saidaCliente.println("[SERVIDOR] Não foi possível executar o comando porque a capacidade máxima do servidor já foi atingida");
					}
					
					break;
				case "":
					break;
				case "fim":
					saidaCliente.println("[SERVIDOR] Desligando o servidor. Por favor, aguarde.");
					server.rotinaDesligamento();
					ServidorTarefas.releaseThread();
					break;
				default:
					saidaCliente.println("[SERVIDOR] Comando inválido. Tente novamente.");
				}				
				
			}
			
			System.out.println(ServidorTarefas.currentDateTime() + "O cliente " + client.getNome() + " foi desconectado.");
			
			saidaCliente.close();
			entradaCliente.close();
			
			ServidorTarefas.releaseThread();
			System.out.println(ServidorTarefas.currentCapacity());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean isValidCommand(String comandoUsuario) {
		for (Comandos comando : Comandos.values()) {
			if (comando.name().equalsIgnoreCase(comandoUsuario)) {
				return true;
			}
		}
		
		return false;
	}

}
