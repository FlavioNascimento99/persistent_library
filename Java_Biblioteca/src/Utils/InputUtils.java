package Utils;

import java.util.Scanner;

public class InputUtils {
	private Scanner scanner;
	
	public InputUtils(Scanner scanner) {
		this.scanner = scanner;
	}
	
	
	public int integerInput(String message) {	// Input Inteiro
		System.out.print(message);
		while(true) {
			try {
				System.out.print(message);
				return Integer.parseInt(scanner.nextLine()); // parseInt: converte entrada para um número inteiro;
			} catch (NumberFormatException e) { // Solicita nova entrada em caso de erro.
				System.out.print("Entrada inválida. Tente novamente: ");
			}
		}
		
	}
	
	public String stringInput(String message) {	// Input String
		System.out.print(message);
		return scanner.nextLine().trim();
	}
	
	public double doubleInput(String message) {
		while(true) {
			try {
				System.out.print(message);
				return Double.parseDouble(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Entrada inválida. Tente digitar um número decimal. ");
			}
		}
	}
	
	public boolean booleanInput(String message) {
		while(true) {
			System.out.print(message);
			String in = scanner.nextLine().trim().toLowerCase();
			if(in.equals("s")) {
				return true;
			} else if (in.equals("n")) {
				return false;
			} else {
				System.out.print("Entrada inválida, responda apenas com 's' ou 'n'.");
			}
		}
	}
	
	public int confirmMenuSelection(String message, int min, int max) {
		while(true) {
			int menuSelected = inputInteger(message);
		}
	}
}
