public class Polynomial {
    int degree;
    int[] coef;

    public Polynomial(int[] arr) {
        if (arr.length == 0) {
            this.degree = 1;
            this.coef = new int[] {0};
        }
        else {
            this.degree = arr.length;
            this.coef = arr;
        }
    }

    /**
     * Вычисляет сумму.
     *
     * @param p Polynomial
     * @return sum
     */
    public Polynomial plus(Polynomial p) {
        int newDegree = Math.max(this.degree, p.degree);
        int[] newCoef = new int[newDegree];

        if (this.degree >= 0) {
            System.arraycopy(this.coef, 0, newCoef, 0, this.degree);
        }
        for (int i = 0; i < p.degree; ++i) {
            newCoef[i] += p.coef[i];
        }
        return new Polynomial(newCoef);
    }

    /**
     * Вычисляет разность.
     *
     * @param p Polynomial
     * @return differential
     */
    public Polynomial minus(Polynomial p) {
        int newDegree = Math.max(this.degree, p.degree);
        int[] newCoef = new int[newDegree];

        if (this.degree >= 0) {
            System.arraycopy(this.coef, 0, newCoef, 0, this.degree);
        }
        for (int i = 0; i < p.degree; ++i) {
            newCoef[i] -= p.coef[i];
        }
        return new Polynomial(newCoef);
    }

    /**
     * Вычисляет произведение полиномов.
     *
     * @param p Polynomial
     * @return production
     */
    public Polynomial multiply(Polynomial p) {
        int newDegree = this.degree * p.degree;
        int[] newCoef = new int[newDegree];

        for (int i = 0; i < this.degree; ++i) {
            for (int j = 0; j < p.degree; ++j) {
                newCoef[i + j] += this.coef[i] * p.coef[j];
            }
        }

        return new Polynomial(newCoef);
    }

    /**
     * Вычисляет значение полинома в точке.
     *
     * @param point Integer
     * @return value
     */
    public int evaluate(int point) {
        int answer = 0;
        for (int i = 0; i < this.degree; ++i) {
            answer += this.coef[i] * (int) Math.pow(point, i);
        }
        return answer;
    }

    /**
     * Вычесляет n производную.
     *
     * @param n
     * @return PolYnomial
     */
    public Polynomial differentiate(int n) {
        int[] newCoef = new int[this.degree];
        int newDegree = this.degree;
        System.arraycopy(this.coef, 0, newCoef, 0, this.degree);

        for (int i = 0; i < n; ++i) {
            for (int j = 1; j < this.degree; ++j) {
                newCoef[j - 1] = newCoef[j] * j;
            }
            newCoef[--newDegree] = 0;

        }

        return new Polynomial(newCoef);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
    public boolean equals(Polynomial p) {
        return this.toString().equals(p.toString());
    }

    @Override
    public String toString() {
        String answer = "";
        for (int i = this.degree - 1; i >= 0; --i) {
            if ((answer == "") && this.coef[i] < 0) {
                answer += "-";
            } else if (this.coef[i] < 0) {
                answer += " - ";
            } else if (this.coef[i] > 0 && answer != "") {
                answer += " + ";
            }

            if (answer.toString().isEmpty() && i == 0) {
                answer += Math.abs(this.coef[i]);
            } else if (this.coef[i] != 0) {
                if (i == 0) {
                    answer += Math.abs(this.coef[i]);
                } else if (Math.abs(this.coef[i]) != 1) {
                    answer += Math.abs(this.coef[i]);
                }

                if (i == 1) {
                    answer += "x";
                } else if (i > 1) {
                    answer += ("x^" + i);
                }
            }

        }
        return answer.toString();
    }
}

