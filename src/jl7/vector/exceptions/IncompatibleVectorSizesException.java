package jl7.vector.exceptions;

public class IncompatibleVectorSizesException extends  Exception{
    public IncompatibleVectorSizesException() {
        super("Incompatible vector sizes");
    }

    public IncompatibleVectorSizesException(String message) {
        super(message);
    }
}
