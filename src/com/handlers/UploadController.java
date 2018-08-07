package com.handlers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {
    
	  @RequestMapping(value="test",method=RequestMethod.GET)
	    public String test() {
	        return "test";
	    }
	 
	    @RequestMapping(value="/upload.do")
	    @ResponseBody
	    public Map<String, String> upload(MultipartFile photo) throws IllegalStateException, IOException {
	    	
	    	System.out.println("测试内容");
	    	
	        Map<String, String> map = new HashMap<String, String>();
	        try {
	            //存储文件夹
	            String holder = "fileUpload/";
	            if (photo == null) {
	                return null;
	            }
	            String orgginalFileName = photo.getOriginalFilename();
	            //新文件名称
	            String newFileName = UUID.randomUUID().toString() + orgginalFileName;
	            //保存路径
	            String serverPath = getRealPath() + holder;
	 
	            photo.transferTo(new File(serverPath, newFileName));
	 
	            //返回客户端的JSON
	            map.put("success", "true");
	            map.put("msg", "成功");
	            //返回服务器地址
	            map.put("file_path",getServerPath() + holder + newFileName);
	        } catch (IOException e) {
	            map.put("success", "false");
	        }
	        return map;
	    }
	 
	    /**
	     * 服务器地址
	     * @return
	     */
	    public String getServerPath() {
	        HttpServletRequest request = getRequest();
	        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	 
	    }
	    /**
	     * 获取项目根目录
	     * @return
	     */
	    public String getRealPath() {
	        HttpServletRequest request = getRequest();
	        return request.getServletContext().getRealPath("/");
	    }
	    /**
	     * 
	     * @return
	     */
	    private HttpServletRequest getRequest() {
	        return ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest();
	    }

    
	
	
}