package Package;

import java.io.IOException;
import java.util.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;


public class App {

    //PART1
    public static void directoryDetails(String directoryPath) throws IOException{
        Path dir = Paths.get(directoryPath);
        if (!Files.isDirectory(dir)){
            throw new IllegalArgumentException("Invalid directory path: " + directoryPath);
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)){
            for (Path path : stream){
                BasicFileAttributes basicAttributes = Files.readAttributes(path, BasicFileAttributes.class);
                String fileName = path.getFileName().toString();
                long fileSize = basicAttributes.size();
                FileTime lastModifiedTime = basicAttributes.lastModifiedTime();

                System.out.println("File Name: " + fileName);
                System.out.println("File Size: " + fileSize);
                System.out.println("Last Modified Date: " + lastModifiedTime);

            }
        } catch (IOException e ){
            System.out.println("Error!");
            e.printStackTrace();
        }
    }

    //PART2
    //copy files
    public static void copyFile(String sourcePath, String destinationPath) throws IOException{
        Path source = Paths.get(sourcePath);
        Path destination = Paths.get(destinationPath);
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("File copied");
    }
    //move file
    public static void moveFile(String sourcePath, String destinationPath) throws IOException{
        Path source = Paths.get(sourcePath);
        Path destination = Paths.get(destinationPath);
        Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("File moved");
    }
    //detele file
    public static void deleteFile(String filePath) throws IOException{
        Path path = Paths.get(filePath);
        Files.deleteIfExists(path);
        System.out.println("File deleted");
    }
    //PART3
    //creates a directory
    public static void createDirectory(String directoryPath) throws IOException{
        Path path = Paths.get(directoryPath);
        Files.createDirectories(path);
        System.out.println("File created");
    }
    public static void deleteDirectory(String directoryPath) throws IOException{
        Path path = Paths.get(directoryPath);
        Files.delete(path);
    }

    //PART4
    public static void searchFile(String directoryPath, String keyword){
        Path dir = Paths.get(directoryPath);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, keyword)){
            System.out.println("Search here:");
            for (Path entry : stream) {
                System.out.println(entry.getFileName());
            }
        } catch (IOException e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    //PART5
    public static String prompt(String question){
        System.out.println(question);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }
    
    public static int promptInt(String question){
        String input = prompt(question);
        int parsedInput = Integer.parseInt(input);
        return parsedInput;
    }

    public static void main(String[] args) throws Exception{
        String input = prompt("Select directory path");

        boolean userDetails = true;

        while(userDetails){
            try {

                System.out.println("Thank you!");
                int userInput = promptInt("Would you like to: \n1) Show the directory details \n2) Search for a file \n3) Copy a file \n4) Move a file \n5) Delete a file \n6) Create a directoy \n7) Delete a directoy \n8) To exit/stop");
                if (userInput == 1){
                    directoryDetails(input);
                } else if(userInput == 2){
                    String keyword = prompt("What keyword would you like to search for?");
                    searchFile(input, keyword);
                } else if (userInput == 3){
                    String mainFile = prompt("What file  would you like to copy?");
                    String copyFile = prompt(" Thank you! Where would you like to copy this file to?");
                    copyFile(mainFile, copyFile);
                } else if(userInput == 4){
                    String mainFile = prompt("What file would you like to move?");
                    String moveFile = prompt(" Thank you! Where would you like to move this file to?");
                    moveFile(mainFile, moveFile);
                } else if(userInput == 5){
                    String sourcePath = prompt("What file would you like to delete?");
                    deleteFile(sourcePath);
                } else if(userInput == 6){
                    String directoryPath = prompt("What is the name of the directory would you like to create?");
                    createDirectory(directoryPath);
                } else if (userInput == 7){
                    String directoryPath = prompt("What is the name of the directory would you like to delete?");
                    deleteDirectory(directoryPath);
                } else if (userInput == 8){
                    userDetails = false;
                }
            } catch (IOException e){
                    if (e.getMessage() != "Error"){
                       promptInt("Invalid... Please restart to try again");
                    }
            }
        }
    }
}
