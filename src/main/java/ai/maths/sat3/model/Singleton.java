package ai.maths.sat3.model;

public interface Singleton extends Clause<Variable>, ThreeDisjunctOfSingletonsOrSingleton<Variable>, ThreeConjunctOfSingletonsOrSingleton<Variable> {

}
