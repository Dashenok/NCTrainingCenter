package jl15;

import jl15.vector.container.VectorMap;
import jl15.vector.impl.ArrayVector;
import jl15.vector.impl.LinkedVector;

public class Main {
    public static void main(String[] args) {
        double[] mass = {1.0, 0.9, -6.4, 8, -0.4};
         double[] mass2 = {-6.4, 8, -0.4};
         double[] mass3 = {2.0, 5, -0.6};

        ArrayVector arrayVectorInstance = new ArrayVector(5);
        LinkedVector linkedVectorInstance = new LinkedVector();
        arrayVectorInstance.fillFromMass(mass);
        linkedVectorInstance.fillFromMass(mass);
        for (Double elem: arrayVectorInstance){
            System.out.println(elem);
        }
        for (Object elem: linkedVectorInstance){
            System.out.println((elem).toString());
        }

        ArrayVector vector1 = new ArrayVector(5);
        ArrayVector vector2 = new ArrayVector(3);
        ArrayVector vector3 = new ArrayVector(3);

        vector1.fillFromMass(mass);
        vector2.fillFromMass(mass2);
        vector3.fillFromMass(mass3);

        VectorMap vectorMap = new VectorMap();
        vectorMap.clear();
        vectorMap.put(1, vector2);
        vectorMap.put(2, vector3);

        for (Object v: vectorMap) {
            System.out.println(v);
        }

    }


}
