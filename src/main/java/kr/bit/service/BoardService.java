package kr.bit.service;

import kr.bit.dao.BoardDao;
import kr.bit.entity.Boards;
import kr.bit.entity.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
   private BoardDao boardDao;

    public List<Boards> getBoards(Criteria criteria){
        return boardDao.getBoards(criteria);
    }
    public int getTotalCount(Criteria criteria){return boardDao.getTotalCount(criteria);}
    public Boards getDetailBoard(int user_id){return boardDao.getDetailBoard(user_id);}
    public int updateBoardBlind(int user_id){return boardDao.updateBoardBlind(user_id);}
    public Boards getDetailNotice(int admin_writer_id){return boardDao.getDetailNotice(admin_writer_id);}

}
