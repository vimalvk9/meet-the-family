package manager.relations;

import enitity.Family;
import enums.Relation;
import util.OutputPrinter;

import java.util.HashMap;
import java.util.Map;

public class RelationExecutorFactory {

    private Family family;
    private Map<String, AbstractRelationExecutor> relationMap = new HashMap<>();

    public RelationExecutorFactory(final Family family, final OutputPrinter outputPrinter) {
        relationMap.put(Relation.SON.name(), new SonRelationExecutor(family, outputPrinter));
        relationMap.put(Relation.DAUGHTER.name(), new DaughterRelationExecutor(family, outputPrinter));
        relationMap.put(Relation.SIBLINGS.name(), new SiblingsRelationExecutor(family, outputPrinter));
        relationMap.put(Relation.SISTER_IN_LAW.name(), new SisterInLawRelationExecutor(family, outputPrinter));
        relationMap.put(Relation.BROTHER_IN_LAW.name(), new BrotherInLawRelationExecutor(family, outputPrinter));
        relationMap.put(Relation.MATERNAL_UNCLE.name(), new MaternalUncleRelationExecutor(family, outputPrinter));
        relationMap.put(Relation.PATERNAL_UNCLE.name(), new PaternalUncleRelationExecutor(family, outputPrinter));
        relationMap.put(Relation.MATERNAL_AUNT.name(), new MaternalAuntRelationExecutor(family, outputPrinter));
        relationMap.put(Relation.PATERNAL_AUNT.name(), new PaternalAuntRelationExecutor(family, outputPrinter));
    }

    public AbstractRelationExecutor getRelationFromName(Relation relation) {
        return relationMap.get(relation.name());
    }
}
