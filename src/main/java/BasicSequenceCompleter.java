
import java.util.ArrayList;

public class BasicSequenceCompleter {

    public long[] PATTERN;
    final int SAME_DIFF = 0;
    final int INCREMENT_DIFF = 1;
    final int GEOMETRIC_DIFF = 2;
    final int FIBONACCI_DIFF = 3;
    final int PRIME_DIFF = 4;
    final int MEMORY_DIFF = 5;

    final int MULTIPLY = 100;
    final int ADD = 200;

    double geometricRatio = 0;
    long diffType = -1;
    int memoryIndex = -1;
    int arrayIndex = -1;
    public int power = 0;
    ArrayList<long[]> prevArrayList = new ArrayList();
    ArrayList<Integer> operationList = new ArrayList();
    ArrayList<Long> operationElementList = new ArrayList();
    ArrayList<Long> lastElementList = new ArrayList();

    public void setSequence(long[] arr) {
        operationList.clear();
        operationElementList.clear();
        lastElementList.clear();
        PATTERN = arr;
    }

    boolean checkForFibonacci(long arr[]) {
        for (int i = 2; i < arr.length; i++) {
            if (!(arr[i] == arr[i - 1] + arr[i - 2])) {
                return false;
            }
        }
        diffType = FIBONACCI_DIFF;
        return true;
    }

    public BasicSequenceCompleter(long arr[]) {
        PATTERN = arr;
    }

    BasicSequenceCompleter() {
    }

    long currentArr[] = PATTERN;

    public long getNextInSequence() {

        power = 0;
        operationList.clear();
        lastElementList.clear();
        operationElementList.clear();

        operationList.add(null);
        lastElementList.add(getLast(PATTERN));
        operationElementList.add(null);

        long[] currentArr = PATTERN;
        prevArrayList.add(currentArr);
        if (patternReduced(currentArr)) {
            long last = getNextElement(currentArr);
            addLastToPattern(last);
            return last;
        }
        try {
            while (!patternReduced(currentArr)) {
                if (canGenerateQuotientArray(currentArr)) {
                    //printArray(currentArr);
                    currentArr = generateQuotientArray(currentArr);
                    prevArrayList.add(currentArr);
                    lastElementList.add(getLast(currentArr));
                    operationList.add(MULTIPLY);
                    if (patternReduced(currentArr)) {
                        operationElementList.add(getNextElement(currentArr));
                    } else {
                        operationElementList.add(null);
                    }

                } else {
                    currentArr = generateDifferenceArray(currentArr);
                    // printArray(currentArr);
                    power++;
                    prevArrayList.add(currentArr);
                    lastElementList.add(getLast(currentArr));
                    operationList.add(ADD);
                    if (patternReduced(currentArr)) {
                        operationElementList.add(getNextElement(currentArr));
                    } else {
                        operationElementList.add(null);
                    }
                }
            }

            /**Log("oe : " + operationElementList);
            Log("le : " + lastElementList);
            Log("o  : " + operationList);**/
            for (int i = operationElementList.size() - 2; i >= 0; i--) {
                operationElementList.set(i, operate(operationElementList.get(i + 1), operationList.get(i + 1), lastElementList.get(i)));
            }
            //Log("----------------------");
            //Log("oe : " + operationElementList);
            //Log("le : " + lastElementList);
            //Log("o  : " + operationList);
            long last = operationElementList.get(0);
            addLastToPattern(last);
            return last;
        } catch (ArrayIndexOutOfBoundsException e) {
            Log("Pattern Not Recognized");
        }
        return 4;
    }

    void addLastToPattern(long l) {
        int patternLength = PATTERN.length;
        long[] temp = new long[patternLength + 1];
        for (int i = 0; i < patternLength; i++) {
            temp[i] = PATTERN[i];
        }
        temp[temp.length - 1] = l;
        PATTERN = temp;

    }

    long operate(long operationElement, long operation, long lastElement) {
        if (operation == MULTIPLY) {
            return operationElement * lastElement;
        } else {
            return operationElement + lastElement;
        }
    }

    long getNextElement(long temp1[]) {
        long lastElement = temp1[temp1.length - 1];
        if (diffType == PRIME_DIFF) {
           //Log("prime after " + lastElement + " is " + nextPrime(lastElement));
            return nextPrime(lastElement);
        } else if (diffType == SAME_DIFF) {
            return lastElement;
        } else if (diffType == INCREMENT_DIFF) {
            return lastElement + 1;
        } else if (diffType == GEOMETRIC_DIFF) {
            return (long) (lastElement * geometricRatio);
        } else if (diffType == FIBONACCI_DIFF) {
            return temp1[temp1.length - 2] + temp1[temp1.length - 1];
        }
        return 13000135;
    }

    boolean patternRecorded(long a[]) {
        return false;
    }

    boolean patternReduced(long arr[]) {

        try {
            //Log("Check " + arr[0] + " " + arr[1]);
            if (arr[0] == arr[1]) {
                // Log("Same");
                return checkForSameDiff(arr);
            } else if (patternRecorded(arr)) {
                return true;
            } else if ((arr[3] == arr[2] + arr[1]) && (arr[2] == arr[0] + arr[1])) {
                //  Log("Fibonacci");
                return checkForFibonacci(arr);
            } else if (isPrime(arr[0]) && isPrime(arr[1]) && isPrime(arr[2]) && isPrime(arr[3])) {
                //  Log("Prime");
                return checkForPrimeSequence(arr);
            } else if (((double) arr[1] / arr[0]) == ((double) arr[2] / arr[1])) {
                //  Log("GP");
                return checkForGeometricDifference(arr);
            }
            diffType = -1;
        } catch (ArithmeticException | ArrayIndexOutOfBoundsException e) {
            // Log("Meow");
        }
        return false;
    }

    boolean checkForPrimeSequence(long arr[]) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (!(isPrime(arr[i]) && arr[i + 1] == nextPrime(arr[i]))) {
                return false;
            }
        }
        diffType = PRIME_DIFF;
        return true;
    }

    long nextPrime(long a) {
        a++;
        while (!isPrime(a)) {
            a++;
        }
        return a;
    }

    boolean isPrime(long a) {
        if (a == 0 || a == 1) {
            return false;
        }
        for (long i = 2; i < a; i++) {
            if (a % i == 0) {
                return false;
            }
        }
        return true;
    }

    boolean checkForGeometricDifference(long[] arr) {
        geometricRatio = (double) arr[1] / arr[0];
        for (int i = 1; i < arr.length - 1; i++) {
            if (((double) arr[i + 1] / arr[i]) != geometricRatio) {
                return false;
            }
        }
        diffType = GEOMETRIC_DIFF;
        return true;
    }

    boolean checkForIncrementDiff(long arr[]) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] != arr[i + 1] - 1) {
                return false;
            }
        }
        diffType = INCREMENT_DIFF;
        return true;
    }

    boolean checkForSameDiff(long arr[]) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] != arr[i + 1]) {
                return false;
            }
        }
        diffType = SAME_DIFF;
        return true;
    }

    long[] temp;

    long[] generateDifferenceArray(long pattern[]) {
        temp = new long[pattern.length - 1];
        long tempLength = temp.length;
        for (int i = 0; i < tempLength; i++) {
            temp[i] = pattern[i + 1] - pattern[i];
        }
        return temp;
    }

    long[] generateQuotientArray(long pattern[]) {
        temp = new long[pattern.length - 1];
        long tempLength = temp.length;
        for (int i = 0; i < tempLength; i++) {
            temp[i] = pattern[i + 1] / pattern[i];
        }
        return temp;
    }

    boolean canGenerateQuotientArray(long pattern[]) {
        for (int i = 0; i < pattern.length - 1; i++) {
            if (!isWholeNumber((double) pattern[i + 1] / pattern[i])) {
                return false;
            }
        }
        return true;
    }

    boolean isWholeNumber(double a) {
        if ((a - (long) a) == 0) {
            return true;
        }
        return false;

    }

    void Log(Object s) {
        System.out.println(s);
    }

    void printArray(long arr[]) {
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j] + " ");
        }
        System.out.println();
    }

    long getLast(long arr[]) {
        return arr[arr.length - 1];
    }

    public String getRelation() {
        if (power >= 2) {
            power++;
            double[][] A = new double[power][power];
            double[][] B = new double[power][1];

            while (PATTERN.length < 2 + power) {
                getNextInSequence();
            }
            for (int k = 0; k < power; k++) {
                B[k][0] = PATTERN[k];
            }
            for (int i = 0; i < power; i++) {
                for (int j = 0; j < power - 1; j++) {
                    A[i][j] = Math.pow(i + 1, power - j - 1);
                }
                A[i][power - 1] = 1;
            }
            //System.out.println("A : ");
            //print2DArr(A);
            //System.out.println("B : ");
            //print2DArr(B);

            Matrix AMatrix = new Matrix(A);
            Matrix BMatrix = new Matrix(B);
            double[] result = AMatrix.getAdjointMatrix().multiply(BMatrix).multiply((double) 1 / AMatrix.getDeterminant()).getColumn(0);
            //printArr(result);

            String tempX = "";
            for (int i = 0; i < result.length; i++) {
                if (result[i] == 0) {
                    continue;
                }
                tempX += ((result[i] < 0) ? "- " : "+ ") + Math.abs(result[i]) + "n^" + (power - 1 - i) + " ";
            }
            if (tempX.charAt(0) == '+') {
                tempX = tempX.substring(1, tempX.length());
            }
            tempX = tempX.trim();

            if (tempX.charAt(tempX.length() - 1) == '0') {
                tempX = (tempX.substring(0, tempX.length() - 3));
            }
            tempX = tempX.trim();
            return tempX;
        }
        return "Not Found";
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
                System.out.print(o[i][j] + " ");
            }
            System.out.println();
        }
    }
}
