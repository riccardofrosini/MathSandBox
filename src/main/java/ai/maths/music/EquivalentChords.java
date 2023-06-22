package ai.maths.music;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import ai.maths.music.Chord.ChordType;
import ai.maths.music.NoteEnums.Note;

public class EquivalentChords {

    public static void main(String[] args) {
        Map<Note, Map<ChordType, Set<Chord>>> collect = new TreeMap<>(NoteEnums.SCALE_NOTES.stream().collect(Collectors.toMap(note -> note,
                note -> new TreeMap<>(Arrays.stream(ChordType.values()).map(chordType -> new Chord(note, chordType))
                        .collect(Collectors.toMap(Chord::getChordType, Chord::getEquivalentChords))))));

        collect.forEach((note, chordTypes) -> System.out.println(chordTypes.entrySet().stream()
                .map(chordTypeListEntry -> note + " " + chordTypeListEntry.getKey() + chordTypeListEntry.getValue().stream()
                        .map(Chord::toString)
                        .collect(Collectors.joining("\t", "\n\t", "\t")))
                .collect(Collectors.joining("\n"))));

    }
}
