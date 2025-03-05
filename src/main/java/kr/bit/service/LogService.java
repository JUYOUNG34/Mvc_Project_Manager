package kr.bit.service;

import kr.bit.dao.LogDao;
import kr.bit.dao.LoginDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@PropertySource({"classpath:application.properties"})
public class LogService {


    @Value("${log.path}")
    private String path;


//    private static final String LOG_DIRECTORY = "C:/logs/";
    private static final Logger log = LoggerFactory.getLogger(LogService.class);
    private static final Pattern LOG_FILE_PATTERN = Pattern.compile("admin-(\\d{4}-\\d{2}-\\d{2})\\.log");

    // 애플리케이션 초기화 시 로그 디렉토리 생성
//    @EventListener(ContextRefreshedEvent.class)
//    public void createLogDirectory(){
//        File logDirectory = new File(LOG_DIRECTORY);
//    }
    // 로그 메시지를 기록하는 메서드
    public void logAction(String logMessage) {
        ensureLogDirectoryExists();
        String date = LocalDate.now().toString();  // 오늘 날짜
        String logFilePath = path + "admin-" + date + ".log";
        Path path = Paths.get(logFilePath);
        try {
            Files.write(path, (logMessage + System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            log.info(logMessage);
        } catch (IOException e) {
            log.error("로그 파일에 쓰는 중 오류 발생: {}", e.getMessage());
        }
    }

    private void ensureLogDirectoryExists() {
        Path logDirectory = Paths.get(path);
        if (!Files.exists(logDirectory)) {
            try {
                Files.createDirectories(logDirectory);
            } catch (IOException e) {
                log.error("로그 디렉토리를 생성할 수 없습니다: {}", e.getMessage());
            }
        }
    }
    //로그 기록된 날짜 읽기
    public List<String> getAllLogDates() throws IOException {
        ensureLogDirectoryExists();
        List<String> dates = new ArrayList<>();
        Files.list(Paths.get(path)).forEach(path -> {
            Matcher matcher = LOG_FILE_PATTERN.matcher(path.getFileName().toString());
            if (matcher.matches()) {
                dates.add(matcher.group(1));
            }
        });
        return dates;
    }
    // 해당 날짜 로그 읽기
    public List<String> readLogsByDate(String date) throws IOException {
        String logFilePath = path + "admin-" + date + ".log";
        Path path = Paths.get(logFilePath);
        if (Files.exists(path)) {
            return Files.readAllLines(path);
        } else {
            return List.of("해당 날짜의 로그가 없습니다.");
        }
    }

}
