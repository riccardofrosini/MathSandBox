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
        A(NaturalNote.A, Accident.NATURAL), ASharp(NaturalNote.A, Accident.SHARP), BFlat(NaturalNote.B, Accident.FLAT),
        B(NaturalNote.B, Accident.NATURAL), CFlat(NaturalNote.C, Accident.FLAT), BSharp(NaturalNote.B, Accident.SHARP),
        C(NaturalNote.C, Accident.NATURAL), CSharp(NaturalNote.C, Accident.SHARP), DFlat(NaturalNote.D, Accident.FLAT),
        D(NaturalNote.D, Accident.NATURAL), DSharp(NaturalNote.D, Accident.SHARP), EFlat(NaturalNote.E, Accident.FLAT),
        E(NaturalNote.E, Accident.NATURAL), FFlat(NaturalNote.F, Accident.FLAT), ESharp(NaturalNote.E, Accident.SHARP),
        F(NaturalNote.F, Accident.NATURAL), FSharp(NaturalNote.F, Accident.SHARP), GFlat(NaturalNote.G, Accident.FLAT),
        G(NaturalNote.G, Accident.NATURAL), GSharp(NaturalNote.G, Accident.SHARP), AFlat(NaturalNote.A, Accident.FLAT),

        ADoubleSharp(NaturalNote.A, Accident.DOUBLE_SHARP), ADoubleFlat(NaturalNote.A, Accident.DOUBLE_FLAT),
        BDoubleSharp(NaturalNote.B, Accident.DOUBLE_SHARP), BDoubleFlat(NaturalNote.B, Accident.DOUBLE_FLAT),
        CDoubleSharp(NaturalNote.C, Accident.DOUBLE_SHARP), CDoubleFlat(NaturalNote.C, Accident.DOUBLE_FLAT),
        DDoubleSharp(NaturalNote.D, Accident.DOUBLE_SHARP), DDoubleFlat(NaturalNote.D, Accident.DOUBLE_FLAT),
        EDoubleSharp(NaturalNote.E, Accident.DOUBLE_SHARP), EDoubleFlat(NaturalNote.E, Accident.DOUBLE_FLAT),
        FDoubleSharp(NaturalNote.F, Accident.DOUBLE_SHARP), FDoubleFlat(NaturalNote.F, Accident.DOUBLE_FLAT),
        GDoubleSharp(NaturalNote.G, Accident.DOUBLE_SHARP), GDoubleFlat(NaturalNote.G, Accident.DOUBLE_FLAT);


        private NaturalNote naturalNote;
        private Accident accident;
        private int alterationFromC;

        private static final Map<Note, Map<Integer, Set<Note>>> ALL_INTERVALS = buildAllSemitoneIntervals();

        Note(NaturalNote naturalNote, Accident accident) {
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
            switch (this) {
                case A:
                    return "A";
                case ASharp:
                    return "A#";
                case BFlat:
                    return "Bb";
                case B:
                    return "B";
                case CFlat:
                    return "Cb";
                case BSharp:
                    return "B#";
                case C:
                    return "C";
                case CSharp:
                    return "C#";
                case DFlat:
                    return "Db";
                case D:
                    return "D";
                case DSharp:
                    return "D#";
                case EFlat:
                    return "Eb";
                case E:
                    return "E";
                case FFlat:
                    return "Fb";
                case ESharp:
                    return "E#";
                case F:
                    return "F";
                case FSharp:
                    return "F#";
                case GFlat:
                    return "Gb";
                case G:
                    return "G";
                case GSharp:
                    return "G#";
                case AFlat:
                    return "Ab";
                case ADoubleSharp:
                    return "A##";
                case BDoubleSharp:
                    return "B##";
                case CDoubleSharp:
                    return "C##";
                case DDoubleSharp:
                    return "D##";
                case EDoubleSharp:
                    return "E##";
                case FDoubleSharp:
                    return "F##";
                case GDoubleSharp:
                    return "G##";
                case ADoubleFlat:
                    return "Abb";
                case BDoubleFlat:
                    return "Bbb";
                case CDoubleFlat:
                    return "Cbb";
                case DDoubleFlat:
                    return "Dbb";
                case EDoubleFlat:
                    return "Ebb";
                case FDoubleFlat:
                    return "Fbb";
                case GDoubleFlat:
                    return "Gbb";
            }
            return null;
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