import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Integer> {
    private int begin;
    private int end;

    public CountTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }
    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = end -begin <=30;
        if(canCompute){
            for (int i = begin; i <= end; i++) {
                sum += i;
            }
            System.out.println("thread: " + Thread.currentThread() + " start: " + begin + " end: " + end);
        }else{
            int mid = (end + begin) / 2;
            CountTask left = new CountTask(begin, mid);
            CountTask right = new CountTask(mid + 1, end);
            left.fork();
            right.fork();

            sum = left.join() + right.join();
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int start = 0;
        int end = 200;

        CountTask task = new CountTask(start, end);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        Future<Integer> ans = pool.submit(task);
        int sum = ans.get();
        System.out.println(sum);
    }
}
