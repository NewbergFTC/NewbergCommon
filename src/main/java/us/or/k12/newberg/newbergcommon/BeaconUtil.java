package us.or.k12.newberg.newbergcommon;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.RobotLog;
import com.vuforia.CameraCalibration;
import com.vuforia.Image;
import com.vuforia.Matrix34F;
import com.vuforia.Tool;
import com.vuforia.Vec3F;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.util.Arrays;

import static us.or.k12.newberg.newbergcommon.math.MathUtil.Roundf;

public class BeaconUtil
{
    public static final String TAG = "BeaconUtil";

    public static Scalar blueLow = new Scalar(100, 0, 220);
    public static Scalar blueHigh = new Scalar(178, 255, 255);

    public enum BeaconStatus
    {
        BEACON_NOT_VISIBLE,
        // Left is red, right is blue
        BEACON_RED_BLUE,
        // Left is blue, right is red
        BEACON_BLUE_RED,
        BEACON_ALL_BLUE,
        BEACON_NO_BLUE
    }

    public static BeaconStatus GetBeaconStatus(final Image img, final VuforiaTrackableDefaultListener beacon,
                                               final CameraCalibration cameraCalibration)
    {
        OpenGLMatrix pose = beacon.getRawPose();

        if (pose == null || img == null || img.getPixels() == null)
        {
            // TODO(Garrison): Error handling
            return BeaconStatus.BEACON_NOT_VISIBLE;
        }

        Matrix34F rawPose = new Matrix34F();
        float[] poseData = Arrays.copyOfRange(pose.transposed().getData(), 0, 12);
        rawPose.setData(poseData);

        float[][] beaconCornersInImage = new float[4][2];

        // Top left
        beaconCornersInImage[0] = Tool.projectPoint(cameraCalibration, rawPose, new Vec3F(-127, 276, 0)).getData();
        // Top right
        beaconCornersInImage[1] = Tool.projectPoint(cameraCalibration, rawPose, new Vec3F(127, 276, 0)).getData();
        // Bot right
        beaconCornersInImage[2] = Tool.projectPoint(cameraCalibration, rawPose, new Vec3F(127, -92, 0)).getData();
        // Bot left
        beaconCornersInImage[3] = Tool.projectPoint(cameraCalibration, rawPose, new Vec3F(-127, -92, 0)).getData();

        final Bitmap bm = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.RGB_565);
        bm.copyPixelsFromBuffer(img.getPixels());

        Mat crop = new Mat(bm.getHeight(), bm.getWidth(), CvType.CV_8UC3);
        Utils.bitmapToMat(bm, crop);

        float x = Math.min(Math.min(beaconCornersInImage[1][0], beaconCornersInImage[3][0]),
                Math.min(beaconCornersInImage[0][0], beaconCornersInImage[2][0]));
        float y = Math.min(Math.min(beaconCornersInImage[1][1], beaconCornersInImage[3][1]),
                Math.min(beaconCornersInImage[0][1], beaconCornersInImage[2][1]));
        float width = Math.max(Math.abs(beaconCornersInImage[0][0] - beaconCornersInImage[2][0]),
                Math.abs(beaconCornersInImage[1][0] - beaconCornersInImage[3][0]));
        float height = Math.max(Math.abs(beaconCornersInImage[0][1] - beaconCornersInImage[2][1]),
                Math.abs(beaconCornersInImage[1][1] - beaconCornersInImage[3][1]));

        x = Math.max(x, 0);
        y = Math.max(y, 0);

        width = (x + width > crop.cols()) ? crop.cols() - x : width;
        height = (y + height > crop.rows()) ? crop.rows() - y : height;

        final Mat cropped = new Mat(crop, new Rect(Roundf(x), Roundf(y),  Roundf(width), Roundf(height)));
        Imgproc.cvtColor(cropped, cropped, Imgproc.COLOR_RGB2HSV_FULL);

        Mat mask = new Mat();
        Core.inRange(cropped, blueLow, blueHigh, mask);

        Moments mnts = Imgproc.moments(mask, true);

        RobotLog.i("00: " + String.valueOf(mnts.get_m00()));
        RobotLog.i("Total: " + mask.total());

        if (mnts.get_m00() >= mask.total() * 0.4)
        {
            return BeaconStatus.BEACON_ALL_BLUE;
        }
        else if (mnts.get_m00() < mask.total() * 0.1)
        {
            return BeaconStatus.BEACON_NO_BLUE;
        }

        if ((mnts.get_m01() / mnts.get_m00()) < cropped.rows() / 2)
        {
            return BeaconStatus.BEACON_RED_BLUE;
        }
        else
        {
            return BeaconStatus.BEACON_BLUE_RED;
        }
    }
}
