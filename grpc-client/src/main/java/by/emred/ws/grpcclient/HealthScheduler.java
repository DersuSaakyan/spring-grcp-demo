package by.emred.ws.grpcclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class HealthScheduler {

    private final HealthClient healthClient;

    @Autowired
    public HealthScheduler(HealthClient healthClient) {
        this.healthClient = healthClient;
    }

    @Scheduled(cron = "0 * * * * *")
    public void runHealthCheck() {
        healthClient.ping();
    }
}
