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

        String motherId = member.getMotherId();
        MemberImmediateFamilyInfo mother = family.getMember(motherId);
        if (mother == null) {
            outputPrinter.noRelatedMembersFound();
        } else {

            List<MemberBasicInfo> sons = Optional.ofNullable(family.getChildren(motherId))
                    .orElseGet(ArrayList::new)
                    .stream()
                    .filter(o -> o.getGender() == Gender.MALE && !o.getId().equals(member.getId()))
                    .collect(Collectors.toList());

            List<String> sisterInLaws = new ArrayList<>();
            for(MemberBasicInfo son: sons) {
                MemberImmediateFamilyInfo sonImmediateInfo = family.getMember(son.getId());
                if (sonImmediateInfo.getPartnerId() != null && !sonImmediateInfo.getPartnerId().isEmpty()) {
                    sisterInLaws.add(sonImmediateInfo.getPartnerId());
                }
            }

            if (sisterInLaws.isEmpty()) {
                outputPrinter.noRelatedMembersFound();

            } else {
                outputPrinter.printMemberNames(sisterInLaws);
            }
        }
    }
}
