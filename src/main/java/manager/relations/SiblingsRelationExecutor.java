package manager.relations;

import enitity.Family;
import enitity.MemberBasicInfo;
import enitity.MemberImmediateFamilyInfo;
import enums.Relation;
import util.Constants;
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
    public void getRelatedMembers(String memberName) {
        Family family = getFamily();
        MemberImmediateFamilyInfo member = family.getMember(memberName);

        String motherId = member.getMotherId();
        Set<MemberBasicInfo> children = family.getChildren(motherId);

        Set<MemberBasicInfo> siblings = Optional.ofNullable(children)
                .orElseGet(HashSet::new)
                .stream()
                .filter(o -> !o.getId().equals(member.getId()))
                .collect(Collectors.toSet());

        if (siblings.isEmpty()) {
            outputPrinter.noRelatedMembersFound();
        }
        else {
            outputPrinter.printMembers(siblings);
        }
    }

}
