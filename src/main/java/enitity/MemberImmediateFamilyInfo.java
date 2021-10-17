package enitity;

/**
 * Stores immediate family info for a family member
 */
public class MemberImmediateFamilyInfo extends MemberBasicInfo {

    private String fatherId;
    private String motherId;
    private String partnerId;

    public MemberImmediateFamilyInfo(MemberBasicInfo memberBasicInfo, String fatherId, String motherId, String partnerId) {
        super(memberBasicInfo);
        this.fatherId = fatherId;
        this.motherId = motherId;
        this.partnerId = partnerId;
    }

    public String getFatherId() {
        return fatherId;
    }

    public String getMotherId() {
        return motherId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    @Override
    public String toString() {
        return "MemberImmediateFamilyInfo{" +
                "fatherId='" + fatherId + '\'' +
                ", motherId='" + motherId + '\'' +
                ", partnerId='" + partnerId + '\'' +
                '}';
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
}
