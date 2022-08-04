package org.bananasamirite.minecrafttetris;

public abstract class GameThread extends Thread {
    private long timeBetweenTicks;
    private boolean isStarted = false;
    public GameThread(long timeBetweenTicks) {
        this.timeBetweenTicks = timeBetweenTicks;
    }


    @Override
    public final void run() {
        isStarted = true;
        while (isStarted) {
            long time = System.currentTimeMillis();
            runGameLoop();
            // calculate time until next frame
            long waitTime = timeBetweenTicks - (System.currentTimeMillis() - time) % timeBetweenTicks;
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopGame() {
        isStarted = false;
    }

    protected void setTimeBetweenTicks(long time) {
        this.timeBetweenTicks = time;
    }

    abstract void runGameLoop();
}
