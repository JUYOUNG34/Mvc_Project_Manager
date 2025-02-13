package kr.bit.entity;

import lombok.Data;

@Data
public class PageCre {
    private Criteria criteria;
    private int totalCount; //총 행수
    private int startPage;  //시작페이지번호
    private int endPage;    //끝 페이지 번호
    private boolean prev;   //이전 버튼
    private boolean next;   //다음 버튼
    private int showPageNum=3;  //하단에 보여질 페이지

    public void setTotalCount(int totalCount){
        this.totalCount=totalCount;
        createPage();
    }
    public void createPage(){

        //1. 화면에 보여질 마지막 페이지번호
        endPage=(int)(Math.ceil(criteria.getCurrent_page()/(double)showPageNum)*showPageNum);
        // Math.ceil() 주어진 숫자를 올림하여 가장 가까운 정수를 반환

        //2.시작 페이지 번호
        startPage=(endPage-showPageNum)+1;

        if(startPage<=0){
            startPage=1;
        }
        //3. 전체 마지막 페이지 계산
        int tmpPage = (int)(Math.ceil((totalCount)/(double)criteria.getPerPageNum()));

        //4. 화면에 보여질 마지막 페이지 체크
        if(tmpPage<endPage){
            endPage=tmpPage;
        }

        //5. 이전페이지 버튼이 존재해야하는지
        prev=(startPage==1)?false:true;   //인덱스가 1~3일경우 => startPage=1

        //6. 다음페이지 버튼이 존재해야하는지
        next=(endPage<tmpPage)?true:false; //내가 현재 보고있는 페이지의 마지막 페이지

    }

}
