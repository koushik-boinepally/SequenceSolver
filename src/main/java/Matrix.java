
/**
 *
 * @author Koushik
 */
public class Matrix {

    int X, Y;

    double matrix[][];

    Matrix(double[][] mat) {
        matrix = mat;
        Y = matrix.length;
        X = matrix[0].length;
    }

    public Matrix getAdjointMatrix() {
        if (X == Y) {
            if (X > 2) {
                double[][] temp = new double[X][Y];
                for (int i = 0; i < X; i++) {
                    for (int j = 0; j < Y; j++) {
                        temp[i][j] = (getSubMatrix(i, j).getDeterminant()) * Math.pow(-1, i + j);
                    }
                }
                return new Matrix(temp).getTranspose();
            } else {
                return new Matrix(new double[][]{{matrix[1][1], -matrix[1][0]}, {-matrix[0][1], matrix[0][0]}});
            }
        }
        return null;
    }

    public void set(double mat[][]) {
        matrix = mat;
    }

    public Matrix getSubMatrix(int x, int y) {
        int xcounter = 0;
        int ycounter = -1;
        double[][] temp = new double[X - 1][Y - 1];
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                if (i != y && j != x) {
                    ycounter++;
                    if (ycounter >= X - 1) {
                        ycounter = 0;
                        xcounter++;
                    }
                    temp[ycounter][xcounter] = matrix[j][i];
                }
            }
        }
        return new Matrix(temp);
    }

    public double getDeterminant() {
        double det = 0;
        Matrix temp;
        if (Y == X) {
            if (X != 2) {
                for (int i = 0; i < X; i++) {
                    temp = getSubMatrix(0, i);
                    det = det + (Math.pow(-1, i) * matrix[0][i] * temp.getDeterminant());
                }
            } else {
                return (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
            }
        }
        return det;
    }

    public Matrix getTranspose() {
        double[][] temp = new double[X][Y];
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                temp[i][j] = matrix[j][i];
            }
        }
        return new Matrix(temp);
    }

    public Matrix multiply(Matrix m) {
        double[][] temp = new double[Y][m.X];
        for (int i = 0; i < Y; i++) {
            for (int j = 0; j < m.X; j++) {
                temp[i][j] = multiplyAndAdd(getRow(i), m.getColumn(j));
            }
        }
        return new Matrix(temp);
    }

    public Matrix multiply(double s) {
        double[][] temp = new double[Y][X];
        for (int i = 0; i < Y; i++) {
            for (int j = 0; j < X; j++) {
                temp[i][j] = matrix[i][j] * s;
            }
        }
        return new Matrix(temp);
    }

    double multiplyAndAdd(double[] a, double[] b) {
        double acc = 0;
        for (int i = 0; i < a.length; i++) {
            acc += a[i] * b[i];
        }
        return acc;
    }

    public double[] getRow(int xx) {
        return matrix[xx];
    }

    public double[] getColumn(int yy) {
        double[] temp = new double[Y];
        for (int i = 0; i < Y; i++) {
            temp[i] = matrix[i][yy];
        }
        return temp;
    }

}
