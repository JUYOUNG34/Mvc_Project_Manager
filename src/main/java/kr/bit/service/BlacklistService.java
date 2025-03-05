package kr.bit.service;

import kr.bit.dao.BlacklistDAO;
import kr.bit.entity.Blacklist;
import kr.bit.entity.Criteria;
import kr.bit.entity.Events;
import kr.bit.mapper.BlacklistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlacklistService {

    @Autowired
    private BlacklistDAO blacklistDAO;
    @Autowired
    private BlacklistMapper blacklistMapper;

    public List<Blacklist> getAllBlack() {
        return blacklistMapper.getAllBlack();
    }

    public List<Blacklist> getBlacklist(Criteria criteria) {
        return blacklistDAO.getBlacklist(criteria);
    }

    public int getTotalCount(Criteria criteria) {
        return blacklistDAO.getTotalCount(criteria);
    }
    public boolean unblockUser(int id) {
        return blacklistDAO.removeFromBlacklist(id) > 0;
    }
}