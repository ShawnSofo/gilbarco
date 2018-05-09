package com.shawnsofo.gilbarco.time.api;

import java.time.Clock;

import com.shawnsofo.gilbarco.time.entity.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/now")
public class CurrentTimeService {

    @Autowired
    private Clock clock;

    private static volatile long calls = 0;

    @RequestMapping(path = "est")
    public synchronized Timestamp getCurrentTimeInEST() {
        calls++;
        return new Timestamp(calls, this.clock);
    }

}