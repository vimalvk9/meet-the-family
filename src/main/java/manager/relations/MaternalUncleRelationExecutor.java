package manager.relations;

import enitity.Family;
import enitity.MemberBasicInfo;
import enitity.MemberImmediateFamilyInfo;
import enums.Gender;
import enums.Relation;

import util.Constants;
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
    public void getRelatedMembers(String memberName) {

        Family family = getFamily();
        MemberImmediateFamilyInfo member = family.getMember(memberName);

        String motherId = member.getMotherId();
        MemberImmediateFamilyInfo mother = family.getMember(motherId);
        if (mother == null) {
            outputPrinter.noRelatedMembersFound();
            return;
        }
        MemberImmediateFamilyInfo grandMother = family.getMember(mother.getMotherId());
        if (grandMother != null) {
            Set<MemberBasicInfo> uncles = Optional.ofNullable(family.getChildren(grandMother.getId()))
                    .orElseGet(HashSet::new)
                    .stream()
                    .filter(o -> (o.getGender() == Gender.MALE))
                    .collect(Collectors.toSet());

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
