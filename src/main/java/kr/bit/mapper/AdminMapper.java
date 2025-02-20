package kr.bit.mapper;

import kr.bit.entity.Admins;
import kr.bit.entity.Criteria;
import kr.bit.sql.ManagerSqlProvider;

import kr.bit.sql.UserSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    @SelectProvider(type = ManagerSqlProvider.class, method = "getAdmins")
    List<Admins> getAdmins(@Param("criteria")Criteria criteria);
    @SelectProvider(type = ManagerSqlProvider.class, method = "getTotalCount")
    int getTotalCount(@Param("criteria") Criteria criteria);

    @Select("select * from admins where id=#{id}")
    Admins oneAdmin(@Param("id") String id);

    @Select("select employee_number from admins order by  admin_id desc LIMIT 1")
    String getLastEmployeeNumber();
    @Insert("insert into admins(id,pass,name,employee_number,admin_level) values (#{id},#{pass},#{name},#{employee_number},#{admin_level} )")
    @Options(useGeneratedKeys = true, keyProperty = "admin_id")
    int insertAdmin(Admins admin);

    @Update("update admins set pass = #{pass}, admin_level = #{admin_level} where admin_id = #{admin_id}")
    int updateAdmin(Admins admin);
    @Delete("delete from admins where id = #{id}")
    int deleteAdmin(@Param("id")String id);


    @Insert("INSERT INTO admins (id, pass, name, employee_number, admin_level, created_at) " +
            "VALUES (#{id}, #{pass}, #{name}, #{employee_number}, #{admin_level}, NOW())")
    int registerAdmin(Admins admin);

    @Update("UPDATE admins SET " +
            "pass = #{pass}, " +
            "name = #{name}, " +
            "admin_level = #{admin_level} " +
            "WHERE admin_id = #{admin_id}")
    int updateAdmin(Admins admin);

    @Delete("DELETE FROM admins WHERE admin_id = #{adminId}")
    int deleteAdmin(int adminId);

    @Select("SELECT * FROM admins WHERE admin_id = #{adminId}")
    Admins getAdminById(int adminId);

    @Select("SELECT COUNT(*) FROM admins WHERE id = #{id}")
    int countById(String id);
}