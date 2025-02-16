package kr.bit.mapper;

import kr.bit.entity.Admins;
import kr.bit.entity.Criteria;
import kr.bit.sql.ManagerSqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface AdminMapper {
    @SelectProvider(type = ManagerSqlProvider.class, method = "getAdmins")
    List<Admins> getAdmins(@Param("criteria")Criteria criteria);

}
