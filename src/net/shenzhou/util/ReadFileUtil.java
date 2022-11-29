package net.shenzhou.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.web.bind.annotation.RequestMapping;


public class ReadFileUtil {

	
	 public static void getReadFile(String name){
		   try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw  

             /* 读入TXT文件 */  
//             String pathname = "D:/zidian/mima/1.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径  
             String pathname = "D:/projectteam/shenzhou/src/net/shenzhou/controller/app/"+name; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
             File filename = new File(pathname); // 要读取以上路径的input。txt文件  
             InputStreamReader reader = new InputStreamReader(  
                     new FileInputStream(filename)); // 建立一个输入流对象reader  
             BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
             String line = null;  
             String title = "";
             while((line = br.readLine())!=null){//使用readLine方法，一次读一行
                 
            	 if (line.contains("@RequestMapping")&&line.contains("/app/")) {
                	 title = line.substring(line.indexOf("\"")+1, line.length()-2);
//                	 System.out.println(title);
            	 }
                 if (line.contains("method")) {
                	 String content = line.substring(line.indexOf("\"")+1, line.indexOf(",")-1);
                	 String contents = title+content+".jhtml?file={}";
//                	 System.out.println(contents);
                	 File writename = new File("D:\\接口文档.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
                     try {
//                     	if (!writename.exists()) {
////                         	writename.createNewFile();
//                            }
//             			writename.createNewFile();
             			 BufferedWriter out = new BufferedWriter(new FileWriter(writename,true));  
             			 		//此处两行代码进行验证
             			 		
//             			 out.newLine(); //换行
             			 out.write(contents+"\r\n"); // \r\n即为换行  
             		        out.flush(); // 把缓存区内容压入文件  
             		        out.close(); // 最后记得关闭文件  
             		} catch (IOException e) {
             			// TODO Auto-generated catch block
             			e.printStackTrace();
             		} // 创建新文件  
                 }
             }
             br.close(); 

         } catch (Exception e) {  
             e.printStackTrace();  
         }  
     }  
		
	 public static void getFileName() {
         String path = "D:/projectteam/shenzhou/src/net/shenzhou/controller/app"; // 路径
         File f = new File(path);
         if (!f.exists()) {
             System.out.println(path + " not exists");
             return;
         }
 
         File fa[] = f.listFiles();
         for (int i = 0; i < fa.length; i++) {
             File fs = fa[i];
             if (fs.isDirectory()) {
                 System.out.println(fs.getName() + " [目录]");
             } else {
//                 System.out.println(">>>"+fs.getName());
                 
                 getReadFile(fs.getName());
             }
         }
     }
	
	public static void main(String[] args) {
//		getReadFile("AdController.java");
		getFileName();
	}
	
	


}
