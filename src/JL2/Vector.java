package JL2;

public class Vector {
    protected int size;
    protected double[] mass;
    Vector (int size) throws NegativeArraySizeException{
        this.size = size;
        this.mass = new double[size];
    }

    public int getSize() {
        return size;
    }

    public double getValue(int ind) throws NullPointerException{
        try {
            return mass[ind];
        } catch (NullPointerException e){
            return 0;
        }

    }

    public void setValue(int ind, double value) throws ArrayIndexOutOfBoundsException{
        try {
            mass[ind] = value;
        } catch (ArrayIndexOutOfBoundsException e){

        }

    }

    public void fillFromMass(double[] newMass){
        int newMassLength = newMass.length;
        if (size != newMassLength){
            size = newMassLength;
            mass = new double[size];
        }
        for (int i = 0; i < size; i++) {
            mass[i] = newMass[i];
        }
    }

    public void fillFromVector(Vector newVector){

        int newMassLength = newVector.mass.length;
        if (size != newMassLength){
            size = newMassLength;
            mass = new double[size];
        }
        for (int i = 0; i < size; i++) {
            mass[i] = newVector.mass[i];
        }
    }

    public boolean equal(Vector newVector){

        int newMassLength = newVector.mass.length;

        if (size != newMassLength){
           System.out.println("Vectors have different length");
            return false;
        }

        for (int i = 0; i < newMassLength; i++) {
            if (mass[i] != newVector.mass[i]) return false;
        }
        return true;
    }

    public double getMaxValue(){
        double maxValue = mass[0];
        for (int i = 1; i < size; i++) {
            maxValue = getMax(maxValue, mass[i]);

        }
        return maxValue;
    }

    private double getMax(double a, double b){
        return a > b ? a : b;
    }

    public double getMinValue(){
        double minValue = mass[0];
        for (int i = 1; i < size; i++) {
            minValue = getMin(minValue, mass[i]);

        }
        return minValue;
    }

    private double getMin(double a, double b){
        return a < b ? a : b;
    }

    public void mult (double factor){
        for (int i = 0; i < size; i++) {
            mass[i] = mass[i]*factor;
        }
    }

    public void sum(Vector newVector){


        if (size != newVector.mass.length){
            System.out.println("Vectors have different length");
            return;
        }

        for (int i = 0; i < size; i++) {
            mass[i] = mass[i] + newVector.mass[i];
        }
    }

    public void sort(boolean incr){
        Sort.sort(this, incr);
    }
}
