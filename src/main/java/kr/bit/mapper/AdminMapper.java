package kr.bit.mapper;

import kr.bit.entity.Admins;
import kr.bit.entity.Criteria;
import kr.bit.sql.ManagerSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    @SelectProvider(type = ManagerSqlProvider.class, method = "getAdmins")
    List<Admins> getAdmins(@Param("criteria")Criteria criteria);

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