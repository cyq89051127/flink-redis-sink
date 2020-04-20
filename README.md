This Project is used to write data to redis in flink stream app with table or sql api. Before, i see the project [bahir-flink](https://github.com/apache/bahir-flink)
 which has a module to help write to redis with flink. But, it workes but it doesn't match my desire perfectly. 
 
 When i want to sink a row/table into redis:

    Firstly,i'd like to choose one or more field as the redis key,
    Secondly, i'd like to take the row as a map or json string as the value.
    Thirdly, watermark needs to be supported too.
    
 But it doesn't look easly without changing the source code. I'd like to write my own redis-sink.
 
 There're some tips to remember when you're looking at this project:
 
 1. This project only supports REDIS CLUSTER mode. 
 2. This project using redisson to connect to redis, if you care about redisson configs, please refer to RedissonConstant.java
 3. If you want to know what you can config for redis sink, please refer to RedisValidator.java
 
 Here is an example(KafkaToRedisSql.java) which read data from kafka and write data to redis cluster. 
