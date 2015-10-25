package JAVA.JL3.Exceptions;

public class VectorIndexOutOfBoundsException extends IndexOutOfBoundsException{
    public VectorIndexOutOfBoundsException() {
        super("Vector index is out of bounds");
    }

    public VectorIndexOutOfBoundsException(String s) {
        super(s);
    }
}
