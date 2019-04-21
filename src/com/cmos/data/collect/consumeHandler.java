package com.cestco.service.collect;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
class consumeHandler implements DisposableBean, Runnable {

    private Thread thread;
    private volatile boolean someCondition;

    @Value("${kafka.consumer.servers}")
    private String servers;

    consumeHandler(){
        someCondition = true;
        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void run(){

        Properties props = new Properties();
System.out.println("kfservers="+servers);
        props.put("bootstrap.servers", servers);
//        props.put("group.id", environment.getProperty("kafka.consumer.group.id"));
//
//        props.put("enable.auto.commit", environment.getProperty("kafka.consumer.enable.auto.commit"));
//        props.put("session.timeout.ms", environment.getProperty("kafka.consumer.session.timeout"));
//        props.put("auto.commit.interval.ms", environment.getProperty("kafka.consumer.auto.commit.interval"));

        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");

        //zk连接超时
//        props.put("kafka.consumer.zookeeper.connect",environment.getProperty("kafka.consumer.zookeeper.connect"));
//          props.put("zookeeper.session.timeout.ms", environment.getProperty("6000"));
//          props.put("zookeeper.sync.time.ms", environment.getProperty("200"));

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //   consumer.subscribe(Collections.singletonList("topic5"));
//          System.out.println("Subscribed to topic5 " + consumer);
        int i = 0;
        long currIndex = 8;
//        consumer.assign(Arrays.asList(new TopicPartition(environment.getProperty("kafka.consumer.topic"), 0)));
////          consumer.seekToBeginning(Arrays.asList(new TopicPartition("topic5", 0)));//不改变当前offset
//        consumer.seek(new TopicPartition(environment.getProperty("kafka.consumer.topic"), 0), currIndex);
        while (true) {

            ConsumerRecords<String, String> records = consumer.poll(1000);

            for (ConsumerRecord<String, String> record : records){

//            	  System.out.println(record);
                // print the offset,key and value for the consumer records.
                System.out.printf("recevice offset = %d, key = %s, value = %s\n",
                        record.offset(), record.key(), record.value());

                long starttime = System.currentTimeMillis();
                String s = "1";//dataHandler(new String(record.value()).trim());
                long endtime = System.currentTimeMillis();
                System.out.println(endtime-starttime);
//                  System.out.println("s======="+s);
                if (s!=null){
                    currIndex = record.offset();
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
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

//        while(someCondition){
//            //doStuff();
//            System.out.println("1");
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void destroy(){
        someCondition = false;
    }

}
