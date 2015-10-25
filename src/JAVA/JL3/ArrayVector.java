package JAVA.JL3;

import JAVA.JL3.Exceptions.*;

public class ArrayVector implements Vector {
    protected int size;
    protected double[] mass;
    ArrayVector (int size) throws VectorIndexOutOfBoundsException{
        if (size < 0) {
            throw new VectorIndexOutOfBoundsException();
        }
        this.size = size;
        this.mass = new double[size];
    }

    @Override
    public double getElement(int index){
        if (index < 0 || index > size){
            throw new VectorIndexOutOfBoundsException();
        }
        return  mass[index];
    }

    @Override
    public void setElement(int index, double element){
        if (index < 0 || index > size){
            throw new VectorIndexOutOfBoundsException();
        }
        mass[index] = element;
    }

    public int getSize() {
        return size;
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

        int newMassLength = newVector.getSize();
        if (size != newMassLength){
            size = newMassLength;
            mass = new double[size];
        }
        for (int i = 0; i < size; i++) {
            mass[i] = newVector.getElement(i);
        }
    }

    public boolean equal(Vector newVector){

        int newMassLength = newVector.getSize();

        if (size != newMassLength){
            System.out.println("Vectors have different length");
            return false;
        }

        for (int i = 0; i < newMassLength; i++) {
            if (mass[i] != newVector.getElement(i)) return false;
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

    public void sum(Vector newVector) throws IncompatibleVectorSizesException{


        if (size != newVector.getSize()){
            throw new IncompatibleVectorSizesException();
        }

        for (int i = 0; i < size; i++) {
            mass[i] = mass[i] + newVector.getElement(i);
        }
    }

    public void sort(boolean incr){
        Vectors.sort(this, incr);
    }


}
