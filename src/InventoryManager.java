import java.io.*;
import java.util.*;

public class InventoryManager {

    public static void main(String[] args) {
    	final String fileName = "D:\\devyani\\npci-advcoding-playground-challenge-3-Devyani28\\src\\inventory.txt";
        Scanner scanner = new Scanner(System.in);
        int choice=0;
        
        do {
            displayMenu();
            try{
                choice = Integer.parseInt(scanner.next());
            }catch(NumberFormatException e){
                System.out.println("Invalid choice. Please select a valid option.");
                continue;
            }
            switch (choice) {
                case 1:
                    readInventory(fileName);
                    break;
                case 2:
                    System.out.println("Enter the item name:");
                    String itemName = scanner.next();
                    System.out.println("Enter the item count:");
                    int itemCount = scanner.nextInt();
                    addItem(fileName, itemName, itemCount);
                    break;
                case 3:
                    System.out.println("Enter the item name:");
                    itemName = scanner.next();
                    System.out.println("Enter the new count:");
                    itemCount = scanner.nextInt();
                    updateItem(fileName, itemName, itemCount);
                    break;
                case 4:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 4);
        scanner.close();

    }

    public static void displayMenu() {
        // display the menu
        System.out.println("Select one option");
        System.out.println("1.Reading and displaying inventory.");
        System.out.println("2.Adding new items.");
        System.out.println("3.Updating existing items.");
        System.out.println("4.Exit");
    }

    public static boolean isFileExist(String fileName) {
        // check if the file exists
        File file = new File(fileName);
        return file.exists();
    }   

    public static void createFile(String fileName) {
        // create a new file
        File file = new File(fileName);
        try {
            if(file.createNewFile()) {
                System.out.println("File Created successfully");
            }else {
                System.out.println("Failed to create file...");
            }
        } catch (IOException e) {
            System.out.println("Error in creating the file"+e.getMessage());
        }
    }

        
    public static void readInventory(String fileName) {
       
        // read the file and display the contents
    	if(isFileExist(fileName)) {
    		File file = new File(fileName);
    		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
    			String line;
    			while ((line = reader.readLine()) != null) {
    				System.out.println(line);
    			}
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}else {
    		createFile(fileName);
    		System.out.println("File Not Exsist New File Created");
    	}
    }

    public static void addItem(String fileName, String itemName, int itemCount) {
        // TODO: Add a new item to the inventory
        if (isAlreadyExists(fileName, itemName)) {
            System.out.println("Item already exists in the inventory.");
            return;
        }
        File file = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(itemName + "," + itemCount);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isAlreadyExists(String fileName, String itemName) {
        // check if the item exists in the inventory
    	if(isFileExist(fileName)) {
    		File file = new File(fileName);
    		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
    			String line;
    			while ((line = reader.readLine()) != null) {
    				String[] parts = line.split(",");
    				if (parts[0].equals(itemName)) {
    					return true;
    				}
    			}
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}else {
    		createFile(fileName);
    		System.out.println("File Not Exsist New File Created");
    	}
    	return false;
    }

    public static void updateItem(String fileName, String itemName, int itemCount) {
        // TODO: Update the count of an existing item
        // update the file with new count and display the updated inventory
        File file = new File(fileName);

        List<String> lines = new ArrayList<>();

       if(isAlreadyExists(fileName, itemName)){
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals(itemName)) {
                        parts[1] = String.valueOf(itemCount);
                        
                    }
                    lines.add(parts[0] + "," + parts[1]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Item not found in the inventory.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
            writer.write(line);
            writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Inventory updated successfully.");

    }
}
