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

public class PaternalUncleRelationExecutor extends AbstractRelationExecutor {

    private OutputPrinter outputPrinter;

    public PaternalUncleRelationExecutor(final Family family, final OutputPrinter outputPrinter) {
        super(Relation.PATERNAL_UNCLE, family);
        this.outputPrinter = outputPrinter;
    }

    @Override
    public void getRelatedMembers(String memberName) {
        Family family = getFamily();
        MemberImmediateFamilyInfo member = family.getMember(memberName);

        String fatherId = member.getFatherId();
        MemberImmediateFamilyInfo father = family.getMember(fatherId);
        if (father == null) {
            outputPrinter.noRelatedMembersFound();
            return;
        }
        MemberImmediateFamilyInfo grandMother =  family.getMember(father.getMotherId());
        if (grandMother != null) {
            Set<MemberBasicInfo> uncles = Optional.ofNullable( family.getChildren(grandMother.getId()))
                    .orElseGet(HashSet::new)
                    .stream()
                    .filter(o -> (o.getGender() == Gender.MALE & !o.getId().equals(fatherId)))
                    .collect(Collectors.toSet());

            if (uncles.size() > 0) {
                for (MemberBasicInfo info : uncles) {
                    System.out.print(info.getId() + " ");
                }
                System.out.println();
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
