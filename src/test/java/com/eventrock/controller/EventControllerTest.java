package com.eventrock.controller;


import com.eventrock.model.Event;
import com.eventrock.model.Seat;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
    public void index_shouldReturnCreatePage() throws Exception {
        assertThat(eventController.index(new ExtendedModelMap()), is("event/createEvent"));
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
        Event event = stubEvent("Event name");
        eventController.createEvent(event, bindingResult, model);

        Mockito.verify(eventRepository).save(event);
    }

    @Test
    public void viewAllEvent_shouldReturnIndexPage() throws Exception {
        assertThat(eventController.viewAllEvents(new ExtendedModelMap()), is("event/index"));
    }

    @Test
    public void viewAllEvents_shouldGetAllEventFromEventRepository() throws Exception {
        Model model = new ExtendedModelMap();

        eventController.viewAllEvents(model);

        Mockito.verify(eventRepository).findAll();
    }

    @Test
    public void viewAllEvents_shouldAddEventsToModel() throws Exception {
        Model model = new ExtendedModelMap();
        List<Event> events = new ArrayList<>();
        events.add(stubEvent("event1"));
        events.add(stubEvent("event2"));

        Mockito.when(eventRepository.findAll()).thenReturn(events);

        eventController.viewAllEvents(model);

        assertThat(model.asMap().get("events"), is(events));
    }

    @Test
    public void viewDetail_shouldReturnDetailPage() throws Exception {
        assertThat(eventController.viewDetail(new ExtendedModelMap(), 1), is("event/showEvent"));
    }

    @Test
    public void viewDetail_shouldGetEventFromEventRepository() throws Exception {
        Model model = new ExtendedModelMap();
        Event event = stubEvent("event1");
        Mockito.when(eventRepository.findOne(1L)).thenReturn(event);
        eventController.viewDetail(model, 1);
        assertThat(model.asMap().get("event"), is(event));
    }

    private Event stubEvent(String name) {
        Seat seat = stubSeat();
        Event event = new Event();
        event.setName(name);
        event.setDescription("This is Description");
        event.setStartDate(LocalDate.of(2016,12,24));
        event.setEndDate(LocalDate.of(2016,12,31));
        event.setStartTime(LocalTime.MIDNIGHT);
        event.setEndTime(LocalTime.MIDNIGHT);
        event.setSeat(seat);
        seat.setEvent(event);
        return event;
    }

    private Seat stubSeat() {
        Seat seat = new Seat();
        seat.setName("Normal");
        seat.setId(1L);
        seat.setNumberOfSeats(20);
        seat.setPrice(BigDecimal.valueOf(100));
        return seat;
    }
}