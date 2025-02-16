package kr.bit.mapper;

import kr.bit.entity.Boards;
import kr.bit.entity.Events;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BoardMapper {

    @Update("update boards set is_blind = true where id = #{id}")
    int blockBoard(int id);
}
