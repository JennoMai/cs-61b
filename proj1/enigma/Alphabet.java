package enigma;
import java.util.HashMap;
import java.util.Map;
import static enigma.EnigmaException.*;

/** An alphabet of encodable characters.  Provides a mapping from characters
 *  to and from indices into the alphabet.
 *  @author Jenny Mei
 */
class Alphabet {

    /** A new alphabet containing CHARS.  Character number #k has index
     *  K (numbering from 0). No character may be duplicated. */
    Alphabet(String chars) {
        if (chars.contains("(") || chars.contains(")") || chars.contains("*")) {
            throw error("Parentheses and asterisks not permitted in alphabet.");
        }
        Map<Character, Integer> map = new HashMap<>();
        char[] string2chars = chars.toCharArray();
        for (char c : string2chars) {
            if (map.containsKey(c)) {
                throw error("Repeated character found: %c", c);
            } else {
                map.put(c, 1);
            }
        }

        _chars = chars;
    }

    /** A default alphabet of all upper-case characters. */
    Alphabet() {
        this("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    /** Returns the size of the alphabet. */
    int size() {
        return _chars.length();
    }

    /** Returns true if CH is in this alphabet. */
    boolean contains(char ch) {
        for (int i = 0; i < _chars.length(); i += 1) {
            if (_chars.charAt(i) == ch) {
                return true;
            }
        }
        return false;
    }

    /** Returns character number INDEX in the alphabet, where
     *  0 <= INDEX < size(). */
    char toChar(int index) {
        return _chars.charAt(index);
    }

    /** Returns the index of character CH which must be in
     *  the alphabet. This is the inverse of toChar(). */
    int toInt(char ch) {
        for (int i = 0; i < _chars.length(); i += 1) {
            if (_chars.charAt(i) == ch) {
                return i;
            }
        }
        return -1;
    }

    /** All chars represented by the alphabet. */
    private String _chars;
}
