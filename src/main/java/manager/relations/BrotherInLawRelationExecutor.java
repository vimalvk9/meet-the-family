package manager.relations;

import enitity.Family;
import enitity.MemberBasicInfo;
import enitity.MemberImmediateFamilyInfo;
import enums.Gender;
import enums.Relation;
import util.OutputPrinter;

import java.util.*;
import java.util.stream.Collectors;

public class BrotherInLawRelationExecutor extends AbstractRelationExecutor {

    private OutputPrinter outputPrinter;

    public BrotherInLawRelationExecutor(final Family family, final OutputPrinter outputPrinter) {
        super(Relation.BROTHER_IN_LAW, family);
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

        String partnerId = member.getPartnerId();
        MemberImmediateFamilyInfo partner = family.getMember(partnerId);
        if (partner == null) {
            outputPrinter.noRelatedMembersFound();
        } else {

            if (partner.getMotherId() == null || partner.getMotherId().isEmpty()) {
                outputPrinter.noRelatedMembersFound();
                return;
            }
            MemberImmediateFamilyInfo partnerMother = family.getMember(partner.getMotherId());
            List<MemberBasicInfo> brotherInLaws = Optional.ofNullable(family.getChildren(partnerMother.getId()))
                    .orElseGet(ArrayList::new)
                    .stream()
                    .filter(o -> o.getGender() == Gender.MALE && !o.getId().equals(partnerId))
                    .collect(Collectors.toList());

            if (brotherInLaws.isEmpty()) {
                outputPrinter.noRelatedMembersFound();
            } else {
                outputPrinter.printMembers(brotherInLaws);
            }
        }
    }
}
