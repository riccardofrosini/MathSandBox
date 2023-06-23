package ai.maths.music;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import ai.maths.music.NoteEnums.Note;
import ai.maths.music.Scale.ModeType;

public class EquivalentChords {

    public static final Map<Scale, Map<Chord, Map<Scale, Set<Note>>>> ALL_CHORDS_OF_SCALE =
            Collections.unmodifiableNavigableMap(new TreeMap<>(NoteEnums.SCALE_NOTES.stream()
                    .flatMap(note -> Arrays.stream(ModeType.values())
                            .map(modeType -> Scale.buildScaleOrEquivalent(note, modeType))
                            .distinct())
                    .collect(Collectors.toMap(scale -> scale, Scale::getChordsWithNotesDifference))));

    public static void main(String[] args) {
        printAllChordsOfScales();
        printChordsOfScale(Scale.buildScaleOrEquivalent(Note.EFlat, ModeType.IONIAN));
    }

    private static void printAllChordsOfScales() {
        for (Scale scale : ALL_CHORDS_OF_SCALE.keySet()) {
            printChordsOfScale(scale);
        }
    }

    private static void printChordsOfScale(Scale scale) {
        System.out.println(scale + " " + ALL_CHORDS_OF_SCALE.get(scale).entrySet().stream()
                .map(chordMapEntry -> chordMapEntry.getKey().toString() + setOfScalesAsString(chordMapEntry.getValue()))
                .collect(Collectors.joining("\n\t", "\n\t", "\n")));
    }

    private static String setOfScalesAsString(Map<Scale, Set<Note>> scales) {
        return scales.entrySet().stream()
                .map(scaleSetEntry -> scaleSetEntry.getKey() + "\n\t\t\t\tNotes difference from original scale: " + scaleSetEntry.getValue())
                .collect(Collectors.joining("\n\t\t", "\n\t\t", ""));
    }
}
