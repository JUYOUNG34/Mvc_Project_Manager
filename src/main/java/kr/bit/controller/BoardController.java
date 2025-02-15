package kr.bit.controller;

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

    @PostMapping("/block/{id}")
    public String blockBoard(@PathVariable int id) {
        boardService.blockBoard(id);
        return "redirect:/menu/board/list";
    }
}