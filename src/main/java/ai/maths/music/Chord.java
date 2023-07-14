package ai.maths.music;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import ai.maths.music.NoteEnums.Note;
import ai.maths.music.Scale.ModeType;

public class Chord implements Comparable<Chord> {

    private Note note;
    private ChordType chordType;
    private Map<ModeType, Scale> scaleByModeType;
    private Map<ModeType, List<Note>> notesByModeType;

    public Chord(Note note, ChordType chordType) {
        this.note = note;
        this.chordType = chordType;
        this.scaleByModeType = buildScaleByModeTypes();
        this.notesByModeType = buildNotesByModeTypes();
    }

    public Map<ModeType, List<Note>> getNotesByModeType() {
        return notesByModeType;
    }

    public Map<Scale, Set<Note>> getScalesWithNoteDifferencesFrom(Scale scale) {
        return scale.noteDifferencesFromScaleCollection(scaleByModeType.values());
    }

    public boolean isSameChord(Chord chord) {
        return this.note.isSameNote(chord.note) && this.chordType == chord.chordType;
    }

    @Override
    public int compareTo(Chord otherChord) {
        return Comparator.<Chord, Note>comparing(chord -> chord.note)
                .thenComparing(chord -> chord.chordType)
                .compare(this, otherChord);
    }

    private Map<ModeType, Scale> buildScaleByModeTypes() {
        return Collections.unmodifiableNavigableMap(new TreeMap<>(chordType.modeTypes.stream()
                .map(modeType -> Scale.buildScaleOrEquivalent(note, modeType))
                .collect(Collectors.toUnmodifiableMap(Scale::getModeType, scale -> scale))));

    }

    private Map<ModeType, List<Note>> buildNotesByModeTypes() {
        return Collections.unmodifiableNavigableMap(new TreeMap<>(scaleByModeType.values().stream()
                .collect(Collectors.toUnmodifiableMap(Scale::getModeType,
                        scale -> scale.findCorrespondingNotesFromIntervals(chordType.intervals)))));
    }

    @Override
    public String toString() {
        return note + "" + chordType + ", " + notesByModeType;
    }

    public enum ChordType {
        MAJOR("", List.of(0, 4, 7)), MAJOR_SIXTH("6", List.of(0, 4, 7, 9)), DOMINANT_SEVENTH("7", List.of(0, 4, 7, 10)), MAJOR_SEVENTH("Δ", List.of(0, 4, 7, 11)),
        AUGMENTED("+", List.of(0, 4, 8)), AUGMENTED_SEVENTH("+7", List.of(0, 4, 8, 9)),
        MINOR("m", List.of(0, 3, 7)), MINOR_SIXTH("m6", List.of(0, 3, 7, 9)), MINOR_SEVENTH("m7", List.of(0, 3, 7, 10)), MINOR_MAJOR_SEVENTH("mM7", List.of(0, 3, 7, 11)),
        DIMINISHED("o", List.of(0, 3, 6)), DIMINISHED_SEVENTH("o7", List.of(0, 3, 6, 9)), HALF_DIMINISHED("ø", List.of(0, 3, 6, 10)),
        SUS4("sus4", List.of(0, 5, 7)), SUS4_SEVENTH("sus4(add7)", List.of(0, 5, 7, 10));

        private String alteration;
        private List<Integer> intervals;
        private Set<ModeType> modeTypes;

        ChordType(String alteration, List<Integer> intervals) {
            this.alteration = alteration;
            this.intervals = intervals;
            this.modeTypes = buildScaleTypes();
        }

        private Set<ModeType> buildScaleTypes() {
            return Arrays.stream(ModeType.values())
                    .filter(modeType -> modeType.areIntervalsInTheModeType(intervals))
                    .collect(Collectors.toUnmodifiableSet());
        }

        @Override
        public String toString() {
            return alteration;
        }
    }
}
