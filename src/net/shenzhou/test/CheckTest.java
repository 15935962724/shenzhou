package net.shenzhou.test;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class CheckTest {
	public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
//		CheckTest rz=new CheckTest();
//        rz.readZipContext();
        
		 try {
             readfile("D://aaa//c.zip");
             
             
             // deletefile("D:/file");
             
		 	} catch (FileNotFoundException ex) {
		 	} catch (IOException ex) {
     }
     System.out.println("ok");
}  
   /*
    * ��ȡzip�е��ļ� ��sql��䣩    
    */
       
    public static void readZipContext(String zipPath) throws IOException{
        ZipFile zf=new ZipFile(zipPath);
//    	zipPath = "D://aaa//c.zip";
//        ZipFile zf=new ZipFile("D://aaa//c.zip");
        
        System.out.println("aa"+zipPath);
        FileReader fileReader=new FileReader(new File(zipPath));
        InputStream in=new BufferedInputStream(new FileInputStream(zipPath));
        ZipInputStream zin=new ZipInputStream(in);
        //ZipEntry �����ڱ�ʾ ZIP �ļ���Ŀ��
        ZipEntry ze;
        while((ze=zin.getNextEntry())!=null){
            if(ze.isDirectory()){
                //Ϊ�յ��ļ���ʲô������
            }else{

                System.err.println("file:"+ze.getName()+"\nsize:"+ze.getSize()+"bytes");
                if(ze.getSize()>0){
                    BufferedReader reader;
                    try {
                        reader = new BufferedReader(new InputStreamReader(zf.getInputStream(ze), "utf-8"));
                        String line=null;
                        while((line=reader.readLine())!=null){
                            System.out.println(line);
                        }
                        reader.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
        }
    }
    
    /**
     * ��ȡĳ���ļ����µ������ļ�
     */
    public static  boolean readfile(String filepath) throws FileNotFoundException, IOException {
            try {

                    File file = new File(filepath);
                    if (!file.isDirectory()) {
                            System.out.println("�ļ�path=" + file.getPath());
                           // System.out.println("absolutepath=" + file.getAbsolutePath());
                            System.out.println("�ļ�name=" + file.getName());

                    } else if (file.isDirectory()) {
                            System.out.println("�ļ���");
                            String[] filelist = file.list();
                            for (int i = 0; i < filelist.length; i++) {
                                    File readfile = new File(filepath + "\\" + filelist[i]);
                                    if (!readfile.isDirectory()) {//isDirectory�ж��Ƿ����ļ���
                                    		readZipContext(file.getPath());
                                            System.out.println("�ļ���path=" + readfile.getPath());
                                          //  System.out.println("absolutepath="
                                          //                  + readfile.getAbsolutePath());
                                            System.out.println("�ļ���name=" + readfile.getName());

                                    } else if (readfile.isDirectory()) {
                                            readfile(filepath + "\\" + filelist[i]);
                                    }
                            }

                    }

            } catch (FileNotFoundException e) {
                    System.out.println("Exception:" + e.getMessage());
            }
            return true;
    }
    

}
