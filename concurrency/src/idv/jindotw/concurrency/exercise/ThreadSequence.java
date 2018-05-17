package idv.jindotw.concurrency.exercise;

import idv.jindotw.util.Exercise;

public class ThreadSequence extends Exercise {
    private static final int id = 1;

    public ThreadSequence() {
        super(id);
    }

    @Override
    public String getTitle() {
        return "Thread Running Sequence";
    }

    @Override
    public String getDesc() {
        return "You have thread T1, T2, and T3, how will you ensure that thread T2 run after T1 and thread T3 run after T2?";
    }

    @Override
    public void doExercise() {
        ThreadRunner runner = this.new ThreadRunner();
        Thread t1 = new Thread(runner, "T1");
        Thread t2 = new Thread(runner, "T2");
        Thread t3 = new Thread(runner, "T3");

        try {
            t1.start();
            t1.join();
            t2.start();
            t2.join();
            t3.start();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class ThreadRunner implements Runnable {
        @Override
        public void run() {
            final String threadName = Thread.currentThread().getName();
            System.out.println("I am " + threadName + ".  I am about to run");
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("I am " + threadName + ".  I have finished running");
        }
    }
}
