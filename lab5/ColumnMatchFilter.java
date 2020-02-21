import java.util.Iterator;

/**
 * TableFilter to filter for entries whose two columns match.
 *
 * @author Matthew Owen
 */
import java.util.List;

public class ColumnMatchFilter extends TableFilter {

    public ColumnMatchFilter(Table input, String colName1, String colName2) {
        super(input);
        // FIXME: Add your code here.
        _headerList = headerList();
        _col1 = colName1;
        _col2 = colName2;
    }

    @Override
    protected boolean keep() {
        // FIXME: Replace this line with your code.
        int first = _headerList.indexOf(_col1);
        int sec = _headerList.indexOf(_col2);
        Table.TableRow row = candidateNext();

        if (row.getValue(first).equals(row.getValue(sec))) {
            return true;
        } else {
        return false;
        }
    }

    // FIXME: Add instance variables?
    private List<String> _headerList;
    private String _col1;
    private String _col2;
}
