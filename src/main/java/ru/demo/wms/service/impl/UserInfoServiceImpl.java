package ru.demo.wms.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.demo.wms.consts.UserMode;
import ru.demo.wms.model.UserInfo;
import ru.demo.wms.repo.UserInfoRepository;
import ru.demo.wms.service.IUserInfoService;

@Service
public class UserInfoServiceImpl implements IUserInfoService, UserDetailsService {

	@Autowired
	private UserInfoRepository repo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public Integer saveUserInfo(UserInfo ui) {

		String pwd = ui.getPassword();

		String encPwd = passwordEncoder.encode(pwd);

		ui.setPassword(encPwd);

		return repo.save(ui).getId();
	}

	public List<UserInfo> getAllUserInfos() {
		return repo.findAll();
	}

	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		Optional<UserInfo> opt = repo.findByEmail(username);
		if(!opt.isPresent() || opt.get().getMode().equals(UserMode.DISABLED)) {
				throw new UsernameNotFoundException("User not exist");
		}
		UserInfo info = opt.get();
		return new User(
				info.getEmail(),  // username 
				info.getPassword(),  // password
				info.getRoles()   //authorities
				.stream()
				.map(r -> new SimpleGrantedAuthority( r.getRole().name() ))
				.collect(Collectors.toSet())
				);	
	}

	public Optional<UserInfo> getOneUserInfoByEmail(String email) {
		return repo.findByEmail(email);
	}
	
	@Transactional
	public void updateUserStatus(Integer id, UserMode mode) {
		repo.updateUserStatus(id, mode);
	}
	
	@Transactional
	public void updateUserPassword(String email, String password) {
		repo.updateUserPassword(email, password);
	}
	

	@Override
	public boolean isUserEmail(String email) {
		
		return repo.existsByEmail(email);
	}
}
/*
Класс UserInfoServiceImpl реализует два интерфейса: IUserInfoService для операций, связанных с пользователями в приложении, и UserDetailsService, требуемый Spring Security для аутентификации и авторизации. Это обеспечивает централизованное управление пользователями, включая регистрацию, обновление статуса и пароля, а также загрузку деталей пользователя для процессов безопасности. Давайте разберем ключевые моменты этой реализации:

Важные аспекты:
Внедрение зависимостей: Используется UserInfoRepository для взаимодействия с базой данных и BCryptPasswordEncoder для шифрования паролей пользователей.
Шифрование паролей: При сохранении пользователя пароль шифруется с использованием BCryptPasswordEncoder, что повышает безопасность хранения паролей.
Реализация loadUserByUsername: Этот метод загружает пользовательские данные по имени пользователя (в данном случае по email), необходимые для аутентификации и авторизации в Spring Security. При отсутствии пользователя или если он отключен, генерируется исключение UsernameNotFoundException.
Возможные улучшения:
Обработка исключений: Можно создать более специфические исключения для различных ошибочных ситуаций, связанных с пользователями, что улучшит управление ошибками и предоставит клиентам более детальную обратную связь.
Логирование: Добавление логирования операций может помочь в отладке и мониторинге системы, особенно при работе с системами безопасности.
Валидация данных: Рекомендуется включить шаги валидации для входящих данных перед их обработкой и сохранением, чтобы гарантировать корректность и безопасность данных.
Разделение ответственности: Возможно, стоит рассмотреть разделение функционала между разными сервисами или классами, например, выделить работу с безопасностью в отдельный сервис, чтобы улучшить модульность и читаемость кода.
Дополнительные замечания:
Принципы SOLID: При дальнейшем развитии и расширении сервиса рекомендуется следовать принципам SOLID для обеспечения гибкости, расширяемости и удобства поддержки кода.
Использование Optional: В методе loadUserByUsername, использование Optional может быть упрощено до цепочки вызовов orElseThrow, что улучшит читаемость.
Управление транзакциями: Для методов, изменяющих данные, уже используется @Transactional, что обеспечивает корректность выполнения операций в рамках одной транзакции. Важно продолжать поддерживать эту практику при добавлении новых функций.
UserInfoServiceImpl играет ключевую роль в управлении учетными записями пользователей и интеграции с механизмами безопасности Spring, обеспечивая надежную и безопасную работу с пользовательскими данными.
*/