/**
 *
 */
package com.nuocf.yshuobang.http;

/**
 * @author xiong
 * @ClassName: APIConstants
 * @Description: todo(各个API接口)
 * @date 2016/8/27
 */
public class APIConstants {
    private static final String HOST = "http://huobanys.com/userApi/";
    private static final String COMMON_URL_HOST = "http://huobanys.com/";

    public static final String APIVERSION = "1.0";
    //登录接口
    public static final String LOGIN = HOST + "verify_login.html";
    //注册接口
    public static final String REGISTER = HOST + "verify_register.html";
    //获取验证码接口
    public static final String VERIFY_CODE = HOST + "verify_getCode.html";
    //广告轮播接口
    public static final String GET_ADVERTISEMENT = HOST + "recommendation_getadvertisment.html";
    //名医推荐接口
    public static final String GET_FAMOUS_DOCTOR = HOST + "recommendation_getfamousdoctor.html";
    //健康资讯接口
    public static final String GET_HEALTH_NEWS = HOST + "recommendation_gethealthnews.html";
    //推荐医院接口
    public static final String GET_HOSPITAL = HOST + "recommendation_gethothospital.html";
    //医院详情接口
    public static final String HOSPTAL_DETAILS = HOST + "recommendation_gethospital.html";
    //医生列表接口
    public static final String HOSPITAL_DOCTORS_LIST = HOST + "recommendation_getdoctorslist.html";
    //医生详情接口
    public static final String GET_DOCTOR_DETAILS = HOST + "recommendation_getdoctor.html";
    //手术预约、精准预约及海外医疗接口
    public static final String ORDER_OPERATION = HOST + "process_orderoperation.html";
    //快速问诊接口
    public static final String INTERROGATION_SPEED = HOST + "process_inquiry.html";
    //我的问诊接口
    public static final String MINE_INTERROGATION = HOST + "process_getinquirylist.html";
    //问诊详情接口
    public static final String INTERROGATION_DETAILS = HOST + "process_getinquiry.html";
    //取消问诊接口
    public static final String INTERROGATION_CANCEL = HOST + "process_cancelinquiry.html";
    //我的预约接口
    public static final String MINE_RESERVE = HOST + "process_getorderlist.html";
    //预约详情接口
    public static final String RESERVE_DETAIL = HOST + "process_getorder.html";
    //预约确认/取消/评价接口
    public static final String RESERVE_ACT = HOST + "process_handleorder.html";
    //检查更新接口
    public static final String CHECK_UPDATE = HOST + "mysetting_inquiry.html";
    //上传文件接口
    public static final String UPLOAD_FILE = HOST + "myfile_uploadfile.html";
    //疾病列表接口
    public static final String GET_DISEASE = HOST + "recommendation_getdeseaselist.html";
    //常见疾病接口
    public static final String COMMON_DISEASE = HOST + "recommendation_getcommondesease.html";
    //搜索接口
    public static final String SERACH_ALL = HOST + "recommendation_searchdoctorhospital.html";
    //获取科目接口
    public static final String GET_SECTIOM = HOST + "recommendation_getsectionlist.html";
    //重置密码接口
    public static final String RESET_PASSWORD = HOST + "verify_resetPassword.html";
    //编辑用户资料接口
    public static final String EDIT_USER = HOST + "mysetting_edituser.html";

    //关于我们
    public static final String ABOUT_US = COMMON_URL_HOST + "fnews_info.html?id=18";
    //常见问题
    public static final String COMMON_PROBLEM = COMMON_URL_HOST + "ffaq_index.html";
    //海外医疗
    public static final String OVERSEA = COMMON_URL_HOST + "fabroadmedical_index.html";
    //绿色通道
    public static final String GREEN_CHANNEL = COMMON_URL_HOST + "fabroadmedical_green.html";
}
