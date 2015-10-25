package JAVA.JL5.Vector.Exceptions;

public class IncompatibleVectorSizesException extends  Exception{
    public IncompatibleVectorSizesException() {
        super("Incompatible vector sizes");
    }

    public IncompatibleVectorSizesException(String message) {
        super(message);
    }
}
