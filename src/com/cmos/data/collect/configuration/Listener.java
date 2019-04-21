package com.cestco.service.collect.configuration;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

public class Listener {
//    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @KafkaHandler
//    public void handler(){
//
//        Properties props = new Properties();
//
//        props.put("bootstrap.servers", "192.168.0.164:9092");
////	      props.put("bootstrap.servers", "192.168.13.50:9092");
//        props.put("group.id", "test");
//
////          props.put("enable.auto.commit", "false"); //取消自动提交
////          props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
////          props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//
//        props.put("enable.auto.commit", "true");
//        props.put("session.timeout.ms", "10000");
//        props.put("auto.commit.interval.ms", "1000");
//
//        props.put("key.deserializer",
//                "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer",
//                "org.apache.kafka.common.serialization.StringDeserializer");
//
//        //zk连接超时
////          props.put("zookeeper.session.timeout.ms", "6000");
////          props.put("zookeeper.sync.time.ms", "200");
//
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
//        //   consumer.subscribe(Collections.singletonList("topic5"));
////          System.out.println("Subscribed to topic5 " + consumer);
//        int i = 0;
//        consumer.assign(Arrays.asList(new TopicPartition("topic5", 0)));
////          consumer.seekToBeginning(Arrays.asList(new TopicPartition("topic5", 0)));//不改变当前offset
//        consumer.seek(new TopicPartition("topic5", 0), currIndex);
//        while (true) {
//
//            ConsumerRecords<String, String> records = consumer.poll(1000);
//
//            for (ConsumerRecord<String, String> record : records){
//
////            	  System.out.println(record);
//                // print the offset,key and value for the consumer records.
//                System.out.printf("recevice offset = %d, key = %s, value = %s\n",
//                        record.offset(), record.key(), record.value());
//
//                long starttime = System.currentTimeMillis();
//                String s = "1";//dataHandler(new String(record.value()).trim());
//                long endtime = System.currentTimeMillis();
//                System.out.println(endtime-starttime);
////                  System.out.println("s======="+s);
//                if (s!=null){
//                    currIndex = record.offset();
//                    FileWriter writer = null;
//                    try {
//                        writer = new FileWriter(fileCurr, false);
//                        writer.write(String.valueOf(currIndex));
//
//                    } catch (IOException e2) {
//                        // TODO Auto-generated catch block
////		     				e2.printStackTrace();
//                    }
//                    finally{
//                        try {
//                            if (writer!=null) writer.close();
//                        } catch (IOException e) {
//                            // TODO Auto-generated catch block
//                            //					e.printStackTrace();
//                        }
//                    }
//                }
//
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


    @KafkaListener(topics = {"test"})
    public void listen(ConsumerRecord<?, ?> record) {
        System.out.println("kafka的key: " + record.key());
        System.out.println(record);
        System.out.println("kafka的value: " + record.value().toString());
//        logger.info("kafka的key: " + record.key());
//        logger.info("kafka的value: " + record.value().toString());
    }
}