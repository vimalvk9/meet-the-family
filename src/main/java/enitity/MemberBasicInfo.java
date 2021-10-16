package enitity;

import enums.Gender;

import java.util.Objects;


public class MemberBasicInfo {

    private final String id;
    private final Gender gender;

    public MemberBasicInfo(String name, Gender gender) {
        this.id = name;
        this.gender = gender;
    }

    public MemberBasicInfo(MemberBasicInfo memberBasicInfo) {
        this.id = memberBasicInfo.getId();
        this.gender = memberBasicInfo.getGender();
    }

    public String getId() {
        return id;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MemberBasicInfo{" +
                "id='" + id + '\'' +
                ", gender=" + gender +
                '}';
    }
}
