package manager.relations;

import enitity.Family;
import enitity.MemberBasicInfo;
import enitity.MemberImmediateFamilyInfo;
import enums.Gender;
import enums.Relation;
import util.OutputPrinter;

import java.util.*;
import java.util.stream.Collectors;

public class MaternalAuntRelationExecutor extends AbstractRelationExecutor {

    private OutputPrinter outputPrinter;

    public MaternalAuntRelationExecutor(final Family family, final OutputPrinter outputPrinter) {
        super(Relation.MATERNAL_AUNT, family);
        this.outputPrinter = outputPrinter;
    }


    @Override
    public void findRelatedMembers(String memberName) {

        Family family = getFamily();
        MemberImmediateFamilyInfo member = family.getMember(memberName);
        if (member == null) {
            outputPrinter.personNotFound();
            return;
        }

        String motherId = member.getMotherId();
        MemberImmediateFamilyInfo mother = family.getMember(motherId);
        if (mother == null) {
           outputPrinter.noRelatedMembersFound();
           return;
        }
        MemberImmediateFamilyInfo grandMother = family.getMember(mother.getMotherId());
        if (grandMother != null) {
            List<MemberBasicInfo> aunts = Optional.ofNullable(family.getChildren(grandMother.getId()))
                    .orElseGet(ArrayList::new)
                    .stream()
                    .filter(o -> o.getGender() == Gender.FEMALE & !o.getId().equals(motherId))
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
