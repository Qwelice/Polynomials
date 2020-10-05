package polynomials.pairs;

public class Pairs<T1, T2> {
    T1 first;
    T2 second;

    public Pairs(T1 x1, T2 x2) {
        first = x1;
        second = x2;
    }

    @Override
    public boolean equals(Object other) {
        if (getClass() != other.getClass())
            return false;
        else {
            return ((Pairs)other).first == first && ((Pairs)other).second == second;
        }
    }

    @Override
    public int hashCode(){
        return first.hashCode() + second.hashCode();
    }

    public T1 getFirst(){
        return first;
    }

    public T2 getSecond(){
        return second;
    }

}
