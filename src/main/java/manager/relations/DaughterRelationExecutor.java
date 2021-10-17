package manager.relations;

import enitity.Family;
import enitity.MemberBasicInfo;
import enitity.MemberImmediateFamilyInfo;
import enums.Gender;
import enums.Relation;
import util.OutputPrinter;

import java.util.*;
import java.util.stream.Collectors;

public class DaughterRelationExecutor extends AbstractRelationExecutor {

    private OutputPrinter outputPrinter;

    public DaughterRelationExecutor(final Family family, final OutputPrinter outputPrinter) {
        super(Relation.DAUGHTER, family);
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

        Gender gender = member.getGender();
        String motherId = null;

        if (gender == Gender.MALE) {
            motherId = member.getPartnerId();
        } else {
            motherId = member.getId();
        }

        if (motherId == null) {
            outputPrinter.noRelatedMembersFound();
        } else {
            List<MemberBasicInfo> children = family.getChildren(motherId);
            List<MemberBasicInfo> daughters = Optional.ofNullable(children)
                    .orElseGet(ArrayList::new)
                    .stream().filter(s -> s.getGender() == Gender.FEMALE)
                    .collect(Collectors.toList());

            if (daughters.isEmpty()) {
                outputPrinter.noRelatedMembersFound();
            } else {
                outputPrinter.printMembers(daughters);
            }
        }
    }
}
