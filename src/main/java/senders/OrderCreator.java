package senders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.Message;
import quickfix.field.*;
import util.Utility;

import java.util.concurrent.ThreadLocalRandom;

public class OrderCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCreator.class);
    private static PriceRange priceRange = PriceRange.getInstance();

    public static Message createClientOrder(final String symbol,final String clientId, final String clientName) {

        ThreadLocalRandom random = ThreadLocalRandom.current();
        PriceRange.Circuit circuit = priceRange.getTodaysSymbolCircuit(symbol);
        double price = circuit.getPriceRange();
        final double sendingPrice = Double.valueOf(Utility.dataFormat.format(random.nextDouble(price, price + 5)));
        double lowerCircuit = circuit.getLowerCircuit();
        double upperCircuit = circuit.getUpperCircuit();
        Message message = new Message();
        if (sendingPrice >= lowerCircuit && sendingPrice <= upperCircuit && spreadValidity(sendingPrice)) {
            /*message = new quickfix.fix42.NewOrderSingle(new ClOrdID(UUID.randomUUID().toString()), new HandlInst('1'), new Symbol("6758.T"),
                    new Side(Side.BUY), new TransactTime(LocalDateTime.now()), new OrdType(OrdType.MARKET));*/
            message.getHeader().setField(new MsgType("D"));
            message.setField(new Account("Ashish"));
            message.setField(new AvgPx(45.3));
            message.setField(new CumQty(45.3));
            message.setField(new ExecID(""));
            message.setField(new ExecInst(""));
            message.setField(new ExecBroker("brokerId"));
            message.setField(new ExecTransType(ExecTransType.NEW));
            message.setField(new LastCapacity(ExecTransType.NEW));
            message.setField(new LastMkt("exchange"));
            message.setField(new LastShares(100));
            message.setField(new OrderID("a100"));
            message.setField(new OrderQty(100l));
            message.setField(new OrdStatus(OrdStatus.NEW));
            message.setField(new OrdType(OrdStatus.NEW));
            message.setField(new Side(Side.BUY));
            message.setField(new Symbol(symbol));
            message.setField(new ClientID(clientId));
            message.setField(new Text(symbol));
            message.setField(new TimeInForce(TimeInForce.DAY));
            message.setField(new TransactTime());
            message.setField(new SymbolSfx(""));
            message.setField(new TradeDate());
            message.setField(new StopPx(10.2));
            message.setField(new OrdRejReason());
            message.setField(new MinQty(10));
            message.setField(new MaxFloor(10));
            message.setField(new ExecType());
            message.setField(new LeavesQty());
            message.setField(new SecurityType());
            message.setField(new ContraBroker());
            message.setField(new LastLiquidityInd());
            message.setField(new OrigClOrdID());
            message.setField(new SendingDate());
            message.setField(new SendingTime());
            message.setField(new ClOrdID());
            message.setField(new HandlInst('1'));
        } else {
           // LOGGER.warn("Order has been rejected by broker due to out of range price for stock {} , circuit {} OR spread check failed ", symbol, circuit);
            return null;
        }
        return message;
    }

    private static boolean spreadValidity(double sendingPrice) {
        return ((sendingPrice % 5) * 10) % 5 == 0;
    }
}
