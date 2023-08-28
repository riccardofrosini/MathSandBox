package ai.maths.sat3.model;

import java.util.Set;

public interface Clause {

    Set<VariableOrBoolean> getAllVariablesAndConstants();
}
