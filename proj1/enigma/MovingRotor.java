package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author Jenny Mei
 */
class MovingRotor extends Rotor {

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        // FIXME
        _notches = notches;
    }

    // FIXME?

    @Override
    /** Return true iff I have a ratchet and can move. */
    boolean rotates() { return true; }

    @Override
    /** Advance me one position, if possible. By default, does nothing. */
    void advance() {
        if (setting() == size() - 1) {
            set(0);
        } else {
            set(setting() + 1);
        }
    }

    @Override
    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        for (int i = 0; i < _notches.length(); i += 1) {
            if (setting() == alphabet().toInt(_notches.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    // FIXME: ADDITIONAL FIELDS HERE, AS NEEDED
    private String _notches;

}
