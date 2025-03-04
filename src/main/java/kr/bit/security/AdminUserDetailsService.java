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
        // 데이터베이스 조회
        Admins foundAdmin = loginMapper.findById(username);

        if (foundAdmin == null) {
            throw new UsernameNotFoundException("User not found with id: " + username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        // 권한 설정
        switch (foundAdmin.getAdmin_level()) {
            case "master":
                authorities.add(new SimpleGrantedAuthority("ROLE_master"));
                break;
            case "service_manager":
                authorities.add(new SimpleGrantedAuthority("ROLE_service_manager"));
                break;
            default:
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                break;
        }

        // 로그 출력 (디버깅용)
        System.out.println("로그인 시도 - 아이디: " + foundAdmin.getId());
        System.out.println("권한: " + authorities);
        System.out.println("DB 비밀번호: " + foundAdmin.getPass());

        return new User(
                foundAdmin.getId(),
                foundAdmin.getPass(),
                authorities
        );

    }
}