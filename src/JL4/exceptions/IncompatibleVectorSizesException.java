package JL4.exceptions;

public class IncompatibleVectorSizesException extends  Exception{
    public IncompatibleVectorSizesException() {
        super("Incompatible JL56.vector sizes");
    }

    public IncompatibleVectorSizesException(String message) {
        super(message);
    }
}
