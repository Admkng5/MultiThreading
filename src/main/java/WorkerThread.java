import java.util.LinkedList;

public class WorkerThread extends Thread {
    private final CustomThreadPool threadPool;

    public WorkerThread(CustomThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    @Override
    public void run() {
        while (true) {
            Runnable task;
            synchronized (threadPool) {
                while (threadPool.taskQueue.isEmpty() && !threadPool.isShutdown) {
                    try {
                        threadPool.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                if (threadPool.isShutdown && threadPool.taskQueue.isEmpty()) {
                    return;
                }
                task = threadPool.taskQueue.poll();
            }
            task.run();
        }
    }
}
