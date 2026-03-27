package expense;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.util.List;
import java.util.Date;

public class ExpenseReportTest {

    String expected = String.format("""
            Expenses %s
            Dinner	5000
            Dinner	5001	X
            Breakfast	1000
            Breakfast	1001	X
            Car Rental	200
            Meal expenses: 12002
            Total expenses: 12202
            """, new Date());

    @Test
    public void testExpenseReport() throws IOException {
        //Redirect System.out to buffer
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bo));

        var expenses = List.of(
                createExpense(ExpenseType.DINNER, 5000),
                createExpense(ExpenseType.DINNER, 5001),
                createExpense(ExpenseType.BREAKFAST, 1000),
                createExpense(ExpenseType.BREAKFAST, 1001),
                createExpense(ExpenseType.CAR_RENTAL, 200)
        );
        new ExpenseReport().printReport(expenses);

        bo.flush();
        String allWrittenLines = bo.toString();
        Assertions.assertEquals(normalize(expected), normalize(allWrittenLines));
    }

    private Expense createExpense(ExpenseType expenseType, int amount) {
        var expense = new Expense();
        expense.type = expenseType;
        expense.amount = amount;
        return expense;
    }
    private String normalize(String input) {
        return input.replace("\r\n", "\n").replace("\r", "\n");
    }

}