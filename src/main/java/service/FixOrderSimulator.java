package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.Message;
import senders.OrderSender;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixOrderSimulator {
    private String[] symbols;
    private ExecutorService service;
    private List<OrderSender> workerThreads;
    //private Throughput throughputWorker;
    private static final Logger LOGGER = LoggerFactory.getLogger(FixOrderSimulator.class);

    public FixOrderSimulator() {
        workerThreads = new ArrayList<>();
        this.service = Executors.newFixedThreadPool(10, r -> new Thread(r, "Fix Order Sending Thread"));
    }

    public void startSimulatorInAutomaticMode(final String[] symbols, int workers,
                                              BlockingQueue<Message> inputQueue) {
        for (int i = 0; i < workers; i++) {
            OrderSender senderEMS = new OrderSender(symbols, inputQueue);
            workerThreads.add(senderEMS);
        }
        workerThreads.forEach(t -> service.submit(t));
    }

    public void shutDown() {
        if (workerThreads != null) {
            workerThreads.forEach(t -> t.setRunning(false));
        }
        service.shutdown();
        LOGGER.info("All threads has been shutdown!");
    }

    /*public void startSimulatorInManualMode(final String[] symbols, final String exchange, final String brokerName,
                                           final String brokerId, final String clientId, final String clientName,
                                           int workers, boolean manualMode, BlockingQueue<Message> inputQueue) {
        workerThreads = new ArrayList<>();
        for (int i = 0; i < workers; i++) {
            OrderSender senderEMS = new OrderSender(serverUrl, topic, symbols, exchange, brokerName, brokerId, clientId, clientName, manualMode, inputQueue);
            workerThreads.add(senderEMS);
        }
        workerThreads.forEach(t -> service.submit(t));
    }*/
}
