package com.moments.nlw.events.controller;

import com.moments.nlw.events.dto.ErrorMessage;
import com.moments.nlw.events.dto.SubscriptionResponse;
import com.moments.nlw.events.exception.EventNotFoundException;
import com.moments.nlw.events.exception.SubscriptionConflictException;
import com.moments.nlw.events.exception.UserIndicadorNotFoundException;
import com.moments.nlw.events.model.Subscription;
import com.moments.nlw.events.model.User;
import com.moments.nlw.events.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionService service;

    @PostMapping({"/subscription/{prettyName}", "/subscription/{prettyName}/{userId}"})
    public ResponseEntity<?> createSubscription(@PathVariable("prettyName") String prettyName,
                                                    @RequestBody User subscriber,
                                                    @PathVariable(required = false) Integer userId) {
        try {
            SubscriptionResponse res = service.createNewSubscription(prettyName, subscriber, userId);
            if (res != null) {
                return ResponseEntity.ok(res);
            }
        }  catch (EventNotFoundException | UserIndicadorNotFoundException ex) {
                return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }
        catch (SubscriptionConflictException ex) {
            return ResponseEntity.status(409).body(new ErrorMessage(ex.getMessage()));
        }
        return ResponseEntity.badRequest().build();
    }
}
