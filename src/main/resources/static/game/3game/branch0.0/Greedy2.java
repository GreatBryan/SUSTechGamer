import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Greedy2 {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        InputReader scanner = new InputReader(inputStream);
        int n = scanner.nextInt();
        Magic[] magics = new Magic[n];
//        int min = 10001;
        int early = 10001;
        int late = 0;
        for (int i = 0; i < n; i++) {
            int start = scanner.nextInt();
            int end = scanner.nextInt();
            magics[i] = new Magic(start,end);
//            if (end-start<min)
//                min = end-start;
            if (start<early)
                early = start;
            if (end>late)
                late = end;
        }
        Magic[] tmp = new Magic[n];
        mergeSort(magics,tmp,0,n-1);

        int left = 0;
//        int right = min;
        int right = (late-early+1)/n;
        int mid  = (left+right)/2;
        while (right-left>1){
            if (judge(mid,magics,early,late)){
                left = mid;
            }else {
                right = mid;
            }
            mid = (left+right)/2;
        }
//        if (right - left == 1){
            if (judge(right,magics,early,late))
                System.out.print(right);
            else
                System.out.print(left);
//        }
    }

    public static boolean judge(int time,Magic[] info,int early,int late){
        PriorityQueue<Magic> queue = new PriorityQueue<>();
        int[] left_time = new int[info.length];
        for (int i = 0; i < info.length; i++) {
            left_time[i] = time;
            info[i].index = i;
        }
        int count = 0;
        for (int i = early; i <= late; i++) {
            while (count<info.length&&i == info[count].start){
                queue.add(info[count]);
                count++;
            }
            if (queue.size() == 0)
                continue;
            while(queue.peek().end<i) {
                queue.poll();
            }
            int allocate = queue.peek().index;
            left_time[allocate]--;
            if (left_time[allocate] == 0)
                queue.poll();

        }
        for (int i = 0; i < info.length; i++) {
            if (left_time[i] != 0)
                return false;
        }
        return true;
    }
    public static void mergeSort(Magic[] list, Magic[] tmp, int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergeSort(list, tmp, start, mid);
            mergeSort(list, tmp, mid + 1, end);
            Merge(list, tmp, start, mid, end);
        }
    }

    public static void Merge(Magic[] list, Magic[] tmp, int start, int mid, int end) {
        int i = start;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= end) {
            if (list[i].start <= list[j].start) {
                tmp[k] = list[i];
                i++;
                k++;
            } else {
                tmp[k] = list[j];
                j++;
                k++;
            }
        }
        while (i <= mid) {
            tmp[k] = list[i];
            k++;
            i++;
        }
        while (j <= end) {
            tmp[k] = list[j];
            k++;
            j++;
        }
        for (int m = 0; m < k; m++) {
            list[start + m] = tmp[m];
        }
    }
}

class Magic implements Comparable<Magic>{
    int start;
    int end;
    int index;
    public Magic(int start,int end){
        this.start = start;
        this.end = end;
    }

    @Override
    public int compareTo(Magic o) {
        if (this.end<o.end)
            return -1;
        else if (this.end == o.end)
            return 0;
        else
            return 1;
    }
}

//class InputReader {
//    public BufferedReader reader;
//    public StringTokenizer tokenizer;
//
//    public InputReader(InputStream var1) {
//        this.reader = new BufferedReader(new InputStreamReader(var1), 32768);
//        this.tokenizer = null;
//    }
//
//    public String next() {
//        while (this.tokenizer == null || !this.tokenizer.hasMoreTokens()) {
//            try {
//                this.tokenizer = new StringTokenizer(this.reader.readLine());
//            } catch (IOException var2) {
//                throw new RuntimeException(var2);
//            }
//        }
//
//        return this.tokenizer.nextToken();
//    }
//
//    public int nextInt() {
//        return Integer.parseInt(this.next());
//    }
//
//    public long nextLong() {
//        return Long.parseLong(this.next());
//    }
//
//    public double nextDouble() {
//        return Double.parseDouble(this.next());
//    }
//
//    public char[] nextCharArray() {
//        return this.next().toCharArray();
//    }
//}