import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum RomanNumbers {
    I(1), IV(4), V(5), IX(9), X(10), XL(40), L(50), XC(90), C(100);

    private int value;

    RomanNumbers(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
//getting enums to list in reversed sorted order for cycling through them
    public static List<RomanNumbers> getReversedSortedNumbers(){
        return Arrays.stream(values())
                .sorted(Comparator.comparing((RomanNumbers e) -> e.value)
                .reversed())
                .collect(Collectors.toList());
    }
}
