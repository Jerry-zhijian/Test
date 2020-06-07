package c.b.s.common.logging.domain;

import java.util.List;

/**
 * Created by guiqingqing on 2018/8/11.
 */
public interface OperationLogRepository {
    void save(OperationLog operationLog);

    List<OperationLog> findByUserId(int pageNum, int pageSize, Long applicationId, Long userId);

    int countByUserId(Long applicationId, Long userId);

    List<OperationLog> findByUserName(int pageNum, int pageSize, Long applicationId, String userName);

    int countByUserName(Long applicationId, String userName);

    List<OperationLog> findByRealName(int pageNum, int pageSize, Long applicationId, String realName);

    int countByRealName(Long applicationId, String realName);
}