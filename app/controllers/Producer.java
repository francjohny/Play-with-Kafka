package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Properties;

public class Producer extends Controller {
    public Result sayHello() {
        ObjectNode result = Json.newObject();
        result.put("foo", "bar");
        return ok(result);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result sayHi() {
        JsonNode json = request().body().asJson();
        String name = json.findPath("name").textValue();
        if (name == null) {
            return badRequest("Missing parameter [name]");
        } else {
            String topicName = "second";
            Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("acks", "all");
            props.put("retries", 0);
            props.put("batch.size", 16384);
            props.put("linger.ms", 1);
            props.put("buffer.memory", 33554432);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

            org.apache.kafka.clients.producer.Producer<String, String> producer = new KafkaProducer<>(props);
             producer.send(new ProducerRecord<>(topicName, "Hi " + name));
            System.out.println("Message sent successfully");
            producer.close();
            return ok("Hi " + name);
        }
    }
}
