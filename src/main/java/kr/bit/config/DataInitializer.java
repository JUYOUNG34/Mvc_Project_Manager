//package kr.bit.config;
//
//import kr.bit.dao.AdminDao;
//import kr.bit.entity.Admins;
//import kr.bit.mapper.LoginMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import javax.annotation.PostConstruct;
//
//@Configuration
//public class DataInitializer {
//
//    @Autowired
//    private AdminDao adminDao;
//
//    @Autowired
//    private LoginMapper loginMapper;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostConstruct
//    public void init() {
//        try {
//            // 마스터 계정이 이미 존재하는지 확인
//            Admins existingAdmin = loginMapper.findById("admin");
//
//            if (existingAdmin == null) {
//                // 마스터 계정 생성
//                Admins masterAdmin = new Admins();
//                masterAdmin.setId("admin");
//                String encodedPassword = passwordEncoder.encode("admin123!");
//                masterAdmin.setPass(encodedPassword);
//                masterAdmin.setName("관리자");
//                masterAdmin.setEmployee_number("MASTER001");
//                masterAdmin.setAdmin_level("master");
//
//                // 마스터 계정 등록
//                boolean success = adminDao.registerAdmin(masterAdmin);
//
//                if (success) {
//                    System.out.println("마스터 계정이 성공적으로 생성되었습니다. 아이디: admin");
//                    System.out.println("비밀번호 (인코딩됨): " + encodedPassword);
//                } else {
//                    System.out.println("마스터 계정 생성 실패");
//                }
//            } else {
//                System.out.println("마스터 계정이 이미 존재합니다. 아이디: " + existingAdmin.getId());
//            }
//        } catch (Exception e) {
//            System.err.println("마스터 계정 초기화 중 오류 발생: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}