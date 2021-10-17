package manager.relations;

import enitity.Family;
import enitity.MemberBasicInfo;
import enitity.MemberImmediateFamilyInfo;
import enums.Gender;
import enums.Relation;

import util.OutputPrinter;

import java.util.*;
import java.util.stream.Collectors;

public class MaternalUncleRelationExecutor extends AbstractRelationExecutor {

    private OutputPrinter outputPrinter;

    public MaternalUncleRelationExecutor(final Family family, final OutputPrinter outputPrinter) {
        super(Relation.MATERNAL_UNCLE, family);
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
            List<MemberBasicInfo> uncles = Optional.ofNullable(family.getChildren(grandMother.getId()))
                    .orElseGet(ArrayList::new)
                    .stream()
                    .filter(o -> (o.getGender() == Gender.MALE))
                    .collect(Collectors.toList());

            if (uncles.size() > 0) {
                outputPrinter.printMembers(uncles);
            }
            else {
                outputPrinter.noRelatedMembersFound();
            }
        }
        else {
            outputPrinter.noRelatedMembersFound();
        }
    }
}
