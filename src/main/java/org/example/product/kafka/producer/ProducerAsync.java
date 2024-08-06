package org.example.product.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.properties.PropertiesLoader;

import java.util.Properties;

/**
 * sync와는 다르게 결과값을 기다리지 않는다. 언젠가는 데이터 결과값이 들어오면 그때 결과를 out 할수 있다.
 * 순서를 중요시 여기는 운영에서는 맞지 않겠지만, 단순 데이터만 들어가기만 하면 되고 순서는 맞지 않아도 되는 곳에서는
 * 속도면에서는 좋은 선택인듯.
 */
public class ProducerAsync {
    public static void main(String[] args) {
        Properties props = new Properties(); //Properties 오브젝트를 시작합니다.
        props.put("bootstrap.servers", PropertiesLoader.getInstance().getProperties().getValue("kafka","bootstrap.servers")); //브로커 리스트를 정의합니다.
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer"); //메시지 키와 벨류에 문자열을 지정하므로 내장된 StringSerializer를 지정합니다.
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props); //Properties 오브젝트를 전달해 새 프로듀서를 생성합니다.

        try {
            for (int i = 0; i < 5; i++) {
                ProducerRecord<String, String> record = new ProducerRecord<>("gon-basic01", "Date : "+ System.currentTimeMillis()+",  Apache Kafka is a distributed streaming platform"); //ProducerRecord 오브젝트를 생성합니다.
                producer.send(record, new ProducerGonCallback(record)); //프로듀서에서 레코드를 보낼 때 콜백 오브젝트를 같이 보냅니다.
                Thread.sleep(1000);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            producer.close(); // 프로듀서 종료
        }
    }
}