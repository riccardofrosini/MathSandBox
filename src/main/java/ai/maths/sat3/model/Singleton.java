package ai.maths.sat3.model;

public interface Singleton extends Clause<Variable>, DisjunctOfSingletonsOrSingleton<Variable>, ConjunctOfSingletonsOrSingleton<Variable> {

}
