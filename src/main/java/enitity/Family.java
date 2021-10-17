package enitity;


import enums.Gender;

import java.util.*;


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
    public void addChildForMother(String motherId, MemberBasicInfo child) {
        List<MemberBasicInfo> children = this.getMotherChildrenMap().get(motherId);
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
        this.motherChildrenMap.put(motherId, children);
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
}
