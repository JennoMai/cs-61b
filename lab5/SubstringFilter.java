import java.util.List;

/**
 * TableFilter to filter for containing substrings.
 *
 * @author Matthew Owen
 */
public class SubstringFilter extends TableFilter {

    public SubstringFilter(Table input, String colName, String subStr) {
        super(input);
        // FIXME: Add your code here.
        _headerList = headerList();
        _col = colName;
        _subStr = subStr;
    }

    @Override
    protected boolean keep() {
        // FIXME: Replace this line with your code.
        int i = _headerList.indexOf(_col);
        if (candidateNext().getValue(i).contains(_subStr)) {
            return true;
        } else {
            return false;
        }
    }

    // FIXME: Add instance variables?
    private List<String> _headerList;
    private String _col;
    private String _subStr;
}
