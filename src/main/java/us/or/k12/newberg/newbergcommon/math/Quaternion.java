package us.or.k12.newberg.newbergcommon.math;

/**
 * A semi-complete and functional quaternion
 *
 * @author Garrison Peacock
 * @version 1.1
 */
public class Quaternion
{
    /**
     * X component
     */
    public float x;
    /**
     * Y component
     */
    public float y;
    /**
     * Z component
     */
    public float z;
    /**
     * W component
     */
    public float w;

    /**
     * Initializes a (0, 0, 0, 1) quaternion
     */
    public Quaternion()
    {
        this(0, 0, 0, 1);
    }

    /**
     * Initializes a quaternion
     *
     * @param x X value
     * @param y Y value
     * @param z Z value
     * @param w W value
     */
    public Quaternion(float x, float y, float z, float w)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Initializes a rotation quaternion
     *
     * @param axis  Axis of rotation
     * @param angle Angle of rotation
     * @return Rotation quaternion
     */
    public Quaternion InitRotation(Vector3f axis, float angle)
    {
        float sinHalfAngle = (float) Math.sin(angle / 2);
        float cosHalfAngle = (float) Math.cos(angle / 2);

        this.x = axis.x * sinHalfAngle;
        this.y = axis.y * sinHalfAngle;
        this.z = axis.z * sinHalfAngle;
        this.w = cosHalfAngle;

        return this;
    }

    /**
     * Length of the quaternion
     *
     * @return Quaternion length
     */
    public float Length()
    {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    /**
     * Normalize the quaternion
     *
     * @return Normalized quaternion
     */
    public Quaternion Normalized()
    {
        float length = Length();

        x /= length;
        y /= length;
        z /= length;
        w /= length;

        return this;
    }

    /**
     * Conjugate of the current quaternion
     *
     * @return Conjugate
     */
    public Quaternion Conjugate()
    {
        return new Quaternion(-x, -y, -z, w);
    }

    /**
     * Multiply this against r
     *
     * @param r Another quaternion
     * @return Newly multiplied quaternion
     */
    public Quaternion Mul(Quaternion r)
    {
        float w_ = w * r.w - x * r.x - y * r.y - z * r.z;
        float x_ = x * r.w + w * r.x + y * r.z - z * r.y;
        float y_ = y * r.w + w * r.y + z * r.x - x * r.z;
        float z_ = z * r.w + w * r.z + x * r.y - y * r.x;

        return new Quaternion(x_, y_, z_, w_);
    }

    /**
     * Multiply this against r
     *
     * @param r A Vector3f
     * @return Newly multiplied quaternion
     */
    public Quaternion Mul(Vector3f r)
    {
        float w_ = -x * r.x - y * r.y - z * r.z;
        float x_ = w * r.x + y * r.z - z * r.y;
        float y_ = w * r.y + z * r.x - x * r.z;
        float z_ = w * r.z + x * r.y - y * r.x;

        return new Quaternion(x_, y_, z_, w_);
    }

    /**
     * Turns this into a rotation matrix
     *
     * @return 4x4 rotation matrix
     */
    public Matrix4f ToRotationMatrix()
    {
        return new Matrix4f().InitRotation(GetForawrd(), GetUp(), GetRight());
    }

    /**
     * Forward direction
     *
     * @return Forward
     */
    public Vector3f GetForawrd()
    {
        return new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
    }

    /**
     * Back direction
     *
     * @return Back
     */
    public Vector3f GetBack()
    {
        return new Vector3f(-2.0f * (x * z - w * y), -2.0f * (y * z + w * x), -(1.0f - 2.0f * (x * x + y * y)));
    }

    /**
     * Up direction
     *
     * @return Up
     */
    public Vector3f GetUp()
    {
        return new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
    }

    /**
     * Down direction
     *
     * @return Down
     */
    public Vector3f GetDown()
    {
        return new Vector3f(-2.0f * (x * y + w * z), -(1.0f - 2.0f * (x * x + z * z)), -2.0f * (y * z - w * x));
    }

    /**
     * Right direction
     *
     * @return Right
     */
    public Vector3f GetRight()
    {
        return new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));
    }

    /**
     * Left direction
     *
     * @return Left
     */
    public Vector3f GetLeft()
    {
        return new Vector3f(-(1.0f - 2.0f * (y * y + z * z)), -2.0f * (x * y - w * z), -2.0f * (x * z + w * y));
    }
}