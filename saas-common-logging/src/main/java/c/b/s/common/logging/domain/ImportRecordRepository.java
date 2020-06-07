package c.b.s.common.logging.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by guiqingqing on 2018/8/29.
 */
public interface ImportRecordRepository {

    ImportRecord findImportRecordById(Long id);

    int save(ImportRecord importRecord);

    int modify(ImportRecord importRecord);

    int pagingImportRecordCount(Long assetId, String taskId, Date beginTime, Date endTime, Long userId);

    List<ImportRecord> pagingImportRecord(int pageNum, int pageSize, Long assetId, String taskId, Date beginTime, Date endTime, Long userId);
}