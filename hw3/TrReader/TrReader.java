import java.io.Reader;
import java.io.IOException;

/** Translating Reader: a stream that is a translation of an
 *  existing reader.
 *  @author Jenny Mei
 */
public class TrReader extends Reader {
    /** A new TrReader that produces the stream of characters produced
     *  by STR, converting all characters that occur in FROM to the
     *  corresponding characters in TO.  That is, change occurrences of
     *  FROM.charAt(i) to TO.charAt(i), for all i, leaving other characters
     *  in STR unchanged.  FROM and TO must have the same length. */
    private Reader reader;
    private String f;
    private String t;

    public TrReader(Reader str, String from, String to) {
        reader = str;
        f = from;
        t = to;
    }
    public int read (char[] cbuf, int off, int len) throws IOException {
        int r = reader.read(cbuf, off, len);
        System.out.println(r);
        if (r == -1) {
            return -1;
        }
        for (int n = off; n < len + off; n += 1) {
            for (int i = 0; i < f.length(); i += 1) {
                if (f.charAt(i) == cbuf[n]) {
                    cbuf[n] = t.charAt(i);
                    break;
                }
            }
        }
        return r;
    }
    public void close() {}
}
