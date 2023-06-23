package ai.maths.music;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import ai.maths.music.NoteEnums.Accident;
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
        this.notesByModeType = buildNotesByModeType();
    }

    public Map<ModeType, List<Note>> getNotesByModeType() {
        return notesByModeType;
    }

    @Override
    public String toString() {
        return note + " " + chordType + ", " + notesByModeType + scaleByModeType.values().stream()
                .map(Scale::toString).collect(Collectors.joining("\n\t\t", "\n\t\t", ""));
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
                .map(modeType -> Scale.buildScale(note, modeType)
                        .orElseGet(() -> note.findNotesWithInterval(0).stream()
                                .filter(equivalentNote -> equivalentNote != note && equivalentNote.getAccident() != Accident.DOUBLE_FLAT && equivalentNote.getAccident() != Accident.DOUBLE_SHARP)
                                .map(equivalentNote -> Scale.buildScale(equivalentNote, modeType)).findFirst().get().get()))
                .collect(Collectors.toUnmodifiableMap(Scale::getModeType, scale -> scale))));

    }

    private Map<ModeType, List<Note>> buildNotesByModeType() {
        return Collections.unmodifiableNavigableMap(new TreeMap<>(scaleByModeType.values().stream()
                .collect(Collectors.toUnmodifiableMap(Scale::getModeType,
                        scale -> scale.findCorrespondingNotesFromIntervals(chordType.intervals)))));
    }

    public enum ChordType {
        MAJOR(List.of(0, 4, 7)), MAJOR_SIXTH(List.of(0, 4, 7, 9)), DOMINANT_SEVENTH(List.of(0, 4, 7, 10)), MAJOR_SEVENTH(List.of(0, 4, 7, 11)),
        AUGMENTED(List.of(0, 4, 8)), AUGMENTED_SEVENTH(List.of(0, 4, 8, 9)),
        MINOR(List.of(0, 3, 7)), MINOR_SIXTH(List.of(0, 3, 7, 9)), MINOR_SEVENTH(List.of(0, 3, 7, 10)), MINOR_MAJOR_SEVENTH(List.of(0, 3, 7, 11)),
        DIMINISHED(List.of(0, 3, 6)), DIMINISHED_SEVENTH(List.of(0, 3, 6, 9)), HALF_DIMINISHED(List.of(0, 3, 6, 10));

        private List<Integer> intervals;
        private Set<ModeType> modeTypes;

        ChordType(List<Integer> intervals) {
            this.intervals = intervals;
            this.modeTypes = buildScaleTypes();
        }

        private Set<ModeType> buildScaleTypes() {
            return Arrays.stream(ModeType.values())
                    .filter(modeType -> modeType.areIntervalsInTheModeType(intervals))
                    .collect(Collectors.toUnmodifiableSet());

        }
    }
}
