package ai.maths.sat3.model;

import java.util.stream.Stream;

public interface Clause<T extends Clause<?>> {

    Stream<T> getSubClauses();
}
