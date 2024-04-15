import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CustomThreadPool {
    private final int capacity;
    private final List<WorkerThread> threads;
    final LinkedList<Runnable> taskQueue;
    boolean isShutdown;

    public CustomThreadPool(int capacity) {
        this.capacity = capacity;
        this.threads = new ArrayList<>();
        this.taskQueue = new LinkedList<>();
        this.isShutdown = false;

        for (int i = 0; i < capacity; i++) {
            WorkerThread workerThread = new WorkerThread(this);
            workerThread.start();
            threads.add(workerThread);
        }
    }

    public synchronized void execute(Runnable task) {
        if (isShutdown) {
            throw new IllegalStateException("Пул закрывается.");
        }
        taskQueue.add(task);
        notify();
    }

    public synchronized void shutdown() {
        isShutdown = true;
        for (WorkerThread workerThread : threads) {
            workerThread.interrupt();
        }
    }

    public void awaitTermination() throws InterruptedException {
        for (WorkerThread workerThread : threads) {
            workerThread.join();
        }
    }
}
