package manager;

import enitity.Family;
import enitity.MemberBasicInfo;
import enitity.MemberImmediateFamilyInfo;
import enums.Gender;
import enums.Relation;
import manager.relations.AbstractRelationExecutor;
import manager.relations.RelationExecutorFactory;
import util.Constants;
import util.OutputPrinter;

import java.util.*;

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

    public void addSpouse(String memberName, String spouseName, Gender spouseGender) {
        family.addSpouse(memberName, spouseName, spouseGender);
    }

    public void addChildThroughMother(String motherName, String childName, Gender childGender) {
        MemberImmediateFamilyInfo mother = family.getMember(motherName);

        if (Objects.nonNull(mother)) {

            MemberBasicInfo childBasicInfo = new MemberBasicInfo(childName, childGender);

            if (mother.getPartnerId() != null && !mother.getPartnerId().isEmpty()) {
                family.addMember(childBasicInfo.getId(), new MemberImmediateFamilyInfo(childBasicInfo, mother.getPartnerId(), mother.getId(), null));
                family.addChildForMother(mother.getId(), childBasicInfo);
                outputPrinter.childAdditionSucceeded();
            }
            else {
                outputPrinter.childAdditionFailed();
            }
        }
        else {
            outputPrinter.personNotFound();
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
            relationToExecute.getRelatedMembers(memberName);
        }
    }
}