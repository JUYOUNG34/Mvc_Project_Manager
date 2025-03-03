package kr.bit.service;

import kr.bit.entity.Boards;
import kr.bit.mapper.NoticeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;


    public List<Boards> getAllNotices() {
        return noticeMapper.getAllNotices();
    }
    public Boards getDetailNotice(int id){return noticeMapper.getDetailNotice(id);}
}
