package com.eventrock.controller;


import com.eventrock.model.Event;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class EventControllerTest {

    private EventController eventController;

    @Before
    public void setUp() throws Exception {
        eventController = new EventController();
    }

    @Test
    public void index_shouldReturnIndexPage() throws Exception {
        assertThat(eventController.index(new ExtendedModelMap()), is("index"));
    }

    @Test
    public void index_shouldAddEventToViewModel() throws Exception {
        Model model = new ExtendedModelMap();
        eventController.index(model);

        assertThat(model.asMap().get("event"), is(instanceOf(Event.class)));
    }

    //    @Test
//    public void createEvent_shouldCallEventServiceToCreateEvent() throws Exception {
//        eventController.createEvent();
//
//    }
}