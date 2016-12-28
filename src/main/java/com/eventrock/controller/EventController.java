package com.eventrock.controller;

import com.eventrock.model.Event;
import com.eventrock.model.User;
import com.eventrock.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RequestMapping(value = "/events")
@Controller
public class EventController {

    private EventRepository eventRepository;

    @Autowired
    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String index(Model model) {
        Event event = new Event();
        model.addAttribute("event", event);
        model.addAttribute("event", new Event());
        return "event/createEvent";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createEvent(Event event, BindingResult bindingResult, Model model) {
        model.addAttribute("event", event);
        model.addAttribute("success", true);
        event.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        eventRepository.save(event);
        return "event/showEvent";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String viewAllEvents(Model model) {
        List<Event> events = eventRepository.findAll();
        model.addAttribute("events", events);
        return "event/index";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String viewDetail(Model model, @PathVariable long id) {
        Event event = eventRepository.findOne(id);
        model.addAttribute("event", event);
        model.addAttribute("success", false);
        return "event/showEvent";
    }
}
