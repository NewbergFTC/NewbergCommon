package us.or.k12.newberg.newbergcommon.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class LinearNewbergOpMode extends LinearOpMode
{
    protected void PreInit() {}
    abstract protected void Init();
    protected void PostInit() {}

    protected void Cleanup() {}

    abstract public void Run() throws InterruptedException;

    @Override
    public void runOpMode() throws InterruptedException
    {
        PreInit();
        Init();
        PostInit();

        waitForStart();

        Run();

        Cleanup();
    }

    public void WaitOneFullHardwareCycle() throws InterruptedException
    {
        WaitForNextHardwareCycle();
        Thread.sleep(1);
        WaitForNextHardwareCycle();
    }

    public void WaitForNextHardwareCycle() throws InterruptedException
    {
        synchronized (this)
        {
            this.wait();
        }
    }
}
