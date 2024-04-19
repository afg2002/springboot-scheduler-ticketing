package com.ticketing.afghan.scheduler.Controller;


import com.ticketing.afghan.scheduler.SchedulerService.TicketingScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scheduler")
public class TicketingController {

    @Autowired
    private TicketingScheduler ticketingScheduler;
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketingController.class);
    @PostMapping("/toggle")
    public ResponseEntity<String> toggleScheduler() {
        boolean currentStatus = ticketingScheduler.isActiveJob();
        ticketingScheduler.setActiveJob(!currentStatus);
        String message = "Scheduler has been " + (currentStatus ? "deactivated" : "activated");
        LOGGER.info(message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
