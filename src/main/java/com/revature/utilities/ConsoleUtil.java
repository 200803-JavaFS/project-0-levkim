package com.revature.utilities;

public class ConsoleUtil {
	
	private static Services menu = new Services();
	
	public void welcome() {
		System.out.println("WHALEcome to Beluga Bank, where banking is a splash!");
		System.out.println("               __   __\r\n" + 
				"              __ \\ / __\r\n" + 
				"             /  \\ | /  \\\r\n" + 
				"                 \\|/\r\n" + 
				"            _,.---v---._\r\n" + 
				"   /\\__/\\  /            \\\r\n" + 
				"   \\_  _/ /              \\\r\n" + 
				"     \\ \\_|           @ __|\r\n" + 
				"  hjw \\                \\_\r\n" + 
				"  `97  \\     ,__/       /\r\n" + 
				"     ~~~`~~~~~~~~~~~~~~/~~~~");
		
		menu.prompt();
	}

}
