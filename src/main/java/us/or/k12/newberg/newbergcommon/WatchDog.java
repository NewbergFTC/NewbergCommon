package us.or.k12.newberg.newbergcommon;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A 'WatchDog' that will run the given Runnable task after the given time, unless Stop() is called
 *
 * @author Garrison Peacock
 * @version 1.0
 */
public class WatchDog
{
    private final AtomicBoolean _shouldStop = new AtomicBoolean(false);

    private void Reset()
    {
        _shouldStop.set(false);
    }

    public void Watch(final Runnable task, final long millis)
    {
        final Thread slave = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(millis);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                if (!_shouldStop.get())
                {
                    task.run();
                }
            }
        });

        final Thread master = new Thread(new Runnable()
         {
             @Override
             public void run()
             {
                slave.start();

                try
                {
                    slave.join();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                Reset();
             }
         });

        master.start();
    }

    public void Stop()
    {
        _shouldStop.set(true);
    }
}
