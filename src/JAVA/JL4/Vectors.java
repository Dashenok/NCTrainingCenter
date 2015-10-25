package JAVA.JL4;

public class Vectors {

    public static void sort(Vector arrayVector, boolean incr){
        int size = arrayVector.getSize()-1;
        int startIndex = 0;
        double temp;
        while (size > startIndex) {
            for (int i = startIndex; i < size; i++) {
                if (incr ? arrayVector.getElement(i) > arrayVector.getElement(i + 1) : arrayVector.getElement(i) < arrayVector.getElement(i + 1)) {
                    temp = arrayVector.getElement(i);
                    arrayVector.setElement(i, arrayVector.getElement(i + 1));
                    arrayVector.setElement(i + 1, temp);
                }
            }
            --size;
            for (int i = size; i > 0; i--) {
                if (incr ? arrayVector.getElement(i) < arrayVector.getElement(i - 1) : arrayVector.getElement(i) > arrayVector.getElement(i - 1)) {
                    temp = arrayVector.getElement(i);
                    arrayVector.setElement(i, arrayVector.getElement(i - 1));
                    arrayVector.setElement(i - 1, temp);
                }

            }
            ++startIndex;
        }
    }
}
