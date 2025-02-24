package com.moments.nlw.events.repository;

import com.moments.nlw.events.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {
    Event findByPrettyName(String prettyName);
}
