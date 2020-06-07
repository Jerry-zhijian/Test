package c.b.s.common.logging.domain;

import java.util.List;

/**
 * Created by guiqingqing on 2018/8/11.
 */
public interface LoginLogRepository {
    int save(LoginLog loginLog);

    List<LoginLog> findByUserId(int pageNum, int pageSize, Long userId);

    int countByUserId(Long userId);

    List<LoginLog> findByUserName(int pageNum, int pageSize, String userName);

    int countByUserName(String userName);

    List<LoginLog> findByRealName(int pageNum, int pageSize, String realName);

    int countByRealName(String realName);
}