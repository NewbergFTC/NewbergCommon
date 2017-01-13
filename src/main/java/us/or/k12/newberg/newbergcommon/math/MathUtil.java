package us.or.k12.newberg.newbergcommon.math;

/**
 * FTC team 6712 Bulletproof
 */

public class MathUtil
{
    private static final double EPSILON = 0.000001; // A very tiny number (10e-6)

    public static boolean FuzzyEquals(double value1, double value2)
    {
        return FuzzyEquals(value1, value2, EPSILON);
    }

    public static boolean FuzzyEquals(double value1, double value2, double epsilon)
    {
        boolean result = false;

        double diff = value1 - value2;

        if (Math.abs(diff) < epsilon)
        {
            result = true;
        }

        return result;
    }

    public static float Mapf(float minIn, float maxIn, float minOut, float maxOut, float value)
    {
        float result = (value - minIn) * (maxOut - minOut) / (maxIn - minIn) + minOut;

        return result;
    }
}
