package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author Jenny Mei
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified by ARGS, where 1 <= ARGS.length <= 3.
     *  ARGS[0] is the name of a configuration file.
     *  ARGS[1] is optional; when present, it names an input file
     *  containing messages.  Otherwise, input comes from the standard
     *  input.  ARGS[2] is optional; when present, it names an output
     *  file for processed messages.  Otherwise, output goes to the
     *  standard output. Exits normally if there are no errors in the input;
     *  otherwise with code 1. */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /** Check ARGS and open the necessary files (see comment on main). */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /** Return a Scanner reading from the file named NAME. */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Return a PrintStream writing to the file named NAME. */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Configure an Enigma machine from the contents of configuration
     *  file _config and apply it to the messages in _input, sending the
     *  results to _output. */
    private void process() {
        Machine machine = readConfig();
        System.setOut(_output);
        boolean hasSetting = false;

        while (_input.hasNext()) {
            String outmsg = "";
            if (_input.hasNext("\\*")) {
                String asterisk = _input.next();
                String setUpLine = _input.nextLine();
                if (setUpLine.isEmpty()) {
                    setUpLine = _input.nextLine();
                }
                hasSetting = true;
                setUp(machine, setUpLine);
            }
            if (!hasSetting) {
                throw error("No configuration line found.");
            }
            while (_input.hasNext() && !_input.hasNext("\\*")) {
                outmsg = outmsg.concat(machine.convert(_input.next()));
            }
            printMessageLine(outmsg);
            System.out.println();
        }
    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            _alphabet = new Alphabet(_config.next());

            int numRotors = _config.nextInt();
            int pawls = _config.nextInt();

            ArrayList<Rotor> allRotors = new ArrayList<>();
            while (_config.hasNext()) {
                allRotors.add(readRotor());
            }
            return new Machine(_alphabet, numRotors, pawls, allRotors);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /** Return a rotor, reading its description from _config. */
    private Rotor readRotor() {
        try {
            String name = _config.next();

            String settings = _config.next();

            String cycles = "";
            while (_config.hasNext("\\(.*\\)")) {
                cycles = cycles.concat(_config.next() + " ");
            }
            Permutation perm = new Permutation(cycles, _alphabet);

            if (settings.charAt(0) == 'M') {
                String notches = settings.substring(1);
                return new MovingRotor(name, perm, notches);
            } else if (settings.charAt(0) == 'N') {
                return new FixedRotor(name, perm);
            } else if (settings.charAt(0) == 'R') {
                return new Reflector(name, perm);
            } else {
                throw error("bad rotor type specification");
            }
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        Scanner sScanner = new Scanner(settings);

        String[] rotors = new String[M.numRotors()];
        for (int i = 0; i < M.numRotors(); i += 1) {
            rotors[i] = sScanner.next();
        }
        M.insertRotors(rotors);

        boolean setRotors = false;
        String cycles = "";
        while (sScanner.hasNext()) {
            String next = sScanner.next();
            if (next.charAt(0) == '(') {
                cycles = cycles.concat(next);
                setRotors = true;
            } else if (next.charAt(0) != '(' && !setRotors) {
                M.setRotors(next);
            } else {
                throw error("Incorrect settings format.");
            }
        }
        Permutation plugboard = new Permutation(cycles, _alphabet);
        M.setPlugboard(plugboard);
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        int count = 0;
        for (int i = 0; i < msg.length(); i += 1) {
            if (count == 5) {
                System.out.print(" ");
                count = 0;
            }
            System.out.print(msg.charAt(i));
            count += 1;
        }
    }

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;

    /** Source of input messages. */
    private Scanner _input;

    /** Source of machine configuration. */
    private Scanner _config;

    /** File for encoded/decoded messages. */
    private PrintStream _output;
}
