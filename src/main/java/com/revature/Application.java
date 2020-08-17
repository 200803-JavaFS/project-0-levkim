package com.revature;

import com.revature.utilities.Services;

public class Application {

	public static void main(String[] args) {
		
		Services console = new Services();

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
		
		console.prompt();
		
	}
	
}
