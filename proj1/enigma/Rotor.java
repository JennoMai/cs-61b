package enigma;

import static enigma.EnigmaException.*;

/** Superclass that represents a rotor in the enigma machine.
 *  @author Jenny Mei
 */
class Rotor {

    /** A rotor named NAME whose permutation is given by PERM. */
    Rotor(String name, Permutation perm) {
        if (name.contains("(") || name.contains(")") || name.contains("*")) {
            throw error("No parentheses or asterisks allowed in rotor names.");
        }
        _name = name;
        _permutation = perm;
        _setting = 0;
        _ring = 0;
    }

    /** Return my name. */
    String name() {
        return _name;
    }

    /** Return my alphabet. */
    Alphabet alphabet() {
        return _permutation.alphabet();
    }

    /** Return my permutation. */
    Permutation permutation() {
        return _permutation;
    }

    /** Return the size of my alphabet. */
    int size() {
        return _permutation.size();
    }

    /** Return true iff I have a ratchet and can move. */
    boolean rotates() {
        return false;
    }

    /** Return true iff I reflect. */
    boolean reflecting() {
        return false;
    }

    /** Return my current setting. */
    int setting() {
        return _setting;
    }

    /** Set setting() to POSN.  */
    void set(int posn) {
        _setting = posn;
    }

    /** Set setting() to character CPOSN. */
    void set(char cposn) {
        _setting = _permutation.alphabet().toInt(cposn);
    }

    void setRing(int posn) {_ring = posn; }

    void setRing(char crposn) {
        _ring = _permutation.alphabet().toInt(crposn);
    }

    /** Return the conversion of P (an integer in the range 0..size()-1)
     *  according to my permutation. Where P is the signal entering the rotor. */
    int convertForward(int p) {
        int contact = p + _setting - _ring;
        int translation = _permutation.permute(contact);
        int exit = translation - _setting + _ring;
        return _permutation.wrap(exit);
    }

    /** Return the conversion of E (an integer in the range 0..size()-1)
     *  according to the inverse of my permutation. */
    int convertBackward(int e) {
        int contact = e + _setting - _ring;
        int translation = _permutation.invert(contact);
        int exit = translation - _setting + _ring;
        return _permutation.wrap(exit);
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        return false;
    }

    /** Advance me one position, if possible. By default, does nothing. */
    void advance() {
    }

    @Override
    public String toString() {
        return "Rotor " + _name;
    }

    /** My name. */
    private final String _name;

    /** The permutation implemented by this rotor in its 0 position. */
    private Permutation _permutation;

    private int _setting;
    private  int _ring;

}
