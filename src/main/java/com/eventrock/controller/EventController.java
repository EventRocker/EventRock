package com.eventrock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EventController {

    @RequestMapping(value = "/create-event")
    public String index(){
        return "index";
    }
}
