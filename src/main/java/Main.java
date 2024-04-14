public class Main {
    public static void main(String[] args) throws InterruptedException {
        CustomThreadPool pool = new CustomThreadPool(3);

        for (int i = 0; i < 10; i++) {
            int taskId = i + 1; // Нумерация задач начинается с 1.
            pool.execute(() -> {
                System.out.println("Задача №" + taskId + " выполняется потоком " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(5000); // Ожидание выполнения всех задач.

        pool.shutdown(); // Остановим пул.
        pool.awaitTermination(); // Ожидание завершения всех задач.

        System.out.println("Все задачи завершены.");
    }
}
