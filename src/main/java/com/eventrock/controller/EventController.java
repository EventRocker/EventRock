package com.eventrock.controller;

import com.eventrock.model.Event;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "/event")
@Controller
public class EventController {

    @RequestMapping(value = "/create" , method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("event", new Event());
        return "index";
    }

    @RequestMapping(value = "/create" , method = RequestMethod.POST)
    public String createEvent(Event event, BindingResult bindingResult, Model model) {
        model.addAttribute("event", event);
        model.addAttribute("success", true);
        return "showEvent";
    }
}
