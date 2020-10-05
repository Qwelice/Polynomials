package polynomials;

import java.util.ArrayList;

public class Polynomial {
    protected ArrayList<Double> coef;
    public final Double ZERO = 1e-24;
    private ArrayList<Double> cash = new ArrayList<>();

    public Polynomial() {
        coef = new ArrayList<>();
        coef.add(0.);
    }

    public Polynomial(ArrayList<Double> e) {
        coef = (ArrayList<Double>) e.clone();
        correctDeg();
    }

    public Polynomial(int n) {
        coef = new ArrayList<>();
        for (int i = 0; i < n; i++)
            coef.add(0.);
    }

    public Polynomial(double[] arr) {
        coef = new ArrayList<>();
        for (int i = 0; i < arr.length; i++)
            coef.add(Double.valueOf(arr[i]));
        correctDeg();
    }

    public int getDeg() {
        return coef.size() - 1;
    }

    public Double get(int index) {
        return coef.get(index);
    }

    public void set(int index, double value) {
        coef.set(index, value);
    }

    private void correctDeg() {
        for (int i = coef.size() - 1; i > 0; i--) {
            if (coef.get(i) == 0)
                coef.remove(i);
            else
                break;
        }
    }

    public Polynomial plus(Polynomial e) {
        Polynomial maxP, minP, result;
        maxP = coef.size() > e.coef.size() ? this : e;
        minP = e.coef.size() < coef.size() ? e : this;
        result = new Polynomial(maxP.coef.size());
        for (int i = 0; i < maxP.coef.size(); i++) {
            if (i < minP.coef.size()) {
                result.coef.set(i, maxP.coef.get(i) + minP.coef.get(i));
                continue;
            }
            result.coef.set(i, maxP.coef.get(i));
        }
        result.correctDeg();
        return result;
    }

    public Polynomial plusAssign(Polynomial e) {
        if (this.getDeg() < e.getDeg()) {
            for (int i = 0; i < e.getDeg() + 1; i++) {
                if (i < this.getDeg() + 1) {
                    this.coef.set(i, this.get(i) + e.get(i));
                    continue;
                }
                this.coef.add(e.get(i));
            }
        } else {
            for (int i = 0; i < this.getDeg() + 1; i++) {
                if (i < e.coef.size())
                    this.coef.set(i, this.get(i) + e.get(i));
            }
        }
        correctDeg();
        return this;
    }

    public Polynomial plusAssign(double x) {
        for (int i = 0; i < getDeg() + 1; i++)
            coef.set(i, coef.get(i) + x);
        return this;
    }

    public Polynomial minus(Polynomial e) {
        return this.plus(e.times(-1));
    }

    public Polynomial minusAssign(Polynomial e) {
        if (this.getDeg() < e.getDeg()) {
            for (int i = 0; i < e.getDeg() + 1; i++) {
                if (i < this.getDeg() + 1) {
                    this.coef.set(i, this.get(i) - e.get(i));
                    continue;
                }
                this.coef.add(-e.get(i));
            }
        } else {
            for (int i = 0; i < this.getDeg() + 1; i++) {
                if (i < e.coef.size())
                    this.coef.set(i, this.get(i) - e.get(i));
            }
        }
        return this;
    }

    public Polynomial times(double k) {
        Polynomial result = new Polynomial(coef.size());
        for (int i = 0; i < result.coef.size(); i++) {
            result.coef.set(i, coef.get(i) * k);
        }
        correctDeg();
        return result;
    }

    public Polynomial times(Polynomial e) {
        Polynomial result = new Polynomial(getDeg() + e.getDeg() + 1);
        for (int i = 0; i < getDeg() + 1; i++) {
            for (int j = 0; j < e.getDeg() + 1; j++)
                result.coef.set(i + j, result.get(i + j) + coef.get(i) * e.coef.get(j));
        }
        result.correctDeg();
        return result;
    }

    public Polynomial timesAssign(double k) {
        for (int i = 0; i < coef.size(); i++)
            coef.set(i, coef.get(i) * k);
        return this;
    }

    public Polynomial timesAssign(Polynomial e) {
        cash.clear();
        for (int k = 0; k < getDeg() + e.getDeg() + 1; k++)
            cash.add(0.);
        for (int i = 0; i < this.getDeg() + 1; i++) {
            for (int j = 0; j < e.getDeg() + 1; j++) {
                cash.set(i + j, cash.get(i + j) + coef.get(i) * e.coef.get(j));
            }
        }
        for (int i = 0; i < cash.size(); i++) {
            if (i < coef.size()) {
                coef.set(i, cash.get(i));
                continue;
            }
            coef.add(cash.get(i));
        }
        correctDeg();
        return this;
    }

    public Polynomial div(double x) throws Exception {
        Polynomial result = new Polynomial(this.getDeg() + 1);
        for (int i = 0; i < getDeg() + 1; i++)
            result.coef.set(i, coef.get(i) / x);
        return result;
    }

    public Polynomial divAssign(double x) throws Exception {
        for (int i = 0; i < coef.size(); i++)
            coef.set(i, coef.get(i) / x);
        return this;
    }
    
     public double substitute(double x){
        double result = 0;
        for(int i = 0; i < coef.size(); i++)
            result += coef.get(i) * Math.pow(x, i);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (coef.size() == 1)
            return result.append(coef.get(0)).toString();
        for (int i = coef.size() - 1; i >= 0; i--) {
            if (Math.abs(coef.get(i)) < ZERO)
                continue;
            if (coef.get(i) < 0) {
                result.append(i == coef.size() - 1 ? "-" : " - ");
            } else {
                result.append(i == coef.size() - 1 ? "" : " + ");
            }
            if(Math.abs(coef.get(i)) == 1 && i == 0)
                result.append(1);
            result.append(Math.abs(coef.get(i)) != 1 ? ((Math.abs(coef.get(i)) -  (long)(Math.abs(coef.get(i)))) > ZERO ? Math.round(100 * Math.abs(coef.get(i))) / 100. : (long)Math.abs(coef.get(i))) : "");
            if(i > 1)
                result.append("x^" + i);
            else
                result.append(i != 0 ? "x" : "");
        }
        return result.toString();
    }
}
