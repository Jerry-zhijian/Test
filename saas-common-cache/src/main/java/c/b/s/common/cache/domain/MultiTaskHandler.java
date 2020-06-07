package c.b.s.common.cache.domain;

import c.b.s.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by guiqingqing on 2018/11/17.
 */
@Service
public class MultiTaskHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiTaskHandler.class);
    private static final String RUN_FLAG = "run_flag";
    private static final String SOURCE_LIST = "list";

    @Autowired
    private ResourceService resourceService;

    public void handle(String taskName, List<? extends Object> resources, MultiTaskCallBack callBack) throws InterruptedException {
        // 确保多台服务器只有一台服务器设置资源列表
        boolean setResult = resourceService.setResources(taskName + "_" + RUN_FLAG, taskName, taskName + "_" + SOURCE_LIST, resources);
        if (setResult) {
            LOGGER.info(String.format("%s - 获取资源列表: %s", taskName, JsonUtil.convertToJson(resources)));
        }
        while (true) {
            // 确保多台服务器获取到唯一的资源
            Object grabResource = resourceService.getResource(taskName, taskName + "_" + SOURCE_LIST);
            if (Objects.isNull(grabResource)) {
                break;
            }
            callBack.execute(grabResource);
        }
    }
}