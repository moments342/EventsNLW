package com.moments.nlw.events.service;

import com.moments.nlw.events.dto.SubscriptionResponse;
import com.moments.nlw.events.exception.EventNotFoundException;
import com.moments.nlw.events.exception.SubscriptionConflictException;
import com.moments.nlw.events.exception.UserIndicadorNotFoundException;
import com.moments.nlw.events.model.Event;
import com.moments.nlw.events.model.Subscription;
import com.moments.nlw.events.model.User;
import com.moments.nlw.events.repository.EventRepository;
import com.moments.nlw.events.repository.SubscriptionRepository;
import com.moments.nlw.events.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SubscriptionRepository subRepo;

    public SubscriptionResponse createNewSubscription(String eventName, User user, Integer userId) {

        //recuperar o evento pelo nome
        Event evt = eventRepo.findByPrettyName(eventName);

        if  (evt == null) { //caso alternativo 2
            throw new EventNotFoundException("Evento " + eventName + " nao existe");
        }

        User userRec = userRepo.findByEmail(user.getEmail());

        if (userRec == null) { //caso alternativo 1
            userRec = userRepo.save(user);
        }

        User indicador = userRepo.findById(userId).orElse(null);
        if (indicador == null) {
            throw new UserIndicadorNotFoundException("Usuario " + userId + " nao existe");
        }

        Subscription tmpSub = subRepo.findByEventAndSubscriber(evt, userRec);
        if (tmpSub != null) { //caso alternativo 3
            throw new SubscriptionConflictException("Usuario " + userRec.getName() + " ja esta inscrito no evento " + evt.getTitle());
        }

        Subscription subs = new Subscription();
        subs.setEvent(evt);
        subs.setSubscriber(userRec);
        subs.setIndication(indicador);

        Subscription res = subRepo.save(subs);

        return new SubscriptionResponse(res.getSubscriptionNumber(), "http://codecraft.com/subscription/" + res.getEvent()
                .getPrettyName()+"/"+res.getSubscriber()
                .getId());
    }

}
