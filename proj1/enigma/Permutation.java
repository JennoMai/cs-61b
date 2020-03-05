package enigma;

import java.util.ArrayList;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Jenny Mei
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _usedChars = new boolean[_alphabet.size()];
        for (int i = 0; i < _alphabet.size(); i += 1) {
            _map.add(i, _alphabet.toChar(i));
        }

        int last = 0;
        for (int i = 0; i < cycles.length(); i += 1) {
            char currentChar = cycles.charAt(i);
            if (currentChar == '(') {
                last = i + 1;
            } else if (currentChar == ')') {
                if (i - last == 0) {
                    throw error("() is not a valid cycle.");
                }
                addCycle(cycles.substring(last, i));
            }
        }
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm.
     *
     *  _alphabet containing [A, B, C, D, E, F, G] maps to [C, D, E, F, G, B, A]*/
    private void addCycle(String cycle) {
        int length = cycle.length();
        for (int i = 0; i < length; i += 1) {
            char currentChar = cycle.charAt(i);
            if (!_alphabet.contains(currentChar)) {
                throw error("Alphabet does not contain %c", currentChar);
            }

            int aIndex = _alphabet.toInt(currentChar);
            if (_usedChars[aIndex]) {
                throw error("%c is duplicated.", currentChar);
            } else {
                _usedChars[aIndex] = true;
            }

            if (i == length - 1) {
                _map.set(aIndex, cycle.charAt(0));
            } else {
                _map.set(aIndex, cycle.charAt(i+1));
            }
        }
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        int r = wrap(p);
        return _alphabet.toInt(permute(_alphabet.toChar(r)));
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        int r = wrap(c);
        return _alphabet.toInt(invert(_alphabet.toChar(r)));
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        return _map.get(_alphabet.toInt(p));
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        return _alphabet.toChar(_map.indexOf(c));
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        for (boolean value : _usedChars) {
            if (!value) {
                return false;
            }
        }
        return true;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;

    /** For each letter in _alphabet at index i, _map contains its permutation at the same index i. */
    private ArrayList<Character> _map = new ArrayList<>();
    private boolean[] _usedChars;
}
