import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Класс выражения.
 */
public class Expression {
    ArrayList<String> expression = new ArrayList<>();
    Stack<Double> stack = new Stack<>();

    /**
     * Конструктор класса.
     *
     * @param expr - Строковое представление выражения в польской нотации.
     */
    public Expression(String expr) {
        String[] intermediateExpression = expr.trim().replaceAll(" {2,5}", " ").split(" ");
        for (int i = intermediateExpression.length - 1; i >= 0; --i) {
            expression.add(intermediateExpression[i]);
        }
    }

    /**
     * Вычисление выражения.
     *
     * @return Значение выпажения.
     * @throws IllegalOperationException    - Если произожшла некорректная опреацияю
     * @throws WrongPolishNotationException - Если выражение записано не в прямой польской нотации.
     */
    public double calculate() throws IllegalOperationException, WrongPolishNotationException {
        double number;
        for (String s : expression) {
            try {
                number = Double.parseDouble(s);
            } catch (NumberFormatException e) {
                try {
                stack.push(new Operation(s).apply(stack));
                } catch (EmptyStackException e2) {
                    throw new WrongPolishNotationException();
                }
                continue;
            }
            stack.push(number);
        }

        double answer = stack.pop();

        try {
            stack.peek();
        } catch (EmptyStackException e) {
            return answer;
        }
        throw new WrongPolishNotationException();
    }

}
