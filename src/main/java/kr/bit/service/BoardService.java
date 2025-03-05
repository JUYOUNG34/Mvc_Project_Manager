package kr.bit.service;


import kr.bit.dao.BoardDao;
import kr.bit.entity.Boards;
import kr.bit.entity.Criteria;


import kr.bit.entity.Reports;
import kr.bit.mapper.BoardMapper;
import kr.bit.mapper.EventMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private BoardDao boardDao;

    public List<Boards> getBoards(Criteria criteria){
        return boardDao.getBoards(criteria);
    }
    public int getTotalCount(Criteria criteria){return boardDao.getTotalCount(criteria);}
    public Boards getDetailBoard(int user_id){return boardDao.getDetailBoard(user_id);}
    public int updateBoardBlind(int user_id){return boardDao.updateBoardBlind(user_id);}

    @Autowired
    private BoardMapper boardMapper;

    //공지사항 부분
    public Boards getDetailNotice(int admin_writer_id){return boardDao.getDetailNotice(admin_writer_id);}
    public List<Boards> getAllNotices(){return boardDao.getAllNotices();}
    public void insertNotice(Boards board){
        boardDao.insertNotice(board);
    }

    //수정중
    // 게시글 업데이트
    public void updateBoard(Boards board) {
        boardMapper.updateBoard(board);  // BoardMapper의 updateBoard 호출
    }

    // 공지사항 삭제
    public void deleteNotice(int id) {
        boardMapper.deleteNotice(id);  // BoardMapper의 deleteNotice 호출
    }


    // 검색 기능 추가 - 검색 타입에 따라 다른 검색 메소드 호출
    public List<Boards> searchNotices(String searchType, String searchKeyword) {
        // 검색 타입에 따라 적절한 검색 메소드 호출
        switch (searchType) {
//            case "writer_id":
//                // 작성자 ID로 검색
//                return boardDao.searchNoticesByWriterId(searchKeyword);
            case "nickname":
                // 닉네임으로 검색
                return boardDao.searchNoticesByNickname(searchKeyword);
            case "title":
                // 제목으로 검색
                return boardDao.searchNoticesByTitle(searchKeyword);
            default:
                // 기본적으로 모든 공지사항 반환
                return getAllNotices();
        }
    }
}

