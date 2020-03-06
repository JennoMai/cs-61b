/** P2Pattern class
 *  @author Josh Hug & Vivant Sakore
 */

public class P2Pattern {
    /* Pattern to match a valid date of the form MM/DD/YYYY. Eg: 9/22/2019 */
    public static final String P1 = "(\\d|0\\d|1[0-2])/(\\d|0\\d|1\\d|2\\d|3[01])/(19\\d\\d|[2-9]\\d\\d\\d)"; //FIXME: Add your regex here

    /** Pattern to match 61b notation for literal IntLists. */
    public static final String P2 = "\\((\\d+,\\s+)*(\\d+)\\)"; //FIXME: Add your regex here

    /* Pattern to match a valid domain name. Eg: www.support.facebook-login.com */
    public static final String P3 = "^([^-.\\s_]|[^-.\\s_]([^\\s_])*[^-.\\s_])\\.[^\\s_]{2,5}[^-.\\s_]$"; //FIXME: Add your regex here

    /* Pattern to match a valid java variable name. Eg: _child13$ */
    public static final String P4 = "^[^\\d].*"; //FIXME: Add your regex here

    /* Pattern to match a valid IPv4 address. Eg: 127.0.0.1 */
    public static final String P5 = "(\\d|\\d\\d|[0-1]\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|\\d\\d|[0-1]\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|\\d\\d|[0-1]\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|\\d\\d|[0-1]\\d\\d|2[0-4]\\d|25[0-5])$"; //FIXME: Add your regex here

}
