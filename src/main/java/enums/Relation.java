package enums;

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
    Relation(String action) {
        this.value = action;
    }

    public String getValue() {
        return this.value;
    }
}
