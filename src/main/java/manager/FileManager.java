package manager;

import enums.Command;
import enums.Gender;
import enums.Mode;
import util.Constants;
import util.OutputPrinter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Logic to read files / commands and direct the request to concerned component
 */
public class FileManager {

    private RelationshipManager relationshipManager;
    private OutputPrinter outputPrinter;

    public FileManager(final RelationshipManager relationshipManager, final  OutputPrinter outputPrinter) {
        this.relationshipManager = relationshipManager;
        this.outputPrinter = outputPrinter;
    }

    public void processInputFile(File file, final boolean init) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                if (init) {
                    processInitCommand(command);
                } else {
                    processInputCommand(command);
                }
            }
        } catch (FileNotFoundException e) {
            outputPrinter.printWithNewLine(e.getMessage());
        }
    }

    public void processInitCommand(final String command) {
        String[] params = command.split(Constants.COMMA_DELIMITER);

        switch (Command.valueOf(params[0])) {
            case ADD_FAMILY_HEAD:
                relationshipManager.addFamilyHead(params[1], Gender.valueOf(params[2].toUpperCase()));
                break;
            case ADD_SPOUSE:
                relationshipManager.addSpouse(params[1], params[2], Gender.valueOf(params[3].toUpperCase()), Mode.INIT);
                break;
            case ADD_CHILD:
                relationshipManager.addChildThroughMother(params[1], params[2], Gender.valueOf(params[3].toUpperCase()), Mode.INIT);
                break;
            default:
               outputPrinter.invalidCommand();
        }
    }

    public void processInputCommand(final String command) {
        String[] params = command.split(Constants.SPACE_DELIMITER);

        switch (Command.valueOf(params[0])) {

            case ADD_CHILD: {
                relationshipManager.addChildThroughMother(params[1], params[2], Gender.valueOf(params[3].toUpperCase()), Mode.INPUT);
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
