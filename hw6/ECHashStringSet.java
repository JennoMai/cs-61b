import java.util.ArrayList;
import java.util.List;

/** A set of String values.
 *  @author Jenny Mei
 */
class ECHashStringSet implements StringSet {
    /** Sorted buckets containing lists of strings. Initially contains 10 buckets. */
    ArrayList<String>[] buckets = new ArrayList[10];

    int n = 0;

    @Override
    public void put(String s) {
        if (contains(s)) {
            return;
        }
        if ((n + 1) / buckets.length > 5) {
            resize();
        }

        n += 1;
        int code = s.hashCode();
        if (code < 0) {
            code = code & 0x7fffffff;
        }
        int bucket = code % buckets.length;

        if (buckets[bucket] == null) {
            buckets[bucket] = new ArrayList<>();
        }

        buckets[bucket].add(s);
    }

    @Override
    public boolean contains(String s) {
        int code = s.hashCode();
        if (code < 0) {
            code = code & 0x7fffffff;
        }
        int bucket = code % buckets.length;
        if (buckets[bucket] == null) {
            return false;
        }
        return buckets[bucket].contains(s);
    }

    @Override
    public List<String> asList() {
        ArrayList<String> result = new ArrayList<>();
        for (ArrayList<String> list : buckets) {
            if (list != null) {
                result.addAll(list);
            }
        }
        return result;
    }

    private void resize() {
        List<String> prev = asList();
        buckets = new ArrayList[buckets.length*2];
        for (String s : prev) {
            int code = s.hashCode();
            if (code < 0) {
                code = code & 0x7fffffff;
            }
            int bucket = code % buckets.length;

            if (buckets[bucket] == null) {
                buckets[bucket] = new ArrayList<>();
            }

            buckets[bucket].add(s);
        }
    }
}
