//Name: David Song
//NetID: DXS180082

package RedBox;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException  {
		
		//BSTree Object 
		BSTree<String> inventory = new BSTree<String>();
		
		//For writing error from transaction file
		PrintWriter writeError = new PrintWriter(new FileWriter("error.log"));
		
		//Read Inventory File
		Scanner readInventory = new Scanner(new File("inventory.dat"));
		while (readInventory.hasNextLine()) {
			String readLine = readInventory.nextLine();
			String[] tempArray = readLine.split(",");
			String title = tempArray[0].trim().replace("\"", "");
			int available = Integer.parseInt(tempArray[1]);
			int rental = Integer.parseInt(tempArray[2]);
			
			//Insert into BST object
			inventory.insert(title, available, rental);	
		}
		
		//Read Transaction File
		Scanner readTransaction = new Scanner(new File("transaction.log"));
		while (readTransaction.hasNextLine()) {
			String key = readTransaction.next();
			String readLine = readTransaction.nextLine();
			String[] tempArray = readLine.split(",");
			String title = tempArray[0].trim();
			int value = 0;
			if (tempArray.length == 2) {
				value = Integer.parseInt(tempArray[1]);
			}

			//Update Inventory with Transaction.log
			//Add 
			if (key.equals("add")) {			
				if (value >= 0 && tempArray.length == 2 && title.charAt(0) == '\"' && title.charAt(title.length() - 1) == '\"') {	//Handle Invalid
					inventory.add(title.replace("\"", ""), value);	
				} else {
					writeError.write(key + readLine);
					writeError.println();
				}
			} //Rent 
			else if (key.contentEquals("rent") && inventory.search(title.replace("\"", ""))) {
				if (value == 0 && tempArray.length == 1 && title.charAt(0) == '\"' && title.charAt(title.length() - 1) == '\"') {	//Handle Invalid
					inventory.rent(title.replace("\"", ""));				
				} else {
					writeError.write(key + readLine);
					writeError.println();
				}
			} //Return
			else if (key.contentEquals("return")  && inventory.search(title.replace("\"", ""))) {
				if (value == 0 && tempArray.length == 1 && title.charAt(0) == '\"' && title.charAt(title.length() - 1) == '\"') {	//Handle Invalid
					inventory.Return(title.replace("\"", ""));				
				} else {
					writeError.write(key + readLine);
					writeError.println();
				}
			} //Remove
			else if (key.contentEquals("remove") && inventory.search(title.replace("\"", ""))) {	
				if (value >= 0 && tempArray.length == 2 && title.charAt(0) == '\"' && title.charAt(title.length() - 1) == '\"') {	//Handle Invalid
					if (inventory.searchAvailableValue(title.replace("\"", ""),value) && inventory.searchRentalValue(title.replace("\"", ""))) {
						//Delete if available and rental value is 0
						inventory.delete(title.replace("\"", ""));
					} else {
						inventory.remove(title.replace("\"", ""), value);
					}
				} else {
					writeError.write(key + readLine);
					writeError.println();
				}
			}
			else {
				writeError.write(key + readLine);
				writeError.println();
			}
		}
		writeError.close();
		//Write to file redbox_kiosk.txt 
		PrintWriter write = new PrintWriter(new FileWriter("redbox_kiosk.txt"));
		write.printf("Title               " +  "       Available" + "   Rented");
		write.println();
		inventory.writeFile(write);
		write.close();	
		readInventory.close();
		readTransaction.close();
		System.out.println("Written to redbox_ kiosk.txt sucessfully...");
	}
}


