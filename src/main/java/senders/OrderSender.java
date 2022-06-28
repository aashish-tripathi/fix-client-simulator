package senders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.Message;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.field.ClientID;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class OrderSender implements Runnable{
    private volatile boolean running = true;
    final private String[] symbols;
    final private String clientId;
    final private String clientName;
    //private Throughput throughput;
    private boolean manualMode;
    private BlockingQueue<Message> inputQueue;
    private SessionID sessionID;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSender.class);

    public OrderSender(String[] symbols, String clientId, String clientName,
                       BlockingQueue<Message> inputQueue) {
        this.symbols = symbols;
        this.clientId = clientId;
        this.clientName = clientName;
        //this.throughput = throughputWorker;
        this.inputQueue = inputQueue;
        LOGGER.info("Order sending started by client {} ", clientName);
    }

    @Override
    public void run() {
        ThreadLocalRandom localRandom = ThreadLocalRandom.current();
        long start = System.currentTimeMillis();
        int msgCount = 0;
        while (isRunning()) {
            try {
                String randomStock = symbols[localRandom.nextInt(symbols.length)];
                Message newOrder = null;
                if (manualMode) {
                    // take it from Queue
                    newOrder = inputQueue.poll();
                } else {
                    newOrder = OrderCreator.createClientOrder(randomStock,clientId, clientName);
                }
                if (newOrder == null) {
                    continue;
                }
                Session.sendToTarget(newOrder, sessionID);
                //publishToKafka(newOrder);
                Thread.sleep(5000);
                LOGGER.info("Order {} sent by {}", newOrder, newOrder.getField(new ClientID()).getValue());
            } catch (Exception e) {
                LOGGER.error("Error occurred while sending order " + e.fillInStackTrace());
            }
            msgCount++;
        }
        LOGGER.warn("Thread {} received shutdown signal ", Thread.currentThread().getName());
        long end = System.currentTimeMillis();
        long timeT = (end - start) / 1000;
        long msgF = msgCount;
        LOGGER.info("Message rate/sec {}", msgF / timeT);
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}
