package kr.bit.mapper;

import kr.bit.entity.Admins;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {

    @Select("select * " +
            "from admins " +
            "where id = #{id} and pass = #{pass}")
    Admins getLoginAdmin(Admins admins);

    @Select("select * from admins where id = #{id}")
    Admins findById(String id);
}