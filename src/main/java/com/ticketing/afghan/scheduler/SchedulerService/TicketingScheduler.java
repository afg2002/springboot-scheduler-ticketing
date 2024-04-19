package com.ticketing.afghan.scheduler.SchedulerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class TicketingScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketingScheduler.class);

    private boolean activeJob = true;

    public void setActiveJob(boolean active) {
        this.activeJob = active;
    }

    public boolean isActiveJob() {
        return activeJob;
    }

    @Scheduled(cron = "0 */1 8-23 * * *")
    public void runScheduler() {
        if (isActiveJob()) {
            LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Jakarta"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", new Locale("id", "ID"));
            String formattedDateTime = localDateTime.format(formatter);
            if (localDateTime.getHour() >= 8) {
                LOGGER.info("Scheduler berjalan pada: {}", formattedDateTime);
                executeScheduler();
            } else {
                LOGGER.info("Scheduler di luar jam kerja.");
            }
        } else {
            LOGGER.info("Scheduler dinonaktifkan.");
        }
    }

    private void executeScheduler() {
        String apiUrl = "http://localhost:0308/api/booking/expired-bookings";
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.getForObject(apiUrl, Object.class);
            LOGGER.info("Scheduler selesai.");
        } catch (Exception e) {
            LOGGER.error("Gagal menjalankan scheduler: {}", e.getMessage());
        }
    }
}
