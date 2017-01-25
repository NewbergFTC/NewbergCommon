package us.or.k12.newberg.newbergcommon.math;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;

public class VuforiaUtil
{
    public static VectorF NavOffWall(VectorF trans, double robotAngle, VectorF offWall)
    {
        return new VectorF((float) (trans.get(0) - offWall.get(0) * Math.sin(Math.toRadians(robotAngle))
                - offWall.get(2) * Math.cos(Math.toRadians(robotAngle))), trans.get(1),
                (float) (trans.get(2) + offWall.get(0) * Math.cos(Math.toRadians(robotAngle)) - offWall.get(2)
                        * Math.sin(Math.toRadians(robotAngle))));
    }

    public static VectorF AnglesFromTarget(VuforiaTrackableDefaultListener image)
    {
        float [] data = image.getRawPose().getData();
        float [] [] rotation = {{data[0], data[1]}, {data[4], data[5], data[6]}, {data[8], data[9], data[10]}};
        double thetaX = Math.atan2(rotation[2][1], rotation[2][2]);
        double thetaY = Math.atan2(-rotation[2][0],
                        Math.sqrt(rotation[2][1] * rotation[2][1] + rotation[2][2] * rotation[2][2]));
        double thetaZ = Math.atan2(rotation[1][0], rotation[0][0]);

        return new VectorF((float)thetaX, (float)thetaY, (float)thetaZ);
    }
}
