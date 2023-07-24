package br.com.paulo.servidor;

import java.lang.Thread.UncaughtExceptionHandler;

public class ExceptionHandler implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("[EXCEPTION HANDLER]Exceção capturada na " + t.getName() + " | " + e.getMessage());
	}

}
