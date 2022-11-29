package net.shenzhou.util;



import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.shenzhou.plugin.StoragePlugin;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

public class UploadUtil implements  ServletContextAware  {
	
	/** 目标扩展名 */
	private static final String DEST_EXTENSION = "jpg";
	/** 目标文件类型 */
	private static final String DEST_CONTENT_TYPE = "image/jpeg";
	
	/** servletContext */
	private ServletContext servletContext;

	@Resource
	private static List<StoragePlugin> storagePlugins;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public static Map<String, Object> getUploadImg(HttpServletRequest request ,String path,String name) {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        try {
            //使用Apache文件上传组件处理文件上传步骤：
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");

            //3、判断提交上来的数据是否是上传表单的数据
//            if (!ServletFileUpload.isMultipartContent(request)) {
//                parameterMap.put("data", new Object());
//                parameterMap.put("message", "获取参数失败,不是上传表单数据!");
//                parameterMap.put("status", "400");
//                return parameterMap;
//            }

            File  temp = null;
            List<FileItem> items = upload.parseRequest(request);
            Iterator<FileItem> iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = iter.next();
                if (item.isFormField()) {  // 如果是表单域 ，就是非文件上传元素
                    String key = item.getFieldName(); // 获取name属性的值
                    String value = item.getString(); // 获取value属性的值
                    if (item.getFieldName().equals("intro")) {
                        System.out.println(value+"yeah");
                    }
                } else {
                    String realPath = request.getRealPath("/");
                    //根据不同方法获得存储路径
//              String path = getPath(parameterMap.get("method").toString());
                    //获得图片存储路径
                    String savePath = realPath + path;
                    //创建存储路径文件夹文件
                    File file = new File(savePath);
                    //判断上传文件的保存目录是否存在
                    if (!file.exists() ) {
                        System.out.println(savePath + "目录不存在，需要创建");
                        //创建目录
                        file.mkdirs();
                    }

                    String fieldName = item.getFieldName(); // 文件域中name属性的值
                    String fileName = item.getName(); // 文件的全路径，绝对路径名加文件名
                    String contentType = item.getContentType(); // 文件的类型
                    Map<String,Object> data_map = new HashMap<String, Object>();
                    
                    data_map.put("fileName",fileName);
                    data_map.put("contentType",contentType);
                    long size = item.getSize(); // 文件的大小，以字节为单位

                    String domain = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
                    System.out.println("ip地址：端口号"+domain);
                    
                    String url2=request.getScheme()+"://"+ request.getServerName();//+request.getRequestURI();
                    System.out.println("协议名：//域名="+url2);
                    
                    name = name+".jpg";
                    System.out.println(name);

                    temp = new File( file.toString()+"/" + name); // 定义一个file指向一个具体的文件
                     item.write(temp);// 把上传的内容写到一个文件中

                    data_map.put("file",domain+path+"/"+ name);
                    parameterMap.put("data",new Object());
                    parameterMap.put("message","上传成功");
                    parameterMap.put("status","200");
                }
            }
        } catch (Exception e) {
        	parameterMap.put("data",new Object());
            parameterMap.put("message",e.getMessage());
            parameterMap.put("status","400");
        }
        return parameterMap;
    }


    /**
     * 从request中获取上传文件表单中的普通参数
     * 请求图片参数放到最后边
     * @param request
     * @author WFJ
     * @return request
     * @time 2016年11月2日15:11:50
     */
    public  Map<String, Object> getUplodMemberImg(HttpServletRequest request) {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        try {
            //使用Apache文件上传组件处理文件上传步骤：
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");

            //3、判断提交上来的数据是否是上传表单的数据
            if (!ServletFileUpload.isMultipartContent(request)) {
                parameterMap.put("invoke_result", "INVOKE_FAILURE");
                parameterMap.put("invoke_message", "获取参数失败,不是上传表单数据!");
                parameterMap.put("invoke_status", "400");
                return parameterMap;
            }

            File  temp = null;
            List<FileItem> items = upload.parseRequest(request);
            Iterator<FileItem> iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = iter.next();
                if (item.isFormField()) {  // 如果是表单域 ，就是非文件上传元素
                    String name = item.getFieldName(); // 获取name属性的值
                    String value = item.getString(); // 获取value属性的值
                    if (item.getFieldName().equals("intro")) {
                        System.out.println(value+"yeah");
                    }
                } else {
                    String realPath = request.getRealPath("/");
                    //根据不同方法获得存储路径
//              String path = getPath(parameterMap.get("method").toString());
                    String path = "/upload/webMemberImages";
                    //获得图片存储路径
                    String savePath = realPath + path;
                    //创建存储路径文件夹文件
                    File file = new File(savePath);
                    //判断上传文件的保存目录是否存在
                    if (!file.exists() ) {
                        System.out.println(savePath + "目录不存在，需要创建");
                        //创建目录
                        file.mkdirs();
                    }

                    String fieldName = item.getFieldName(); // 文件域中name属性的值
                    String fileName = item.getName(); // 文件的全路径，绝对路径名加文件名
                    String contentType = item.getContentType(); // 文件的类型
                    parameterMap.put("fileName",fileName);
                    parameterMap.put("contentType",contentType);
                    long size = item.getSize(); // 文件的大小，以字节为单位

                    String domain = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
                    System.out.println(domain);
                    String name = UUID.randomUUID().toString()+".jpg";
                    System.out.println(name);

                    temp = new File( file.toString()+"/" + name); // 定义一个file指向一个具体的文件
                    item.write(temp);// 把上传的内容写到一个文件中

                    parameterMap.put("file",domain+path+"/"+ name);
                    parameterMap.put("invoke_status","200");
                }
            }
        } catch (Exception e) {
            parameterMap.put("invoke_result", "INVOKE_FAILURE");
            parameterMap.put("invoke_message", "获取参数失败!");
            parameterMap.put("invoke_status", "400");
        }
        return parameterMap;
    }
    /**
     * 验证图片类型是否合法
     * @param fileType
     * @return
     */
    private static boolean validator(String fileType) {
        List<String> allowType = Arrays.asList(new String[]{"jpg", "gif", "bmp", "jpeg", "png"});
        if (fileType != null && !"".equals(fileType)) {
            return allowType.contains(fileType.toLowerCase());
        }
        return false;
    }

    
    /*public static String upload(FileType fileType,Integer width, Integer height, MultipartFile multipartFile,
			String path, boolean async) {
		
		if (multipartFile != null && !multipartFile.isEmpty()) {
			try {
				Map<String, Object> model = new HashMap<String, Object>(); 
				model.put("uuid", UUID.randomUUID().toString());
				String uploadPath = FreemarkerUtils.process(path, model);
				String uuid = UUID.randomUUID().toString();
				String sourcePath = uploadPath + uuid + "-source." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
				String largePath = uploadPath + uuid + "-large." + DEST_EXTENSION;

				for (StoragePlugin storagePlugin : storagePlugins) {
					if (storagePlugin.getIsEnabled()) {
						File tempFile = new File(System.getProperty("java.io.tmpdir") + "/upload_" + UUID.randomUUID() + ".tmp");
						if (!tempFile.getParentFile().exists()) {
							tempFile.getParentFile().mkdirs();
						}
						multipartFile.transferTo(tempFile);
//						addTaskZoom(sourcePath,width,height, largePath,tempFile, multipartFile.getContentType());
						
						return storagePlugin.getUrl(largePath);
						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}*/
    
//	/**
//	 * 文件类型
//	 */
//	public enum FileType {
//
//		/** 图片 */
//		image,
//
//		/** Flash */
//		flash,
//
//		/** 媒体 */
//		media,
//
//		/** 文件 */
//		file
//	}

	
}
