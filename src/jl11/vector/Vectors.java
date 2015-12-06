package jl11.vector;


import jl11.vector.impl.ArrayVector;

import java.io.*;

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

    public static void outputVector(Vector v, OutputStream out) throws IOException{
            int l = v.getSize();
            DataOutputStream dataOutputStream = new DataOutputStream(out);
            dataOutputStream.writeInt(l);
            for (int i = 0; i < l; i++) {
                dataOutputStream.writeDouble(v.getElement(i));
            }

    }
    public static Vector inputVector(InputStream in) throws IOException{
        DataInputStream dataInputStream = new DataInputStream(in);
        try {
            int l = dataInputStream.readInt();
            ArrayVector arrayVector = new ArrayVector(l);
            for (int i = 0; i < l; i++) {
                arrayVector.setElement(i, dataInputStream.readDouble());
            }
            return arrayVector;
        }catch (Exception e){
            e.printStackTrace();
        }
//        ArrayVector arrayVector = new ArrayVector(l);
//        for (int i = 0; i < l; i++) {
//            arrayVector.setElement(i, dataInputStream.readDouble());
//        }
        return null;

    }
    public static void writeVector(Vector v, Writer out){

        int l = v.getSize();
        PrintWriter printWriter = new PrintWriter(out);
        printWriter.print(l);
        for (int i = 0; i < l; i++) {
            printWriter.print(" " + v.getElement(i));
        }
        printWriter.println();

    }
    public static Vector readVector(Reader in) throws IOException{
            StreamTokenizer st = new StreamTokenizer(in);
            st.nextToken();
            int size = (int) st.nval;
            Vector v = new ArrayVector(size);
            for (int i = 0; i < size; i++) {
                st.nextToken();
                v.setElement(i, st.nval);
            }
            return v;

    }
}
