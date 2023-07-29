package br.com.paulo.cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefas {

	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("localhost", 12345);
		System.out.println("[CLIENTE][ *** CONEXÃO ESTABELECIDA COM SUCESSO *** ]");		
		
		Thread threadEnviarComando = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					System.out.println("[CLIENTE] *** Enviando comandos ao servidor ***");
					PrintStream saida = new PrintStream(socket.getOutputStream());
					
					System.out.print("[CLIENTE] > ");
					Scanner teclado = new Scanner(System.in);	

					while(teclado.hasNextLine()) {
						String comando = teclado.nextLine();
						
						if (comando.trim().equals("")) {
							System.out.println("[CLIENTE] Desconectando. Aguarde.");
							break;
						}
						
						saida.println(comando);
						
						long inicio = System.currentTimeMillis();
						
						do {
							try {
								Thread.sleep(300);
								System.out.print(".");
							} catch (InterruptedException e) {
								saida.close();
								teclado.close();
								throw new RuntimeException();			
							}
						} while(System.currentTimeMillis() < inicio + 19000);
						
						System.out.println();
					}					
					
					System.out.println("[CLIENTE] *** Cliente desconectando ***");
					
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
				System.out.println("[CLIENTE] *** Recebendo dados do servidor ***");
				Scanner respostaServidor;
				try {
					respostaServidor = new Scanner(socket.getInputStream());
					
					while(respostaServidor.hasNextLine()) {
						String resposta = respostaServidor.nextLine();
						System.out.println(resposta);
						
						System.out.print("[CLIENTE] > ");
					}					
					
					respostaServidor.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}				
			}
		});
		
		threadReceberResposta.start();
		threadEnviarComando.start();
		
		threadEnviarComando.join();
		
		System.out.println("[CLIENTE] *** Fechando socket do cliente ***");		
		System.out.println("[CLIENTE][ *** CONEXÃO ENCERRADA *** ]");
		
		socket.close();
	}
	
}
