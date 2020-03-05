package enigma;

import java.util.HashMap;
import java.util.Collection;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author Jenny Mei
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;

        _availableRotors = allRotors;
        _rotorSlots = new Rotor[numRotors];
        _movingRotors = pawls;
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _rotorSlots.length;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _movingRotors;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        HashMap<Rotor, Integer> usedRotors = new HashMap<>();
        for (int i = 0; i < rotors.length; i += 1) {
            for (Rotor rotor : _availableRotors) {
                if (rotor.name().equals(rotors[i])) {
                    if (i == 0 && !rotor.reflecting()) {
                        throw error("Last rotor must be a reflector!");
                    } else if (i != 0 && rotor.reflecting()) {
                        throw error("Only the last rotor can be a reflector!");
                    } else if (i < _rotorSlots.length - _movingRotors &&
                                rotor.rotates()) {
                        throw error("Moving rotor placed in non-moving slot.");
                    } else if (i >= _rotorSlots.length - _movingRotors &&
                                !rotor.rotates()) {
                        throw error("Non-moving rotor placed in moving slot.");
                    }

                    if (!usedRotors.containsKey(rotor)) {
                        usedRotors.put(rotor, 1);
                    } else {
                        throw error("Rotors may not be repeated in one machine.");
                    }

                    _rotorSlots[i] = rotor;
                }
            }
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        if (setting.length() >= _rotorSlots.length || setting.length() < _rotorSlots.length - 1) {
            throw error("Misformatted rotor settings.");
        }
        for (int i = 0; i < setting.length(); i += 1) {
            _rotorSlots[i + 1].set(setting.charAt(i));
        }
    }

    void setRings() {
        for (int i = 0; i < _rotorSlots.length - 1; i += 1) {
            _rotorSlots[i + 1].setRing('A');
        }
    }

    void setRings(String setting) {
        if (setting.length() >= _rotorSlots.length || setting.length() < _rotorSlots.length - 1) {
            throw error("Misformatted ring settings.");
        }
        for (int i = 0; i < setting.length(); i += 1) {
            _rotorSlots[i + 1].setRing(setting.charAt(i));
        }
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        _plugboard = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing

     *  the machine. */
    int convert(int c) {
            boolean[] willAdvance = new boolean[_rotorSlots.length];
            for (int r = 0; r < _rotorSlots.length; r += 1) {
                if (r == _rotorSlots.length - 1) {
                    willAdvance[r] = true;
                } else if (_rotorSlots[r].rotates() && _rotorSlots[r + 1].atNotch()) {
                    willAdvance[r] = willAdvance[r + 1] = true;
                }
            }
            for (int r = 0; r < _rotorSlots.length; r += 1) {
                if (willAdvance[r]) {
                    _rotorSlots[r].advance();
                }
            }

//        for (int r = _rotorSlots.length - 1; r >= 0; r -= 1) {
//            if (r == _rotorSlots.length - 1) {
//                _rotorSlots[r].advance();
//            } else if (_rotorSlots[r].rotates() && _rotorSlots[r + 1].atNotch()) {
//                _rotorSlots[r].advance();
//                _rotorSlots[r+1].advance();
//            }
//        }

        int newc =_plugboard.permute(c);
        for (int r = _rotorSlots.length - 1; r >= 0; r -= 1) {
            newc = _rotorSlots[r].convertForward(newc);
        }
        for (int r = 1; r < _rotorSlots.length; r += 1) {
            newc = _rotorSlots[r].convertBackward(newc);
        }

        return _plugboard.invert(newc);
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        char[] newMsg = msg.toCharArray();
        for (char letter : newMsg) {
            if (!_alphabet.contains(letter)) {
                throw error("Alphabet does not contain this letter.");
            }
        }
        for (int i = 0; i < msg.length(); i += 1) {
            int newI = convert(_alphabet.toInt(msg.charAt(i)));
            char newChar = _alphabet.toChar(newI);
            newMsg[i] = newChar;
        }
        return new String(newMsg);
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    private Collection<Rotor> _availableRotors;
    private Rotor[] _rotorSlots;
    private int _movingRotors;
    private Permutation _plugboard;

}
