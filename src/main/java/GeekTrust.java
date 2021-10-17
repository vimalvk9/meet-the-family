import enitity.Family;
import manager.RelationshipManager;
import manager.relations.RelationExecutorFactory;
import util.OutputPrinter;

import java.io.File;


import static util.Constants.INIT_FILE_PATH;

public class GeekTrust {


    public static void main(String[] args) {

        final Family family = new Family();
        final OutputPrinter outputPrinter = new OutputPrinter();
        final RelationExecutorFactory relationshipExecutorFactory = new RelationExecutorFactory(family, outputPrinter);
        final RelationshipManager relationshipManager = new RelationshipManager(family, relationshipExecutorFactory, outputPrinter);
        final FileProcessor fileProcessor = new FileProcessor(relationshipManager, outputPrinter);

        try {
            fileToProcess(fileProcessor);
            fileToProcess(fileProcessor, args[0]);
        } catch (ArrayIndexOutOfBoundsException exception) {
            outputPrinter.printWithNewLine("No file found to process !");
        }

//        System.out.println("Head of Family -> " + family.getHeadOfFamily());
//        System.out.println("Children Map -> " + family.getMotherChildrenMap());
//        System.out.println("Members Map -> " + family.getMemberInfoMap());
    }


    public static void fileToProcess(final FileProcessor fileProcessor) {
        fileProcessor.processInputFile(new File(INIT_FILE_PATH), true);
    }

    public static void fileToProcess(final FileProcessor fileProcessor,  final String filePath) {
        fileProcessor.processInputFile(new File(filePath), false);
    }
}
