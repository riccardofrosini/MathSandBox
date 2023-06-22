package ai.maths.music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ai.maths.music.NoteEnums.NaturalNote;
import ai.maths.music.NoteEnums.Note;

public class Scale {

    private Note scaleNote;
    private ModeType modeType;
    private List<Note> notes;
    private KeySignature keySignature;

    public Scale(Note scaleNote, ModeType modeType) throws ScaleDoesNotExist {
        this.scaleNote = scaleNote;
        this.modeType = modeType;
        this.notes = buildScaleNotes();
        this.keySignature = KeySignature.findKey(notes.get((notes.size() - modeType.modalInterval) % notes.size()), modeType.scaleType);
    }

    public ModeType getModeType() {
        return modeType;
    }

    @Override
    public String toString() {
        return modeType + " in " + scaleNote + " " + notes + ", " + keySignature;
    }

    private List<Note> buildScaleNotes() {
        List<Integer> modeIntervals;
        if (modeType.scaleType == ScaleType.PENTATONIC_MAJOR) {
            int modalInterval = modeType.modalInterval >= 3 ? modeType.modalInterval + 1 : modeType.modalInterval;
            modeIntervals = new ArrayList<>(ScaleType.MAJOR.rotateScaleIntervals(-modalInterval));
            Iterator<Integer> pentatonicIntervalsIterator = modeType.intervals.iterator();
            Integer pentatonicInterval = pentatonicIntervalsIterator.next();
            for (int i = 0; i < modeIntervals.size(); i++) {
                if (!Objects.equals(modeIntervals.get(i), pentatonicInterval)) {
                    modeIntervals.set(i, null);
                } else if (pentatonicIntervalsIterator.hasNext()) {
                    pentatonicInterval = pentatonicIntervalsIterator.next();
                }
            }
        } else {
            modeIntervals = modeType.intervals;
        }
        List<HashSet<Note>> scalesVariants = modeIntervals.stream().map(interval -> interval == null ? null : scaleNote.findNotesWithInterval(interval)).collect(Collectors.toList());
        NaturalNote currentNote = scaleNote.getNaturalNote();
        ArrayList<Note> scaleNotes = new ArrayList<>(scalesVariants.size());
        for (HashSet<Note> scalesVariant : scalesVariants) {
            if (scalesVariant != null) {
                final NaturalNote currentNoteFinal = currentNote;
                scaleNotes.add(scalesVariant.stream().filter(note -> note.getNaturalNote() == currentNoteFinal).findFirst().get());
            }
            currentNote = currentNote.next();
        }
        return Collections.unmodifiableList(scaleNotes);
    }

    public List<Note> findCorrespondingNotesFromIntervals(List<Integer> intervals) {
        List<Note> notes = new ArrayList<>();
        Iterator<Integer> intervalIterator = intervals.iterator();
        Integer interval = intervalIterator.next();
        for (int i = 0; i < modeType.intervals.size(); i++) {
            if (interval.equals(modeType.intervals.get(i))) {
                notes.add(this.notes.get(i));
                if (intervalIterator.hasNext()) {
                    interval = intervalIterator.next();
                }
            }
        }
        return notes;
    }

    public enum ScaleType {
        MAJOR(List.of(0, 2, 4, 5, 7, 9, 11)), MINOR(List.of(0, 2, 3, 5, 7, 8, 10)),
        MINOR_HARMONIC(List.of(0, 2, 3, 5, 7, 8, 11)), MINOR_MELODIC(List.of(0, 2, 3, 5, 7, 9, 11)),
        PENTATONIC_MAJOR(List.of(0, 2, 4, 7, 9)), PENTATONIC_MINOR(List.of(0, 3, 5, 7, 10));

        private List<Integer> intervals;

        ScaleType(List<Integer> intervals) {
            this.intervals = intervals;
        }

        public List<Integer> rotateScaleIntervals(int rotate) {
            if (rotate == 0) {
                return Collections.unmodifiableList(intervals);
            }
            ArrayList<Integer> newIntervals = new ArrayList<>(intervals);
            Collections.rotate(newIntervals, rotate);
            Integer firstInterval = newIntervals.get(0);
            return newIntervals.stream().map(interval -> (interval - firstInterval + 12) % 12).collect(Collectors.toUnmodifiableList());
        }
    }

    public enum ModeType {
        IONIAN(ScaleType.MAJOR, 0), DORIAN(ScaleType.MAJOR, 1), PHRYGIAN(ScaleType.MAJOR, 2),
        LYDIAN(ScaleType.MAJOR, 3), MIXOLYDIAN(ScaleType.MAJOR, 4), AEOLIAN(ScaleType.MAJOR, 5),
        LOCRIAN(ScaleType.MAJOR, 6),

        IONIAN_HARMONIC_MINOR(ScaleType.MINOR_HARMONIC, 0), DORIAN_HARMONIC_MINOR(ScaleType.MINOR_HARMONIC, 1),
        PHRYGIAN_HARMONIC_MINOR(ScaleType.MINOR_HARMONIC, 2), LYDIAN_HARMONIC_MINOR(ScaleType.MINOR_HARMONIC, 3),
        MIXOLYDIAN_HARMONIC_MINOR(ScaleType.MINOR_HARMONIC, 4), AEOLIAN_HARMONIC_MINOR(ScaleType.MINOR_HARMONIC, 5),
        LOCRIAN_HARMONIC_MINOR(ScaleType.MINOR_HARMONIC, 6),

        IONIAN_MELODIC_MINOR(ScaleType.MINOR_MELODIC, 0), DORIAN_MELODIC_MINOR(ScaleType.MINOR_MELODIC, 1),
        PHRYGIAN_MELODIC_MINOR(ScaleType.MINOR_MELODIC, 2), LYDIAN_MELODIC_MINOR(ScaleType.MINOR_MELODIC, 3),
        MIXOLYDIAN_MELODIC_MINOR(ScaleType.MINOR_MELODIC, 4), AEOLIAN_MELODIC_MINOR(ScaleType.MINOR_MELODIC, 5),
        LOCRIAN_MELODIC_MINOR(ScaleType.MINOR_MELODIC, 6),

        MAJOR_PENTATONIC(ScaleType.PENTATONIC_MAJOR, 0), SUSPENDED_PENTATONIC_MAJOR(ScaleType.PENTATONIC_MAJOR, 1),
        BLUES_MINOR_PENTATONIC_MAJOR(ScaleType.PENTATONIC_MAJOR, 2),
        BLUES_MAJOR_PENTATONIC_MAJOR(ScaleType.PENTATONIC_MAJOR, 3), MINOR_PENTATONIC(ScaleType.PENTATONIC_MAJOR, 4);

        private ScaleType scaleType;
        private int modalInterval;
        private List<Integer> intervals;

        ModeType(ScaleType scaleType, int modalInterval) {
            this.scaleType = scaleType;
            this.modalInterval = modalInterval;
            this.intervals = scaleType.rotateScaleIntervals(-modalInterval);
        }

        public boolean areIntervalsInTheModeType(Collection<Integer> intervals) {
            return this.intervals.containsAll(intervals);
        }
    }

    public enum KeySignature {
        CFlatMajorAFlatMinor(Note.CFlat, Note.AFlat, List.of(Note.BFlat, Note.EFlat, Note.AFlat, Note.DFlat, Note.GFlat, Note.CFlat, Note.FFlat)),
        GFlatMajorEFlatMinor(Note.GFlat, Note.EFlat, List.of(Note.BFlat, Note.EFlat, Note.AFlat, Note.DFlat, Note.GFlat, Note.CFlat)),
        DFlatMajorBFlatMinor(Note.DFlat, Note.BFlat, List.of(Note.BFlat, Note.EFlat, Note.AFlat, Note.DFlat, Note.GFlat)),
        AFlatMajorFMinor(Note.AFlat, Note.F, List.of(Note.BFlat, Note.EFlat, Note.AFlat, Note.DFlat)),
        EFlatMajorCMinor(Note.EFlat, Note.C, List.of(Note.BFlat, Note.EFlat, Note.AFlat)),
        BFlatMajorGMinor(Note.BFlat, Note.G, List.of(Note.BFlat, Note.EFlat)),
        FMajorDMinor(Note.F, Note.D, List.of(Note.BFlat)),
        CMajorAMinor(Note.C, Note.A, List.of()),
        GMajorEMinor(Note.G, Note.E, List.of(Note.FSharp)),
        DMajorBMinor(Note.D, Note.B, List.of(Note.FSharp, Note.CSharp)),
        AMajorFSharpMinor(Note.A, Note.FSharp, List.of(Note.FSharp, Note.CSharp, Note.GSharp)),
        EMajorCSharpMinor(Note.E, Note.CSharp, List.of(Note.FSharp, Note.CSharp, Note.GSharp, Note.DSharp)),
        BMajorGSharpMinor(Note.B, Note.GSharp, List.of(Note.FSharp, Note.CSharp, Note.GSharp, Note.DSharp, Note.ASharp)),
        FSharpMajorDSharpMinor(Note.FSharp, Note.DSharp, List.of(Note.FSharp, Note.CSharp, Note.GSharp, Note.DSharp, Note.ASharp, Note.ESharp)),
        CSharpMajorASharpMinor(Note.CSharp, Note.ASharp, List.of(Note.FSharp, Note.CSharp, Note.GSharp, Note.DSharp, Note.ASharp, Note.ESharp, Note.BSharp));

        private Note scaleMajor;
        private Note scaleMinor;
        private List<Note> alterations;

        KeySignature(Note scaleMajor, Note scaleMinor, List<Note> alterations) {
            this.scaleMajor = scaleMajor;
            this.scaleMinor = scaleMinor;
            this.alterations = alterations;
        }

        public static KeySignature findKey(Note scaleNote, ScaleType scaleType) throws ScaleDoesNotExist {
            if (scaleType == ScaleType.MAJOR || scaleType == ScaleType.PENTATONIC_MAJOR) {
                return Arrays.stream(values()).filter(keySignature -> keySignature.scaleMajor == scaleNote)
                        .min(Comparator.comparing(o -> o.alterations.size())).orElseThrow(() -> new ScaleDoesNotExist(scaleNote, scaleType));
            }
            return Arrays.stream(values()).filter(keySignature -> keySignature.scaleMinor == scaleNote)
                    .min(Comparator.comparing(o -> o.alterations.size())).orElseThrow(() -> new ScaleDoesNotExist(scaleNote, scaleType));
        }

        @Override
        public String toString() {
            return name() + " alterations=" + alterations;
        }
    }

    public static class ScaleDoesNotExist extends Exception {

        public ScaleDoesNotExist(Note note, ScaleType scaleType) {
            super("Scale " + note + " in " + scaleType + ", does not exist.");
        }
    }
}
