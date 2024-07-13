
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("Please input string with arithmetic operation: ");
        Scanner console = new Scanner(System.in);
        String str1 = console.nextLine();
        String str2 = calc(str1);
        System.out.println("Result is: " + str2);

    }

    //function start
    public static String calc(String input) {
        char[] charArray = input.toCharArray();

        int[] numbers = new int[2];
        byte offset = 0;

        int result = 0;

        byte startFilt;
        byte endfilt;

        boolean isfirstRoman = false;
        boolean checkRoman = false;
        boolean isMinus = false;
        char arith = '0';
        //Starting search for different elements
        for (int i = 0, j; i < input.length(); ) {
            charArray[i] = Character.toUpperCase(charArray[i]);
            //for Roman numbers
            if (isRoman(charArray[i])) {
                j = i;
                while (isRoman(charArray[j])) {
                    if (charArray[j] == 'I') {
                        numbers[offset] += 1;
                        j++;

                    } else if (charArray[j] == 'V') {
                        if (numbers[offset] != 0)
                            numbers[offset] = 4;
                        else numbers[offset] += 5;
                        j++;
                    } else if (charArray[j] == 'X') {
                        if (numbers[offset] != 0 && numbers[offset] != 10)
                            numbers[offset] = 9;
                        else numbers[offset] += 10;
                        j++;
                    }
                    if (j >= input.length()) {
                        i++;
                        break;
                    }
                    i = j;
                }
                checkRoman = !checkRoman;
                offset++;
                // for digits
            } else if (Character.isDigit(charArray[i])) {
                if (charArray[0] == '-') {
                    isMinus = true;
                }
                startFilt = (byte) i;
                endfilt = (byte) i;
                j = i;
                while (Character.isDigit(charArray[j])) {

                    endfilt++;
                    if (j >= input.length() - 1) {
                        i++;
                        break;
                    }
                    j++;
                    i = j;
                }
                //ArrayIndexOutOfBoundException in another form ;)
                if (offset == 2) {
                    throw new IllegalArgumentException("Only integer numbers and only two number");
                }
                numbers[offset] = Integer.parseInt(input.substring(startFilt, endfilt).trim());
                offset++;
                // if it's char and arithmetic operation
            } else if (charArray[i] == '+' || charArray[i] == '-' || charArray[i] == '*' || charArray[i] == '/') {
                arith = charArray[i];
                i++;
            } else i++;
        }
//checking if first number is negative
        if (isMinus) {
            numbers[0] *= -1;
        }
//checking if there are roman numbers and changing result to roman from arabic
        if (Character.isLetter(charArray[0])) {
            isfirstRoman = true;
        }
        //Searching for some unchecked exceptions
        if ((isRoman(charArray[0])) && arith == '-' && numbers[1] > numbers[0]) {
            throw new IllegalArgumentException("Only arabic or roman numbers between 1 and 10 allowed");
        } else if ((numbers[0] <= 0 || numbers[0] > 10) || (numbers[1] <= 0 || numbers[1] > 10)) {
            throw new IllegalArgumentException("Only arabic or roman numbers between 1 and 10 allowed");
        } else if (checkRoman) {
            throw new IllegalArgumentException("Only arabic or roman numbers between 1 and 10 allowed");
        } else if (arith == '0') {
            throw new IllegalArgumentException("Only arabic or roman numbers between 1 and 10 allowed");
        }

        // Doing arithmetic operation after searching
        result = switch (arith) {
            case '+' -> numbers[0] + numbers[1];
            case '-' -> numbers[0] - numbers[1];
            case '*' -> numbers[0] * numbers[1];
            case '/' -> numbers[0] / numbers[1];
            default -> result;
        };

        if (isfirstRoman)
            return arabicToRoman(result);
        else {
            return String.valueOf(result);
        }
    }
//for convenience
    public static boolean isRoman(char letter) {
        return letter == 'I' || letter == 'V' || letter == 'X';
    }

    public static String arabicToRoman(int number) {
        if ((number <= 0) || (number > 4000)) {
            throw new IllegalArgumentException(number + " is not in range (0,4000]");
        }

        List<RomanNumbers> romanNumerals = RomanNumbers.getReversedSortedNumbers();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumbers currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }
}