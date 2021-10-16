import enitity.Family;
import util.FileProcessor;

import java.io.File;


import static util.Constants.INIT_FILE_PATH;

public class GeekTrust {


    public static void main(String[] args) {

        Family family = new Family();
        GeekTrust driver = new GeekTrust();

        try {
            driver.fileToProcess(family);
            driver.fileToProcess(family, args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("File path not provided");
        }

//        System.out.println("Head of Family -> " + family.getHeadOfFamily());
//        System.out.println("Children Map -> " + family.getMotherChildrenMap());
//        System.out.println("Members Map -> " + family.getMemberInfoMap());
    }


    public void fileToProcess(Family family) {
        FileProcessor processor = new FileProcessor();
        processor.processInputFile(family, new File(INIT_FILE_PATH), true);
    }

    public void fileToProcess(Family family, String filePath) {
        File file = new File(filePath);
        FileProcessor processor = new FileProcessor();
        processor.processInputFile(family, file, false);
    }
}
