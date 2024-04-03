import com.google.gson.JsonParser;
import java.io.Reader;
import java.util.ArrayList;
import workers.Baker;
import workers.Delivery;

/**
 * Class read json.
 */
public class ReadJson {
    Reader reader;

    /**
     * ReadJson constructor.
     *
     * @param reader Reader.
     */
    public ReadJson(Reader reader) {
        this.reader = reader;
    }

    /**
     * Get ArrayList of bakers from json.
     *
     * @return bakers.
     */
    public ArrayList<Baker> getBakers() {

        var bakersArray = JsonParser.parseReader(reader)
                .getAsJsonObject().get("bakers").getAsJsonArray();

        var bakers = new ArrayList<Baker>();

        for (var i : bakersArray) {
            if (i.isJsonObject()) {
                var name = i.getAsJsonObject().get("name").getAsString();
                var time = i.getAsJsonObject().get("time").getAsInt();
                bakers.add(new Baker(name, time));
            }
        }

        return bakers;

    }

    /**
     * Get ArrayList of Deliverymen from json.
     *
     * @return deliverymen.
     */
    public ArrayList<Delivery> getDelivery() {
        var deliveryArray = JsonParser.parseReader(reader)
                .getAsJsonObject().get("delivery").getAsJsonArray();
        var delivery = new ArrayList<Delivery>();

        for (var i : deliveryArray) {
            if (i.isJsonObject()) {
                var name    = i.getAsJsonObject().get("name").getAsString();
                var amount  = i.getAsJsonObject().get("amount").getAsInt();
                delivery.add(new Delivery(name, amount));
            }
        }

        return delivery;
    }

    /**
     * Get working day time from json.
     *
     * @return working day int time.
     */
    public int getWorkingDay() {
        return JsonParser.parseReader(reader).getAsJsonObject().get("workday").getAsInt();
    }

    /**
     * Get stock size from json.
     *
     * @return stock int size.
     */
    public int getStockSize() {
        return JsonParser.parseReader(reader).getAsJsonObject().get("stock").getAsInt();
    }



}