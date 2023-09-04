package ai.maths.sat3.model;

public interface SingletonOrDisjunctsConjunctsOfNonConstant extends Clause {

    SingletonOrDisjunctsConjunctsOfNonConstant addConjunct(SingletonOrDisjunctsConjunctsOfNonConstant clause);
}
