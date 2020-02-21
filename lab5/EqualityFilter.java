import java.util.Iterator;
import java.util.List;

/**
 * TableFilter to filter for entries equal to a given string.
 *
 * @author Matthew Owen
 */
public class EqualityFilter extends TableFilter {

    public EqualityFilter(Table input, String colName, String match) {
        super(input);
        // FIXME: Add your code here.
        _headerList = headerList();
        _col = colName;
        _match = match;
    }

    @Override
    protected boolean keep() {
        // FIXME: Replace this line with your code.
        int i = _headerList.indexOf(_col);
        if (candidateNext().getValue(i).equals(_match)) {
            return true;
        } else {
            return false;
        }
    }

    // FIXME: Add instance variables?
    private List<String> _headerList;
    private String _col;
    private String _match;
}
