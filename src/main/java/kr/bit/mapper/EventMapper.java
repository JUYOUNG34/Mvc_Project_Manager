package kr.bit.mapper;

import kr.bit.entity.Admins;
import kr.bit.entity.Events;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface EventMapper {
    @Select("select * from events")
    List<Events> getAllEvents();


    @Insert("insert into events (name, image_url, start_date, end_date) " +
            "values (#{name}, #{image_url}, #{start_date}, #{end_date})")
    int insertEvent(Events event);

    @Select(" select * from events where id =#{id}")
    Events getEventById(int id);

    @Update("UPDATE events SET name = #{name}, " +
            "image_url = CASE WHEN #{image_url} IS NOT NULL THEN #{image_url} ELSE image_url END, " +
            "start_date = #{start_date}, " +
            "end_date = #{end_date} " +
            "WHERE id = #{id}")
    int updateEvent(Events event);


    @Delete("delete from events where id=#{id}")
    int deleteEvent(int id);

}