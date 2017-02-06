package rZFinalProject;

import java.util.Scanner;

public class rZMenu {
	
	private String[] options;
		//the options will show in the console to let the users to select
		
	public rZMenu(String[] options){
			this.options = options;
			//declare the nondefault constructor.
	}
		
	public int getChoice(Scanner input){
		System.out.println("Please input your choice as a integer.");
		int choice = input.nextInt();
		return choice;
			//promote user to input a integer
	}
		
	public void displayMenu(){
		for(String element : options){
			System.out.println(element);
				
		}
			//display the options in the console
	}

}
