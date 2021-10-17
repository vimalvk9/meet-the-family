package enitity;


import enums.Gender;

import java.util.*;

/**
 * Structure of family consists of headOfFamily, a map of membername vs his immediate family info
 * and a map of mother and all their children
 */
public class Family {

    private MemberBasicInfo headOfFamily;
    private Map<String, MemberImmediateFamilyInfo> memberInfoMap = new HashMap<>();
    private Map<String, List<MemberBasicInfo>> motherChildrenMap = new HashMap<>();

    private Map<String, MemberImmediateFamilyInfo> getMemberInfoMap() {
        return memberInfoMap;
    }

    private Map<String, List<MemberBasicInfo>> getMotherChildrenMap() {
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
        this.getMotherChildrenMap().put(motherId, children);
    }

    public void addHeadOfFamily(MemberBasicInfo head) {
        this.headOfFamily = head;
    }
}
