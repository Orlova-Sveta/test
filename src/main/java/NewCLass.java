import java.util.Scanner;

public class NewCLass {
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine(); // получаем строку из консоли
        String[] mas = input.split(" "); // разделяем строку по пробелу на элементы

        if (mas.length != 3) { // кол-во элементов не может быть больше 3, иначе выражение не верное
            throw new Exception("Неверное выражение");
        }
        // начальное значение
        int a = 0;
        int b = 0;

        boolean isRim = false; // помогает распознать римские цифры далее в коде

        for (RimNumber rimNumber : RimNumber.values()) { // пытаемся найти римские числа
            if (rimNumber.toString().equals(mas[0])) {
                a = rimNumber.number;
                isRim = true;
            }
            if (rimNumber.toString().equals(mas[2])) {
                b = rimNumber.number;
                isRim = true;
            }
        }

        if ((a == 0 && b != 0) || (a != 0 && b == 0)) { // Считаем, что если мы ранее нашли римское число, то они оба должны быть указаны, если нет, то ошибка выражения
            throw new Exception(("Неверное значение или выражение"));
        } else if (a == 0 && b == 0) { // если мы не нашли римские числа, то ищем арабские
            try {
                a = Integer.parseInt(mas[0]);
                b = Integer.parseInt(mas[2]);
            } catch (Exception e) {// Если не удалось перевести в цифры, значит введено неверное выражение
                throw new Exception("Неверное значение или выражение");
            }
        }

        if ((a > 10 || a < 1) || (b > 10 || b < 1)) { // проверяем на допустимое значение от 1 до 10
            throw new Exception("Неверное значение");
        }

        int result = 0; //  задаём результат

        switch (mas[1]) { // ищем нужную операцию и высчитываем результат
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
            default:
                throw new Exception("Указана неверная операция"); // если не нашли совпадений, значит выражение или операция неверные
        }

        if (isRim && result <= 0) { //Проверяем на условие результата для Римских чисел
            throw new Exception("Результат не может быть меньше нуля");
        }

        if (isRim) { // Выводим результат в нужном формате
            System.out.println("= " + toRimNumber(result));
        } else {
            System.out.println("= " + result);
        }
    }

    public static String toRimNumber(int i) { // метод для перевода арабских в римские
        // Условия перевода в Римские числа следующий:
        // Запускаем цикл, пока значение не станет меньше 0
        // Вычитаем максимально возможное Римское число, которое меньше или равно значению
        // Добавляем получившееся значение в строку слева направо
        // Выводим получившееся значение
        RimNumber[] mas = RimNumber.values();
        StringBuilder result = new StringBuilder();
        while (i > 0) {
            for (int j = mas.length - 1; j >= 0; j--) {
                if (mas[j].number <= i) {
                    i = i - mas[j].number;
                    result.append(mas[j].toString());
                    break;
                }
            }
        }
        return result.toString();
    }
}


enum RimNumber {
    I(1), II(2), III(3), IV(4), V(5), VI(6), VII(7), VIII(8), IX(9), X(10), XL(40), L(50), C(100);
    int number;

    RimNumber(int number) {
        this.number = number;
    }
}