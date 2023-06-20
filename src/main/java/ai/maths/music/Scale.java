package ai.maths.music;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import ai.maths.music.NoteEnums.NaturalNote;
import ai.maths.music.NoteEnums.Note;

public class Scale {

    private Note scaleNote;
    private ModeType modeType;
    private List<Note> notes;

    public Scale(Note scaleNote, ModeType modeType) {
        this.scaleNote = scaleNote;
        this.modeType = modeType;
        this.notes = buildScaleNotes();
    }

    public Note getScaleNote() {
        return scaleNote;
    }

    public ModeType getModeType() {
        return modeType;
    }

    @Override
    public String toString() {
        return modeType + " in " + scaleNote + " " + notes.toString();
    }

    private List<Note> buildScaleNotes() {
        List<HashSet<Note>> scalesVariants = modeType.intervals.stream()
                .map(interval -> interval == null ? null : scaleNote.findNoteInterval(interval))
                .collect(Collectors.toList());
        NaturalNote currentNote = scaleNote.getNaturalNote();
        ArrayList<Note> scaleNotes = new ArrayList<>(scalesVariants.size());
        for (HashSet<Note> scalesVariant : scalesVariants) {
            if (scalesVariant != null) {
                final NaturalNote currentNoteFinal = currentNote;
                Note noteToAdd = scalesVariant.stream().filter(note -> note.getNaturalNote() == currentNoteFinal).findFirst().get();
                scaleNotes.add(noteToAdd);
            }
            currentNote = currentNote.next();
        }
        return scaleNotes;
    }

    public List<Note> findCorrespondingNotesFromIntervals(List<Integer> intervals) {
        List<Note> notes = new ArrayList<>();
        int j = 0;
        int k = 0;
        for (int i = 0; i < modeType.intervals.size() && j < intervals.size(); i++) {
            Integer interval = modeType.intervals.get(i);
            if (interval != null) {
                if (intervals.get(j).equals(interval)) {
                    notes.add(this.notes.get(k));
                    j++;
                }
                k++;
            }
        }
        return notes;
    }

    public boolean containsEquivalentNotes(Collection<Note> notes) {
        return notes.stream().allMatch(note -> this.notes.stream().anyMatch(noteScale -> noteScale.areTheSame(note)));
    }

    public enum ScaleType {
        MAJOR(List.of(0, 2, 4, 5, 7, 9, 11), true), MINOR(List.of(0, 2, 3, 5, 7, 8, 10), false),
        MINOR_HARMONIC(List.of(0, 2, 3, 5, 7, 8, 11), false), MINOR_MELODIC(List.of(0, 2, 3, 5, 7, 9, 11), false),
        PENTATONIC_MAJOR(List.of(0, 2, 4, 7, 9), true), PENTATONIC_MINOR(List.of(0, 3, 5, 7, 10), false);

        private List<Integer> intervals;
        private boolean major;

        ScaleType(List<Integer> intervals, boolean major) {
            this.intervals = intervals;
            this.major = major;
        }

        public List<Integer> rotateScaleIntervals(int rotate) {
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
        BLUES_MAJOR_PENTATONIC_MAJOR(ScaleType.PENTATONIC_MAJOR, 4), MINOR_PENTATONIC(ScaleType.PENTATONIC_MAJOR, 5);

        private ScaleType scaleType;
        private List<Integer> intervals;

        ModeType(ScaleType scaleType, int modalInterval) {
            this.scaleType = scaleType;
            this.intervals = rotateScaleAndAdjust(-modalInterval);
        }

        public boolean areIntervalsInTheScale(Collection<Integer> intervals) {
            return this.intervals.containsAll(intervals);
        }

        private List<Integer> rotateScaleAndAdjust(int rotate) {
            if (rotate == 0 && scaleType != ScaleType.PENTATONIC_MAJOR) {
                return scaleType.intervals;
            }
            if (scaleType == ScaleType.PENTATONIC_MAJOR) {
                List<Integer> newIntervals = new ArrayList<>(ScaleType.MAJOR.intervals);
                List<Integer> pentatonicIntervals = scaleType.intervals;
                Integer initialNote = pentatonicIntervals.get(0);
                int j = 0;
                for (int i = 0; i < newIntervals.size(); i++) {
                    if (j >= pentatonicIntervals.size() || !newIntervals.get(i).equals(pentatonicIntervals.get(j))) {
                        newIntervals.set(i, null);
                    } else {
                        newIntervals.set(i, (newIntervals.get(i) - initialNote + 12) % 12);
                        j++;
                    }
                }
                return newIntervals;
            } else {
                return scaleType.rotateScaleIntervals(rotate);
            }
        }
    }

    public enum KeySignatures {
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

        KeySignatures(Note scaleMajor, Note scaleMinor, List<Note> alterations) {
            this.scaleMajor = scaleMajor;
            this.scaleMinor = scaleMinor;
            this.alterations = alterations;
        }
    }
}
