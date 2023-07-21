package br.com.paulo.cliente;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Cliente {
	private String nomeCliente;
	private Scanner entradaCliente;	
	private PrintStream saidaCliente;
	
	public Cliente(int clientesConectados, InputStream inputStream, OutputStream outputStream) {
		this.nomeCliente = "CLIENTE " + clientesConectados;
		this.entradaCliente = new Scanner(inputStream);
		this.saidaCliente = new PrintStream(outputStream);
	}
	
	public String getNome() {
		return this.nomeCliente;
	}
	
	public Scanner getEntrada() {
		return this.entradaCliente;
	}
	
	public PrintStream getSaida() {
		return this.saidaCliente;
	}
	
}
