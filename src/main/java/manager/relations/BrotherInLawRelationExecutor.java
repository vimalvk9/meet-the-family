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

            List<MemberBasicInfo> daughters = Optional.ofNullable(family.getChildren(motherId))
                    .orElseGet(ArrayList::new)
                    .stream()
                    .filter(o -> o.getGender() == Gender.FEMALE && !o.getId().equals(member.getId()))
                    .collect(Collectors.toList());

            List<String> brotherInLaws = new ArrayList<>();
            for(MemberBasicInfo son: daughters) {
                MemberImmediateFamilyInfo daughterImmediateInfo = family.getMember(son.getId());
                if (daughterImmediateInfo.getPartnerId() != null && !daughterImmediateInfo.getPartnerId().isEmpty()) {
                    brotherInLaws.add(daughterImmediateInfo.getPartnerId());
                }
            }

            if (brotherInLaws.isEmpty()) {
                outputPrinter.noRelatedMembersFound();

            } else {
                outputPrinter.printMemberNames(brotherInLaws);
            }
        }
    }
}
