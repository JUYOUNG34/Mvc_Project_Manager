package kr.bit.service;

import kr.bit.entity.note;
import kr.bit.mapper.NoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteMapper noteMapper;

    public List<note> getReceivedNotes(int receiverId) {
        return noteMapper.getReceivedNotes(receiverId);
    }

    public List<note> getSentNotes(int senderId) {
        return noteMapper.getSentNotes(senderId);
    }
}