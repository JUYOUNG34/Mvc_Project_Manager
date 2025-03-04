package kr.bit.controller;


import kr.bit.entity.Boards;
import kr.bit.entity.Criteria;
import kr.bit.entity.PageCre;
import kr.bit.service.BoardService;
import kr.bit.service.LogService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu/board")
public class BoardController {

    @Autowired
    private BoardService boardService;;

    @Autowired
    private LogService logService;

    private String adminId;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @GetMapping("/boardList")
    public String boardList(@AuthenticationPrincipal UserDetails userDetails, Criteria criteria, Model model){

        adminId=userDetails.getUsername();

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

    @GetMapping("/ajaxBoards")
    @ResponseBody
    public Map<String,Object> ajaxBoards(Criteria criteria){

//        Criteria criteria = new Criteria();
//               criteria.setCurrent_page(Integer.parseInt(paramMap.get("current_page")));
//               criteria.setType(paramMap.get("type"));
//               criteria.setKeyword(paramMap.get("keyword"));


        List<Boards> boards = boardService.getBoards(criteria);
        int totalCount = boardService.getTotalCount(criteria);
        System.out.println(boards);
        PageCre pageCre = new PageCre();
        pageCre.setCriteria(criteria);
        pageCre.setTotalCount(totalCount);

        Map<String, Object> result = new HashMap<>();
        result.put("boards", boards);
        result.put("totalCount", totalCount);
        result.put("pageCre",pageCre);
        return result;
    }

    @GetMapping("/detailBoard/{id}")
    public @ResponseBody Boards detailBoard(@PathVariable("id")int id){
        return boardService.getDetailBoard(id);
    }
    @PutMapping("/blindProc/{id}")
    public @ResponseBody int boardBlind(@PathVariable("id")int board_id){

        String logMessage = String.format("%s|%s|%s|%s",
                LocalDateTime.now().format(formatter),adminId,"게시물 블라인드 : "+board_id,"");
        logService.logAction(logMessage);

        return boardService.updateBoardBlind(board_id);
    }
    @GetMapping("/notice/detail")
    public String noticeDetail(@Param("admin_writer_id")int admin_writer_id, Model model){
        Boards board = boardService.getDetailNotice(admin_writer_id);
        model.addAttribute("board",board);
        return "menu/board/notice/detail";
    }

}
