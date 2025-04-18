package com.zsd.celeste.controller.sys;

import com.zsd.celeste.service.common.TrafficService;
import com.zsd.celeste.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys")
public class TrafficController {

    @Autowired
    private TrafficService service;
    @GetMapping("/traffic")
    public Result traffic() {
        return Result.map("traffic", service.getTraffic());
    }
}
