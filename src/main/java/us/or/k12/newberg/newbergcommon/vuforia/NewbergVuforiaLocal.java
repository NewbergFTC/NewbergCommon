package us.or.k12.newberg.newbergcommon.vuforia;

import com.qualcomm.robotcore.util.RobotLog;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.State;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.internal.VuforiaLocalizerImpl;

public class NewbergVuforiaLocal extends VuforiaLocalizerImpl
{
    private Image _rgbImage;

    public NewbergVuforiaLocal(Parameters parameters)
    {
        super(parameters);
        stopAR();
        ClearGlSurface();

        this.vuforiaCallback = new NewbergVuforiaCallback();
        startAR();

        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);
    }

    public void ClearGlSurface()
    {
        if (this.glSurfaceParent != null)
        {
            appUtil.synchronousRunOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    glSurfaceParent.removeAllViews();
                    glSurfaceParent.getOverlay().clear();
                    glSurface = null;
                }
            });
        }
    }

    public Image GetImage()
    {
        return _rgbImage;
    }

    protected class NewbergVuforiaCallback extends VuforiaLocalizerImpl.VuforiaCallback
    {
        @Override
        public synchronized void Vuforia_onUpdate(State state)
        {
            super.Vuforia_onUpdate(state);

            us.or.k12.newberg.newbergcommon.vuforia.CloseableFrame frame =
                    new us.or.k12.newberg.newbergcommon.vuforia.CloseableFrame(state.getFrame());

            long imageCount = frame.getNumImages();

            for (int i = 0; i < imageCount; ++i)
            {
                if (frame.getImage(i).getFormat() == PIXEL_FORMAT.RGB565)
                {
                    _rgbImage = frame.getImage(i);
                }
            }

            RobotLog.vv(TAG, "Received Vuforia frame #%d", frame.getIndex());
            frame.Close();
        }
    }
}
