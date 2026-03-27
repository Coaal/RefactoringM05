package theatricalplays;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class StatementPrinter {

    public String print(Invoice invoice, Map<String, Play> plays) {
        var totalAmount = 0;
        var volumeCredits = 0;
        var result = String.format("Statement for %s\n", invoice.customer);

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

        for (var performance : invoice.performances) {
            var play = plays.get(performance.playID);

            var thisAmount = calculateAmount(performance, play);

            volumeCredits += Math.max(performance.audience - 30, 0);

            if ("comedy".equals(play.type)) {
                volumeCredits += performance.audience / 5;
            }

            result += String.format(
                    "  %s: %s (%s seats)\n",
                    play.name,
                    format.format(thisAmount / 100),
                    performance.audience
            );

            totalAmount += thisAmount;
        }

        result += String.format("Amount owed is %s\n", format.format(totalAmount / 100));
        result += String.format("You earned %s credits\n", volumeCredits);

        return result;
    }

    private int calculateAmount(Performance performance, Play play) {
        int result = 0;

        switch (play.type) {
            case "tragedy":
                result = 40000;
                if (performance.audience > 30) {
                    result += 1000 * (performance.audience - 30);
                }
                break;

            case "comedy":
                result = 30000;
                if (performance.audience > 20) {
                    result += 10000 + 500 * (performance.audience - 20);
                }
                result += 300 * performance.audience;
                break;

            default:
                throw new Error("unknown type: " + play.type);
        }

        return result;
    }
}