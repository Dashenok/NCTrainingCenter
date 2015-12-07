package jl15.vector;


import jl15.vector.patterns.ArrayVectorFactory;
import jl15.vector.patterns.JVectorAdapter;
import jl15.vector.patterns.ProtectedVector;

import java.io.*;

public class Vectors {

    private static VectorFactory vectorFactory = new ArrayVectorFactory();

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
        int l = dataInputStream.readInt();
        Vector vector = createInstance(l);
        for (int i = 0; i < l; i++) {
            vector.setElement(i, dataInputStream.readDouble());
        }
        return vector;

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
            Vector v = createInstance(size);
            for (int i = 0; i < size; i++) {
                st.nextToken();
                v.setElement(i, st.nval);
            }
            return v;

    }

    public static void setVectorFactory(VectorFactory vectorFactoryIn){
        vectorFactory = vectorFactoryIn;
    }

    public static Vector createInstance(int size){
        return vectorFactory.createVector(size);
    }
    public static Vector createInstance(){
        return vectorFactory.createVector();
    }


    public static Vector getProtectedVector(Vector vector){
        return new ProtectedVector(vector);
    }

    public static Vector getVectorAdapter(java.util.Vector jVector){
        return new JVectorAdapter(jVector);
    }
}
