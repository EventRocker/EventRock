package com.eventrock.controller;


import com.eventrock.model.Event;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class EventControllerTest {

    @Mock
    private BindingResult bindingResult;

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

    @Test
    public void createEvent_shouldAddEventToViewModel() throws Exception {
        Model model = new ExtendedModelMap();
        Event event = new Event();
        event.setName("Event_Name");
        eventController.createEvent(event, bindingResult, model);

        assertThat(model.asMap().get("event"), is(event));
    }

    @Test
    public void createEvent_shouldAddSuccessAttributeToViewModel() throws Exception {
        Model model = new ExtendedModelMap();
        Event event = new Event();
        event.setName("Event_Name");
        eventController.createEvent(event, bindingResult, model);

        assertThat(model.asMap().get("success"), is(true));
    }
}