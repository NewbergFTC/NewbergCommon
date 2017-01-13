package us.or.k12.newberg.newbergcommon.math;

/**
 * FTC team 6712 Bulletproof
 */
public class Vector3f
{
    /** The x component */
    public float x;
    /** The y component */
    public float y;
    /** The z component */
    public float z;

    /**
     * Initializes a vector3f with a Vector2f and z component
     * @param vector2f The x and y values
     * @param z        The z value
     */
    public Vector3f(Vector2f vector2f, float z)
    {
        this(vector2f.x, vector2f.y, z);
    }

    /**
     * Initializes a vector3f with a x, y, and z
     *
     * @param x The x value
     * @param y The y value
     * @param z The z value
     */
    public Vector3f(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Length of the vector
     *
     * @return length
     */
    public float Length()
    {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Dot product with another Vector3f
     *
     * @param r Vector3f
     * @return Dot product
     */
    public float Dot(Vector3f r)
    {
        return x * r.x + y * r.y + z * r.z;
    }

    /**
     * Cross product with another Vector3f
     *
     * @param r Vector3f
     * @return Cross product
     */
    public Vector3f Cross(Vector3f r)
    {
        float x_ = y * r.z - z * r.y;
        float y_ = z * r.x - x * r.z;
        float z_ = x * r.y - y * r.x;

        return new Vector3f(x_, y_, z_);
    }

    /**
     * Normalize the vector
     *
     * @return Normalized vector
     */
    public Vector3f Normalized()
    {
        float length = Length();

        return new Vector3f(x / length, y / length, z / length);
    }

    /**
     * Rotate the vector a certain angle along an axis.
     *
     * @param angle Angle of the rotation
     * @param axis Axis of rotation
     * @return Rotated vector
     */
    public Vector3f Rotate(float angle, Vector3f axis)
    {
        Quaternion rotation = new Quaternion().InitRotation(axis, angle);
        Quaternion conjugate = rotation.Conjugate();

        Quaternion w = rotation.Mul(this).Mul(conjugate);

        return new Vector3f(w.x, w.y, w.z);
    }

    /**
     * Lerps the vector
     *
     * @param dest The Vector3f to be lerped into
     * @param lerpFactor The lerp factor
     * @return The lerped vector
     */
    public Vector3f Lerp(Vector3f dest, float lerpFactor)
    {
        return dest.Sub(this).Mul(lerpFactor).Add(this);
    }

    /**
     * Add this to another vector
     *
     * @param r The other vector
     * @return Newly added vector
     */
    public Vector3f Add(Vector3f r)
    {
        return new Vector3f(x + r.x, y + r.y, z + r.z);
    }

    /**
     * Add r to all components (x, y, z)
     *
     * @param r The value to add
     * @return Newly added vector
     */
    public Vector3f Add(float r)
    {
        return new Vector3f(x + r, y + r, z + r);
    }

    /**
     * Subtract r from this
     *
     * @param r The other vector
     * @return Newly subtracted vector
     */
    public Vector3f Sub(Vector3f r)
    {
        return new Vector3f(x - r.x, y - r.y, z - r.z);
    }

    /**
     * Subtract r from this
     *
     * @param r The value to subtract
     * @return Newly subtracted vector
     */
    public Vector3f Sub(float r)
    {
        return new Vector3f(x - r, y - r, z - r);
    }

    /**
     * Multiply this and r
     *
     * @param r The vector to multiply against
     * @return Newly multiplied vector
     */
    public Vector3f Mul(Vector3f r)
    {
        return new Vector3f(x * r.x, y * r.y, z * r.z);
    }

    /**
     * Multiply this and r
     *
     * @param r The value to multiply against
     * @return Newly multiplied vector
     */
    public Vector3f Mul(float r)
    {
        return new Vector3f(x * r, y * r, z * r);
    }

    /**
     * Divide this and r
     *
     * @param r The vector to divide
     * @return Newly divided vector
     */
    public Vector3f Div(Vector3f r)
    {
        return new Vector3f(x / r.x, y / r.y, z / r.z);
    }

    /**
     * Divide this and r
     *
     * @param r The value to divide
     * @return Newly divided vector
     */
    public Vector3f Div(float r)
    {
        return new Vector3f(x / r, y / r, z / r);
    }

    /**
     * Absolute value of the vector
     *
     * @return The absolute value
     */
    public Vector3f Abs()
    {
        return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    /**
     * The greatest component
     *
     * @return greatest component
     */
    public float Max()
    {
        return Math.max(x, Math.max(y, z));
    }

    public Vector2f GetXY()
    {
        return new Vector2f(x, y);
    }

    public Vector2f GetYZ()
    {
        return new Vector2f(y, z);
    }

    public Vector2f GetZX()
    {
        return new Vector2f(z, x);
    }

    public Vector2f GetYX()
    {
        return new Vector2f(y, x);
    }

    public Vector2f GetZY()
    {
        return new Vector2f(z, y);
    }

    public Vector2f GetXZ()
    {
        return new Vector2f(x, z);
    }

    public void Set(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Vector3f)
            return x == ((Vector3f) obj).x && y == ((Vector3f) obj).y && z == ((Vector3f) obj).z;
        else
            return obj instanceof Vector2f && x == ((Vector2f) obj).x && y == ((Vector2f) obj).y;
    }

    @Override
    public String toString()
    {
        return "Vector3f{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
