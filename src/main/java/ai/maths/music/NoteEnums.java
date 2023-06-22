package ai.maths.music;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class NoteEnums {

    public enum Note {
        A(NaturalNote.A, Accident.NATURAL), ASharp(NaturalNote.A, Accident.SHARP), BFlat(NaturalNote.B, Accident.FLAT),
        B(NaturalNote.B, Accident.NATURAL), CFlat(NaturalNote.C, Accident.FLAT), BSharp(NaturalNote.B, Accident.SHARP),
        C(NaturalNote.C, Accident.NATURAL), CSharp(NaturalNote.C, Accident.SHARP), DFlat(NaturalNote.D, Accident.FLAT),
        D(NaturalNote.D, Accident.NATURAL), DSharp(NaturalNote.D, Accident.SHARP), EFlat(NaturalNote.E, Accident.FLAT),
        E(NaturalNote.E, Accident.NATURAL), FFlat(NaturalNote.F, Accident.FLAT), ESharp(NaturalNote.E, Accident.SHARP),
        F(NaturalNote.F, Accident.NATURAL), FSharp(NaturalNote.F, Accident.SHARP), GFlat(NaturalNote.G, Accident.FLAT),
        G(NaturalNote.G, Accident.NATURAL), GSharp(NaturalNote.G, Accident.SHARP), AFlat(NaturalNote.A, Accident.FLAT),

        ADoubleSharp(NaturalNote.A, Accident.DOUBLE_SHARP),
        BDoubleSharp(NaturalNote.B, Accident.DOUBLE_SHARP),
        CDoubleSharp(NaturalNote.C, Accident.DOUBLE_SHARP),
        DDoubleSharp(NaturalNote.D, Accident.DOUBLE_SHARP),
        EDoubleSharp(NaturalNote.E, Accident.DOUBLE_SHARP),
        FDoubleSharp(NaturalNote.F, Accident.DOUBLE_SHARP),
        GDoubleSharp(NaturalNote.G, Accident.DOUBLE_SHARP),
        ADoubleFlat(NaturalNote.A, Accident.DOUBLE_FLAT),
        BDoubleFlat(NaturalNote.B, Accident.DOUBLE_FLAT),
        CDoubleFlat(NaturalNote.C, Accident.DOUBLE_FLAT),
        DDoubleFlat(NaturalNote.D, Accident.DOUBLE_FLAT),
        EDoubleFlat(NaturalNote.E, Accident.DOUBLE_FLAT),
        FDoubleFlat(NaturalNote.F, Accident.DOUBLE_FLAT),
        GDoubleFlat(NaturalNote.G, Accident.DOUBLE_FLAT);

        private NaturalNote naturalNote;
        private Accident accident;
        private int alterationFromC;
        private static final HashMap<Note, HashMap<Integer, HashSet<Note>>> ALL_INTERVALS = buildAllSemitoneIntervals();
        public static final List<Note> SCALE_NOTES = List.of(A, ASharp, BFlat, B, CFlat, BSharp, C, CSharp,
                DFlat, D, DSharp, EFlat, E, FFlat, ESharp, F, FSharp, GFlat, G, GSharp, AFlat);

        Note(NaturalNote naturalNote, Accident accident) {
            this.naturalNote = naturalNote;
            this.accident = accident;
            this.alterationFromC = (naturalNote.intervalFromC + accident.interval + 12) % 12;
        }

        public NaturalNote getNaturalNote() {
            return naturalNote;
        }

        public HashSet<Note> findNotesWithInterval(int interval) {
            return ALL_INTERVALS.get(this).get(interval);
        }

        public int findNoteInterval(Note note) {
            return (note.alterationFromC - alterationFromC + 12) % 12;
        }

        private static HashMap<Note, HashMap<Integer, HashSet<Note>>> buildAllSemitoneIntervals() {
            HashMap<Note, HashMap<Integer, HashSet<Note>>> allIntervals = new HashMap<>(35);
            for (Note note1 : values()) {
                for (Note note2 : values()) {
                    int interval = note1.findNoteInterval(note2);
                    allIntervals.compute(note1, (note, intervalCurrent) -> intervalCurrent == null ? new HashMap<>() : intervalCurrent)
                            .compute(interval, (integer, notes) -> notes == null ? new HashSet<>(2) : notes)
                            .add(note2);
                }
            }
            return allIntervals;
        }

        public boolean isTheSame(Note other) {
            return this.alterationFromC == other.alterationFromC;
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

    }

    public enum Accident {
        FLAT(-1), NATURAL(0), SHARP(1), DOUBLE_FLAT(-2), DOUBLE_SHARP(2);
        private int interval;

        Accident(int interval) {
            this.interval = interval;
        }
    }

    public enum NaturalNote {
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
                    return A;
            }
            return null;
        }
    }
}