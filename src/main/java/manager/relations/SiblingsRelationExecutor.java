package manager.relations;

import enitity.Family;
import enitity.MemberBasicInfo;
import enitity.MemberImmediateFamilyInfo;
import enums.Relation;
import util.OutputPrinter;

import java.util.*;
import java.util.stream.Collectors;

public class SiblingsRelationExecutor extends AbstractRelationExecutor {

    private OutputPrinter outputPrinter;

    public SiblingsRelationExecutor(final Family family, final OutputPrinter outputPrinter) {
        super(Relation.SIBLINGS, family);
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
        List<MemberBasicInfo> children = family.getChildren(motherId);

        List<MemberBasicInfo> siblings = Optional.ofNullable(children)
                .orElseGet(ArrayList::new)
                .stream()
                .filter(o -> !o.getId().equals(member.getId()))
                .collect(Collectors.toList());

        if (siblings.isEmpty()) {
            outputPrinter.noRelatedMembersFound();
        }
        else {
            outputPrinter.printMembers(siblings);
        }
    }

}
