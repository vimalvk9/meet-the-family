package enitity;

import enums.Gender;

import java.util.Objects;

/**
 * Stores basic info for a family member. `id` is the same as `name` since its unique.
 * If names are not unique, can easily modify `id` by appending any unique parameter like
 * name + delimiter + unique param => vimal_kumarvimalvk9@gmail.com
 *                                    [name][delimiter][email]
 */
public class MemberBasicInfo {

    private final String id; // same as name since its unique
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
