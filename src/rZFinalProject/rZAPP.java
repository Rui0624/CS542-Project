package rZFinalProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class rZAPP {
					
	public static void main(String[] args) throws IOException{
		
		
		/*File file = new File("src/rZFinalProject/test.txt");
		BufferedReader buffer = new BufferedReader(new FileReader(file));
		*/
		int[][] topology = new int[100][100]; // Create an array to store the topology
		int[][] matix = new int[100][100];
		int[][] read = new int[100][100];// Create an array to store the topology
		int line = 0;// Store the length of the topology
		
		String str = null; //store the file scan
		
		/*while((buffer.read()) != -1){
			str = buffer.readLine();
			String[] element = str.split(" ");
			
			for(int i = 0; i < element.length; i++){
				
				topology[line][i] = Integer.parseInt(element[i]);
				
				matix[line][i] = Integer.parseInt(element[i]);
			}
			line++;
		}
		*/
		
		
		/*(Scanner scan = new Scanner(file);
		
		String line;
		String[] element;
		int[][] topology = new int[100][100];
		for(int i = 0; i < 5; i++){
			line = scan.nextLine();
			element = line.split(" ");
			
			
		}*/
		
		String[] options = {"CS542 Link State Routing Simulator",
				"1. Input orginal network topology matix data file",
				"2. Select a source router",
				"3. Select the destionation router",
				"4. Select a router to be removed",
				"5. Show broadcast router",
				"6. Exit"
		
		};  // the contain in the menu
		rZMenu menu = new rZMenu(options);// initial the menu
		Scanner input = new Scanner(System.in);
		
		int start = 0, destination = 0, remove = 0;//parameters used in the algorithm 
		int[] distance = new int[100]; // create an array to store the shortest distance from the source to others node
		int[] sum = new int[100];
		boolean[] visit = new boolean[100];// mark the routers which are already visited
		int[] Interface = new int[100];// store the last routers before the destination
		while(true){
			menu.displayMenu();// display menu
			int number = menu.getChoice(input);// get choice
			int min;
			int v = 0;
			switch(number){
			case 1:
				System.out.println("Please input the path of the input file. e.g. src/rZFinalProject/test.txt");
				Scanner in = new Scanner (System.in);
				String path = in.nextLine();
				File file = new File(path);
				BufferedReader buffer = new BufferedReader(new FileReader(file));
				
				while((buffer.read()) != -1){
					str = buffer.readLine();
					String[] element = str.split(" ");
					
					for(int i = 0; i < element.length; i++){
						if(Integer.parseInt(element[i])==-1){
							topology[line][i] = 9999999;
							matix[line][i] = 9999999;
						}else{
						
						topology[line][i] = Integer.parseInt(element[i]);
						
						matix[line][i] = Integer.parseInt(element[i]);
						}
						read[line][i]= Integer.parseInt(element[i]);
					}
					line++;
				}
				//read and store the file
				
				System.out.println("The original matix table:");
				System.out.print("  ");
				for(int i = 0; i < line; i++){
					System.out.print("R"+ i + " ");
				}
				System.out.println();
				for(int i = 0; i < line; i++){
					System.out.print("R" + i + " ");
					for(int j = 0; j < line; j++){
						System.out.print(read[i][j] + " ");
					}
					System.out.println();
				}
				break;
				// show the topology input
			case 2:
				System.out.println("Please input the number of source router");
				start = input.nextInt();// get the number of source router
				for(int i = 0; i < line; i++){
					distance [i] = Math.abs(topology [start][i]);
					visit[i] = false;
					Interface[i] = 99999;
				} //initialization
				
				visit[start] = true;//set the source as visited
				v = 0;
				for(int i = 0; i < line ; i++){ //check every router
					min = 99999999;
					for(int j = 0; j < line; j++){// find the shortest router to the source router
						if(visit[j] != true && distance[j] < min){ //if the router is not visited and distance is smaller than the min
							min = distance[j];
							v = j;
						}
					}
					visit[v] = true;// make the router v as visited
					for(int j = 0; j < line; j++){
						if(visit[j] != true && distance[j] > distance[v] + Math.abs(topology[v][j])){
							//when the distance is shorter if pass router v, refresh the distance
							distance[j] = distance[v] + Math.abs(topology[v][j]);
							Interface[j] = v;
						}
						
					}
				
					
				}
				System.out.println("Destination" + " " + "Interface");
				for(int i = 0; i < line; i++){
					System.out.print(i + "  ");
					if(Interface[i] == 99999){
						System.out.println("-");
					}else{
						System.out.println(Interface[i] + "");
							
					}
				} // display the Destination and Interface
				break;
			case 3:
				System.out.println("Please input the number of destination router");
				destination = input.nextInt(); //record the destination
				for(int i = 0; i < line; i++){ // the dijkstra's algorithm same as above
					distance [i] = Math.abs(topology [start][i]);
					visit[i] = false;
					Interface[i] = start;
				}
				
				visit[start] = true;
				v = 0;
				for(int i = 0; i < line ; i++){
					min = 99999999;
					for(int j = 0; j < line; j++){
						if(visit[j] != true && distance[j] < min){
							min = distance[j];
							v = j;
						}
					}
					visit[v] = true;
					
					for(int j = 0; j < line; j++){
						if(visit[j] != true && distance[j] > distance[v] + Math.abs(topology[v][j])){
							distance[j] = distance[v] + Math.abs(topology[v][j]);
							Interface[j] = v;
						}
						
					}
				
					
				}
				System.out.println("the route is ");
				for(int i = 0; i < line; i++){
					if(i == destination){
						int pre = i;
						Stack<Integer> save = new Stack<>();
						save.push(i);
						while(pre!=start){
							save.push(Interface[pre]);
						
							pre = Interface[pre];
						}// display the specific path
						while(!save.isEmpty()){
							System.out.print("R" + save.pop()+ " , ");
						}
						System.out.println("the total cost is " + distance[i]);// show the distance
						System.out.println(); 
					}
					
					
					
				}
				break;
			case 4:
				System.out.println("Please input the number of the router you want to remove");
				remove = input.nextInt();//record the removed router
				System.out.println("The changed matix table:");
				System.out.print("  ");
				for(int i = 0; i < line; i++){
					if(i != remove){
						System.out.print("R"+ i + " ");
					}
				}// remove the router name in the list
				System.out.println();
				for(int i = 0; i < line; i++){
					if(i != remove){
						System.out.println();
						System.out.print("R" + i + " ");
						for(int j = 0; j < line; j++){
							if(j != remove){
								System.out.print(read[i][j] + " ");
							}else{matix[i][j] = 999999999;
									

							}
						}// do not display the removed router in the console and set the distance to the removed router as a really large numeber
						
					}else{
						
						for(int j = 0; j < line; j++){
								matix[i][j] = 999999999;
						
					}	
				
				}
				
				
			}
				System.out.println();
				for(int i = 0; i < line; i++){// dijkstra's algorithm same as above
					distance [i] = Math.abs(matix [start][i]);
					visit[i] = false;
					Interface[i] = start;
				}
				
				visit[start] = true;
				v = 0;
				for(int i = 0; i < line ; i++){
					min = 99999999;
					for(int j = 0; j < line; j++){
						if(visit[j] != true && distance[j] < min){
							min = distance[j];
							v = j;
						}
					}
					visit[v] = true;
					
					for(int j = 0; j < line; j++){
						if(visit[j] != true && distance[j] > distance[v] + Math.abs(matix[v][j])){
							distance[j] = distance[v] + Math.abs(matix[v][j]);
							Interface[j] = v;
						}
						
					}
				
					
				}
				System.out.println("the route is ");
				for(int i = 0; i < line; i++){
					if(i == destination){
						int pre = i;
						Stack<Integer> save = new Stack<>();
						save.push(i);
						while(pre!=start){
							save.push(Interface[pre]);
						
							pre = Interface[pre];
						}
						while(!save.isEmpty()){
							System.out.print("R" + save.pop()+ " , ");
						}
						System.out.println("the total cost is " + distance[i]);
					}
					
					
					
				}
				System.out.println();
				for(int i = 0; i < line; i++){
					for(int j = 0; j < line; j ++){
						matix[i][j] = topology[i][j];
					}
				}
				break;
			case 5:
				for(start = 0; start < line; start++){
					System.out.println("For R" + start + " to start: ");
					System.out.println("The total cost is ");
					for(int i = 0; i < line; i++){
						distance [i] = Math.abs(topology[start][i]);
						visit[i] = false;
						Interface[i] = start;
					}
					
					visit[start] = true;
					v = 0;
					for(int i = 0; i < line ; i++){
						min = 99999999;
						for(int j = 0; j < line; j++){
							if(visit[j] != true && distance[j] < min){
								min = distance[j];
								v = j;
							}
						}
						visit[v] = true;
						
						for(int j = 0; j < line; j++){
							if(visit[j] != true && distance[j] > distance[v] + Math.abs(topology[v][j])){
								distance[j] = distance[v] + Math.abs(topology[v][j]);
								Interface[j] = v;
							}
							
						}
					}
					for(int k = 0; k < line; k++){
						int a = 0;
						a = a + distance[k];
						sum[start] = sum[start] + a;
					}	
					System.out.println(sum[start]);
					
					
					/*for(int k = 0; k < line; k++){
						System.out.println("To R" + k);
						int pre = k;
						Stack<Integer> save = new Stack<>();
						save.push(k);
						while(pre!=start){
							save.push(Interface[pre]);	
							pre = Interface[pre];
						}
						while(!save.isEmpty()){
							System.out.print("R" + save.pop()+ " , ");
						}
						System.out.println("the total cost is " + distance[k]);
						System.out.println();
							
						
					}*/
					
				}
				int mini = sum[0]; int broadcast = 0;
				for(int a = 0; a < line; a++){
					if(sum[a] < mini){
						mini = sum[a];
						broadcast = a;
					}
				}
				System.out.println("The broadcast router is R" + broadcast + ", and the minnum distance is " + mini);
				
				
				
				start = broadcast;
				System.out.println("For R" + start + " to start: ");
				for(int i = 0; i < line; i++){
					distance [i] = Math.abs(topology[start][i]);
					visit[i] = false;
					Interface[i] = start;
				}
				
				visit[start] = true;
				v = 0;
				for(int i = 0; i < line ; i++){
					min = 99999999;
					for(int j = 0; j < line; j++){
						if(visit[j] != true && distance[j] < min){
							min = distance[j];
							v = j;
						}
					}
					visit[v] = true;
					
					for(int j = 0; j < line; j++){
						if(visit[j] != true && distance[j] > distance[v] + Math.abs(topology[v][j])){
							distance[j] = distance[v] + Math.abs(topology[v][j]);
							Interface[j] = v;
						}
						
					}
				}
				
				for(int k = 0; k < line; k++){
					System.out.println("To R" + k);
					int pre = k;
					Stack<Integer> save = new Stack<>();
					save.push(k);
					while(pre!=start){
						save.push(Interface[pre]);	
						pre = Interface[pre];
					}
					while(!save.isEmpty()){
						System.out.print("R" + save.pop()+ " , ");
					}
					System.out.println("the total cost is " + distance[k]);
					System.out.println();
						
					
				}
				break;
			case 6:
				System.out.println("Thank you :)");
				break;
				
				
				
		}
			if(number == 6){
				break;
			}
			
		}
		
		}
		

	}



