package ai.maths.music;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class NoteEnums {

    public enum Note {
        A(NaturalNote.A, Accident.NATURAL, 9), ASharp(NaturalNote.A, Accident.SHARP, 10), BFlat(NaturalNote.B, Accident.FLAT, 10),
        B(NaturalNote.B, Accident.NATURAL, 11), CFlat(NaturalNote.C, Accident.FLAT, 11), BSharp(NaturalNote.B, Accident.SHARP, 0),
        C(NaturalNote.C, Accident.NATURAL, 0), CSharp(NaturalNote.C, Accident.SHARP, 1), DFlat(NaturalNote.D, Accident.FLAT, 1),
        D(NaturalNote.D, Accident.NATURAL, 2), DSharp(NaturalNote.D, Accident.SHARP, 3), EFlat(NaturalNote.E, Accident.FLAT, 3),
        E(NaturalNote.E, Accident.NATURAL, 4), FFlat(NaturalNote.F, Accident.FLAT, 4), ESharp(NaturalNote.E, Accident.SHARP, 5),
        F(NaturalNote.F, Accident.NATURAL, 5), FSharp(NaturalNote.F, Accident.SHARP, 6), GFlat(NaturalNote.G, Accident.FLAT, 6),
        G(NaturalNote.G, Accident.NATURAL, 7), GSharp(NaturalNote.G, Accident.SHARP, 8), AFlat(NaturalNote.A, Accident.FLAT, 8),

        ADoubleSharp(NaturalNote.A, Accident.DOUBLE_SHARP, 11),
        BDoubleSharp(NaturalNote.B, Accident.DOUBLE_SHARP, 1),
        CDoubleSharp(NaturalNote.C, Accident.DOUBLE_SHARP, 2),
        DDoubleSharp(NaturalNote.D, Accident.DOUBLE_SHARP, 4),
        EDoubleSharp(NaturalNote.E, Accident.DOUBLE_SHARP, 6),
        FDoubleSharp(NaturalNote.F, Accident.DOUBLE_SHARP, 7),
        GDoubleSharp(NaturalNote.G, Accident.DOUBLE_SHARP, 9),
        ADoubleFlat(NaturalNote.A, Accident.DOUBLE_FLAT, 7),
        BDoubleFlat(NaturalNote.B, Accident.DOUBLE_FLAT, 9),
        CDoubleFlat(NaturalNote.C, Accident.DOUBLE_FLAT, 10),
        DDoubleFlat(NaturalNote.D, Accident.DOUBLE_FLAT, 0),
        EDoubleFlat(NaturalNote.E, Accident.DOUBLE_FLAT, 2),
        FDoubleFlat(NaturalNote.F, Accident.DOUBLE_FLAT, 3),
        GDoubleFlat(NaturalNote.G, Accident.DOUBLE_FLAT, 5);

        private NaturalNote naturalNote;
        private Accident accident;
        private int alterationFromC;
        private static final HashMap<Note, HashMap<Integer, HashSet<Note>>> ALL_INTERVALS = buildAllSemitoneIntervals();
        public static final List<Note> SCALE_NOTES = List.of(A, ASharp, BFlat, B, C, CSharp, DFlat,
                D, DSharp, EFlat, E, F, FSharp, GFlat, G, GSharp, AFlat);

        Note(NaturalNote naturalNote, Accident accident, int alterationFromC) {
            this.naturalNote = naturalNote;
            this.accident = accident;
            this.alterationFromC = alterationFromC;
        }

        public NaturalNote getNaturalNote() {
            return naturalNote;
        }

        public HashSet<Note> findNoteInterval(int interval) {
            return ALL_INTERVALS.get(this).get(interval);
        }

        public boolean areTheSame(Note other) {
            return this.alterationFromC == other.alterationFromC;
        }

        private static HashMap<Note, HashMap<Integer, HashSet<Note>>> buildAllSemitoneIntervals() {
            HashMap<Note, HashMap<Integer, HashSet<Note>>> allIntervals = new HashMap<>(35);
            for (Note note1 : values()) {
                for (Note note2 : values()) {
                    int interval = (note2.alterationFromC - note1.alterationFromC + 12) % 12;
                    allIntervals.compute(note1, (note, intervalCurrent) -> intervalCurrent == null ? new HashMap<>() : intervalCurrent)
                            .compute(interval, (integer, notes) -> notes == null ? new HashSet<>(2) : notes)
                            .add(note2);
                }
            }
            return allIntervals;
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
        FLAT, NATURAL, SHARP, DOUBLE_FLAT, DOUBLE_SHARP
    }

    public enum NaturalNote {
        A, B, C, D, E, F, G;

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