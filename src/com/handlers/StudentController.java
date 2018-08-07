package com.handlers;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.beans.Student;
import com.exception.AgeException;
import com.exception.NameException;
import com.exception.StudentException;
import com.services.IStudentService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/test")
public class StudentController{

	@Autowired
	@Qualifier("studentService")
	//@Resource(name="studentService")
	private IStudentService service;
	
	public void setService(IStudentService service) {
		this.service = service;
	}

	@RequestMapping(value="/register5.do")
	public ModelAndView doRegister5(String name,int age) throws StudentException {

		if(!"张三".equals(name)){
			throw new NameException("姓名不正确");
		}
		
		if(age > 50){
			throw new AgeException("年龄太大");
		}
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/welcome2.jsp");
		return mv;
	}
	
	
	
	@RequestMapping(value="/register.do",method=RequestMethod.POST)
	public String doRegister(String name,int age) throws Exception {
		Student student = new Student(name,age);
		service.addStudent(student);
		return "/welcome.jsp";
	}
	
	
	@RequestMapping(value="/register1.do")
	public String doRegister1(String name,int age) throws Exception {
		System.out.println("hello");
		Student student = new Student(name,age);
		service.addStudent(student);
		return "/welcome.jsp";
	}

	@RequestMapping(value="/register2.do")
	public ModelAndView doRegister2(String name,int age) throws Exception {
		Student student = new Student(name,age);
		service.addStudent(student);
		ModelAndView mv = new ModelAndView();
		mv.addObject("student", student);
		mv.setViewName("/welcome2.jsp");
		return mv;
	}
	
	@RequestMapping(value="/register3.do")
	public ModelAndView doRegister3(Student student) throws Exception {
		System.out.println(student.getId());
		System.out.println(student.getName());
		System.out.println(student.getAge());
		System.out.println("doRegister3+++++++++++");
		service.addStudent(student);
		ModelAndView mv = new ModelAndView();
		mv.addObject("student", student);
		mv.setViewName("/welcome2.jsp");
		return mv;
	}
	
	@RequestMapping(value="/register4.do")
	public void doRegister4(HttpServletRequest request,HttpServletResponse response,Student student) throws Exception {
		
		service.addStudent(student);
		
		request.getRequestDispatcher("/welcome3.jsp").forward(request, response);
		
	}
	
	@RequestMapping("/myajax.do")
	public void doAjax(HttpServletResponse response,Student student) throws Exception{
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("name", student.getName());
		map.put("age", student.getAge());
		
		JSONObject myJson = JSONObject.fromObject(map);
		String jsonString = myJson.toString();
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(jsonString);
		out.close();
		
		
	}
	
	//produces必须要写"application/json;charset=UTF-8"，否则会报406错误
	@RequestMapping(value="/myajax2.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object doAjax2(Student student) throws Exception{
		
		System.out.println(student.getName());
		System.out.println(student.getAge());
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("name", student.getName());
		map.put("age", student.getAge());
		
		
		
		return map;
		
	//	return student;
		
	}
	
	
	
}
