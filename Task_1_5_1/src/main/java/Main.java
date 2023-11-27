import java.util.Scanner;

/**
 * Класс для работы с пользователем.
 */
public class Main {
    /**
     * Взаимодействие с пользовательем.
     */
    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println(new Expression(input.nextLine()).calculate());
        } catch (WrongPolishNotationException e1) {
             System.out.println("Incorrect writing of the expression");
        } catch (IllegalOperationException e2) {
            System.out.println("Unable to get the final value");
        }
    }
}
