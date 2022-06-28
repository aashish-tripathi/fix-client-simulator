package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

public class Utility {

    public static DecimalFormat dataFormat = new DecimalFormat("####.#");

    private static final Logger LOGGER = LoggerFactory.getLogger(Utility.class);

    /*public static byte[] serealizeAvroHttpRequestJSON(
            Message request) {
        DatumWriter<Message> writer = new SpecificDatumWriter<>(
                Message.class);
        byte[] data = new byte[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Encoder jsonEncoder = null;
        try {
            jsonEncoder = EncoderFactory.get().jsonEncoder(
                    Message.getClassSchema(), stream);
            writer.write(request, jsonEncoder);
            jsonEncoder.flush();
            data = stream.toByteArray();
        } catch (IOException e) {
            LOGGER.error("Serialization error:" + e.getMessage());
        }
        return data;
    }*/

    /*public static Order deSerealizeAvroHttpRequestJSON(byte[] data) {
        DatumReader<Order> reader
                = new SpecificDatumReader<>(Order.class);
        Decoder decoder = null;
        try {
            decoder = DecoderFactory.get().jsonDecoder(Order.getClassSchema(), new String(data));
            return reader.read(null, decoder);
        } catch (IOException e) {
            LOGGER.error("Deserialization error:" + e.getMessage());
        }
        return null;
    }*/
}
