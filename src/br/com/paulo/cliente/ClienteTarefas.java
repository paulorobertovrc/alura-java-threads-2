package br.com.paulo.cliente;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefas {

	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("localhost", 12345);
		System.out.println("[ *** CONEXÃO ESTABELECIDA COM SUCESSO *** ]");
		
		String clientName = Integer.toOctalString(socket.getLocalPort()).substring(4);
		
		PrintStream saida = new PrintStream(socket.getOutputStream());
		saida.println("cliente : " + clientName);
		
		Scanner teclado = new Scanner(System.in);
		System.out.print("> ");
		teclado.nextLine();
		
		saida.close();
		teclado.close();
		socket.close();
		System.out.println("[ *** CONEXÃO ENCERRADA *** ]");
	}

}
