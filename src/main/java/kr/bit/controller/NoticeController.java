package kr.bit.controller;

import kr.bit.entity.Admins;
import kr.bit.entity.Boards;
import kr.bit.entity.Reports;
import kr.bit.mapper.LoginMapper;

import kr.bit.service.AdminService;
import kr.bit.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/menu/board/notice")
public class NoticeController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    AdminService adminService;

    // 검색 기능이 추가된 공지사항 목록 조회
    @GetMapping("/list")
    public String BoardsList(
            // 검색 파라미터 추가
            @RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            @RequestParam(value = "showModal", required = false) Boolean showModal,
            Model model) {

        int admin_writer_id;
        String name;
        List<Boards> boards;

        // 검색어가 있는 경우 검색 결과를 가져옴
        if (searchType != null && searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            // 검색 서비스 호출
            boards = boardService.searchNotices(searchType, searchKeyword);

            // 검색 결과를 유지하기 위해 모델에 검색 파라미터 추가
            model.addAttribute("searchType", searchType);
            model.addAttribute("searchKeyword", searchKeyword);

            // 검색 결과가 없는 경우 모달창을 표시하기 위한 플래그 설정
            if (boards.isEmpty()) {
                model.addAttribute("showModal", true);
                model.addAttribute("modalMessage", "잘못된 검색입니다. 다시 입력해주세요.");
            }
        } else {
            // 검색어가 없는 경우 모든 공지사항을 가져옴
            boards = boardService.getAllNotices();

            // 명시적으로 모달창을 표시해야 하는 경우 (리다이렉트 후)
            if (showModal != null && showModal) {
                model.addAttribute("showModal", true);
                model.addAttribute("modalMessage", "잘못된 검색입니다. 다시 입력해주세요.");
            }
        }

        // 각 게시글에 작성자 이름 설정
        for(Boards board : boards){
            admin_writer_id = board.getAdmin_writer_id();
            name = adminService.getAdminName(admin_writer_id);
            board.setNickname(name);
        }

        model.addAttribute("notices", boards);
        return "menu/board/notice/list";
    }

    // 검색 유효성 체크를 위한 별도 메소드 추가
    @PostMapping("/search")
    public String searchNotices(
            @RequestParam("searchType") String searchType,
            @RequestParam("searchKeyword") String searchKeyword,
            RedirectAttributes redirectAttributes) {

        // 검색어가 비어있는 경우
        if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
            redirectAttributes.addAttribute("showModal", true);
            return "redirect:/menu/board/notice/list";
        }

        // 검색 파라미터를 리다이렉트에 추가
        redirectAttributes.addAttribute("searchType", searchType);
        redirectAttributes.addAttribute("searchKeyword", searchKeyword);
        return "redirect:/menu/board/notice/list";
    }

    @GetMapping("/detail/{id}")
    public String noticeDetail(@PathVariable("id") int id, Model model) {
        Boards getDetailNotice = boardService.getDetailNotice(id);
        model.addAttribute("notices", getDetailNotice);
        return "menu/board/notice/detail";
    }

    // 기존 코드 유지...
    @GetMapping("/add")
    public String noticeAdd(){
        return "menu/board/notice/add";
    }

    @PostMapping("/add")
    public String noticeAdd1(Boards boards, @AuthenticationPrincipal UserDetails userDetails){
        Admins admins = loginMapper.findById("admin");
        int admin_id = admins.getAdmin_id();

        Boards board = boards;
        board.setIs_notice(1);

        board.setAdmin_writer_id(1);
        board.setAdmin_writer_id(admin_id);

        //공지사항 추가
        boardService.insertNotice(board);

        return "redirect:./list";
    }

    @GetMapping("/update/{id}")
    public String noticeUpdate(@PathVariable("id") int id, Model model) {

        Boards getDetailNotice = boardService.getDetailNotice(id);
        model.addAttribute("notices", getDetailNotice);
        System.out.println(getDetailNotice);
        return "menu/board/notice/update";
    }

    //수정중
    @PostMapping("/update")
    public String noticeUpdate1(@ModelAttribute Boards board) {
        boardService.updateBoard(board);  // 게시글 업데이트
        return "redirect:/menu/board/notice/list";  // 목록 페이지로 리다이렉트
    }

    // 공지사항 삭제
    @PostMapping("/delete/{id}")
    public String deleteBoard(@PathVariable("id") int id) {
        boardService.deleteNotice(id);  // BoardService의 삭제 로직 호출
        return "redirect:/menu/board/notice/list";  // 삭제 후 목록 페이지로 리다이렉트
    }
}