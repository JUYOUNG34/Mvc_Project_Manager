package kr.bit.dao;

import kr.bit.entity.Blacklist;
import kr.bit.entity.Criteria;
import kr.bit.mapper.BlacklistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BlacklistDAO {

    @Autowired
    private BlacklistMapper blacklistMapper;

    public List<Blacklist> getBlacklist(Criteria criteria) {
        return blacklistMapper.getBlacklist(criteria);
    }

    public int getTotalCount(Criteria criteria) {
        return blacklistMapper.getTotalCount(criteria);
    }

    public int removeFromBlacklist(int id) {
        return blacklistMapper.removeFromBlacklist(id);
    }

    public int blockUser(int user_id, int admin_id){return blacklistMapper.blockUser(user_id,admin_id);}
    public Integer oneBlockUserID(int blockUserId){return blacklistMapper.oneBlockUserID(blockUserId);}
    public int blockCancel(int blocked_user_id){return blacklistMapper.blockCancel(blocked_user_id);}
}