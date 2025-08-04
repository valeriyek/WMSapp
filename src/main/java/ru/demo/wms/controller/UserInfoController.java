package ru.demo.wms.controller;


import java.security.Principal;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.demo.wms.consts.UserMode;
import ru.demo.wms.model.UserInfo;
import ru.demo.wms.service.IRoleService;
import ru.demo.wms.service.IUserInfoService;
import ru.demo.wms.util.MailUtil;
import ru.demo.wms.util.MyAppUtil;
import ru.demo.wms.util.UserInfoUtil;
/**
 * Контроллер для управления пользователями системы складского учета (WMS).
 * <p>
 * Реализует регистрацию, авторизацию, активацию по OTP, восстановление и обновление пароля,
 * включение/отключение пользователей, просмотр профиля и проверку email.
 */

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

	/**
	 * Добавляет карту ролей в модель для отображения в UI.
	 */
	private void commonUi(Model model) {
		model.addAttribute("rolesMap", roleService.getRolesMap());
	}

	/**
	 * Отображение страницы регистрации нового пользователя.
	 */
	@GetMapping("/register")
	public String showUserReg(Model model) {
		commonUi(model);
		return "UserInfoRegister";
	}

	/**
	 * Сохранение нового пользователя, генерация пароля и OTP, отправка письма.
	 */
	@PostMapping("/create")
	public String saveUser(@ModelAttribute UserInfo userInfo, Model model) {
		String pwdGen = myAppUtil.genPwd(); // генерация пароля
		String otp = myAppUtil.genOtp();    // генерация OTP

		userInfo.setPassword(pwdGen);
		userInfo.setOtp(otp);

		Integer id = service.saveUserInfo(userInfo);

		if (id != 0) {
			// формируем текст письма и отправляем
			String text = "UserName " + userInfo.getEmail() + ", password " + pwdGen + ", OTP " + otp +
					", Roles are " + UserInfoUtil.getRolesAsString(userInfo.getRoles());
			System.out.println(text);
			mailUtil.sendEmail(userInfo.getEmail(), "User Created!", text);
		}
		model.addAttribute("message", "User created => " + id);
		commonUi(model);
		return "UserInfoRegister";
	}

	/**
	 * Отображение страницы входа пользователя.
	 */
	@GetMapping("/login")
	public String showUserLogin() {
		return "UserInfoLogin";
	}

	/**
	 * Отображение всех пользователей в системе.
	 */
	@GetMapping("/all")
	public String showAllUsers(Model model) {
		model.addAttribute("list", service.getAllUserInfos());
		return "UserInfoData";
	}

	/**
	 * Установка атрибутов сессии после входа: текущий пользователь и флаг isAdmin.
	 */
	@GetMapping("/setup")
	public String doSetup(HttpSession ses, Principal p) {
		String emailId = p.getName();
		UserInfo info = service.getOneUserInfoByEmail(emailId).get();
		ses.setAttribute("currentUser", info);
		ses.setAttribute("isAdmin", UserInfoUtil.getRolesAsString(info.getRoles()).contains("ADMIN"));
		return "redirect:/uom/register";
	}

	/**
	 * Включение пользователя (режим ENABLED).
	 */
	@GetMapping("/enable")
	public String enableUser(@RequestParam Integer id) {
		service.updateUserStatus(id, UserMode.ENABLED);
		return "redirect:all";
	}

	/**
	 * Отключение пользователя (режим DISABLED).
	 */
	@GetMapping("/disable")
	public String disableUser(@RequestParam Integer id) {
		service.updateUserStatus(id, UserMode.DISABLED);
		return "redirect:all";
	}

	/**
	 * Отображение страницы профиля текущего пользователя.
	 */
	@GetMapping("/profile")
	public String showProfile(HttpSession ses, Model model) {
		UserInfo info = (UserInfo) ses.getAttribute("currentUser");
		Set<String> roles = UserInfoUtil.getRolesAsString(info.getRoles());
		model.addAttribute("userInfo", info);
		model.addAttribute("roles", roles);
		return "UserInfoProfile";
	}

	/**
	 * Отображение формы восстановления пароля (забыл пароль).
	 */
	@GetMapping("/showForgot")
	public String showForgotPwdPage() {
		return "UserInfoForgotPwd";
	}

	/**
	 * Обработка восстановления пароля: генерация нового и отправка по email.
	 */
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
			model.addAttribute("message", "PASSWORD UPDATED. PLEASE CHECK YOUR INBOX!");
		} else {
			model.addAttribute("message", "USER NOT EXIST!");
		}
		return "UserInfoForgotPwd";
	}

	/**
	 * Отображение страницы для изменения пароля после входа.
	 */
	@GetMapping("/showUpdatePwd")
	public String showUpdatePwd() {
		return "UserNewPwdUpdate";
	}

	/**
	 * Сохранение нового пароля после входа пользователя.
	 */
	@PostMapping("/doUpdateNewPwd")
	public String doUpdateNewPwd(@RequestParam String password1, HttpSession session) {
		UserInfo info = (UserInfo) session.getAttribute("currentUser");
		String encPwd = encoder.encode(password1);
		service.updateUserPassword(info.getEmail(), encPwd);
		return "redirect:profile";
	}

	/**
	 * Отображение страницы для активации пользователя по OTP.
	 */
	@GetMapping("/showUserActiveOtp")
	public String showUserActiveOtp() {
		return "UserInfoActiveOtp";
	}

	/**
	 * Активация пользователя по введенному OTP.
	 */
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

	/**
	 * AJAX-валидация email на уникальность (используется при регистрации).
	 */
	@GetMapping("/validateemail")
	@ResponseBody
	public String validateWhUserTypeEmail(@RequestParam String email, @RequestParam Integer id) {
		String message = "";
		if (id == 0 && service.isUserEmail(email))
			message = email + " already exist";
		return message;
	}
}
