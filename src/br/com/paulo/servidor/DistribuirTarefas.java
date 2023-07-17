package br.com.paulo.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class DistribuirTarefas implements Runnable {

	private Socket socket;
	private String clientName;
	private ServidorTarefas server;

	public DistribuirTarefas(Socket socket, String clientName, ServidorTarefas server) {
		this.socket = socket;
		this.clientName = clientName;
		this.server = server;
	}

	@Override
	public void run() {		
		try {
			System.out.println("[SERVIDOR] Distribuindo tarefas para " + socket);
			
			Scanner entradaCliente = new Scanner(socket.getInputStream());
			
			PrintStream saidaCliente = new PrintStream(socket.getOutputStream());
			
			while(entradaCliente.hasNextLine()) {
				String comandoCliente = entradaCliente.nextLine();
				String validoOuInvalido; 

				if (isValidCommand(comandoCliente)) {
					validoOuInvalido = "VÁLIDO";
				} else {
					validoOuInvalido = "INVÁLIDO";
				}
				
				System.out.println("[SERVIDOR] Comando recebido do cliente " + clientName + ": " + comandoCliente);
				System.out.println("[SERVIDOR] Comando " + validoOuInvalido + " recebido do cliente " + clientName + ": " + comandoCliente);
				
				switch (comandoCliente.toLowerCase()) {
				case "c1":
					saidaCliente.println("[SERVIDOR] Confirmação de comando c1 recebido");
					break;
				case "c2":
					saidaCliente.println("[SERVIDOR] Confirmação de comando c2 recebido");
					break;
				case "":
					break;
				case "fim":
					saidaCliente.println("[SERVIDOR] Desligando o servidor. Por favor, aguarde.");
					server.rotinaDesligamento();
					break;
				default:
					saidaCliente.println("[SERVIDOR] Comando inválido. Tente novamente.");
				}
				
				
			}
			
			System.out.println("[SERVIDOR] O cliente " + clientName + " foi desconectado.");
			
			saidaCliente.close();
			entradaCliente.close();
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
