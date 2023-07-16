package br.com.paulo.cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefas {

	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("localhost", 12345);
		System.out.println("[ *** CONEXÃO ESTABELECIDA COM SUCESSO *** ]");
		
		String clientName = Integer.toOctalString(socket.getLocalPort()).substring(4);
		
		Thread threadEnviarComando = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					System.out.println("*** Enviando comandos ao servidor ***");
					PrintStream saida = new PrintStream(socket.getOutputStream());
//					saida.println("cliente : " + clientName);
					
					System.out.print("> ");
					Scanner teclado = new Scanner(System.in);	

					while(teclado.hasNextLine()) {
						String comando = teclado.nextLine();
						
						if (comando.trim().equals("")) {
							break;
						}
						
						saida.println(comando);
						System.out.print("> ");
					}
					
					saida.close();
					teclado.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
		
		Thread threadReceberResposta = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("*** Recebendo dados do servidor ***");
				Scanner respostaServidor;
				try {
					respostaServidor = new Scanner(socket.getInputStream());
					
					while(respostaServidor.hasNextLine()) {
						String resposta = respostaServidor.nextLine();
						System.out.println(resposta);
					}
					
					respostaServidor.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				
			}
		});
		
		threadReceberResposta.start();
		threadEnviarComando.start();
		
		threadEnviarComando.join();
		
		System.out.println("*** Fechando socket do cliente ***");
		socket.close();
		
		System.out.println("[ *** CONEXÃO ENCERRADA *** ]");
	}

}
