package com.mygdx.game;

import java.util.HashMap;
import java.util.Hashtable;

public class SystemProperties {
    final public static Hashtable<String, Integer> entities = new Hashtable<String, Integer>(){
        {put("Sun", 0); put("Mercury", 1); put("Venus", 2);put("Earth", 3);put("Moon", 4);put("Mars", 5);put("Jupiter", 6);put("Saturn", 7);put("Titan", 8);
            put("Neptune", 9); put("Uranus", 10);}
    }; // hashtable with all entities presented in the system. Key is represented by the name and value is index of the entity

    //BELOW GOES ARRAYS OF ENTITY PROPERTIES(they're placed in array relatively to their index)//
    final public static Vector[] coordinates ={
            new Vector(0,0,0),new Vector(7833268.43923962,44885949.3703908, 2867693.20054382),new Vector(-28216773.9426889,103994008.541512,3012326.64296788 ),
            new Vector(-148186906.893642,-27823158.5715694,33746.8987977113 ),new Vector(-148458048.395164,-27524868.1841142,70233.6499287411 ),
            new Vector(-159116303.422552,189235671.561057,7870476.08522969 ),new Vector(692722875.928222,258560760.813524,-16570817.7105996 ),
            new Vector(1253801723.95465,-760453007.810989,-36697431.1565206 ),
            new Vector(1254501624.95946,-761340299.067828,-36309613.8378104 ),
            new Vector(4454487339.09447,-397895128.763904,-94464151.3421107 ),
            new Vector(1958732435.99338,2191808553.21893,-17235283.8321992 )
    };

    final public static Vector[] velocities = {new Vector(0.0, 0.0, 0.0),
            new Vector(-57.496748013982811,11.52095127176,6.21695374334136),new Vector(-34.0236737066136,-8.96521274688838,1.84061735279188),
            new Vector(5.05251577575409,-29.3926687625899,0.00170974277401292),new Vector(4.34032634654904,4.34032634654904,-0.0116103535014229),
            new Vector(-17.6954469224752,13.4635253412947,0.152331928200531),new Vector(-4.71443059866156,12.8555096964427,0.0522118126939208),
            new Vector(4.46781341335014,8.23989540475628,-0.320745376969732),new Vector(8.99593229549645,11.1085713608453,-2.25130986174761),
            new Vector(0.447991656952326,5.44610697514907,-0.122638125365954),new Vector(-5.12766216337626,4.22055347264457,0.0821190336403063)};

    final public static double[] masses={1.9885e30,3.302e23,48.685E23,5.97219E24,7.349e22,6.4171e23,1.89818722e19,5.6834e26,13455.3e19,102.409e24,86.813e24};
    final public static double[] radii ={1,1,1,6370,1,1,1,1,2575,1,1};
    // temporary filled with ones as we don't use radii of any planets except Earth and Titan

}