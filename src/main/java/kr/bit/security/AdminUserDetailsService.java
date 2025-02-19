package kr.bit.security;

import kr.bit.entity.Admins;
import kr.bit.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 실제로 데이터베이스에서 관리자 조회
        // 여기서 username은 아이디임.
        Admins foundAdmin = loginMapper.findById(username);

        if (foundAdmin == null) {
            throw new UsernameNotFoundException("User not found with id: " + username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        // 실제 관리자의 권한 수준에 따라 권한 설정
//        switch (foundAdmin.getAdmin_level()) {
//            case "master":
//                authorities.add(new SimpleGrantedAuthority("ROLE_master"));
//                break;
//            case "service_manager":
//                authorities.add(new SimpleGrantedAuthority("ROLE_service_manager"));
//                break;
//            default:
//                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//                break;
//        }

        // 실제 관리자의 ID와 비밀번호를 사용하여 UserDetails 객체 생성
        return new User(
                foundAdmin.getId(),
                foundAdmin.getPass(), // 이미 암호화된 비밀번호여야 함
                authorities
        );
    }
}