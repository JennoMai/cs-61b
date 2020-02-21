import java.util.Iterator;
import java.util.List;

/**
 * TableFilter to filter for entries greater than a given string.
 *
 * @author Matthew Owen
 */
public class GreaterThanFilter extends TableFilter {

    public GreaterThanFilter(Table input, String colName, String ref) {
        super(input);
        // FIXME: Add your code here.
        _headerList = headerList();
        _col = colName;
        _ref = ref;
    }

    @Override
    protected boolean keep() {
        // FIXME: Replace this line with your code.
        int i =_headerList.indexOf(_col);
        if (candidateNext().getValue(i).compareTo(_ref) > 0) {
            return true;
        } else {
            return false;
        }
    }

    // FIXME: Add instance variables?
    private List<String> _headerList;
    private String _col;
    private String _ref;
}
