import enitity.Family;
import manager.FileManager;
import manager.RelationshipManager;
import manager.relations.RelationExecutorFactory;
import util.OutputPrinter;

import java.io.File;



import static util.Constants.INIT_FILE_PATH;
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

        final GeekTrust driver = new GeekTrust();
        driver.initializeFamilyTree(fileManager);

        if (args.length > 0) {
            driver.processFileArgument(fileManager, args[0]);
        }
    }


    public void initializeFamilyTree(final FileManager fileManager) {
        fileManager.processInputFile(new File(INIT_FILE_PATH), true);
    }

    public void processFileArgument(final FileManager fileManager,  String filePath) {
        fileManager.processInputFile(new File(filePath), false);
    }
}
