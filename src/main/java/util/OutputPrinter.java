package util;

import enitity.MemberBasicInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Printer to help in printing the output back to the user. Output medium can be changed here
 * anytime without affecting any of the other code. Currently, System.out is used. Tomorrow if file
 * has to be used to output, it can be changed here easily.
 */
public class OutputPrinter {

    public void personNotFound() {
        printWithNewLine(Constants.PERSON_NOT_FOUND);
    }
    public void childAdditionSucceeded() {
        printWithNewLine(Constants.CHILD_ADDITION_SUCCEEDED);
    }
    public void childAdditionFailed() {
        printWithNewLine(Constants.CHILD_ADDITION_FAILED);
    }
    public void noRelatedMembersFound() {
        printWithNewLine(Constants.NONE);
    }
    public void invalidFile() {
        printWithNewLine("Invalid file given.");
    }
    public void invalidCommand() {
        printWithNewLine(Constants.INVALID_COMMAND);
    }
    public void invalidRelation() {
        printWithNewLine(Constants.PROVIDE_VALID_RELATION);
    }
    public void printMembers(Set<MemberBasicInfo> member) {
        printMembers(new ArrayList<>(member));
    }
    public void printMembers(List<MemberBasicInfo> members) {
        for (MemberBasicInfo info : members) {
            System.out.print(info.getId() + " ");
        }
        System.out.println();
    }
    public void printWithNewLine(final String msg) {
        System.out.println(msg);
    }

    public static class Constants {
        public static final String PERSON_NOT_FOUND = "PERSON_NOT_FOUND";
        public static final String PROVIDE_VALID_RELATION = "PROVIDE VALID RELATION";
        public static final String CHILD_ADDITION_FAILED = "CHILD_ADDITION_FAILED";
        public static final String CHILD_ADDITION_SUCCEEDED = "CHILD_ADDITION_SUCCEEDED";
        public static final String NONE = "NONE";
        public static final String INVALID_COMMAND = "INVALID COMMAND!";
    }
}