package JL2;

public class Sort {

    public static void sort(Vector vector, boolean incr){
        int size = vector.getSize()-1;
        int startIndex = 0;
        double temp;
        while (size > startIndex) {
            for (int i = startIndex; i < size; i++) {
                if (incr ? vector.getValue(i) > vector.getValue(i + 1) : vector.getValue(i) < vector.getValue(i + 1)) {
                    temp = vector.getValue(i);
                    vector.setValue(i, vector.getValue(i + 1));
                    vector.setValue(i + 1, temp);
                }
            }
            --size;
            for (int i = size; i > 0; i--) {
                if (incr ? vector.getValue(i) < vector.getValue(i - 1) : vector.getValue(i) > vector.getValue(i - 1)) {
                    temp = vector.getValue(i);
                    vector.setValue(i, vector.getValue(i - 1));
                    vector.setValue(i - 1, temp);
                }

            }
            ++startIndex;
        }
    }
}
