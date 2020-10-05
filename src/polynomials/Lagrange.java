package polynomials;

import java.util.HashMap;

public class Lagrange extends Polynomial {

    private final HashMap<Double, Double> dots;
    private Polynomial fundamentalPolynomial;
    private Polynomial temp = new Polynomial(2);

    public Lagrange(HashMap<Double, Double> dots) {
        fundamentalPolynomial = new Polynomial(new double[]{1.0});
        this.dots = (HashMap<Double, Double>) dots.clone();
        createPolynomial();
    }

    private Polynomial fundamental(double x) {
        fundamentalPolynomial.coef.clear();
        fundamentalPolynomial.coef.add(1.0);
        for (var key : dots.keySet()) {
            if (Math.abs(x - key) >= ZERO) {
                temp.set(0, -key);
                temp.set(1, 1);
                try {
                    fundamentalPolynomial.timesAssign(temp.divAssign(x - key));
                } catch (Exception ex) {
                    temp = new Polynomial();
                }
            }
        }
        return fundamentalPolynomial;
    }

    private void createPolynomial() {
        for (var key : dots.keySet())
            this.plusAssign(fundamental(key).timesAssign(dots.get(key)));
    }
}
