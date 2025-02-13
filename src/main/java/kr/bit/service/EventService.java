package kr.bit.service;

import kr.bit.entity.Events;
import kr.bit.mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventMapper eventMapper;


    public List<Events> getAllEvents() {
        return eventMapper.getAllEvents();
    }


    public int insertEvent(Events event) {
        return eventMapper.insertEvent(event);
    }


    public int updateEvent(Events event) {
        return eventMapper.updateEvent(event);
    }


    public int deleteEvent(int id) {
        return eventMapper.deleteEvent(id);
    }

    public Events getEventById(int id) {
        return eventMapper.getEventById(id);
    }
}