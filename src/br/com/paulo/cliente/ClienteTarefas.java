package br.com.paulo.cliente;

import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefas {

	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("localhost", 12345);
		System.out.println("[ *** CONEXÃO ESTABELECIDA COM SUCESSO *** ]");
		
		Scanner teclado = new Scanner(System.in);
		System.out.print("> ");
		teclado.nextLine();
		
		socket.close();
		System.out.println("[ *** CONEXÃO ENCERRADA *** ]");
	}

}
