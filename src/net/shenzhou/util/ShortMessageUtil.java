package net.shenzhou.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * Created on 17/6/7.
 * 短信API产品的DEMO程序,工程中包含了一个SmsDemo类，直接通过
 * 执行main函数即可体验短信产品API功能(只需要将AK替换成开通了云通信-短信产品功能的AK即可)
 * 工程依赖了2个jar包(存放在工程的libs目录下)
 * 1:aliyun-java-sdk-core.jar
 * 2:aliyun-java-sdk-dysmsapi.jar
 *
 * 备注:Demo工程编码采用UTF-8
 */
public class ShortMessageUtil {

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAICqVbGFDDvfPr";
    static final String accessKeySecret = "SEu6EmVdCq3BwgWcQ3Tqo4TBqs18aW";

    public static SendSmsResponse sendSms(String mobile,int code,String messageType) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(mobile);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("好康护");
//        if(!StringUtils.isEmpty(messageType)){
//        	 /** 注册用户 */
//            if(MessageType.register.equals(MessageType.valueOf(messageType))){
//            	 //必填:短信模板-可在短信控制台中找到
//                request.setTemplateCode("SMS_116563019");
//                //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//                request.setTemplateParam("{\"code\":\""+code+"\"}");
//            }
//            /** 修改密码 */
//            else if(MessageType.changePassword.equals(MessageType.valueOf(messageType))){
//            	 //必填:短信模板-可在短信控制台中找到
//                request.setTemplateCode("SMS_116593025");
//                //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//                request.setTemplateParam("{\"code\":\""+code+"\"}");
//            }
//            /** 修改支付密码 */
//            else if(MessageType.changePayPassword.equals(MessageType.valueOf(messageType))){
//            	 //必填:短信模板-可在短信控制台中找到
//                request.setTemplateCode("SMS_116593009");
//                //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//                request.setTemplateParam("{\"code\":\""+code+"\"}");
//            }
//            /** 修改手机号 */
//            else if(MessageType.changePhone.equals(MessageType.valueOf(messageType))){
//            	 //必填:短信模板-可在短信控制台中找到
//                request.setTemplateCode("SMS_116593033");
//                //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//                request.setTemplateParam("{\"code\":\""+code+"\"}");
//            }
//        }else{
//        	 //必填:短信模板-可在短信控制台中找到
//            request.setTemplateCode("SMS_73830052");
//            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//            request.setTemplateParam("{\"code\":\""+code+"\", \"product\":\"神州儿女\"}");
//        }
        
	      //必填:短信模板-可在短信控制台中找到
	      request.setTemplateCode("SMS_126464319");
	      //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
	      request.setTemplateParam("{\"code\":\""+code+"\"}");

	      //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse  = null;
        try {
        	 sendSmsResponse = acsClient.getAcsResponse(request);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
        
        System.out.println("验证码为:"+code);
        System.out.println(ShortMessageUtil.getCode(sendSmsResponse.getCode()));
        return sendSmsResponse;
    }


    public static QuerySendDetailsResponse querySendDetails(String bizId,String phone) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber(phone);
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }

    public static SendSmsResponse seng_message (String mobile,int code,String messageType) throws ClientException, InterruptedException {

        //发短信
        SendSmsResponse response = sendSms(mobile,code,messageType);
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());

        Thread.sleep(3000L);

        //查明细
        if(response.getCode() != null && response.getCode().equals("OK")) {
            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId(),mobile);
            System.out.println("短信明细查询接口返回数据----------------");
            System.out.println("Code=" + querySendDetailsResponse.getCode());
            System.out.println("Message=" + querySendDetailsResponse.getMessage());
            int i = 0;
            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
            {
                System.out.println("SmsSendDetailDTO["+i+"]:");
                System.out.println("Content=" + smsSendDetailDTO.getContent());
                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
            }
            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
        }

        return response;
    }
    
    
    /**
     * 错误原因
     * @param code
     * @return
     */


	public static String getCode(String code){
		String message = "";
		String programme = "";
		if(code.equals("OK")){
			 message = "发送成功;";
			 programme = "无需排查原因;";	
		};
		if(code.equals("isp.RAM_PERMISSION_DENY")){
			message = "RAM权限DENY";
			programme = "当提示RAM权限不足时，就需要给当前使用的AK对应子账号进行授权：AliyunDysmsFullAccess（权限名称）。具体权限授权详见：https://help.aliyun.com/document_detail/55764.html?spm=5176.product44282.6.548.bKZJL2";
			};
		if(code.equals("isv.OUT_OF_SERVICE")){
			message = "业务停机";
			programme = "请先查看账户余额，若余额大于零，则请通过创建工单联系工程师处理";
			};
		if(code.equals("isv.PRODUCT_UN_SUBSCRIPT")){
			message = "未开通云通信产品的阿里云客户";
			programme = "未开通云通信产品的阿里云客户（该AK所属的账号尚未开通云通信的服务，包括短信、语音、流量等服务）注：阿里云短信服务包含：1、消息服务 2、云通信短信服务 3、云市场短信接口，账号和短信接口不可混用。当出现此类提示报错需要检查当前AK是否已经开通阿里云云通信短信服务，如已开通消息服务，则参照消息服务文档调用接口。";
			};
		if(code.equals("isv.PRODUCT_UNSUBSCRIBE")){
			message = "产品未开通";
			programme = "产品未订购（该AK所属的账号尚未开通当前接口的产品，如仅开通了短信服务的用户调用语音接口。），检查AK对应账号是否已开通调用接口对应的服务。短信服务开通链接：https://www.aliyun.com/product/sms";
			};
		if(code.equals("isv.ACCOUNT_NOT_EXISTS")){
			message = "账户不存在";
			programme = "请确认使用的账号是否与申请的账号一致";
			};
		if(code.equals("isv.ACCOUNT_ABNORMAL")){
			message = "账户异常";
			programme = "请确认使用的账号是否与申请的账号一致";
			};
		if(code.equals("isv.SMS_TEMPLATE_ILLEGAL")){
			message = "短信模板不合法";
			programme = "TemplateCode参数请传入审核通过的模板ID，模板见：见：https://dysms.console.aliyun.com/dysms.htm#/template";
			};
		if(code.equals("isv.SMS_SIGNATURE_ILLEGAL")){
			message = "短信签名不合法";
			programme = "SignName请传入审核通过的签名内容，签名见：https://dysms.console.aliyun.com/dysms.htm#/sign";
			};
		if(code.equals("isv.INVALID_PARAMETERS")){
			message = "参数异常";
			programme = "对照文档，检查参数格式。例：短信查询接口SendDate日期格式yyyyMMdd，错误：2017-01-01正确：20170101";
			};
		if(code.equals("isp.SYSTEM_ERROR")){
			message = "isp.SYSTEM_ERROR";
			programme = "请重试接口调用，如仍存在此情况请创建工单反馈工程师查看";
			};
		if(code.equals("isv.MOBILE_NUMBER_ILLEGAL")){
			message = "非法手机号";
			programme = "PhoneNumbers参数请传入11位国内号段的手机号码";
			};
		if(code.equals("isv.MOBILE_COUNT_OVER_LIMIT")){
			message = "手机号码数量超过限制";
			programme = "短信接收号码,支持以英文逗号分隔的形式进行批量调用，批量上限为1000个手机号码，PhoneNumbers参数单次调用不传入过多接收号码";
			};
		if(code.equals("isv.TEMPLATE_MISSING_PARAMETERS")){
			message = "模板缺少变量";
			programme = "TemplateParam中需要以json格式字符串给使用的模板中出现的所有变量进行赋值。例：模板为：您好${name}，验证码${code} TemplateParam={“name”:”Tom”,”code”:”123”}";
			};
		if(code.equals("isv.BUSINESS_LIMIT_CONTROL")){
			message = "业务限流";
			programme = "将短信发送频率限制在正常的业务流控范围内，默认流控：短信验证码 ：使用同一个签名，对同一个手机号码发送短信验证码，支持1条/分钟，5条/小时 ，累计10条/天。";
			};
		if(code.equals("isv.INVALID_JSON_PARAM")){
			message = "JSON参数不合法，只接受字符串值";
			programme = "TemplateParam入参以Json格式字符串形式传入。例：正确{“code”:”123”}错误{“code”:123}";
			};
		if(code.equals("isv.BLACK_KEY_CONTROL_LIMIT")){
			message = "黑名单管控";
			programme = "黑名单管控是指变量内容含有限制发送的内容，变量不支持透传url，同时检查通过变量是否透传了一些敏感信息触发关键字";
			};
		if(code.equals("isv.PARAM_LENGTH_LIMIT")){
			message = "参数超出长度限制";
			programme = "仅对个人用户的限制，单个变量长度限制在15字符内。企业用户无限制";
			};
		if(code.equals("isv.PARAM_NOT_SUPPORT_URL")){
			message = "不支持URL";
			programme = "变量不支持透传url，同时检查通过变量是否透传了一些敏感信息触发关键字";
			};
		if(code.equals("isv.AMOUNT_NOT_ENOUGH")){
			message = "账户余额不足";
			programme = "转入金额不足以发送当前信息，确保余额足够发送当前短信";
			};
		if(code.equals("isv.TEMPLATE_PARAMS_ILLEGAL")){
			message = "模板变量里包含非法关键字";
			programme = "变量不支持透传url，同时检查通过变量是否透传了一些敏感信息触发关键字";
			};
		if(code.equals("SignatureDoesNotMatch")){
			message = "Specified signature is not matched with our calculation.";
			programme = "Signature加密错误，如为SDK调用，则需要注意accessKeyId和accessKeySecret字符串赋值正确无误；如自行加密的Signature，则需要检查加密逻辑，对照文档：https://help.aliyun.com/document_detail/56189.html";
			};
		if(code.equals("InvalidTimeStamp.Expired")){
			message = "Specified time stamp or date value is expired.";
			programme = "时间戳错误，发出请求的时间和服务器接收到请求的时间不在15分钟内。经常出现该错误的原因是时区原因造成的,目前网关使用的时间是GMT时间";
			};
		if(code.equals("SignatureNonceUsed")){
			message = "Specified signature nonce was used already.";
			programme = "唯一随机数重复，SignatureNonce为唯一随机数，用于防止网络重放攻击。不同请求间要使用不同的随机数值。";
			};
		if(code.equals("InvalidVersion")){
			message = "Specified parameter Version is not valid.";
			programme = "版本号错误，需要确认接口的版本号，如云通信短信、语音、流量服务的Version=2017-05-25";
			};
		if(code.equals("InvalidAction.NotFound")){
			message = "Specified api is not found, please check your url and method";
			programme = "接口名错误，需要确认接口地址和接口名，如云通信短信服务短信发送：dysmsapi.aliyuncs.com，接口名Action=SendSms";
			};
		return "错误原因:"+message+",解决方案:"+programme;
		}
	
}
