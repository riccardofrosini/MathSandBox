package ai.maths.music;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import ai.maths.music.Chord.ChordType;
import ai.maths.music.NoteEnums.Note;
import ai.maths.music.Scale.ModeType;

public class EquivalentChords {

    public static void main(String[] args) {
        Map<Scale, List<Chord>> chordsForScale = new TreeMap<>(NoteEnums.SCALE_NOTES.stream()
                .flatMap(note -> Arrays.stream(ModeType.values())
                        .map(modeType -> Scale.buildScale(note, modeType)))
                .filter(Optional::isPresent).map(Optional::get)
                .collect(Collectors.toMap(scale -> scale, Scale::getChords)));

        chordsForScale.forEach((scale, chords) -> System.out.println(scale + " " + chords.stream()
                .map(Chord::toString)
                .collect(Collectors.joining("\t", "\n\t", "\t"))));

        Map<Note, Map<ChordType, Set<Chord>>> allChords = new TreeMap<>(NoteEnums.SCALE_NOTES.stream().collect(Collectors.toMap(note -> note,
                note -> new TreeMap<>(Arrays.stream(ChordType.values()).map(chordType -> new Chord(note, chordType))
                        .collect(Collectors.toMap(Chord::getChordType, Chord::getEquivalentChords))))));

        allChords.forEach((note, chordTypes) -> System.out.println(chordTypes.entrySet().stream()
                .map(chordTypeListEntry -> note + " " + chordTypeListEntry.getKey() + chordTypeListEntry.getValue().stream()
                        .map(Chord::toString)
                        .collect(Collectors.joining("\t", "\n\t", "\t")))
                .collect(Collectors.joining("\n"))));
    }
}
