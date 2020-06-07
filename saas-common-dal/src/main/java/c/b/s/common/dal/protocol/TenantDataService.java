package c.b.s.common.dal.protocol;

import c.p.b.api.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created 2018-08-28 14:11:55
 * 
 * @author Michael.Zhang
 */
@FeignClient(url = "${rest.url.saas-platform}", name = "saas-platform-tenant")
public interface TenantDataService {

    @RequestMapping(
            value = "/tenant/getTenantData",
            method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
    )
    Response<List<TenantData>> getDataSourceMetas();

}