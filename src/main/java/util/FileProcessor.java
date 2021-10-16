package util;


import enitity.Family;
import enitity.MemberBasicInfo;
import enitity.MemberImmediateFamilyInfo;
import enums.Gender;
import enums.Relationship;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static util.Constants.*;


public class FileProcessor {

    public void processInputFile(Family family, File file, boolean isInputFile) {
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String command = sc.nextLine();
                if (isInputFile) {
                    processInitCommand(family, command);
                } else {
                    processInputCommand(family, command);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!! Path must be incorrect");
        }
    }

    private void processInitCommand(Family family, String command) {
        String[] commandParams = command.split(",");
        switch (commandParams[0]) {
            case ADD_FAMILY_HEAD:
                MemberBasicInfo head = new MemberBasicInfo(commandParams[1], Gender.valueOf(commandParams[2].toUpperCase()));
                family.addHeadOfFamily(head);
                family.addMember(head.getId(), new MemberImmediateFamilyInfo(head, "", "", ""));
                break;
            case ADD_SPOUSE:
                family.addSpouse(commandParams[1], commandParams[2], Gender.valueOf(commandParams[3].toUpperCase()));
                break;
            case ADD_CHILD:
                family.addChildForMother(commandParams[1], commandParams[2], Gender.valueOf(commandParams[3].toUpperCase()));
                break;
            default:
                System.out.println(INVALID_COMMAND);
        }
    }

    private void processInputCommand(Family family, String command) {
        String[] commandParams = command.split(" ");
        switch (commandParams[0]) {
            case ADD_CHILD:
                family.addChildForMother(commandParams[1], commandParams[2], Gender.valueOf(commandParams[3].toUpperCase()));
                break;

            case GET_RELATIONSHIP:
                Relationship[] relationships = Relationship.values();
                for (Relationship relationship : relationships) {
                    if (commandParams[2].equals(relationship.getAction())) {
                        family.getRelationship(commandParams[1], Relationship.valueOf(relationship.name()));
                        return;
                    }
                }
                System.out.println("INVALID_RELATIONSHIP");
                break;

            default:
                System.out.println(INVALID_COMMAND);
                break;
        }
    }
}
