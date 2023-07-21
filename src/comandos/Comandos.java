package comandos;

public enum Comandos {
	C1, C2, FIM;
	
	public static boolean isValidCommand(String comandoUsuario) {
		for (Comandos comando : Comandos.values()) {
			if (comando.name().equalsIgnoreCase(comandoUsuario)) {
				return true;
			}
		}
		
		return false;
	}
}
