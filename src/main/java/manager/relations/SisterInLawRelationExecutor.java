package manager.relations;

import enitity.Family;
import enitity.MemberBasicInfo;
import enitity.MemberImmediateFamilyInfo;
import enums.Gender;
import enums.Relation;
import util.OutputPrinter;

import java.util.*;
import java.util.stream.Collectors;

public class SisterInLawRelationExecutor extends AbstractRelationExecutor {

    private OutputPrinter outputPrinter;

    public SisterInLawRelationExecutor(final Family family, final OutputPrinter outputPrinter) {
        super(Relation.SISTER_IN_LAW, family);
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
            List<MemberBasicInfo> sisterInLaws = Optional.ofNullable(family.getChildren(partnerMother.getId()))
                    .orElseGet(ArrayList::new)
                    .stream()
                    .filter(o -> o.getGender() == Gender.FEMALE && !o.getId().equals(partnerId))
                    .collect(Collectors.toList());

            if (sisterInLaws.isEmpty()) {
                outputPrinter.noRelatedMembersFound();

            } else {
                outputPrinter.printMembers(sisterInLaws);
            }
        }
    }
}
