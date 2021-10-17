package enums;
/**
 * All valid relations added here along with mapping value in input file / commands
 */
public enum Relation {

    SON("Son"),
    DAUGHTER("Daughter"),
    SIBLINGS("Siblings"),
    PATERNAL_UNCLE("Paternal-Uncle"),
    MATERNAL_UNCLE("Maternal-Uncle"),
    PATERNAL_AUNT("Paternal-Aunt"),
    MATERNAL_AUNT("Maternal-Aunt"),
    SISTER_IN_LAW("Sister-In-Law"),
    BROTHER_IN_LAW("Brother-In-Law");

    private String value;
    Relation(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
