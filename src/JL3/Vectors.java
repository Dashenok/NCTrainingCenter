package JL3;

public class Vectors {

    public static void sort(Vector vector, boolean incr){
        int size = vector.getSize()-1;
        int startIndex = 0;
        double temp;
        while (size > startIndex) {
            for (int i = startIndex; i < size; i++) {
                if (incr ? vector.getElement(i) > vector.getElement(i + 1) : vector.getElement(i) < vector.getElement(i + 1)) {
                    temp = vector.getElement(i);
                    vector.setElement(i, vector.getElement(i + 1));
                    vector.setElement(i + 1, temp);
                }
            }
            --size;
            for (int i = size; i > 0; i--) {
                if (incr ? vector.getElement(i) < vector.getElement(i - 1) : vector.getElement(i) > vector.getElement(i - 1)) {
                    temp = vector.getElement(i);
                    vector.setElement(i, vector.getElement(i - 1));
                    vector.setElement(i - 1, temp);
                }

            }
            ++startIndex;
        }
    }
}
