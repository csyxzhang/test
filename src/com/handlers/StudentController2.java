package com.handlers;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.validator.StudentBean;



@Controller
@RequestMapping("/test2")
public class StudentController2{

	@RequestMapping(value="system.do")
	public ModelAndView doCheck(){
		System.out.println("欢迎进入系统");
		return new ModelAndView("/welcome.jsp");
	}
	
	
	@RequestMapping(value="/upload.do")
	public ModelAndView doUpload(MultipartFile photo,HttpSession session) throws Exception{
		if(!photo.isEmpty()){
			String path = session.getServletContext().getRealPath("/images");
			
			String fileName = photo.getOriginalFilename();
			
			if(fileName.endsWith(".jpg") || fileName.endsWith(".png")){
				File file = new File(path,fileName);
				photo.transferTo(file);
			}else{
				return new ModelAndView("/fail.jsp");
			}
		}
		return new ModelAndView("/success.jsp");
		
	}
	
	@RequestMapping(value="/some.do")
	public ModelAndView doSome(@Validated StudentBean student,BindingResult br){

		ModelAndView mv = new ModelAndView();
		
		List<ObjectError> errors = br.getAllErrors();
		
		if(errors.size() > 0){
			FieldError nameError = br.getFieldError("name");
			FieldError scoreError = br.getFieldError("score");
			FieldError mobileError =  br.getFieldError("mobile");
			
			if(nameError!=null){
				mv.addObject("nameError", nameError.getDefaultMessage());
			}
			if(scoreError!=null){
				mv.addObject("scoreError", scoreError.getDefaultMessage());
			}
			if(mobileError!=null){
				mv.addObject("mobileError", mobileError.getDefaultMessage());
			}
			
			mv.setViewName("/index5.jsp");
			return mv;
		}
		
		mv.addObject("student", student);
		mv.setViewName("/show.jsp");
		return mv;
	}
	
	
	
}
