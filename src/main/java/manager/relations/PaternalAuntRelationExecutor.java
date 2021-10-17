package manager.relations;

import enitity.Family;
import enitity.MemberBasicInfo;
import enitity.MemberImmediateFamilyInfo;
import enums.Gender;
import enums.Relation;
import util.OutputPrinter;

import java.util.*;
import java.util.stream.Collectors;

public class PaternalAuntRelationExecutor extends AbstractRelationExecutor {

    private OutputPrinter outputPrinter;

    public PaternalAuntRelationExecutor(final Family family, final OutputPrinter outputPrinter) {
        super(Relation.PATERNAL_AUNT, family);
        this.outputPrinter = outputPrinter;
    }


    @Override
    public void getRelatedMembers(String memberName) {

        Family family = getFamily();
        MemberImmediateFamilyInfo member = family.getMember(memberName);
        if (member == null) {
            outputPrinter.personNotFound();
            return;
        }

        String fatherId = member.getFatherId();
        MemberImmediateFamilyInfo father = family.getMember(fatherId);
        if (father == null) {
            outputPrinter.noRelatedMembersFound();
            return;
        }
        MemberImmediateFamilyInfo grandMother = family.getMember(father.getMotherId());
        if (grandMother != null) {
            List<MemberBasicInfo> aunts = Optional.ofNullable(family.getChildren(grandMother.getId()))
                    .orElseGet(ArrayList::new)
                    .stream()
                    .filter(o -> o.getGender() == Gender.FEMALE)
                    .collect(Collectors.toList());

            if (aunts.size() > 0) {
                outputPrinter.printMembers(aunts);
            }
            else {
                outputPrinter.noRelatedMembersFound();
            }
        } else {
            outputPrinter.noRelatedMembersFound();
        }
    }
}
