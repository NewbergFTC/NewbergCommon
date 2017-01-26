package us.or.k12.newberg.newbergcommon.vuforia;

import com.vuforia.Frame;

// Clone the frame so we can use it beyond callback
public class CloseableFrame extends Frame
{
    public CloseableFrame(Frame other)
    {
        super(other);
    }

    public void Close()
    {
        super.delete();
    }
}