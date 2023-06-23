package ai.maths.music;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import ai.maths.music.Scale.ModeType;

public class EquivalentChords {

    public static final Map<Scale, Set<Chord>> ALL_CHORDS_OF_SCALE =
            Collections.unmodifiableNavigableMap(new TreeMap<>(NoteEnums.SCALE_NOTES.stream()
                    .flatMap(note -> Arrays.stream(ModeType.values())
                            .map(modeType -> Scale.buildScale(note, modeType))
                            .filter(Optional::isPresent)
                            .map(Optional::get))
                    .collect(Collectors.toMap(scale -> scale, Scale::getChords))));

    public static void main(String[] args) {
        printAllChordsOfScales();
    }

    private static void printAllChordsOfScales() {
        System.out.println(ALL_CHORDS_OF_SCALE.entrySet().stream()
                .map(chordSetEntry -> chordSetEntry.getKey() + " " + chordSetEntry.getValue().stream()
                        .map(Chord::toString)
                        .collect(Collectors.joining("\n\t", "\n\t", "\n")))
                .collect(Collectors.joining("\n")));
    }
}
