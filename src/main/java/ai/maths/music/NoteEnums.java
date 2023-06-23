package ai.maths.music;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

interface NoteEnums {

    List<Note> SCALE_NOTES = List.of(Note.A, Note.ASharp, Note.BFlat, Note.B, Note.CFlat, Note.BSharp,
            Note.C, Note.CSharp, Note.DFlat, Note.D, Note.DSharp, Note.EFlat, Note.E, Note.FFlat, Note.ESharp,
            Note.F, Note.FSharp, Note.GFlat, Note.G, Note.GSharp, Note.AFlat);

    static boolean areEquivalentNotes(Collection<Note> notes1, Collection<Note> notes2) {
        return notes1.stream().allMatch(note1 -> notes2.stream().anyMatch(note1::isSameNote));
    }

    enum Note {
        A("A", NaturalNote.A, Accident.NATURAL), ASharp("A#", NaturalNote.A, Accident.SHARP), BFlat("Bb", NaturalNote.B, Accident.FLAT),
        B("B", NaturalNote.B, Accident.NATURAL), CFlat("Cb", NaturalNote.C, Accident.FLAT), BSharp("B#", NaturalNote.B, Accident.SHARP),
        C("C", NaturalNote.C, Accident.NATURAL), CSharp("C#", NaturalNote.C, Accident.SHARP), DFlat("Db", NaturalNote.D, Accident.FLAT),
        D("D", NaturalNote.D, Accident.NATURAL), DSharp("D#", NaturalNote.D, Accident.SHARP), EFlat("Eb", NaturalNote.E, Accident.FLAT),
        E("E", NaturalNote.E, Accident.NATURAL), FFlat("Fb", NaturalNote.F, Accident.FLAT), ESharp("E#", NaturalNote.E, Accident.SHARP),
        F("F", NaturalNote.F, Accident.NATURAL), FSharp("F#", NaturalNote.F, Accident.SHARP), GFlat("Gb", NaturalNote.G, Accident.FLAT),
        G("G", NaturalNote.G, Accident.NATURAL), GSharp("G#", NaturalNote.G, Accident.SHARP), AFlat("Ab", NaturalNote.A, Accident.FLAT),

        ADoubleSharp("Abb", NaturalNote.A, Accident.DOUBLE_SHARP), ADoubleFlat("A##", NaturalNote.A, Accident.DOUBLE_FLAT),
        BDoubleSharp("Bbb", NaturalNote.B, Accident.DOUBLE_SHARP), BDoubleFlat("B##", NaturalNote.B, Accident.DOUBLE_FLAT),
        CDoubleSharp("Cbb", NaturalNote.C, Accident.DOUBLE_SHARP), CDoubleFlat("C##", NaturalNote.C, Accident.DOUBLE_FLAT),
        DDoubleSharp("Dbb", NaturalNote.D, Accident.DOUBLE_SHARP), DDoubleFlat("D##", NaturalNote.D, Accident.DOUBLE_FLAT),
        EDoubleSharp("Ebb", NaturalNote.E, Accident.DOUBLE_SHARP), EDoubleFlat("E##", NaturalNote.E, Accident.DOUBLE_FLAT),
        FDoubleSharp("Fbb", NaturalNote.F, Accident.DOUBLE_SHARP), FDoubleFlat("F##", NaturalNote.F, Accident.DOUBLE_FLAT),
        GDoubleSharp("Gbb", NaturalNote.G, Accident.DOUBLE_SHARP), GDoubleFlat("G##", NaturalNote.G, Accident.DOUBLE_FLAT);

        private String note;
        private NaturalNote naturalNote;
        private Accident accident;
        private int alterationFromC;

        private static final Map<Note, Map<Integer, Set<Note>>> ALL_INTERVALS = buildAllSemitoneIntervals();

        Note(String note, NaturalNote naturalNote, Accident accident) {
            this.note = note;
            this.naturalNote = naturalNote;
            this.accident = accident;
            this.alterationFromC = (naturalNote.intervalFromC + accident.interval + 12) % 12;
        }

        public NaturalNote getNaturalNote() {
            return naturalNote;
        }

        public Accident getAccident() {
            return accident;
        }

        private static Map<Note, Map<Integer, Set<Note>>> buildAllSemitoneIntervals() {
            return Arrays.stream(values()).collect(Collectors.toUnmodifiableMap(note -> note, note ->
                    Collections.unmodifiableMap(Arrays.stream(values())
                            .collect(Collectors.groupingBy(note::findNoteInterval, Collectors.toUnmodifiableSet())))));
        }

        public Set<Note> findNotesWithInterval(int interval) {
            return ALL_INTERVALS.get(this).get(interval);
        }

        public int findNoteInterval(Note note) {
            return (note.alterationFromC - alterationFromC + 12) % 12;
        }

        public String toString() {
            return note;
        }

        public boolean isSameNote(Note note) {
            return this.alterationFromC == note.alterationFromC;
        }
    }

    enum Accident {
        FLAT(-1), NATURAL(0), SHARP(1), DOUBLE_FLAT(-2), DOUBLE_SHARP(2);
        private int interval;

        Accident(int interval) {
            this.interval = interval;
        }
    }

    enum NaturalNote {
        A(9), B(11), C(0), D(2), E(4), F(5), G(7);

        private int intervalFromC;

        NaturalNote(int intervalFromC) {
            this.intervalFromC = intervalFromC;
        }

        public NaturalNote next() {
            switch (this) {
                case A:
                    return B;
                case B:
                    return C;
                case C:
                    return D;
                case D:
                    return E;
                case E:
                    return F;
                case F:
                    return G;
                case G:
            }
            return A;
        }
    }
}