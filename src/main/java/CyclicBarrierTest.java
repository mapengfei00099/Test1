import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    public static void main(String[] args) {
        int n = 4;
        CyclicBarrier barrier  = new CyclicBarrier(n);
        for(int i=0;i<n;i++)
            new Writer(barrier).start();
    }
    static class Writer extends Thread{
        private CyclicBarrier cyclicBarrier;
        public Writer(CyclicBarrier cyclicBarrier){
            this.cyclicBarrier = cyclicBarrier;

        }

        @Override
        public void run() {
            System.out.println("thread"+ Thread.currentThread().getName());

            try {
                Thread.sleep(5000);
                System.out.println("thread" + Thread.currentThread().getName() +"end");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("所有线程写入完毕，继续处理其他任务...");
        }
    }
}
