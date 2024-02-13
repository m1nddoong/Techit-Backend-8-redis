package com.example.redis;

import com.example.redis.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class SimpleController {
    // 문자열 Key와 문자열로 구성된 Value를 다루기 위한 RedisTemplate
    private final StringRedisTemplate redisTemplate;


    @PutMapping("string")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setString(
            @RequestParam("key")
            String key,
            @RequestParam("value")
            String value
    ) {
        // Redis에 String을 저장하고 싶다
        // ValueOperations : Redis 기준 문자열 작업을 위한 클래스
        ValueOperations<String, String> operations
                = redisTemplate.opsForValue();
        // SET key value 라는 Redus 명령어를 사용한 것과 동일한 것
        operations.set(key, value);

        // List 를 위한 클래스
        //ListOperations<String, String> listOperations = redisTemplate.opsForList();
        //listOperations.leftPush(key, value);
        //listOperations.leftPop(key);

        // Set을 위한 클래스
        //SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        //setOperations.add(key, value)

    }

    @GetMapping("string")
    public String getString(
            @RequestParam("key")
            String key
    ) {
        ValueOperations<String, String> operations
                = redisTemplate.opsForValue();

        // GET key
        String value = operations.get(key);
        if (value == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return value;
    }


    // config 에 bean 으로 등록되어있던 것
    private final RedisTemplate<String, PersonDto> personDtoRedisTemplate;

    @PutMapping("person")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setPerson(
            @RequestParam("name")
            String name,
            @RequestBody
            PersonDto dto
    ) {
        ValueOperations<String, PersonDto> operations
                = personDtoRedisTemplate.opsForValue();
        operations.set(name, dto);
    }

    @GetMapping("person")
    public PersonDto getPerson(
            @RequestParam("name")
            String name
    ) {
        ValueOperations<String, PersonDto> operations
                = personDtoRedisTemplate.opsForValue();
        // GET key
        PersonDto value = operations.get(name);
        if (value == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return value;
    }
}
