package com.moments.nlw.events.controller;

import com.moments.nlw.events.model.Event;
import com.moments.nlw.events.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventService service;

    @PostMapping("/events")
    public Event addNewEvent(@RequestBody Event newEvent) {
        return service.addNewEvent(newEvent);
    }

    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return  service.getAllEvents();
    }

    @GetMapping("/events/{prettyName}")
    public ResponseEntity<Event> getEventByPrettyName(@PathVariable String prettyName) {
        Event evt = service.getByPrettyName(prettyName);
        if (evt == null) { //garante que caso nao exista o evento, seja respondido um 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else  {
            return ResponseEntity.ok().body(evt); //se existir ele responde um 200 e insere o evt no body
        }

    }



}
