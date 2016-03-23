package Control;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.InputStreamReader;

/**
 * Get all commands in the input file.
 * Let user determine if read from command line or from an input file.
 * 
 * @author BINLI
 *
 */
public class CommandParser {
  public List<List<String>> getOperations(String filename) throws IOException, FileNotFoundException {
      List<List<String>> operList = new ArrayList<>();
      String commandType;
      System.out.println("Please specify where do you want to "
          + "read operations from (c: command line, f: intput file)...");
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      commandType = br.readLine();
      if (commandType != null) {
        if (commandType.equals("c") || commandType.equals("C")) {
          String line;
          while ((line = br.readLine()) != null) {
            if (line == "q") {
              return operList;
            }
            operList.add(Arrays.asList(line));  
          }
        } else if (commandType.equals("f") || commandType.equals("F")) {
          br.close();
          BufferedReader reader = new BufferedReader(new FileReader(filename));
          String command;
          while ((command = reader.readLine()) != null) {
            if (command.length() != 0) {
              if (!command.trim().startsWith("//")) {
                String[] commandList = command.split(";");
                operList.add(Arrays.asList(commandList));
              } 
            }
          }
        } else {
          System.out.println("Input type is not correct.");
        }
      }
      return operList;
  }
  public static void main(String[] args) throws Exception {
    CommandParser parser = new CommandParser();
    List<List<String>> commandsList = parser.getOperations(
        "/Users/BINLI/Documents/Course/Adv_db/adv_db/projectSampletests.txt");
    System.out.println(commandsList.size());
    for (List<String> list : commandsList) {
      System.out.println(list);
    }
  }
}

