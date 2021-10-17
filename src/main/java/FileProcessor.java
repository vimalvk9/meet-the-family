import enums.Command;
import enums.Gender;
import manager.RelationshipManager;
import util.OutputPrinter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class FileProcessor {

    private RelationshipManager relationshipManager;
    private OutputPrinter outputPrinter;

    public FileProcessor(final RelationshipManager relationshipManager, final  OutputPrinter outputPrinter) {
        this.relationshipManager = relationshipManager;
        this.outputPrinter = outputPrinter;
    }

    public void processInputFile(final File file, final boolean isInputFile) {

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                if (isInputFile) {
                    processInitCommand(command);
                } else {
                    processInputCommand(command);
                }
            }
        } catch (FileNotFoundException e) {
            outputPrinter.printWithNewLine("File Not Found!! Path must be incorrect");
        }
    }

    private void processInitCommand(final String command) {
        String[] params = command.split(",");

        switch (Command.valueOf(params[0])) {
            case ADD_FAMILY_HEAD:
                relationshipManager.addFamilyHead(params[1], Gender.valueOf(params[2].toUpperCase()));
                break;
            case ADD_SPOUSE:
                relationshipManager.addSpouse(params[1], params[2], Gender.valueOf(params[3].toUpperCase()));
                break;
            case ADD_CHILD:
                relationshipManager.addChildThroughMother(params[1], params[2], Gender.valueOf(params[3].toUpperCase()));
                break;
            default:
               outputPrinter.invalidCommand();
        }
    }

    private void processInputCommand(final String command) {
        String[] params = command.split(" ");

        switch (Command.valueOf(params[0])) {

            case ADD_CHILD: {
                relationshipManager.addChildThroughMother(params[1], params[2], Gender.valueOf(params[3].toUpperCase()));
                break;
            }

            case GET_RELATIONSHIP: {
                relationshipManager.getRelatedMembers(params[1], params[2]);
                break;
            }
            default: {
                outputPrinter.invalidCommand();
                break;
            }
        }
    }
}
