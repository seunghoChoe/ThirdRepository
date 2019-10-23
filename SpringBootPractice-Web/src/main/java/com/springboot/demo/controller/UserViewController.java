package com.springboot.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.springboot.demo.model.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import static com.springboot.demo.global.Constants.SERVER_MESSAGE;
import static com.springboot.demo.global.Constants.createBaseURI;

/**
 * @Class: 계정 뷰 컨트롤러 클래스
 */
@RestController
public class UserViewController {
    private static final Logger logger = LoggerFactory.getLogger(WebViewController.class);
    private String baseURI = createBaseURI("user-service");

    @Autowired
    private RestTemplate restTemplate;

    /**
     * @Method: 계정 등록 폼
     */
	@RequestMapping(value = "/users/join", method = RequestMethod.GET)
	public ModelAndView getJoin(ModelAndView mv) {
		logger.info("getJoin()");

		mv.addObject("user", new User());
		mv.setViewName("users/join"); // /users/join 지정 시, 타일즈 적용 안되므로 슬래시 삭제
		return mv;
	}

	/**
	 * @Method: 계정 등록
	 */
	@RequestMapping(value = "/users/join", method = RequestMethod.POST)
	public ModelAndView postJoin(@ModelAttribute("user") @Valid User user, BindingResult result, ModelAndView mv, RedirectAttributes rttr) {
		logger.info("postJoin()");

		if (result.hasErrors()) {
			logger.info(result.toString());
			mv.addObject(SERVER_MESSAGE, "계정 등록 정보를 확인해주세요.");
			mv.setViewName("users/join");
		} else {
			ResponseEntity<User> response = restTemplate.postForEntity(baseURI + "/users/join", user, User.class);
			if (response.getStatusCode().is2xxSuccessful()) {
				rttr.addFlashAttribute(SERVER_MESSAGE, "계정 등록이 완료되었습니다.");
				mv.setViewName("redirect:/");
			} else {
				mv.addObject(SERVER_MESSAGE, "계정 등록 중, 에러가 발생하였습니다.");
				mv.setViewName("users/join");
			}
		}

		return mv;
	}

    /**
     * @Method: 계정 로그인 폼
     */
	@RequestMapping(value = "/users/login", method = RequestMethod.GET)
	public ModelAndView getLogin(ModelAndView mv) {
		logger.info("getLogin()");

		mv.setViewName("users/login");
		return mv;
	}

	/**
	 * @Method: 계정 로그인
	 */
	@RequestMapping(value = "/users/login", method = RequestMethod.POST)
	public void postLogin() {
		logger.info("postLogin()");

	}
}