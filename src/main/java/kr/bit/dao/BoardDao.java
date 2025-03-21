package kr.bit.dao;

import kr.bit.entity.Boards;
import kr.bit.entity.Criteria;
import kr.bit.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardDao {
    @Autowired
    private BoardMapper boardMapper;

    public List<Boards> getBoards(Criteria criteria){
        return boardMapper.getBoards(criteria);
    }
    public int getTotalCount(Criteria criteria){return boardMapper.getTotalCount(criteria);}
    public Boards getDetailBoard(int user_id){return boardMapper.getDetailBoard(user_id);}
    public int updateBoardBlind(int user_id){return boardMapper.updateBoardBlind(user_id);}
    public Boards getDetailNotice(int admin_writer_id){return boardMapper.getDetailNotice(admin_writer_id);}
    public List<Boards> getAllNotices(){return boardMapper.getAllNotices();}
    public void insertNotice(Boards board){
        boardMapper.insertNotice(board);
    }

//    // 아이디(작성자)로 공지사항 검색하는 메소드 추가
//    public List<Boards> searchNoticesByWriterId(String keyword) {
//        return boardMapper.searchNoticesByWriterId(keyword);
//    }

    // 닉네임으로 공지사항 검색하는 메소드 추가
    public List<Boards> searchNoticesByNickname(String keyword) {
        return boardMapper.searchNoticesByNickname(keyword);
    }

    // 제목으로 공지사항 검색하는 메소드 추가
    public List<Boards> searchNoticesByTitle(String keyword) {
        return boardMapper.searchNoticesByTitle(keyword);
    }
}