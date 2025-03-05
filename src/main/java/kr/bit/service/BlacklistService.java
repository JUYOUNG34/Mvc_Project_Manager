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


    public List<Blacklist> getBlacklist(Criteria criteria) {
        return blacklistDAO.getBlacklist(criteria);
    }

    public int getTotalCount(Criteria criteria) {
        return blacklistDAO.getTotalCount(criteria);
    }
    public boolean unblockUser(int id) {
        return blacklistDAO.removeFromBlacklist(id) > 0;
    }
    public int blockUser(int user_id, int admin_id){return blacklistDAO.blockUser(user_id,admin_id);}
    public Integer oneBlockUserID(int blocked_user_id){return blacklistDAO.oneBlockUserID(blocked_user_id);}
    public int blockCancel(int blocked_user_id){return blacklistDAO.blockCancel(blocked_user_id);}

}