package ai.maths.sat3.model;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class SingletonVariable implements SingletonVariableOrDisjunctsOfNonConstants, SingletonVariableOrConjunctsOfNonConstants {

    public abstract Variable getVariable();

    @Override
    public DisjunctsConjunctsOfNonConstantAndSingletons simplify() {
        return this;
    }

    @Override
    public DisjunctsConjunctsOfNonConstantAndSingletons addConjunct(DisjunctsConjunctsOfNonConstantAndSingletons conjunct) {
        return new ConjunctClause<>(Stream.of(this, conjunct).collect(Collectors.toSet())).simplify();
    }

}
