package net.shenzhou;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import net.sf.json.JSONObject;
import net.shenzhou.util.SettingUtils;


/**
 * 系统常量
 */
public class Config {


	public static Setting stting = SettingUtils.get();
	public enum ImageType {

		/** 医生*/
		doctor,
		
		/** 项目*/
		project,
		
		/**医生资质**/
		aptitude,
		
		/**项目详情**/
		 projectIntroduce
		
	}

	/** 提现手续费千分之一 */
	public static final String taxRate = "0.01";
	
	/** 经度 */
	public static final String longitude = "116.331398";
	
	/** 纬度 */
	public static final String latitude = "39.897445";
	
	/** 每页显示条数 */
	public static final Integer pageSize = 10;
	
	/** 机构头像上传路径 */
	public static final String mechanismLogoUrl = "/upload/mechanism/logo/";
	
	/** 机构介绍图片上传路径 */
	public static final String mechanismIntroduceImgUrl = "/upload/mechanism/introduceImg/";
	
	/** 用户头像上传路径 */
	public static final String memberLogoUrl = "/upload/member/logo/";
	
	/** 医生头像上传路径 */
	public static final String doctorLogoUrl = "/upload/doctor/logo/";
	
	/** 项目logo上传路径 */
	public static final String projectLogoUrl = "/upload/project/logo/";
	
	/** 项目介绍图片上传路径 */
	public static final String projectIntroduceImgUrl = "/upload/project/introduceImg/";
	
	/** 医生资质图片上传路径 */
	public static final String doctorAptitudeImgUrl = "/upload/doctor/aptitudeImg/";
	
	/** 医生身份证图片上传路径 */
	public static final String doctorIdCardImgUrl = "/upload/doctor/idCardImg/";
	
	/** 机构营业执照上传路径 */
	public static final String certificatesImgUrl = "/upload/certificates/certificatesImg/";
	
	/** 用户获取默认头像 */
	public static String getMemberDefaultLogo(String gender){
		return stting.getSiteUrl()+memberLogoUrl+"member_"+gender+"_logo.png";
	}
	
	/** 医生获取默认头像 */
	public static String getDoctorDefaultLogo(String gender){
		return stting.getSiteUrl()+doctorLogoUrl+"doctor_"+gender+"_logo.png";
	}
	
	/** 项目获取默认logo */
	public static String getProjectDefaultLogo(){
		return stting.getSiteUrl()+projectLogoUrl+"project_default_logo.png";
	}

	/** 项目获取默认介绍图片 */
	public static String getProjectIntroduceImgUrl(){
		return stting.getSiteUrl()+projectIntroduceImgUrl+"project_default_introduceImg.png";
	}
	
	/** 机构获取默认logo */
	public static String getMechanismDefaultLogo(){
		return stting.getSiteUrl()+mechanismLogoUrl+"mechanism_default_logo.png";
	}
	
	/** 项目获取默认介绍图片 */
	public static String getMechanismIntroduceImgUrl(){
		return stting.getSiteUrl()+mechanismIntroduceImgUrl+"mechanism_default_introduceImg.png";
	}
	
	
	
}






