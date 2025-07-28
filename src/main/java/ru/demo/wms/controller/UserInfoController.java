package ru.demo.wms.controller;
/*UserInfoController управляет пользователями в системе управления складом. Вот его ключевые функции:

Регистрация пользователя (showUserReg, saveUser): Позволяет регистрировать новых пользователей, 
генерирует пароль и OTP, отправляет электронное письмо с данными для входа.

Вход в систему (showUserLogin): Отображает страницу входа для пользователей.

Отображение всех пользователей (showAllUsers): Предоставляет список всех пользователей в системе.

Настройка сессии (doSetup): Устанавливает сессию для пользователя после входа, 
определяя его роли и права доступа.

Включение/выключение пользователя (enableUser, disableUser): 
Позволяет администратору включать или выключать пользователей.

Отображение профиля пользователя (showProfile): 
Предоставляет информацию о профиле текущего пользователя, включая его роли.

Восстановление забытого пароля (showForgotPwdPage, reGenNewPwd): 
Позволяет пользователям сбрасывать забытые пароли, отправляя новый пароль на электронную почту.

Обновление пароля после входа в систему (showUpdatePwd, doUpdateNewPwd): 
Предлагает функционал для обновления пароля после входа в систему.

Активация пользователя через OTP (showUserActiveOtp, doUserActiveOtp): 
Реализует процесс активации пользователя через OTP, отправленный на электронную почту при регистрации.

Валидация электронной почты (validateWhUserTypeEmail): 
AJAX-запрос для проверки уникальности адреса электронной почты пользователя.

Контроллер важен для управления доступом к системе, 
обеспечения безопасности через аутентификацию и авторизацию пользователей, 
а также для предоставления пользовательского интерфейса для управления профилем пользователя.
*/

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.demo.wms.consts.UserMode;
import ru.demo.wms.model.UserInfo;
import ru.demo.wms.service.IRoleService;
import ru.demo.wms.service.IUserInfoService;
import ru.demo.wms.util.MailUtil;
import ru.demo.wms.util.MyAppUtil;
import ru.demo.wms.util.UserInfoUtil;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/user")
public class UserInfoController {

	@Autowired
	private IUserInfoService service;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private MailUtil mailUtil;
        
        @Autowired
	private MyAppUtil myAppUtil;

	@Autowired
	private BCryptPasswordEncoder encoder;

	private void commonUi(Model model) {
		model.addAttribute("rolesMap", roleService.getRolesMap());
	}


	@GetMapping("/register")
	public String showUserReg(Model model) {
		commonUi(model);
		return "UserInfoRegister";
	}


	@PostMapping("/create")
	public String saveUser(@ModelAttribute UserInfo userInfo, Model model) {
		String pwdGen = myAppUtil.genPwd();
		String otp = myAppUtil.genOtp();

		userInfo.setPassword(pwdGen);
		userInfo.setOtp(otp);

		Integer id = service.saveUserInfo(userInfo);
		if (id != 0) {
			String text = "UserName " + userInfo.getEmail() + ", password " + pwdGen + ", OTP " + otp + ", Roles are "
					+ UserInfoUtil.getRolesAsString(userInfo.getRoles());
			System.out.println(text);
			mailUtil.sendEmail(userInfo.getEmail(), "User Created!", text);
		}
		model.addAttribute("message", "User created => " + id);
		commonUi(model);
		return "UserInfoRegister";
	}


	@GetMapping("/login")
	public String showUserLogin() {
		return "UserInfoLogin";
	}


	@GetMapping("/all")
	public String showAllUsers(Model model) {
		model.addAttribute("list", service.getAllUserInfos());
		return "UserInfoData";
	}


	@GetMapping("/setup")
	public String doSetup(HttpSession ses, Principal p) {
		String emailId = p.getName();
		UserInfo info = service.getOneUserInfoByEmail(emailId).get();
		ses.setAttribute("currentUser", info);
		ses.setAttribute("isAdmin", UserInfoUtil.getRolesAsString(info.getRoles()).contains("ADMIN"));
		return "redirect:/uom/register";
	}


	@GetMapping("/enable")
	public String enableUser(@RequestParam Integer id) {
		service.updateUserStatus(id, UserMode.ENABLED);
		return "redirect:all";
	}


	@GetMapping("/disable")
	public String disableUser(@RequestParam Integer id) {
		service.updateUserStatus(id, UserMode.DISABLED);
		return "redirect:all";
	}


	@GetMapping("/profile")
	public String showProfile(HttpSession ses, Model model) {

		UserInfo info = (UserInfo) ses.getAttribute("currentUser");


		Set<String> roles = UserInfoUtil.getRolesAsString(info.getRoles());


		model.addAttribute("userInfo", info);
		model.addAttribute("roles", roles);

		return "UserInfoProfile";
	}

	@GetMapping("/showForgot")
	public String showForgotPwdPage() {
		return "UserInfoForgotPwd";
	}


	@PostMapping("/reGenNewPwd")
	public String reGenNewPwd(@RequestParam String username, Model model) {
		Optional<UserInfo> opt = service.getOneUserInfoByEmail(username);
		if (opt.isPresent()) {


			String pwdGen = myAppUtil.genPwd();


			String encPwd = encoder.encode(pwdGen);
			service.updateUserPassword(username, encPwd);

			String text = "Hello: " + username + ", NEW PASSWORD " + pwdGen;
			System.out.println(text);
			mailUtil.sendEmail(username, "NEW PASSWORD GENERATED!", text);

			model.addAttribute("message", "PASSWORD UPDATED PLEASE CHECK YOUR INBOX!");
		} else {
			model.addAttribute("message", "USER NOT EXIST!");
		}
		return "UserInfoForgotPwd";
	}


	@GetMapping("/showUpdatePwd")
	public String showUpdatePwd() {
		return "UserNewPwdUpdate";
	}


	@PostMapping("/doUpdateNewPwd")
	public String doUpdateNewPwd(

			@RequestParam String password1, HttpSession session) {

		UserInfo info = (UserInfo) session.getAttribute("currentUser");

		String encPwd = encoder.encode(password1);

		service.updateUserPassword(info.getEmail(), encPwd);

		return "redirect:profile";
	}


	@GetMapping("/showUserActiveOtp")
	public String showUserActiveOtp() {
		return "UserInfoActiveOtp";
	}


	@PostMapping("/doUserActiveOtp")
	public String doUserActiveOtp(@RequestParam String username, @RequestParam String otp, Model model) {

		Optional<UserInfo> opt = service.getOneUserInfoByEmail(username);

		if (opt.isPresent()) {
			UserInfo user = opt.get();


			if (!otp.equals(user.getOtp())) {
				model.addAttribute("message", "INVALID OTP!");
			} else {

				service.updateUserStatus(user.getId(), UserMode.ENABLED);
				model.addAttribute("message", "USER IS ACTIVATED, NOW YOU CAN LOGIN!!");
			}

		} else {
			model.addAttribute("message", "USER NOT EXIST!");
		}
		return "UserInfoActiveOtp";
	}


	@GetMapping("/validateemail")
	@ResponseBody
	public String validateWhUserTypeEmail(@RequestParam String email, @RequestParam Integer id) {

		String message = "";
		if (id == 0 && service.isUserEmail(email))
			message = email + " already exit";

		return message;

	}

}
