package ai.maths.music;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ai.maths.music.Chord.ChordType;
import ai.maths.music.NoteEnums.Note;

public class EquivalentChords {

    public static void main(String[] args) {
        Map<Note, Map<ChordType, Set<Chord>>> collect = Note.SCALE_NOTES.stream().collect(Collectors.toMap(note -> note,
                note -> Arrays.stream(ChordType.values()).map(chordType -> new Chord(note, chordType))
                        .collect(Collectors.toMap(Chord::getChordType, Chord::getEquivalentChords))));

        collect.forEach((note, chordTypes) -> System.out.println(chordTypes.entrySet().stream()
                .map(chordTypeListEntry -> note + " " + chordTypeListEntry.getKey() + chordTypeListEntry.getValue().stream()
                        .map(Chord::toString)
                        .collect(Collectors.joining("\t", "\n\t", "\t")))
                .collect(Collectors.joining("\n"))));

    }
}
