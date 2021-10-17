package manager.relations;

import enitity.Family;
import enums.Relation;


/**
 * Base for a relation and the common part addde her. All relations will inherit these.
 * Inherited relations will then add specific properties in their class
 */
public abstract class AbstractRelationExecutor {

    private Relation relationName;
    private Family family;

    public AbstractRelationExecutor(final Relation name, final Family family) {
        this.family = family;
        this.relationName = name;
    }

    public abstract void findRelatedMembers(String memberName);

    public Relation getRelationName() {
        return relationName;
    }

    public Family getFamily() {
        return family;
    }
}
