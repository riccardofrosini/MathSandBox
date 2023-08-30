package ai.maths.sat3.model;

public class ThreeSatDisjunctClause extends DisjunctOfSingletons {

    public ThreeSatDisjunctClause(SingletonClause<?> singletonClause1, SingletonClause<?> singletonClause2, SingletonClause<?> singletonClause3) {
        super(singletonClause1, singletonClause2, singletonClause3);
    }
}
