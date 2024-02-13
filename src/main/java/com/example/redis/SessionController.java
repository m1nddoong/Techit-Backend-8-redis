package com.example.redis;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("session")
public class SessionController {
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setSession(
            @RequestParam("key")
            String key,
            @RequestParam("value")
            String value,
            HttpSession session
    ) {
        session.setAttribute(key, value);
    }

    @GetMapping
    public String getSession(
            @RequestParam("key")
            String key,
            HttpSession session
    ) {
        Object value = session.getAttribute(key);
        if (value == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        // 문자열인지 아닌지 체크해주는 노심초사 체크하는 부분 (중요하지 않음)
        if (!(value instanceof String))
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        return value.toString();

    }
}
