package manager.relations;

import enitity.Family;
import enums.Relation;

public abstract class AbstractRelationExecutor {

    private Relation relationName;
    private Family family;

    public AbstractRelationExecutor(final Relation name, final Family family) {
        this.family = family;
        this.relationName = name;
    }

    public abstract void getRelatedMembers(String memberName);

    public Relation getRelationName() {
        return relationName;
    }

    public Family getFamily() {
        return family;
    }
}
