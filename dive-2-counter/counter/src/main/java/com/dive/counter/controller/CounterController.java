package com.dive.counter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;

@Slf4j
@RestController
@RequestMapping("/counter")
public class CounterController {

    HostAndPort hostAndPort = new HostAndPort("localhost", 6379);
    private static final String COUNTER_KEY = "my_counter";

    @PostMapping("/increment")
    public long increment() {
        log.info("Incrementing counter");
        try (Jedis jedis = new Jedis(hostAndPort)) {
            Long incrementedValue = jedis.incr(COUNTER_KEY);
            log.info("Incremented value: {}", incrementedValue);
            return incrementedValue;
        } catch (Exception e) {
            log.error("Error incrementing counter", e);
        }
        return 0;
    }

    @PostMapping("/decrement")
    public long decrement() {
        log.info("Decrementing counter");
        try (Jedis jedis = new Jedis(hostAndPort)) {
            Long decrementedValue = jedis.decr(COUNTER_KEY);
            log.info("Decremented value: {}", decrementedValue);
            return decrementedValue;
        } catch (Exception e) {
            log.error("Error decrementing counter", e);
        }
        return 0;
    }

    @PostMapping("/reset")
    public String reset() {
        log.info("Resetting counter");
        try (Jedis jedis = new Jedis(hostAndPort)) {
            var resetValue = jedis.set(COUNTER_KEY, "0");
            log.info("Reset value: {}", resetValue);
            return resetValue;
        } catch (Exception e) {
            log.error("Error resetting counter", e);
        }
        return "0";
    }

}
