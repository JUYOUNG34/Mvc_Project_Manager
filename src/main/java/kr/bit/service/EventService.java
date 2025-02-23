package kr.bit.service;

import kr.bit.entity.Events;
import kr.bit.mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
public class EventService {

    @Autowired
    private EventMapper eventMapper;

    @Transactional  // 트랜잭션 추가
    public int insertEvent(Events event) {
        try {
            return eventMapper.insertEvent(event);
        } catch (Exception e) {
            log.error("이벤트 추가 중 에러 발생", e);
            throw new RuntimeException("이벤트 추가 실패", e);

        }
    }


    public List<Events> getAllEvents() {
        return eventMapper.getAllEvents();
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