package com.eventrock.controller;


import com.eventrock.model.Event;
import com.eventrock.repository.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class EventControllerTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private EventController eventController;

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

    @Test
    public void createEvent_shouldSaveEventToDatabase() throws Exception {
        Model model = new ExtendedModelMap();
        Event event = stubEvent();
        eventController.createEvent(event, bindingResult, model);

        Mockito.verify(eventRepository).save(event);
    }

    private Event stubEvent() {
        Event event = new Event();
        event.setName("Event_Name");
        event.setDescription("This is Description");
        event.setStartDate(LocalDate.of(2016,12,24));
        event.setEndDate(LocalDate.of(2016,12,31));
        event.setStartTime(LocalTime.MIDNIGHT);
        event.setEndTime(LocalTime.MIDNIGHT);
        return event;
    }
}