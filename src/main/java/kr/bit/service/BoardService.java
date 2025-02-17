package kr.bit.service;


import kr.bit.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    public int blockBoard(int id) {
        return boardMapper.blockBoard(id);
    }
}

