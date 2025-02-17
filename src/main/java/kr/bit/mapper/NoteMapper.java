package kr.bit.mapper;

import kr.bit.entity.note;

import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface NoteMapper {
    @Select("select n.*, u.nickname " +
            "from notes n " +
            "join users u on n.sender_id = u.user_id " +
            "where n.receiver_id = #{receiverId} " +
            "order by n.created_at desc")
    List<note> getReceivedNotes(int receiverId);

    @Select("select n.*, u.nickname  " +
            "from notes n " +
            "join users u on n.receiver_id = u.user_id " +
            "where n.sender_id = #{senderId} " +
            "order by n.created_at desc")
    List<note> getSentNotes(int senderId);
}