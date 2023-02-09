
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите 2 числа от 1 до 10 (арабские или римские), используя плюс, минус, деление или умножение (напр. 2+2): ");
        String input = scanner.nextLine();
        System.out.println(calc(input));
    }

    public static String calc(String input) throws Exception {
        int num1;
        int num2;

        String[] actions = {"+", "-", "/", "*"};
        String[] regexActions = {"\\+", "-", "/", "\\*"}; //Я не могу взять индекс из массива actions, потому что в нем находятся + и *, но метод split принимает регулярные выражения, а + и * это синтаксические знаки, поэтому если брать их из метода actions - будет ошибка
        int actionIndex = -1;

        //Определяем арифметическое действие
        for (int i = 0; i < actions.length; i++) {
            if (input.contains(actions[i])) {
                actionIndex = i;
                break;
            }
        }

        //Если не нашли арифметического действия, выбрасываем исключение
        if (actionIndex == -1) throw new Exception("Некорректное выражение");

        //Сплитуем выражение и проверяем сколько чисел в нем находится
        String[] operands = input.split(regexActions[actionIndex]); //Делим (сплитуем) выражение по нашему недавнему найденному индексу (actionIndex), но уже из массива regexActions
        if (operands.length != 2) throw new Exception("Должно быть 2 числа и 1 арифметический знак");

        //Если оба числа римские
        if (Roman1.isRoman(operands[0]) && Roman1.isRoman(operands[1])) {
            //Конвертируем оба числа в арабские для вычисления действий
            num1 = Roman1.convertToArabian(operands[0]);
            num2 = Roman1.convertToArabian(operands[1]);

            if (num1 > 10 || num2 > 10) throw new Exception("Числа должны быть от I до X");


            //выполняем с числами арифметическое действие
            int result;
            String resultRoman;
            switch (actions[actionIndex]) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                default:
                    result = num1 / num2;
                    break;
            }

            if(result < 1) {
                throw new Exception("В римской системе нет отрицательных чисел и нуля");
            }

            resultRoman = Roman1.convertToRoman(result);
            return resultRoman;
        }

        //Если оба числа арабские
        else if (!Roman1.isRoman(operands[0]) && !Roman1.isRoman(operands[1])) {
            num1 = Integer.parseInt(operands[0]); //Для начала сконвертировать operands из типа String в тип int, для этого используется готовая функция Integer.parseInt
            num2 = Integer.parseInt(operands[1]);

            if(num1 > 10 || num1 < 1 || num2 > 10 || num2 < 1){
                throw new Exception("Числа должны быть от 1 до 10");
            }

            //выполняем с числами арифметическое действие
            int result;
            String resultArabian;
            switch (actions[actionIndex]) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                default:
                    result = num1 / num2;
                    break;
            }

            resultArabian = String.valueOf(result); //конвертируем число обратно в String
            //Возвращаем результат
            return resultArabian;
        }
        //Если одно число римское, а другое арабское
        else throw new Exception("Используются одновременно разные системы счисления");
    }

    class Roman1 {
        public static String[] romanArray = new String[]{
                "%", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", //знак процента здесь стоит просто, чтобы  чем-то заполнить символ под индексом 0
                "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX", "XXI", "XXII", "XXIII", "XXIV", "XXV",
                "XXVI", "XXVII", "XXVIII", "XXIX", "XXX", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI",
                "XXXVII", "XXXVIII", "XXXIX", "XL", "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L", "LI",
                "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX", "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX",
                "LXX", "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX", "LXXXI", "LXXXII", "LXXXIII",
                "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC", "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"}; //каждое римское число соответствует индексу арабского

        public static boolean isRoman(String value) {
            for (int i = 0; i < romanArray.length; i++) {
                if (value.equals(romanArray[i])) {
                    return true;
                }
            }
            return false;
        }

        public static int convertToArabian(String roman) throws Exception {
            for (int i = 0; i < romanArray.length; i++) {
                if (roman.equals(romanArray[i])) {
                    return i;
                }
            }
            return -1;

        }

        public static String convertToRoman(int result) {
            return romanArray[result];
        }
    }
}
