package ai.maths.music;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import ai.maths.music.Chord.ChordType;
import ai.maths.music.NoteEnums.Note;

public class EquivalentChords {

    public static void main(String[] args) {
        List<Chord> chords = Note.SCALE_NOTES.stream()
                .flatMap(note -> Arrays.stream(ChordType.values())
                        .map(chordType -> new Chord(note, chordType))).collect(Collectors.toList());
        Map<Note, Map<ChordType, List<Chord>>> collect = new TreeMap<>(Note.SCALE_NOTES.stream()
                .collect(Collectors.toMap(note -> note,
                        note -> new TreeMap<>(chords.stream()
                                .filter(chord -> chord.getNote().isTheSame(note)
                                        //&& chord.getScaleByModeType().values().stream().allMatch(scale -> scale.getKeySignature() != null)
                                )
                                .collect(Collectors.groupingBy(Chord::getChordType))))));
        collect.forEach((note, chordTypes) -> System.out.println(chordTypes.entrySet().stream()
                .map(chordTypeListEntry -> note + " " + chordTypeListEntry.getKey() + chordTypeListEntry.getValue().stream()
                        .map(Chord::toString)
                        .collect(Collectors.joining("\t", "\n\t", "\t")))
                .collect(Collectors.joining("\n"))));

    }
}
