package Utils;

import java.util.Scanner;

public class Input {
	private Scanner scanner;
	
	public Input(Scanner scanner) {
		this.scanner = scanner;
	}
	
	
	public int integerInput(String message) {	
		while(true) {
			try {
				System.out.print(message);
				return Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) { 
				System.out.print("Entrada inválida. Tente novamente: ");
			}
		}
		
	}
	
	
	public String stringInput(String message) {
		while(true) {
			System.out.print(message);
			String input = scanner.nextLine().trim();
			if (!input.isEmpty()) {
				return input;
			} else { 
				System.out.println("Entrada invalida. Valor nao pode ser vazio");
			}
		}
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


	public Long longInput(String message) {
		while(true){
			try {
				System.out.print(message);
				return Long.parseLong(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Entrada inválida. Tente digitar um número Long (BIGINT)");

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
			int menuSelected = integerInput(message);
			if (menuSelected >= min || menuSelected <= max) {
				return menuSelected;
			} else {
				System.out.println("Opcao selecionanda invalida");
			}
		}
	}
	
	public Scanner inputOpen() {
		Scanner s = new Scanner(System.in);
		return s;
	}
	
	public void inputClose() {
		scanner.close();
	}
}
