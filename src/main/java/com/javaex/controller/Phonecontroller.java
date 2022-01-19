package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.Vo.PersonVo;
import com.javaex.dao.PhoneDao;


@Controller
@RequestMapping(value = "/phone") // 대표주소
public class Phonecontroller {

	// 필드
	@Autowired
	private PhoneDao phoneDao;
	//  private PhoneDao phoneDao = new PhoneDao(); 제어 권이 컨트롤러한태 있음 스프링에선 안씀
	// 생성자

	// 메소드gs

	// 메소드 일반

	@RequestMapping(value = "/writeForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String writeForm() {

		System.out.println("phonecontroller>writeForm");



		return "writeForm";
	}
	
	// Vo에 파라미터 값을 자동으로 넣어줌 Vo의 set 값과 파라미터의 이름이 같아야된다. 다르면 안 들어감 디폴트 생성자가 무조건 필요
	// 없으면 오류남 DispacherServlet 전달함 --> controller 전달받음
	
	  @RequestMapping(value="/write", method= { RequestMethod.GET,RequestMethod.POST}) 
	  public String write(@ModelAttribute PersonVo personVo ){
	 
	  System.out.println("phonecontroller>write");
	  
	  System.out.println(personVo);
	  //저장
	  //PhoneDao persondao = new PhoneDao();  스프링에선 new 안해도됨
	  
	  phoneDao.personInsert(personVo);
      
      //리다이렉트
	  return "redirect:/phone/list"; 
	  }
	 

	// 두개 다 같이 쓸 수 있음
	 /*
	  @RequestMapping(value="write", method= { RequestMethod.GET,RequestMethod.POST} ) 
	  public String write2(@ModelAttribute PersonVo personVo, @RequestParam("company") String company){
	  
	  System.out.println("phonecontroller>write");
	  
	  System.out.println(personVo);
	  
	  System.out.println("company"); //저장 return ""; }
	 */

	// 위랑 같은 문법
	  /*
	@RequestMapping(value = "write", method = { RequestMethod.GET, RequestMethod.POST })
	public String write(@RequestParam("name") String name, @RequestParam("hp") String hp,
			@RequestParam("company") String company) {

		System.out.println("phonecontroller>write");
		// 저장
		System.out.println(name);
		System.out.println(hp);
		System.out.println(company);

		PersonVo personVo = new PersonVo(name, hp, company);
		PhoneDao persondao = new PhoneDao();

		persondao.personInsert(personVo);
		return "";
	}
 */
	  
	  @RequestMapping(value="/list", method= { RequestMethod.GET,RequestMethod.POST})
	  public String list(Model model) {
		  System.out.println("phonecontroller>list");
		  
		  
		  //다오에서 리스트를 가져온다 스프링을 안 쓸 결우 모델2에서의 방식
		  //PhoneDao phonedao = new PhoneDao();
		  //List<PersonVo> personList = phonedao.getPersonList();
		  //phoneDao.getPersonList(personList)
		  
		  
		  
		  //applicationContext을 만들어 스프링에서 사용하면 더 간단해짐
		  List<PersonVo> personList = phoneDao.getPersonList();
		  
		  
		  System.out.println(personList.toString());
		  
		  //컨트롤러--> DispacherServlet 데이터를 보냄 (model)
		  model.addAttribute("personList",personList); // ("personList" 리퀘스트 불러오는 값 jstl사용시 사용 값,personList 모델 안에 다오 리스트 가져오는 변수)
		  
		  //jsp 정보를 리턴한다(view)
		  return "list"; 
	  }
	  
	  
	  @RequestMapping(value="/delete", method= { RequestMethod.GET,RequestMethod.POST})
	  public String delete(@RequestParam("personId") int personId){
		  
		  System.out.println("delete");
		    //PoneDao를 메모리에 올린다
			//PhoneDao phoneDao = new PhoneDao();   스프링에선 new 안해도됨
			//삭제
			System.out.println(personId);
			phoneDao.personDelete(personId);
			//리다이렉트
			  return "redirect:/phone/list"; 
	  }
	  
	  
	  @RequestMapping(value="/update", method= { RequestMethod.GET,RequestMethod.POST}) 
	  public String update(@ModelAttribute PersonVo personVo ){
	 
		  System.out.println("update");
	  
		  System.out.println(personVo);
		  //업데이트
		  //PhoneDao persondao = new PhoneDao();   스프링에선 new 안해도됨
	  
		  phoneDao.personUpdate(personVo);
      
		  //리다이렉트
		  return "redirect:/phone/list"; 
	  }
	  
	  
	  //업데이트 폼
	  @RequestMapping(value = "/updateForm", method = { RequestMethod.GET, RequestMethod.POST })
		public String updateForm(@RequestParam("personId") int personId,Model model) {

		  System.out.println("phonecontroller>updateForm");
		  //업데이트
		  //PhoneDao persondao = new PhoneDao();   스프링에선 new 안해도됨
			  
		  PersonVo vo = phoneDao.getPerson(personId);
		  
		  System.out.println(vo);
		  
		  //컨트롤러--> DispacherServlet 데이터를 보냄 (model)
		  model.addAttribute("personVo",vo);
		  
			  
		  return "updateForm";
		}
	  
	  
	  //RequestMapping 파라미터 값이 무조건 있어야됨 안 그러면 애러남 안에 값을 안 적어두면 상관없음
	  @RequestMapping(value = "/test", method = { RequestMethod.GET, RequestMethod.POST })
	   public String test(  @RequestParam(value="name") String name,
			   				@RequestParam(value="age", required = false, defaultValue = "-1") int age) {
		  
		  			System.out.println(name);
		  			System.out.println(age);
		  return "writeForm";
	  }
	  
	  
	  @RequestMapping(value = "/view", method = { RequestMethod.GET, RequestMethod.POST })
	   public String view(  @RequestParam(value="no") int no) {
		  
		  System.out.println("@RequestParam");
		  System.out.println(no+"번글 가져오기");
		  System.out.println("주소값 : http://localhost:8088/phonebook3/phone/view?no=12");		
		  return "writeForm";
	  }
	  
	  
	  
	  
	  @RequestMapping(value = "/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	   public String blog(@PathVariable(value="id") String id) {
		  
		  
		  System.out.println(id+"의 블로그 가져오기");
		  System.out.println("주소값 : phonebook3/phone/10");
		  return "writeForm";
	  }
	  
	  
	  
	  
	  @RequestMapping(value = "/{no}/{num}/view", method = { RequestMethod.GET, RequestMethod.POST })
	   public String view11(@PathVariable("no") int no,@PathVariable("num") int num) {
		  	
		    System.out.println("@PathVariable");
		  	System.out.println(no+"번글 가져오기");
		  	System.out.println("주소값 : phonebook3/phone/10/15/view");
		  			
		  return "writeForm";
	  }
	  
	  	  
}
