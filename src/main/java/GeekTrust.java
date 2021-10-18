import enitity.Family;
import manager.FileManager;
import manager.RelationshipManager;
import manager.relations.RelationExecutorFactory;
import util.Constants;
import util.OutputPrinter;

import java.io.File;
import java.util.List;

/**
 * Driver code
 */
public class GeekTrust {

    public static void main(String[] args) {

        final Family family = new Family();
        final OutputPrinter outputPrinter = new OutputPrinter();
        final RelationExecutorFactory relationshipExecutorFactory = new RelationExecutorFactory(family, outputPrinter);
        final RelationshipManager relationshipManager = new RelationshipManager(family, relationshipExecutorFactory, outputPrinter);
        final FileManager fileManager = new FileManager(relationshipManager, outputPrinter);

        initializeFamilyTree(fileManager);
        if (args.length > 0) {
           processFileArgument(fileManager, args[0]);
        }
    }


    public static void initializeFamilyTree(final FileManager fileManager) {
        List<String> initCommands = Constants.iniitCommands;
        for(String command: initCommands) {
            fileManager.processInitCommand(command);
        }
    }

    public static void processFileArgument(final FileManager fileManager,  String filePath) {
        fileManager.processInputFile(new File(filePath), false);
    }
}
