package com.kh.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.member.domain.Member;
import com.kh.spring.member.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	private MemberService mService;
	
	// 회원가입 화면
	@RequestMapping(value="/member/registerView.kh", method=RequestMethod.GET)
	public String registerView() {
		return "member/register";
	}
	
	// 등록 버튼 누를 때 - 멤버 등록
	// 1. @RequestParam("name값") String 변수명
	// 2. @RequestParam("name값")을 생략할 수 있다. 
	//   - 매개변수명이 name값과 같을 때
	// 3. @ModelAttribute 사용하기
	//   - Domain(VO)클래스 기본생성자 존재
	//   - Setter 메소드 존재
	//   - ** form태그에서 name값이 Domain(VO) 클래스 멤버변수명과 일치
	@RequestMapping(value="/member/register.kh", method=RequestMethod.POST)
	public String memberRegister(
			HttpServletRequest request
			, @ModelAttribute Member member
			, Model model) {
		try {
			int result = mService.insertMember(member);
			if(result > 0) {
				return "redirect:/index.jsp";
			}else {
				model.addAttribute("msg", "회원가입이 완료되지 않았습니다.");
				return "common/error";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/error";
		}
	}
	// 멤버 정보 수정
	@RequestMapping(value="/member/modify.kh", method=RequestMethod.POST)
	public String memberModify(@ModelAttribute Member member, Model model) {
		try {
			int result = mService.updateMember(member);
			if(result > 0) {
				return "redirect:/index.jsp";
			}else {
				model.addAttribute("msg", "정보 수정이 완료되지 않았습니다.");
				return "common/error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/error";
		}
	}

	@RequestMapping(value="/member/out.kh", method=RequestMethod.GET)
	public String memberRemove(@RequestParam("memberId") String memberId, Model model) {
		try {
			int result = mService.deleteMember(memberId);
			if(result > 0) {
				return "redirect:/member/logout.kh";
			}else {
				model.addAttribute("msg", "탈퇴가 완료되지 않았습니다.");
				return "common/error";
			}
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			return "common/error";
		}
	}
	
	// 멤버 로그인
	@RequestMapping(value="/member/login.kh", method=RequestMethod.POST)
	public String memberLogin(
			HttpServletRequest request
			, @RequestParam("member-id") String memberId
			, @RequestParam("member-pw") String memberPw
			, Model model) {
		try {
//			String memberId = request.getParameter("member-id");
//			String memberPw = request.getParameter("member-pw");
			Member mParam = new Member(memberId, memberPw);
			Member member = mService.checkMemberLogin(mParam);
			HttpSession session = request.getSession();
			if(member != null) {
				session.setAttribute("loginUser", member);
				return "redirect:/index.jsp";
			}else {
				model.addAttribute("msg", "로그인 정보가 존재하지 않습니다.");
				return "common/error";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/error";
		}
	}
	
	// 멤버 로그아웃
	@RequestMapping(value="/member/logout.kh", method=RequestMethod.GET)
	public String memberLogout(HttpSession session, Model model) {
		if(session != null) {
			session.invalidate();
			return "redirect:/index.jsp";
		}else {
			model.addAttribute("msg", "로그아웃을 완료하지 못했습니다.");
			return "common/error";
		}
	}
	// 마이페이지
	@RequestMapping(value="/member/mypage.kh", method= {RequestMethod.GET, RequestMethod.POST})
	public String showMypage(HttpSession session, Model model) {
		Member mOne = (Member)session.getAttribute("loginUser");
		String memberId = mOne.getMemberId();
		Member member = mService.selectOneById(memberId);
		model.addAttribute("member", member);
		return "member/mypage";
	}
	
	// /member/list.kh
	public String showMembers() {
		// 1. 페이징 없이 출력해보기
		// 2. 페이징 처리해서 출력해보기
		// 3. 검색해서 출력해보기
		// 4. 검색하고 페이징 처리 출력해보기
		return null;
	}
}



















