package kr.bit.mapper;

import kr.bit.entity.Admins;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {

    @Select("SELECT * FROM admins WHERE id = #{id}")
    Admins findById(String id);

    @Select("SELECT * FROM admins WHERE id = #{id} AND pass = #{pass}")
    Admins getLoginAdmin(Admins admin);
}