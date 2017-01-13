package us.or.k12.newberg.newbergcommon.math;

/**
 * A semi-complete and functional vector2 class
 *
 * @author Garrison Peacock
 * @version 1.0
 */
public class Vector2f
{
    /** X component */
    public float x;
    /** Y component */
    public float y;

    /**
     * Initializes a Vector2f with the value (0, 0)
     */
    public Vector2f()
    {
        this(0, 0);
    }

    /**
     * Initializes a Vector2f with (a, a)
     *
     * @param a The x and y component
     */
    public Vector2f(float a)
    {
        this(a, a);
    }

    /**
     * Initializes a Vector2f with the value (x, y)
     *
     * @param x X component
     * @param y Y component
     */
    public Vector2f(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Length of the vector
     *
     * @return Length
     */
    public float Length()
    {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Dot product of the vector
     *
     * @param r Other vector
     * @return Dot product
     */
    public float Dot(Vector2f r)
    {
        return x * r.x + y * r.y;
    }

    /**
     * Normalize this
     *
     * @return Normalized vector
     */
    public Vector2f Normalized()
    {
        float length = Length();

        return new Vector2f(x / length, y / length);
    }

    /**
     * Rotate the vector with the value angle
     *
     * @param angle Value to rotate by
     * @return Newly rotated vector
     */
    public Vector2f Rotate(float angle)
    {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        return new Vector2f((float)(x * cos - y * sin),(float)(x * sin + y * cos));
    }

    /**
     * Normalize first
     */
    public double AngleWithXAxis()
    {
        double result = Math.toDegrees(Math.atan(y / x));

        return result;
    }

    /**
     * Lerps the vector
     *
     * @param dest The Vector2f to be lerped into
     * @param lerpFactor The lerp factor
     * @return The lerped vector
     */
    public Vector2f Lerp(Vector2f dest, float lerpFactor)
    {
        return dest.Sub(this).Mul(lerpFactor).Add(this);
    }

    /**
     * Add this and anther Vector2f
     *
     * @param r Another Vector2f
     * @return Newly added vector
     */
    public Vector2f Add(Vector2f r)
    {
        return new Vector2f(x + r.x, y + r.y);
    }

    /**
     * Add the vector with r
     *
     * @param r Value to add
     * @return Newly added vector
     */
    public Vector2f Add(float r)
    {
        return new Vector2f(x + r, y + r);
    }

    /**
     * Subtract this and another Vector2f
     *
     * @param r Other vector
     * @return Newly subtracted vector
     */
    public Vector2f Sub(Vector2f r)
    {
        return new Vector2f(x - r.x, y - r.y);
    }

    /**
     * Subtract this and a value
     *
     * @param r Value to subtract
     * @return Newly subtracted vector
     */
    public Vector2f Sub(float r)
    {
        return new Vector2f(x - r, y - r);
    }

    /**
     * Multiply this and another Vector2f
     *
     * @param r Vector to multiply
     * @return Newly multiplied vector
     */
    public Vector2f Mul(Vector2f r)
    {
        return new Vector2f(x * r.x, y * r.y);
    }

    /**
     * Multiply this and a value
     *
     * @param r Value to multiply
     * @return Newly multiplied vector
     */
    public Vector2f Mul(float r)
    {
        return new Vector2f(x * r, y * r);
    }

    /**
     * Divide this and another Vector2f
     *
     * @param r Vector to divide by
     * @return Newly divided vector
     */
    public Vector2f Div(Vector2f r)
    {
        return new Vector2f(x / r.x, y / r.y);
    }

    /**
     * Divide this and a value
     *
     * @param r Value to divide by
     * @return Newly divided vector
     */
    public Vector2f Div(float r)
    {
        return new Vector2f(x / r, y / r);
    }

    /**
     * Absolute value of this
     *
     * @return Absolute value
     */
    public Vector2f Abs()
    {
        return new Vector2f(Math.abs(x), Math.abs(y));
    }

    /**
     * Cross product of the and another Vector2f
     *
     * @param r Vector to cross
     * @return Cross product
     */
    public float Cross(Vector2f r)
    {
        return x * r.y - y * r.x;
    }

    /**
     * Component with the highest value
     *
     * @return Greatest component
     */
    public float Max()
    {
        return Math.max(x, y);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Vector2f)
            return x == ((Vector2f) obj).x && y == ((Vector2f) obj).y;
        else
            return false;
    }

    @Override
    public String toString()
    {
        return "Vector2f{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}