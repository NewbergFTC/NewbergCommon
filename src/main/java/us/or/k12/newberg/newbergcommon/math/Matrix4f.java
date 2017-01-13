package us.or.k12.newberg.newbergcommon.math;

/**
 * FTC team 6712 Bulletproof
 */
public class Matrix4f
{
    /** The actual matrix */
    private float[][] m;

    /**
     * Initializes a empty 4x4 matrix
     */
    public Matrix4f()
    {
        m = new float[4][4];
    }

    /**
     * Initializes a 4x4 identity matrix
     *
     * @return 4x4 identity matrix
     */
    public Matrix4f InitIdentity()
    {
        m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
        m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;	m[1][3] = 0;
        m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	m[2][3] = 0;
        m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;

        return this;
    }

    /**
     * Initializes a 4x4 translation matrix
     *
     * @param x X translation
     * @param y Y translation
     * @param z Z translation
     * @return 4x4 translation matrix
     */
    public Matrix4f InitTranslation(float x, float y, float z)
    {
        m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = x;
        m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;	m[1][3] = y;
        m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	m[2][3] = z;
        m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;

        return this;
    }

    /**
     * Initializes a 4x4 rotation matrix
     *
     * @param x Rotation on the x
     * @param y Rotation on the y
     * @param z Rotation on the z
     * @return 4x4 rotation matrix
     */
    public Matrix4f InitRotation(float x, float y, float z)
    {
        Matrix4f rx = new Matrix4f();
        Matrix4f ry = new Matrix4f();
        Matrix4f rz = new Matrix4f();

        x = (float) Math.toRadians(x);
        y = (float) Math.toRadians(y);
        z = (float) Math.toRadians(z);

        rz.m[0][0] = (float) Math.cos(z);rz.m[0][1] = -(float) Math.sin(z);rz.m[0][2] = 0;				rz.m[0][3] = 0;
        rz.m[1][0] = (float) Math.sin(z);rz.m[1][1] = (float) Math.cos(z);rz.m[1][2] = 0;					rz.m[1][3] = 0;
        rz.m[2][0] = 0;					rz.m[2][1] = 0;					rz.m[2][2] = 1;					rz.m[2][3] = 0;
        rz.m[3][0] = 0;					rz.m[3][1] = 0;					rz.m[3][2] = 0;					rz.m[3][3] = 1;

        rx.m[0][0] = 1;					rx.m[0][1] = 0;					rx.m[0][2] = 0;					rx.m[0][3] = 0;
        rx.m[1][0] = 0;					rx.m[1][1] = (float) Math.cos(x);rx.m[1][2] = -(float) Math.sin(x);rx.m[1][3] = 0;
        rx.m[2][0] = 0;					rx.m[2][1] = (float) Math.sin(x);rx.m[2][2] = (float) Math.cos(x);rx.m[2][3] = 0;
        rx.m[3][0] = 0;					rx.m[3][1] = 0;					rx.m[3][2] = 0;					rx.m[3][3] = 1;

        ry.m[0][0] = (float) Math.cos(y);ry.m[0][1] = 0;					ry.m[0][2] = -(float) Math.sin(y);ry.m[0][3] = 0;
        ry.m[1][0] = 0;					ry.m[1][1] = 1;					ry.m[1][2] = 0;					ry.m[1][3] = 0;
        ry.m[2][0] = (float) Math.sin(y);ry.m[2][1] = 0;					ry.m[2][2] = (float) Math.cos(y);ry.m[2][3] = 0;
        ry.m[3][0] = 0;					ry.m[3][1] = 0;					ry.m[3][2] = 0;					ry.m[3][3] = 1;

        m = rz.Mul(ry.Mul(rx)).GetM();

        return this;
    }

    /**
     * Initializes a 4x4 scale matrix
     *
     * @param x X scale amount
     * @param y Y scale amount
     * @param z Z scale amount
     * @return 4x4 scale matrix
     */
    public Matrix4f InitScale(float x, float y, float z)
    {
        m[0][0] = x;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
        m[1][0] = 0;	m[1][1] = y;	m[1][2] = 0;	m[1][3] = 0;
        m[2][0] = 0;	m[2][1] = 0;	m[2][2] = z;	m[2][3] = 0;
        m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;

        return this;
    }

    /**
     * Initializes a 4x4 perspective matrix
     *
     * @param fov Field of view
     * @param aspectRatio Window/Display aspect ratio
     * @param zNear Closest visible z value
     * @param zFar  Farthest visible z value
     * @return 4x4 perspective matrix
     */
    public Matrix4f InitPerspective(float fov, float aspectRatio, float zNear, float zFar)
    {
        float tanHalfFOV = (float) Math.tan(fov / 2);
        float zRange = zNear - zFar;

        m[0][0] = 1.0f / (tanHalfFOV * aspectRatio);	m[0][1] = 0;					m[0][2] = 0;                    	m[0][3] = 0;
        m[1][0] = 0;						            m[1][1] = 1.0f / tanHalfFOV;	m[1][2] = 0;	                    m[1][3] = 0;
        m[2][0] = 0;						            m[2][1] = 0;					m[2][2] = (-zNear -zFar)/zRange;	m[2][3] = 2 * zFar * zNear / zRange;
        m[3][0] = 0;					            	m[3][1] = 0;					m[3][2] = 1;	                    m[3][3] = 0;


        return this;
    }

    /**
     * Initializes a 4x4 orthographic matrix
     *
     * @param left Far left value
     * @param right Far right value
     * @param bottom Bottom value
     * @param top Top value
     * @param near Nearest value
     * @param far Closest value
     * @return 4x4 ortho matrix
     */
    public Matrix4f InitOrthographic(float left, float right, float bottom, float top, float near, float far)
    {
        float width = right - left;
        float height = top - bottom;
        float depth = far - near;

        m[0][0] = 2 / width;	m[0][1] = 0;	        m[0][2] = 0;	        m[0][3] = -(right + left) / width;
        m[1][0] = 0;	        m[1][1] = 2 / height;	m[1][2] = 0;	        m[1][3] = -(top + bottom) / height;
        m[2][0] = 0;	        m[2][1] = 0;	        m[2][2] = -2 / depth;	m[2][3] = -(far + near) / depth;
        m[3][0] = 0;	        m[3][1] = 0;	        m[3][2] = 0;	        m[3][3] = 1;

        return this;
    }

    /**
     * Initializes a 4x4 rotation matrix
     *
     * @param forward Forward direction
     * @param up Up direction
     * @return 4x4 rotation matrix
     */
    public Matrix4f InitRotation(Vector3f forward, Vector3f up)
    {
        Vector3f f = forward.Normalized();
        Vector3f r = up.Normalized();
        r = r.Cross(f);

        Vector3f u = f.Cross(r);

        return InitRotation(f, u, r);
    }

    /**
     * Initializes a 4x4 rotation matrix
     *
     * @param forward Forward direction
     * @param up Up direction
     * @param right Right direction
     * @return 4x4 rotation matrix
     */
    public Matrix4f InitRotation(Vector3f forward, Vector3f up, Vector3f right)
    {
        Vector3f f = forward;
        Vector3f r = right;
        Vector3f u = up;

        m[0][0] = r.x;	m[0][1] = r.y;	m[0][2] = r.z;	m[0][3] = 0;
        m[1][0] = u.x;	m[1][1] = u.y;	m[1][2] = u.z;	m[1][3] = 0;
        m[2][0] = f.x;	m[2][1] = f.y;	m[2][2] = f.z;	m[2][3] = 0;
        m[3][0] = 0;		m[3][1] = 0;		m[3][2] = 0;		m[3][3] = 1;

        return this;
    }

    /**
     * Multiply this and r
     *
     * @param r Another Matrix4f
     * @return Newly multiplied matrix
     */
    public Matrix4f Mul(Matrix4f r)
    {
        Matrix4f res = new Matrix4f();

        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                res.Set(i, j, m[i][0] * r.Get(0, j) +
                        m[i][1] * r.Get(1, j) +
                        m[i][2] * r.Get(2, j) +
                        m[i][3] * r.Get(3, j));
            }
        }

        return res;
    }

    /**
     * Get the raw float matrix
     *
     * @return 4x4 float matrix
     */
    public float[][] GetM()
    {
        float[][] res = new float[4][4];

        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                res[i][j] = m[i][j];

        return res;
    }

    /**
     * Get the value at
     *
     * @param x X index
     * @param y Y index
     * @return Value at (x, y)
     */
    public float Get(int x, int y)
    {
        return m[x][y];
    }

    /**
     * Set the raw matrix
     *
     * @param m 4x4 float matrix
     */
    public void SetM(float[][] m)
    {
        this.m = m;
    }

    /**
     * Set (x, y) to value
     *
     * @param x X index
     * @param y Y index
     * @param value Value
     */
    public void Set(int x, int y, float value)
    {
        m[x][y] = value;
    }
}
