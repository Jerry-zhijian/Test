package c.b.s.common.util.constant;

/**
 * Created by guiqingqing on 2018/8/17.
 */
public final class BoxinConstant {
    public static final String QUERY_LIKE_CHARACTER = "%";
    public static final byte IMPORT_RECORD_STATUS_HANDLED = 1;

    public static final String UPLOAD_FILE_MESSAGE_TITLE_TEMPLATE = "【%s】你发起的%s结果";
    public static final String UPLOAD_FILE_MESSAGE_SUCCESS_TEMPLATE = "你在%s发起的%s操作已执行完毕，点击下方Excel文件查看导入结果。<br/>结果文件：<a href='%%s'>点击下载</a>";
    public static final String UPLOAD_FILE_MESSAGE_FAIL_TEMPLATE = "你在%s发起的%s操作执行失败，请联系管理员。";
    public static final String UPLOAD_FILE_NEW_MESSAGE_SUCCESS_TEMPLATE = "你在%s发起的%s操作已执行完毕，该次导入共%s条数据，其中%s条数据导入成功，%s条数据导入失败，点击<a href='%%s'>结果文件</a>查看导入结果详情";

    public static final String DOWNLOAD_FILE_MESSAGE_TITLE_TEMPLATE = "【%s】你发起的%s结果";
    public static final String DOWNLOAD_FILE_MESSAGE_SUCCESS_TEMPLATE = "你在%s发起的%s操作已执行完毕，点击下方Excel文件查看结果。<br/>结果文件：<a href='%%s'>点击下载</a>";
    public static final String DOWNLOAD_FILE_MESSAGE_FAIL_TEMPLATE = "你在%s发起的%s操作执行失败，请联系管理员。";

    public static final String DOWNLOAD_ZIP_FILE_MESSAGE_SUCCESS_TEMPLATE = "你在%s发起的%s操作已执行完毕，点击下方Zip文件查看结果。<br/>结果文件：<a href='%%s'>点击下载</a>";
    public static final String DOWNLOAD_ZIP_FILE_MESSAGE_SUCCESS_TEMPLATE_SOUND_RECORD = "你在%s发起的%s操作已执行完毕，点击下方Zip文件查看结果。<br/>";

    public static final String DOWNLOAD_ZIP_FILE_TEMPLATE_SOUND_RECORD = "结果文件：<a href='%s'>%s</a><br/>";

    public static final String BATCH_ALLOT_MESSAGE_TITLE_TEMPLATE = "【%s】你发起的%s结果";
    public static final String BATCH_ALLOT_MESSAGE_SUCCESS_TEMPLATE = "你在%s发起的%s操作已执行完毕。<br/>处理结果：\n%s";
    public static final String BATCH_ALLOT_MESSAGE_FAIL_TEMPLATE = "你在%s发起的%s操作执行失败，请联系管理员。<br/>处理结果：\n%s";

    public static final String CASE_CLEAR_MESSAGE_TITLE_TEMPLATE = "【%s】你发起的%s结果";
    public static final String CASE_CLEAR_MESSAGE_SUCCESS_TEMPLATE = "你在%s发起的%s操作已执行完毕。<br/>处理结果：\n%s";
    public static final String CASE_CLEAR_MESSAGE_FAIL_TEMPLATE = "你在%s发起的%s操作执行失败，请联系管理员。<br/>处理结果：\n%s";

    public static final String CHANGE_OWNER_MESSAGE_TITLE_TEMPLATE = "【%s】你发起的%s结果";
    public static final String CHANGE_OWNER_MESSAGE_SUCCESS_TEMPLATE = "你在%s发起的%s操作已执行完毕。<br/>处理结果：\n%s";
    public static final String CHANGE_OWNER_MESSAGE_FAIL_TEMPLATE = "你在%s发起的%s操作执行失败，请联系管理员。<br/>处理结果：\n%s";

    public static final String EXPORT_MESSAGE_TITLE_TEMPLATE = "【%s】你发起的%s结果";
    public static final String EXPORT_MESSAGE_SUCCESS_TEMPLATE = "你在%s发起的%s操作已执行完毕，点击下方Excel文件查看结果。<br/>结果文件：<a href='%%s'>点击下载</a>";

    public static final String REPAY_APPLY_TITLE_TEMPLATE = "【%s】%s的还款申请审批单";
    public static final String REPAY_APPLY_MESSAGE_TEMPLATE = "%s在%s发起一笔还款申请，<a href='/flow/repay?id=%s'>点击此处</a>查看详情。";

    public static final String ASSIST_APPLY_TITLE_TEMPLATE = "【%s】%s的协催申请审批单";
    public static final String ASSIST_APPLY_MESSAGE_TEMPLATE = "%s在%s发起协催申请，<a href='/flow/assist?id=%s'>点击此处</a>查看详情。";

    public static final String UPLOAD_WITH_HOLDING_MESSAGE_TITLE_TEMPLATE = "【%s】你发起的批量代扣结果";
    public static final String UPLOAD_WITH_HOLDING_MESSAGE_SUCCESS_TEMPLATE = "你在%s发起的批量代扣操作已执行完毕，点击下方Excel文件查看结果。<br/>结果文件：<a href='%%s'>点击下载</a>";
    public static final String UPLOAD_WITH_HOLDING_MESSAGE_FAIL_TEMPLATE = "你在%s发起的批量代扣操作执行失败，请联系管理员。";
    public static final String UPLOAD_WITH_HOLDING_NEW_MESSAGE_SUCCESS_TEMPLATE = "你在%s发起的批量代扣操作已执行完毕，该次导入共%s条数据，其中%s条数据导入成功，%s条数据导入失败，点击<a href='%%s'>结果文件</a>查看导入结果详情";

    public static final String REDAIL_NUMBER_MESSAGE_TITLE_TEMPLATE = "【重新呼叫】你发起的重新呼叫结果";
    public static final String REDAIL_NUMBER_MESSAGE_SUCCESS_TEMPLATE = "你在%s发起的重新呼叫操作已执行完毕，任务名称：%s。";
    public static final String REDAIL_NUMBER_MESSAGE_FAIL_TEMPLATE = "你在%s发起的重新呼叫操作执行失败，请联系管理员。";

    public static final String PROMPT_MESSAGE_TITLE_TEMPLATE = "【%s】你发起的%s结果";
    public static final String PROMPT_MESSAGE_SUCCESS_TEMPLATE = "你在%s发起的%s操作已执行完毕。";
    public static final String PROMPT_MESSAGE_FAIL_TEMPLATE = "你在%s发起的%s操作执行失败，请联系管理员。";

    public static final String MOBILE_PUBLICKEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCk4PaqnHnX01Y2OV2pwRMOljH6z+mobrQHdIMcn7gV4vCo+5wrIWHhFlcFWQMMpdmz7LizCaT3NccjgvOskA3G2qXrS7qxV8BncTDNhT8RQNiJF/S9oxsbOlCCyHLyaSdCUymRrPG50gZZY9X72OmuNciHeC9uEUdNyxHXkOO9XQIDAQAB";
    public static final String MOBILE_PRIVATEKRY="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKTg9qqcedfTVjY5XanBEw6WMfrP6ahutAd0gxyfuBXi8Kj7nCshYeEWVwVZAwyl2bPsuLMJpPc1xyOC86yQDcbapetLurFXwGdxMM2FPxFA2IkX9L2jGxs6UILIcvJpJ0JTKZGs8bnSBllj1fvY6a41yId4L24RR03LEdeQ471dAgMBAAECgYEAg51c6kZscN+v8ysJ3IdWSg+VKAyuDZkJNuZH/yJ0VWxKBeqL+FX2hzJJFui+KWt/BESUD79EI16Xb36KO5bmlyyTW56r1xRt5Q7b+q/0z5nWbrIpmydqusLZ0Xxve0CjhRImbVlmCW2FMkjpHHSjbPq7hLky/LiKxh1FJMRRywECQQDb1n+r9GDn1TnrYHWSUKSiCtY2C3Nfdxe2X4hyVP5sRah1EcoWAUAMDQ5sj3Qqfv3ElXByA5CJ/FJwKxBYntqdAkEAwAAaroLtjeLcit+bv29v3Is2xbQ9I9G6MblRfLnyju/LHd8Yt/zbXsIo0uuVcB+5Q6do1rxebID6ScDtZCaRwQJADhoYXoNyo+x9pqetYxFhPjPLwfPCFuGg18YRDqR9HZzqTXgyqm6yGRPNVRFutjsnNDER4X5xCyjwcNmSVtz50QJBAK4D6irck32TbZuYSsrYryPOMRhC4V8DiKPkeEx5I7QU9ZzfRMIMtK5PfzybNFfpCLqeklB9bX8d7rrF1uJ8aoECQE5av7k4/ulIvhrilx272a2sbH20UoQzNtBvSL2U3VFjVGwkPy4uRzIgRNnIFrarHiP3XXRxexy8ieBfm6InKow=";

    public static final String INTELLIGENT_CALL_MOBILE_PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC78wObaSH9Q1441NpC450uBQewuFkk8UkYnGHTFyTRxK/2BcxTyZ3ZPtvRRXm11/zwFBK3Ru/ut7zyUtvXwDGFpITSIh66sO4aTjWd/GILivponC2XBJQaiL7m6RD31t4Q/q7RkX7e1bny7qc4v2qdVT7YShkowCecH5Z864H6qwIDAQAB";
    public static final String INTELLIGENT_CALL_MOBILE_PRIVATEKRY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALvzA5tpIf1DXjjU2kLjnS4FB7C4WSTxSRicYdMXJNHEr/YFzFPJndk+29FFebXX/PAUErdG7+63vPJS29fAMYWkhNIiHrqw7hpONZ38YguK+micLZcElBqIvubpEPfW3hD+rtGRft7VufLupzi/ap1VPthKGSjAJ5wflnzrgfqrAgMBAAECgYEAuY90QDvMEwAwRmvgEaonxQeyUAex+4c9Y88BxidTcsd98qhG03J7JcGpxTOStrZIQ7+NuuWOfWFqatTjuF9Ig7iHTo5PvYNwlD02LzcKcnWoNoHBg3LHu/H3djDV0A/HindIkEkvymbV5+Ycm70Qs7bbpLJzRaUQrc+iyt48ugECQQDcSPlFrNvj8oNUamcvWcHFfjpQxYstOXWlaqDpZN8xEirOIIc4TlfTIvubGbu+8cZJdXF4uci3cigj3uzNPl3lAkEA2mvuC60TZLqsUV1ld3iNIEiagR6X6k0J8hDCa79WSIUmASEf/cddpYg4q+ghvjUJ6Yq/o/PKSbJW/SuvAZ7tTwJAHb5Yshi6BBhNtUs8rX8QE+uMUjWqKiwU/4XbPkhZfpW2Y+iueWLSpvaVRIb/+7NHk8mgza1ANczbMKS8EqZ2JQJAQ+EM3oYQI9q7mKGs2d8WBLVrhrsKPR9p+tdohHG3D9yJPgqcVZLysHHQIuSGMAVtqi2bdwlZkWOiExXTUm6gkwJAMyMWEqaVogbH6498bSrwxeBMbqG+a9iuoI5I2pK9AAnZUnRp1NWWY/F8rVznqHQGHc7dgNog6UDlT8TQkbF2MQ==";

    // 案件类型
    public static final int NORMAL_CASE = 1; // 正常案件
    public static final int ASSIST_CASE = 2; // 协催案件

    // 分配范围
    public static final int UN_ALLOT_CASE = 1; // 未分配案件
    public static final int ALL_CASE = 2; // 所有案件
    public static final int ALLOT_CASE = 3; // 已分配案件

    public static final int NOT_AUTHORIZED_TO_VIEW_MESSAGES_CODE = 960001;
    public static final String NOT_AUTHORIZED_TO_VIEW_MESSAGES_MESSAGE = "无权查看他人消息";

    public static final int ALREADY_EXIST_TASK_IN_PROGRESS_CODE = 970001;
    public static final String ALREADY_EXIST_TASK_IN_PROGRESS_MESSAGE = "已经存在处理中的任务";

    public static final int UN_SUPPORT_FILE_EXTENSION_CODE = 980001;
    public static final String UN_SUPPORT_FILE_EXTENSION_MESSAGE = "不支持的文件格式";

    public static final int NO_APPLICATION_ACCESS_PERMISSION_CODE = 990001;
    public static final String NO_APPLICATION_ACCESS_PERMISSION_MESSAGE = "没有应用权限";

    public static final long CASE_DEFAULT_STATUS_ID = 0L;
    public static final String CASE_DEFAULT_STATUS_TEXT = "未跟进";

    public static final String NUMBER_NON_EXIST_PREFIX = "number_non_exist_"; // 前缀 + 商户ID + 号码
    public static final String NUMBER_OUT_OF_SERVICE_PREFIX = "number_out_of_service_"; // 前缀 + 商户ID + 号码
    public static final String NUMBER_ABANDONED_PREFIX = "number_abandoned_"; // 前缀 + 商户ID + 号码
    public static final String CALL_TIMES_PREFIX = "call_times_"; // 前缀 + 商户ID + 号码
    public static final String CALL_STATUS_PREFIX = "call_status_"; // 前缀 + 批次号
}