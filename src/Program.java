import polynomials.Newton;

import java.util.HashMap;
import java.util.Random;

public class Program {
    public static void main(String[] args) {
        Random r = new Random();
        HashMap<Double, Double> dots = new HashMap<>();
        dots.put(-1., 1.);
        dots.put(0., 0.);
        dots.put(1., 1.);
        Newton newton = new Newton(dots);
        System.out.println(newton);
    }
}
