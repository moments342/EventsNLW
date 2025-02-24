package com.moments.nlw.events.repository;

import com.moments.nlw.events.model.Event;
import com.moments.nlw.events.model.Subscription;
import com.moments.nlw.events.model.User;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
    public Subscription findByEventAndSubscriber(Event evt, User user);
}
