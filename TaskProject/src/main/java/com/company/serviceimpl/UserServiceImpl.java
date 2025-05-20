package com.company.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.company.entity.Users;
import com.company.payload.UserDto;
import com.company.repository.UserRepository;
import com.company.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		// userDto is not entity class here
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Users users = userDtoToEntity(userDto);// converted UsersDto into Users Class
		Users savedUser = userRepository.save(users);
		return entityToUserDto(savedUser);
	}

	private Users userDtoToEntity(UserDto userDto) {
		Users users = new Users();
		users.setName(userDto.getName());
		users.setEmail(userDto.getEmail());
		users.setPassword(userDto.getPassword());
		return users;
	}

	private UserDto entityToUserDto(Users savedUser) {
		UserDto userDto = new UserDto();
		userDto.setId(savedUser.getId());
		userDto.setName(savedUser.getName());
		userDto.setEmail(savedUser.getEmail());
		userDto.setPassword(savedUser.getPassword());
		return userDto;
	}
}
