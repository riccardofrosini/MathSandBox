package ai.maths.music;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ai.maths.music.Chord.ChordType;
import ai.maths.music.NoteEnums.Note;

public class EquivalentChords {

    public static void main(String[] args) {
        List<Chord> chords = Note.SCALE_NOTES.stream()
                .flatMap(note -> Arrays.stream(ChordType.values())
                        .map(chordType -> new Chord(note, chordType))).collect(Collectors.toList());
        Map<Note, Map<ChordType, List<Chord>>> collect = Note.SCALE_NOTES.stream()
                .collect(Collectors.toMap(note -> note,
                        note -> chords.stream()
                                .filter(chord -> chord.getNote().isTheSame(note))
                                .collect(Collectors.groupingBy(Chord::getChordType))));
        collect.forEach((note, chordTypes) -> System.out.println(note + " \n" + chordTypes.entrySet().stream()
                .map(chordTypeListEntry -> chordTypeListEntry.getKey() + " \n" + chordTypeListEntry.getValue().stream().map(Chord::toString).collect(Collectors.joining()))
                .collect(Collectors.joining("\n"))));

    }
}
