package kr.bit.entity;

import lombok.Data;

@Data
public class Criteria {

    private int current_page;   //현재 페이지 번호
    private int perPageNum;     //한 페이지에 보여줄 게시글

    //검색기능 변수
    private String type;
    private String keyword="";
    private String sort="id";  // 정렬 기준
    private String order="desc"; // 정렬 순서
    private int page=1;
    private int size=3;

    public Criteria(){
        this.current_page=1;
        this.perPageNum=2;
    }

    //현재 페이지 게시글 시작번호
    public int getPageStart(){
        return (current_page-1)*perPageNum;
    }

}
