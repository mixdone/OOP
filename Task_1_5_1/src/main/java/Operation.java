import java.util.EmptyStackException;
import java.util.Stack;

import static java.lang.Double.NaN;

/**
 * Класс операций.
 */
public class Operation {
    String name;
    int argumentCount;

    /**
     * Конструктор класса.
     *
     * @param operation - строковое представление операции.
     * @throws IllegalArgumentException - если операция невозможна.
     */
    public Operation(String operation) throws IllegalArgumentException {
        name = operation;
        switch (name) {
            case "+":
                argumentCount = 2;
                break;
            case "-":
                argumentCount = 2;
                break;
            case "*":
                argumentCount = 2;
                break;
            case "/":
                argumentCount = 2;
                break;
            case "pow":
                argumentCount = 2;
                break;
            case "log":
                argumentCount = 1;
                break;
            case "sin":
                argumentCount = 1;
                break;
            case "cos":
                argumentCount = 1;
                break;
            case "sqrt":
                argumentCount = 1;
                break;
            default:
                throw new IllegalArgumentException();
        };
    }

    /**
     * Применение опреации(функции).
     *
     * @param stack - Стек с аргументами.
     * @return Значение выражения.
     * @throws IllegalOperationException - Если невозможно получить корректный ответ.
     */
    public double apply(Stack<Double> stack) throws IllegalOperationException {
        double answer;
        if (argumentCount == 1) {
            double arg = stack.pop();
            answer = switch (name) {
                case "sin"  -> Math.sin(arg);
                case "cos"  -> Math.cos(arg);
                case "sqrt" -> Math.sqrt(arg);
                case "log"  -> Math.log(arg);
                default     -> NaN;
            };
        } else if (!name.equals("-")){
            double arg1 = stack.pop();
            double arg2 = stack.pop();
            answer = switch (name) {
                case "+"    -> arg1 + arg2;
                case "*"    -> arg1 * arg2;
                case "pow"  -> Math.pow(arg1, arg2);
                default     -> NaN;
            };
        } else {
            double arg1 = stack.pop();
            double arg2;
            try {
                arg2 = stack.pop();
            } catch (EmptyStackException e) {
                arg2 = arg1;
                arg1 = 0;
            }
            answer = arg1 - arg2;
        }

        if (Double.isNaN(answer) || Double.isInfinite(answer)) {
            throw new IllegalOperationException();
        }

        return answer;
    }
}
