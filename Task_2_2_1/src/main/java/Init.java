import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import order.PizzaOrder;
import order.Status;
import queue.OrderQueue;
import queue.Stock;

/**
 * Initialises the whole system according to the data received from json.
 * Goes to sleep while the system is running(full-time work).
 * Wakes up and ensures correct shutdown of the system.
 *      1) Finalise orders.
 *      2) Wait for the bakers.
 *      3) Wait for the delivery men.
 */
public class Init {

    /**
     * Init.
     */
    public void init() {
        try {
            Path path = Path.of("src/pizzeria.json");
            var reader      = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            var readJson    = new ReadJson(reader);
            var workDayTime = readJson.getWorkingDay();

            reader      = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            readJson    = new ReadJson(reader);
            var stockSize   = readJson.getStockSize();

            reader      = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            readJson    = new ReadJson(reader);
            var bakers      = readJson.getBakers();

            reader      = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            readJson    = new ReadJson(reader);
            var delivery    = readJson.getDelivery();
            var uInterface  = new UserInterface();

            var orders  = new OrderQueue<PizzaOrder>();
            var stock   = new Stock<PizzaOrder>(stockSize);

            var bakersThreads    = new ArrayList<Thread>();
            var deliveryThreads  = new ArrayList<Thread>();
            var uiThread        = new Thread(uInterface);


            for (var i : bakers) {
                i.setOrders(orders);
                i.setStock(stock);
            }

            for (var i : delivery) {
                i.setStock(stock);
            }

            uInterface.setOrders(orders);

            uiThread.start();

            for (var i : bakers) {
                var newThread = new Thread(i);
                newThread.start();
                bakersThreads.add(newThread);
            }

            for (var i : delivery) {
                var newThread = new Thread(i);
                newThread.start();
                deliveryThreads.add(newThread);
            }

            try {
                Thread.sleep(workDayTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            uiThread.interrupt();

            for (var ignored : bakers) {
                var finishOrder = new PizzaOrder(-1);
                finishOrder.setStatus(Status.DONE);
                orders.add(finishOrder);
            }

            for (var i : bakersThreads) {
                i.join();
            }

            for (var ignored : delivery) {
                var finishOrder = new PizzaOrder(-1);
                finishOrder.setStatus(Status.DONE);
                stock.add(finishOrder);
                stock.add(finishOrder);
            }

            for (var i : deliveryThreads) {
                i.join();
            }


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}