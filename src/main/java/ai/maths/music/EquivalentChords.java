package ai.maths.music;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import ai.maths.music.Scale.ModeType;

public class EquivalentChords {

    public static final Map<Scale, Set<Chord>> ALL_CHORDS_OF_SCALE =
            Collections.unmodifiableNavigableMap(new TreeMap<>(NoteEnums.SCALE_NOTES.stream()
                    .flatMap(note -> Arrays.stream(ModeType.values())
                            .map(modeType -> Scale.buildScaleOrEquivalent(note, modeType))
                            .distinct())
                    .collect(Collectors.toMap(scale -> scale, Scale::getChords))));

    public static void main(String[] args) {
        printAllChordsOfScales();
    }

    private static void printAllChordsOfScales() {
        for (Scale scale : ALL_CHORDS_OF_SCALE.keySet()) {
            printChordsOfScale(scale);
        }
    }

    private static void printChordsOfScale(Scale scale) {
        System.out.println(scale + " " + ALL_CHORDS_OF_SCALE.get(scale).stream()
                .map(Chord::toString)
                .collect(Collectors.joining("\n\t", "\n\t", "\n")));

    }
}
