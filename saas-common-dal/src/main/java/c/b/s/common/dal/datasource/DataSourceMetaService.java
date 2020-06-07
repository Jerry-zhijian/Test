package c.b.s.common.dal.datasource;

import c.b.s.common.dal.protocol.TenantData;
import c.b.s.common.dal.protocol.TenantDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created 2018-08-28 17:39:04
 * 
 * @author Michael.Zhang
 */
@Slf4j
@Component
public class DataSourceMetaService {

    @Autowired
    private TenantDataService tenantDataService;

    public List<DataSourceMeta> getDataSourceMetas() {

        List<TenantData> resultList = tenantDataService.getDataSourceMetas().getResult();
        if (resultList == null || resultList.isEmpty()) {
            log.warn("Not found any tenant data from remote, please check!!");
            return Collections.emptyList();
        }

        return resultList.stream().map((data) -> data.toDataSourceMeta()).collect(Collectors.toList());
    }

}
