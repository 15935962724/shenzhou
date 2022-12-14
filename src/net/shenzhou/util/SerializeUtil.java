/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;


/**
 * 
 * @author wsr
 *
 */
public final class SerializeUtil {
	

    private static final Logger logger = Logger.getLogger(SerializeUtil.class);


    /**
     * 序列化
     * 
     * @param object
     * @return
     */
    public static byte[] serialize(Object object)
    {
        byte[] bytes = null;
        if (object != null)
        {
            ObjectOutputStream oos = null;
            ByteArrayOutputStream baos = null;
            try
            {
                // 序列化
                baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                bytes = baos.toByteArray();
            }
            catch (Exception e)
            {
                 e.printStackTrace();
                logger.error("序列化出现异常：", e);

            }
            finally
            {
                close(oos);
                close(baos);
            }
        }
        return bytes;
    }

    /**
     * 反序列化
     * 
     * @param bytes
     * @return Object
     */
    public static Object unserialize(byte[] bytes)
    {
        Object returnObject = new Object();
        if (bytes != null)
        {
            ByteArrayInputStream bais = null;
            ObjectInputStream ois = null;
            try
            {
                // 反序列化
                bais = new ByteArrayInputStream(bytes);
                ois = new ObjectInputStream(bais);
                returnObject = ois.readObject();
            }
            catch (Exception e)
            {
                 e.printStackTrace();
                logger.error("反序列化出现异常：", e);
            }
            finally
            {
                close(bais);
                close(ois);
            }
        }
        else
        {
            returnObject = null;
        }

        return returnObject;
    }

    /**
     * 关闭io流对象
     * 
     * @param closeable
     */
    public static void close(Closeable closeable)
    {
        if (closeable != null)
        {
            try
            {
                closeable.close();
            }
            catch (Exception e)
            {
                 e.printStackTrace();
                logger.error("关闭io流对象出现异常：", e);
            }
        }
    }
	
	
	
}