public class Eulers {
    public static double a = 4;
    public static void main (String[] args) {
        double startInput = 0;
        double result = 1;
        double h = 0.1;
        double approximation = calculation(startInput, result, startInput + h, h);
        System.out.println("first approximation");
        System.out.println(approximation);
        System.out.println("first error");
        double error = computeError(approximation, h);
        System.out.println(error);

        double secondApproximation = calculation(startInput, result, startInput + h, h/2);
        double secondError = computeError(secondApproximation, h);
        System.out.println("q8 answer =");
        System.out.println(error/secondError);


        // System.out.println(0.7520603590833794/0.7514968598994781);
    }

public static double calculation(double startInput, double result, double goal, double increment) {
    if (startInput == goal) {
        return result;
    } else  {
        System.out.println("f(" + (startInput + increment) + ") = f(" + startInput + ") + f'(" + startInput + ") * " + increment );
        result = result + increment * calculateTheSecondPart(result);
        startInput += increment;
        System.out.println(" = " + result);
        return calculation(startInput, result, goal, increment);
    }

}

public static double calculateTheSecondPart(double value) {
    return a + value;
}

public static double computeError(double approximation, double t) {
    // double inputForExact = 1 + (a + 1) * 0.1; 
    double exact = (1 + a) * Math.exp(t) - a;
    double error = Math.abs((approximation - exact) / exact);
    return error;
}
}

