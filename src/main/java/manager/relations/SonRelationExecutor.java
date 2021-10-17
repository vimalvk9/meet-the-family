package manager.relations;

import enitity.Family;
import enitity.MemberBasicInfo;
import enitity.MemberImmediateFamilyInfo;
import enums.Gender;
import enums.Relation;
import util.OutputPrinter;

import java.util.*;
import java.util.stream.Collectors;

public class SonRelationExecutor extends AbstractRelationExecutor {

    private OutputPrinter outputPrinter;

    public SonRelationExecutor(final Family family, final OutputPrinter outputPrinter) {
        super(Relation.SON, family);
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
           outputPrinter.personNotFound();

        } else {

            List<MemberBasicInfo> children = family.getChildren(motherId);
            List<MemberBasicInfo> sons = Optional.ofNullable(children)
                    .orElseGet(ArrayList::new)
                    .stream().filter(s -> s.getGender() == Gender.MALE)
                    .collect(Collectors.toList());

            if (sons.isEmpty()) {
                outputPrinter.noRelatedMembersFound();
            } else {
                outputPrinter.printMembers(sons);
            }
        }
    }
}
