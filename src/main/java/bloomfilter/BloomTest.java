package bloomfilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 17/7/7. 
 * 布隆过滤器 
 */
public class BloomTest {
    private static int size = 1000000;


    public static void main(String[] args) {
        testBloomFilter();
        testList();

    }

    public static  void testList(){
        long begin = System.currentTimeMillis();
        List<Integer> arrayList =new ArrayList<Integer>(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(i);
        }

        for (int i = 0; i < size; i++) {
            if (!arrayList.contains(i)) {
                System.out.println("有坏人逃脱了");
            }
        }

        List<Integer> list = new ArrayList<Integer>(1000);
        for (int i = size + 10000; i < size + 20000; i++) {
            if (arrayList.contains(i)) {
                list.add(i);
            }
        }
        System.out.println("有误伤的数量：" + list.size());
        System.out.println("时间是："+(System.currentTimeMillis() -begin));
    }

    public static  void testBloomFilter(){
        long begin = System.currentTimeMillis();

        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size);

        for (int i = 0; i < size; i++) {
            bloomFilter.put(i);
        }

        for (int i = 0; i < size; i++) {
            if (!bloomFilter.mightContain(i)) {
                System.out.println("有坏人逃脱了");
            }
        }

        List<Integer> list = new ArrayList<Integer>(1000);
        for (int i = size + 10000; i < size + 20000; i++) {
            if (bloomFilter.mightContain(i)) {
                list.add(i);
            }
        }
        System.out.println("有误伤的数量：" + list.size());
        System.out.println("时间是："+(System.currentTimeMillis() -begin));
    }
}  