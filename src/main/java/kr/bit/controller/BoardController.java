package kr.bit.controller;


import kr.bit.entity.Boards;
import kr.bit.entity.Criteria;
import kr.bit.entity.PageCre;
import kr.bit.service.BoardService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import kr.bit.service.BoardService;
import kr.bit.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/menu/board")
@RequiredArgsConstructor
public class BoardController {
    @Autowired
    private BoardService boardService;


    @Autowired
    private BoardService boardService;

    @GetMapping("/boardList")
    public String boardList(Criteria criteria, Model model){
        List<Boards> boards = boardService.getBoards(criteria);
        model.addAttribute("boards",boards);

        int totalCount = boardService.getTotalCount(criteria);
        model.addAttribute("totalCount",totalCount);

        PageCre pageCre = new PageCre();
        pageCre.setCriteria(criteria);
        pageCre.setTotalCount(totalCount);
        model.addAttribute("pageCre", pageCre);

        return "menu/board/boardList";
    }

    @GetMapping("/block")
    public String boardBlock(){
        return "menu/board/block";
    }

    @GetMapping("/detailBoard/{user_id}")
    public @ResponseBody Boards detailBoard(@PathVariable("user_id")int user_id){
       return boardService.getDetailBoard(user_id);
    }
    @PutMapping("/boardBlind/{user_id}")
    public @ResponseBody int boardBlind(@PathVariable("user_id")int user_id){
        return boardService.updateBoardBlind(user_id);
    }
    @GetMapping("/notice/detail")
    public String noticeDetail(@Param("admin_writer_id")int admin_writer_id, Model model){
        Boards board = boardService.getDetailNotice(admin_writer_id);
        model.addAttribute("board",board);
        return "menu/board/notice/detail";
    }
    @GetMapping("/notice/add")
    public String noticeAdd(){
        return "menu/board/notice/add";
    }


}

    @PostMapping("/block/{id}")
    public String blockBoard(@PathVariable int id) {
        boardService.blockBoard(id);
        return "redirect:/menu/board/list";
    }
}

