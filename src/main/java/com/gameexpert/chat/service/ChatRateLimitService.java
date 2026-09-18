package com.gameexpert.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ChatRateLimitService {

    private final StringRedisTemplate redisTemplate;

    public boolean allow(Long playerId) {
//        String key = "chat:limit:" + playerId;
//        String value = redisTemplate.opsForValue().get(key);
//        int count = value == null ? 0 : Integer.parseInt(value);
//        if (count >= 5) {
//            return false;
//        }
//
//        Long updated = redisTemplate.opsForValue().increment(key);
//        if (updated == 1L) {
//            redisTemplate.expire(key, Duration.ofSeconds(10));
//        }
//
//        return true;

        // TODO Lv 19: 횟수 확인부터 최초 만료 설정까지 원자적으로 실행합니다.
        String key       = "chat:limit:" + playerId;
        String luaScript =
                "local count = tonumber(redis.call('GET', KEYS[1]) or '0') " +
                        "if count >= 5 then return false " +
                        "end " +
                        "local updated = redis.call('INCR', KEYS[1]) " +
                        "if updated == 1 then redis.call('EXPIRE', KEYS[1], 10) " +
                        "end " +
                        "return true";

        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>(luaScript, Boolean.class);
        Boolean                     result      = redisTemplate.execute(redisScript, Collections.singletonList(key));

        return (result != null) && result;
    }
}
