package enitity;


import enums.Gender;
import enums.Relationship;
import util.Constants;

import java.util.*;
import java.util.stream.Collectors;

public class Family {

    private MemberBasicInfo headOfFamily;
    private Map<String, MemberImmediateFamilyInfo> memberInfoMap = new HashMap<>();
    private Map<String, List<MemberBasicInfo>> motherChildrenMap = new HashMap<>();

    public Family() {}

    public MemberBasicInfo getHeadOfFamily() {
        return new MemberBasicInfo(headOfFamily);
    }

    public Map<String, MemberImmediateFamilyInfo> getMemberInfoMap() {
        return memberInfoMap;
    }

    public Map<String, List<MemberBasicInfo>> getMotherChildrenMap() {
        return motherChildrenMap;
    }

    public MemberImmediateFamilyInfo getMember(String memberId) {
        return this.getMemberInfoMap().get(memberId);
    }

    public List<MemberBasicInfo> getChildren(String motherId) {
        return this.getMotherChildrenMap().get(motherId);
    }

    public void addMember(String memberId, MemberImmediateFamilyInfo info) {
        this.getMemberInfoMap().put(memberId, info);
    }

    public void addChildForMother(String motherName, String childName, Gender childGender) {

        MemberImmediateFamilyInfo mother = getMember(motherName);
        if (Objects.nonNull(mother)) {

            List<MemberBasicInfo> children = this.getMotherChildrenMap().get(mother.getId());
            MemberBasicInfo childBasicInfo = new MemberBasicInfo(childName, childGender);

            if (children == null) {
                children = new ArrayList<>();
            }
            if (mother.getPartnerId() != null && !mother.getPartnerId().isEmpty() && !children.contains(childBasicInfo)) {
                addMember(childBasicInfo.getId(), new MemberImmediateFamilyInfo(childBasicInfo, mother.getPartnerId(), mother.getId(), null));
                children.add(childBasicInfo);
                this.motherChildrenMap.put(mother.getId(), children);
                System.out.println(Constants.CHILD_ADDITION_SUCCEEDED);
            }
            else {
                System.out.println(Constants.CHILD_ADDITION_FAILED);
            }
        }
        else {
            System.out.println(Constants.PERSON_NOT_FOUND);
        }
    }

    public void addHeadOfFamily(MemberBasicInfo head) {
        this.headOfFamily = head;
    }

    public void addSpouse(String memberName, String spouseName, Gender spouseGender) {
        MemberImmediateFamilyInfo member = getMember(memberName);
        if (Objects.nonNull(member)) {
            MemberBasicInfo spouse = new MemberBasicInfo(spouseName, spouseGender);
            addMember(spouse.getId(), new MemberImmediateFamilyInfo(spouse, null, null,  member.getId()));

            member.setPartnerId(spouse.getId());
            addMember(member.getId(), member);
        }
    }

    public void getRelationship(String memberName,  Relationship relationship) {

        MemberImmediateFamilyInfo member = getMember(memberName);
        if (Objects.isNull(member)) {
            System.out.println(Constants.PERSON_NOT_FOUND);
            return;
        }

        switch (relationship) {
            case SON: {
                Gender gender = member.getGender();
                String motherId = null;

                if (gender == Gender.MALE) {
                    motherId = member.getPartnerId();
                } else {
                    motherId = member.getId();
                }

                if (motherId == null) {
                    System.out.println(Constants.PERSON_NOT_FOUND);
                } else {
                    List<MemberBasicInfo> children = getChildren(motherId);
                    List<MemberBasicInfo> sons = Optional.ofNullable(children)
                            .orElseGet(ArrayList::new)
                            .stream().filter(s -> s.getGender() == Gender.MALE)
                            .collect(Collectors.toList());

                    if (sons.isEmpty()) {
                        System.out.println(Constants.NONE);
                    } else {
                        for (MemberBasicInfo info : sons) {
                            System.out.print(info.getId() + " ");
                        }
                        System.out.println();
                    }
                }
            }
            break;
            case DAUGHTER: {
                Gender gender = member.getGender();
                String motherId = null;

                if (gender == Gender.MALE) {
                    motherId = member.getPartnerId();
                } else {
                    motherId = member.getId();
                }

                if (motherId == null) {
                    System.out.println(Constants.NONE);
                } else {
                    List<MemberBasicInfo> children = getChildren(motherId);
                    List<MemberBasicInfo> daughters = Optional.ofNullable(children)
                            .orElseGet(ArrayList::new)
                            .stream().filter(s -> s.getGender() == Gender.FEMALE)
                            .collect(Collectors.toList());

                    if (daughters.isEmpty()) {
                        System.out.println(Constants.NONE);
                    } else {
                        for (MemberBasicInfo info : daughters) {
                            System.out.print(info.getId() + " ");
                        }
                        System.out.println();
                    }
                }
            }
            break;
            case SIBLINGS: {

                String motherId = member.getMotherId();
                List<MemberBasicInfo> children = getChildren(motherId);
                List<MemberBasicInfo> siblings = Optional.ofNullable(children)
                        .orElseGet(ArrayList::new)
                        .stream()
                        .filter(o -> !o.getId().equals(member.getId()))
                        .collect(Collectors.toList());

                if (siblings.isEmpty()) {
                    System.out.println(Constants.NONE);
                }
                else {
                    for (MemberBasicInfo info : siblings) {
                        System.out.print(info.getId() + " ");
                    }
                    System.out.println();
                }
            }
                break;

            case MATERNAL_AUNT: {
                String motherId = member.getMotherId();
                MemberImmediateFamilyInfo mother = getMember(motherId);
                if (mother == null) {
                    System.out.println(Constants.NONE);
                    return;
                }
                MemberImmediateFamilyInfo grandMother = getMember(mother.getMotherId());
                if (grandMother != null) {
                    List<MemberBasicInfo> aunts = Optional.ofNullable(getChildren(grandMother.getId()))
                            .orElseGet(ArrayList::new)
                            .stream()
                            .filter(o -> o.getGender() == Gender.FEMALE & !o.getId().equals(motherId))
                            .collect(Collectors.toList());

                    if (aunts.size() > 0) {
                        for (MemberBasicInfo info : aunts) {
                            System.out.print(info.getId() + " ");
                        }
                        System.out.println();
                    }
                    else {
                        System.out.println(Constants.NONE);
                        return;
                    }
                } else {
                    System.out.println(Constants.NONE);
                    return;
                }
            }
                break;
            case PATERNAL_AUNT: {

                String fatherId = member.getFatherId();
                MemberImmediateFamilyInfo father = getMember(fatherId);
                if (father == null) {
                    System.out.println(Constants.NONE);
                    return;
                }
                MemberImmediateFamilyInfo grandMother = getMember(father.getMotherId());
                if (grandMother != null) {
                    List<MemberBasicInfo> aunts = Optional.ofNullable(getChildren(grandMother.getId()))
                            .orElseGet(ArrayList::new)
                            .stream()
                            .filter(o -> o.getGender() == Gender.FEMALE)
                            .collect(Collectors.toList());

                    if (aunts.size() > 0) {
                        for (MemberBasicInfo info : aunts) {
                            System.out.print(info.getId() + " ");
                        }
                        System.out.println();
                    }
                    else {
                        System.out.println(Constants.NONE);
                        return;
                    }
                } else {
                    System.out.println(Constants.NONE);
                    return;
                }
            }
                break;
            case SISTER_IN_LAW: {
                String partnerId = member.getPartnerId();
                MemberImmediateFamilyInfo partner = getMember(partnerId);
                if (partner == null) {
                    System.out.println(Constants.NONE);
                    return;
                } else {

                    if (partner.getMotherId() == null || partner.getMotherId().isEmpty()) {
                        System.out.println(Constants.NONE);
                        return;
                    }
                    MemberImmediateFamilyInfo partnerMother = getMember(partner.getMotherId());
                    List<MemberBasicInfo> sisterInLaws = Optional.ofNullable(getChildren(partnerMother.getId()))
                            .orElseGet(ArrayList::new)
                            .stream()
                            .filter(o -> o.getGender() == Gender.FEMALE && !o.getId().equals(partnerId))
                            .collect(Collectors.toList());

                    if (sisterInLaws.isEmpty()) {
                        System.out.println(Constants.NONE);
                        return;
                    } else {
                        for (MemberBasicInfo info : sisterInLaws) {
                            System.out.print(info.getId() + " ");
                        }
                        System.out.println();
                    }
                }
            }
                break;
            case BROTHER_IN_LAW: {

                String partnerId = member.getPartnerId();
                MemberImmediateFamilyInfo partner = getMember(partnerId);
                if (partner == null) {
                    System.out.println(Constants.NONE);
                    return;
                } else {

                    if (partner.getMotherId() == null || partner.getMotherId().isEmpty()) {
                        System.out.println(Constants.NONE);
                        return;
                    }
                    MemberImmediateFamilyInfo partnerMother = getMember(partner.getMotherId());
                    List<MemberBasicInfo> brotherInLaws = Optional.ofNullable(getChildren(partnerMother.getId()))
                            .orElseGet(ArrayList::new)
                            .stream()
                            .filter(o -> o.getGender() == Gender.MALE && !o.getId().equals(partnerId))
                            .collect(Collectors.toList());

                    if (brotherInLaws.isEmpty()) {
                        System.out.println(Constants.NONE);
                        return;
                    } else {
                        for (MemberBasicInfo info : brotherInLaws) {
                            System.out.print(info.getId() + " ");
                        }
                        System.out.println();
                    }
                }
            }
                break;

            case MATERNAL_UNCLE: {

                String motherId = member.getMotherId();
                MemberImmediateFamilyInfo mother = getMember(motherId);
                if (mother == null) {
                    System.out.println(Constants.NONE);
                    return;
                }
                MemberImmediateFamilyInfo grandMother = getMember(mother.getMotherId());
                if (grandMother != null) {
                    List<MemberBasicInfo> uncles = Optional.ofNullable(getChildren(grandMother.getId()))
                            .orElseGet(ArrayList::new)
                            .stream()
                            .filter(o -> (o.getGender() == Gender.MALE))
                            .collect(Collectors.toList());

                    if (uncles.size() > 0) {
                        for (MemberBasicInfo info : uncles) {
                            System.out.print(info.getId() + " ");
                        }
                        System.out.println();
                    }
                    else {
                        System.out.println(Constants.NONE);
                        return;
                    }
                }
                else {
                    System.out.println(Constants.NONE);
                    return;
                }
            }
             break;
            case PATERNAL_UNCLE:
                String fatherId = member.getFatherId();
                MemberImmediateFamilyInfo father = getMember(fatherId);
                if (father == null) {
                    System.out.println(Constants.NONE);
                    return;
                }
                MemberImmediateFamilyInfo grandMother = getMember(father.getMotherId());
                if (grandMother != null) {
                    List<MemberBasicInfo> uncles = Optional.ofNullable(getChildren(grandMother.getId()))
                            .orElseGet(ArrayList::new)
                            .stream()
                            .filter(o -> (o.getGender() == Gender.MALE & !o.getId().equals(fatherId)))
                            .collect(Collectors.toList());

                    if (uncles.size() > 0) {
                        for (MemberBasicInfo info : uncles) {
                            System.out.print(info.getId() + " ");
                        }
                        System.out.println();
                    }
                    else {
                        System.out.println(Constants.NONE);
                        return;
                    }
                }
                else {
                    System.out.println(Constants.NONE);
                }
                break;
            default:

        }
    }

}
