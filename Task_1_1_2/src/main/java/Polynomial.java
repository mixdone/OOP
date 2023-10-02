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
     * вычисляет сумму
     * @param p Polynomial
     * @return sum
     */
    public Polynomial plus(Polynomial p) {
        int new_degree = Math.max(this.degree, p.degree);
        int[] new_coef = new int[new_degree];

        if (this.degree >= 0)
            System.arraycopy(this.coef, 0, new_coef, 0, this.degree);
        for (int i = 0; i < p.degree; ++i)
            new_coef[i] += p.coef[i];
        return new Polynomial(new_coef);
    }

    /**
     * вычисляет разность
     * @param p Polynomial
     * @return differential
     */
    public Polynomial minus(Polynomial p) {
        int new_degree = Math.max(this.degree, p.degree);
        int[] new_coef = new int[new_degree];

        if (this.degree >= 0)
            System.arraycopy(this.coef, 0, new_coef, 0, this.degree);
        for (int i = 0; i < p.degree; ++i)
            new_coef[i] -= p.coef[i];
        return new Polynomial(new_coef);
    }

    /**
     * Вычисляет произведение полиномов
     * @param p Polynomial
     * @return production
     */
    public Polynomial multiply(Polynomial p) {
        int new_degree = this.degree * p.degree;
        int[] new_coef = new int[new_degree];

        for (int i = 0; i < this.degree; ++i)
            for (int j = 0; j < p.degree; ++j)
                new_coef[i + j] += this.coef[i] * p.coef[j];

        return new Polynomial(new_coef);
    }

    /**
     * Вычисляет значение полинома в точке
     * @param point Integer
     * @return value
     */
    public int evaluate(int point) {
        int answer = 0;
        for (int i = 0; i < this.degree; ++i) 
            answer += this.coef[i] * (int) Math.pow(point, i);
        return answer;
    }

    /**
     * Вычесляет n производную
     * @param n
     * @return PolYnomial
     */
    public Polynomial differentiate(int n) {
        int[] new_coef = new int[this.degree];
        int new_degree = this.degree;
        System.arraycopy(this.coef, 0, new_coef, 0, this.degree);

        for (int i = 0; i < n; ++i) {
            for (int j = 1; j < this.degree; ++j)
                new_coef[j - 1] = new_coef[j] * j;
            new_coef[--new_degree] = 0;

        }

        return new Polynomial(new_coef);
    }
    public boolean equals(Polynomial p) {
        return this.toString().equals(p.toString());
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder();
        for (int i = this.degree - 1; i >= 0; --i) {
            if ((answer.isEmpty()) && this.coef[i] < 0)
                answer.append("-");
            else if (this.coef[i] < 0)
                answer.append(" - ");
            else if (this.coef[i] > 0 && !answer.toString().isEmpty())
                answer.append(" + ");

            if (answer.toString().isEmpty() && i == 0)
                answer.append(Math.abs(this.coef[i]));
            else if (this.coef[i] != 0) {
                if (i == 0)
                    answer.append(Math.abs(this.coef[i]));
                else if (Math.abs(this.coef[i]) != 1)
                    answer.append(Math.abs(this.coef[i]));

                if (i == 1)
                    answer.append("x");
                else if (i > 1)
                    answer.append("x^").append(i);
            }

        }
        return answer.toString();
    }
}

