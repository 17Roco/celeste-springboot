package com.zsd.celeste.service.common;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class TrafficService {

    private int traffic = 0;

    public void addTraffic() {
        this.traffic += 1;
    }


}
