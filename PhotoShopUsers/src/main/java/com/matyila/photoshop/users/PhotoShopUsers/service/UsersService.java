package com.matyila.photoshop.users.PhotoShopUsers.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.matyila.photoshop.users.PhotoShopUsers.shared.UserDto;

public interface UsersService extends UserDetailsService{

	public UserDto createUser(UserDto userDtoDetails);

	public UserDto getByUserName(String username);

	public UserDto getByUserId(String userId);
}
