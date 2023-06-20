package ai.maths.music;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.music.NoteEnums.Note;
import ai.maths.music.Scale.ModeType;

public class Chord {

    private Note note;
    private ChordType chordType;
    private Map<ModeType, List<Note>> notesByScaleType;

    public Chord(Note note, ChordType chordType) {
        this.note = note;
        this.chordType = chordType;
        this.notesByScaleType = buildNotesByScaleType();
    }

    private Map<ModeType, List<Note>> buildNotesByScaleType() {
        return chordType.modeTypes.stream()
                .collect(Collectors.toMap(modeType -> modeType,
                        modeType -> new Scale(note, modeType).findCorrespondingNotesFromIntervals(chordType.intervals)));
    }

    @Override
    public String toString() {
        return note + " " + chordType + ", " + notesByScaleType;
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
            return Arrays.stream(ModeType.values()).filter(modeType -> modeType.areIntervalsInTheScale(intervals)).collect(Collectors.toSet());

        }
    }


    public static void main(String[] args) {
        Map<Chord, Map<Note, Set<Scale>>> chordScales = new HashMap<>();
        for (Note note : Note.SCALE_NOTES) {
            for (ChordType chordType : ChordType.values()) {
                Chord chord = new Chord(note, chordType);
                Map<Note, Set<Scale>> equivalentScales = Note.SCALE_NOTES.stream()
                        .map(noteScale -> Arrays.stream(ModeType.values()).map(modeType -> new Scale(noteScale, modeType))
                                .filter(scale -> chord.notesByScaleType.containsKey(scale.getModeType()) &&
                                        scale.containsEquivalentNotes(chord.notesByScaleType.get(scale.getModeType())))
                                .collect(Collectors.toSet())).filter(scales -> !scales.isEmpty())
                        .collect(Collectors.toMap(scales -> scales.iterator().next().getScaleNote(), scales -> scales));
                chordScales.put(chord, equivalentScales);
            }
        }
        System.out.println(chordScales.entrySet().stream().map((chordEntry) -> chordEntry.getKey() + "\n" +
                chordEntry.getValue().entrySet().stream()
                        .map(scalesEntry -> scalesEntry.getKey() + scalesEntry.getValue().stream()
                                .map(Scale::toString)
                                .collect(Collectors.joining("\n\t\t\t\t", "\n\t\t\t\t", "")))
                        .collect(Collectors.joining("\n\t\t", "\t\t", ""))).collect(Collectors.joining("\n")));
    }
}
