package manager;

import enitity.Family;
import enitity.MemberBasicInfo;
import enitity.MemberImmediateFamilyInfo;
import enums.Gender;
import enums.Mode;
import enums.Relation;
import manager.relations.AbstractRelationExecutor;
import manager.relations.RelationExecutorFactory;
import util.OutputPrinter;

import java.util.Objects;


/**
 * Logic to do operations to family and output accordingly
 */
public class RelationshipManager {

    private Family family;
    private OutputPrinter outputPrinter;
    private RelationExecutorFactory relationshipExecutorFactory;

    public RelationshipManager(final Family family, final RelationExecutorFactory relationshipExecutorFactory, final OutputPrinter outputPrinter) {
        this.family = family;
        this.outputPrinter = outputPrinter;
        this.relationshipExecutorFactory = relationshipExecutorFactory;
    }

    public void addFamilyHead(String name, Gender gender) {
        MemberBasicInfo head = new MemberBasicInfo(name, gender);
        family.addHeadOfFamily(head);
        family.addMember(head.getId(), new MemberImmediateFamilyInfo(head, "", "", ""));
    }

    public void addSpouse(String memberName, String spouseName, Gender spouseGender, Mode mode) {
        MemberImmediateFamilyInfo member = family.getMember(memberName);
        if (Objects.nonNull(member)) {
            MemberBasicInfo spouse = new MemberBasicInfo(spouseName, spouseGender);
            family.addMember(spouse.getId(), new MemberImmediateFamilyInfo(spouse, "", "",  member.getId()));

            member.setPartnerId(spouse.getId());
            family.addMember(member.getId(), member);
        }
        else {
            if (mode == Mode.INPUT) {
                outputPrinter.personNotFound();
            }
        }
    }

    public void addChildThroughMother(String motherName, String childName, Gender childGender, Mode mode) {
        MemberImmediateFamilyInfo mother = family.getMember(motherName);

        if (Objects.nonNull(mother) && mother.getGender() == Gender.FEMALE) {

            MemberBasicInfo childBasicInfo = new MemberBasicInfo(childName, childGender);

            if (mother.getPartnerId() != null && !mother.getPartnerId().isEmpty()) {
                family.addMember(childBasicInfo.getId(), new MemberImmediateFamilyInfo(childBasicInfo, mother.getPartnerId(), mother.getId(), ""));
                family.addChildForMother(mother.getId(), childBasicInfo);

                if (mode == Mode.INPUT) {
                    outputPrinter.childAdditionSucceeded();
                }
            }
            else {
                if (mode == Mode.INPUT) {
                    outputPrinter.childAdditionFailed();
                }
            }
        }
        else if (mother == null && mode == Mode.INPUT) {
            outputPrinter.personNotFound();
        }
        else if (mode == Mode.INPUT) {
            outputPrinter.childAdditionFailed();
        }
    }

    public void getRelatedMembers(String memberName, String strRelationship) {

        Relation[] relationships = Relation.values();
        Relation currentRelationEnum = null;

        for (Relation relationship : relationships) {
            if (strRelationship.equals(relationship.getValue())) {
                currentRelationEnum = relationship;
            }
        }

        if (Objects.isNull(currentRelationEnum)) {
            outputPrinter.invalidRelation();
            return;
        }

        AbstractRelationExecutor relationToExecute = relationshipExecutorFactory.getRelationFromName(currentRelationEnum);
        if (Objects.isNull(relationToExecute)) {
            outputPrinter.invalidRelation();
        }
        else {
            relationToExecute.findRelatedMembers(memberName);
        }
    }
}
