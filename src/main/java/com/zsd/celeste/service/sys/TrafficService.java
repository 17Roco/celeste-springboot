package com.zsd.celeste.service.sys;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Queue;

@Getter
@Service
public class TrafficService {

    private int traffic = 0;

    public void addTraffic() {
        this.traffic += 1;
    }


}
