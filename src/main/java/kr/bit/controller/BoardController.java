package kr.bit.controller;

import kr.bit.entity.Boards;
import kr.bit.entity.Criteria;
import kr.bit.service.BoardService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

  @GetMapping("/boardList")
  public String boardList(){
      return "menu/board/boardList";
  }

    @GetMapping("/block")
    public String boardBlock(){
        return "menu/board/block";
    }
    @PostMapping("/ajaxBoards")
    @ResponseBody
    public Map<String,Object> ajaxBoards(@RequestBody Criteria criteria){
      List<Boards> boards =boardService.getBoards(criteria);
      int totalCount = boardService.getTotalCount(criteria);

      Map<String, Object> result = new HashMap<>();
      result.put("boards",boards);
      result.put("totalCount",totalCount);
      return result;
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
