package com.eventrock.controller;

import com.eventrock.model.Event;
import com.eventrock.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "/events")
@Controller
public class EventController {

    private EventRepository eventRepository;

    @Autowired
    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @RequestMapping(value = "/create" , method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("event", new Event());
        return "index";
    }

    @RequestMapping(value = "/create" , method = RequestMethod.POST)
    public String createEvent(Event event, BindingResult bindingResult, Model model) {
        model.addAttribute("event", event);
        model.addAttribute("success", true);
        eventRepository.save(event);
        return "showEvent";
    }
}
