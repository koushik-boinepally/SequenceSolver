

import java.util.ArrayList;

public class test {

    static ArrayList<int[]> prevArrayList = new ArrayList();
    static long[] pattern = {2, 3, 5, 7, 11, 13};
    public static void main(String[] args) {
        BasicSequenceCompleter sc = new BasicSequenceCompleter(pattern);
        Logln(sc.getNextInSequence());
        Logln(sc.getRelation());
    }

    static void Log(Object o) {
        System.out.print(o);
    }

    static void Logln(Object o) {
        System.out.println(o);
    }

    static void printArr(double[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    static void print2DArr(double[][] o) {
        for (int i = 0; i < o.length; i++) {
            for (int j = 0; j < o[i].length; j++) {
                System.out.print(o[i][j] + "\t");
            }
            System.out.println();
        }
    }

}
