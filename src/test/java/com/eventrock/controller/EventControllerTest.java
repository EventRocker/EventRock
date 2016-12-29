package com.eventrock.controller;

import com.eventrock.model.Booking;
import com.eventrock.model.Event;
import com.eventrock.model.Seat;
import com.eventrock.model.User;
import com.eventrock.repository.EventRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventControllerTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private EventController eventController;

    @Mock
    private User user;

    @Before
    public void setUp() throws Exception {
        User user = new User();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null));
    }

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
    public void createEvent_shouldSetUser() throws Exception {
        Model model = new ExtendedModelMap();
        Event event = spy(stubEvent("Event name"));

        eventController.createEvent(event, bindingResult, model);
        Mockito.verify(event).setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
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

        when(eventRepository.findAll()).thenReturn(events);

        eventController.viewAllEvents(model);

        assertThat(model.asMap().get("events"), is(events));
    }

    @Test
    public void viewDetail_shouldReturnDetailPage() throws Exception {
        Event event = stubEvent("event1");
        Mockito.when(eventRepository.findOneById(1L)).thenReturn(Optional.of(event));
        assertThat(eventController.viewDetail(new ExtendedModelMap(), 1), is("event/showEvent"));
    }

    @Test
    public void viewDetail_shouldGetEventFromEventRepository() throws Exception {
        Model model = new ExtendedModelMap();
        Event event = stubEvent("event1");
        Mockito.when(eventRepository.findOneById(1L)).thenReturn(Optional.of(event));
        eventController.viewDetail(model, 1);
        assertThat(model.asMap().get("event"), is(event));
    }

    @Test
    public void confirmation_shouldReturnConfirmationPage() throws Exception {
        assertThat(eventController.confirmation(new ExtendedModelMap()), is("event/confirmation"));
    }

    @Test
    public void book_shouldReturnBookPage() throws Exception {
        Model model = new ExtendedModelMap();
        Event event = stubEvent("event1");
        Mockito.when(eventRepository.findOneById(1L)).thenReturn(Optional.of(event));

        assertThat(eventController.book(model, 1), is("event/book"));
    }

    @Test
    public void book_shouldGetEventFromEventRepository() throws Exception {
        Model model = new ExtendedModelMap();
        Event event = stubEvent("event1");
        Mockito.when(eventRepository.findOneById(1L)).thenReturn(Optional.of(event));

        eventController.book(model, 1);

        assertThat(model.asMap().get("event"), is(event));
    }

    @Test
    public void book_shouldHaveOneSeatBooking() throws Exception {
        Model model = new ExtendedModelMap();
        Event event = stubEvent("event1");
        Mockito.when(eventRepository.findOneById(1L)).thenReturn(Optional.of(event));

        eventController.book(model, 1);

        assertThat(((Booking) model.asMap().get("booking")).getNumberOfSeats(), is(1L));
    }

    private Event stubEvent(String name) {
        Seat seat = stubSeat();
        Event event = new Event();
        event.setName(name);
        event.setDescription("This is Description");
        event.setStartDate(LocalDate.of(2016, 12, 24));
        event.setEndDate(LocalDate.of(2016, 12, 31));
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