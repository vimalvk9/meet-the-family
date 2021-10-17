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

public class DaughterRelationExecutor extends AbstractRelationExecutor {

    private OutputPrinter outputPrinter;

    public DaughterRelationExecutor(final Family family, final OutputPrinter outputPrinter) {
        super(Relation.DAUGHTER, family);
        this.outputPrinter = outputPrinter;
    }

    @Override
    public void getRelatedMembers( String memberName) {

        Family family = getFamily();
        MemberImmediateFamilyInfo member = family.getMember(memberName);

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
            Set<MemberBasicInfo> children = family.getChildren(motherId);
            Set<MemberBasicInfo> daughters = Optional.ofNullable(children)
                    .orElseGet(HashSet::new)
                    .stream().filter(s -> s.getGender() == Gender.FEMALE)
                    .collect(Collectors.toSet());

            if (daughters.isEmpty()) {
                outputPrinter.noRelatedMembersFound();
            } else {
                outputPrinter.printMembers(daughters);
            }
        }
    }
}
