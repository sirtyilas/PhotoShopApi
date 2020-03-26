package com.matyila.photoshop.users.PhotoShopUsers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.matyila.photoshop.users.PhotoShopUsers.data.AlbumsServiceClient;
import com.matyila.photoshop.users.PhotoShopUsers.data.UserEntity;
import com.matyila.photoshop.users.PhotoShopUsers.data.UsersRepository;
import com.matyila.photoshop.users.PhotoShopUsers.model.AlbumResponseModel;
import com.matyila.photoshop.users.PhotoShopUsers.shared.UserDto;

@Service
public class UsersServiceImpl implements UsersService {

	
	private UsersRepository repo;
	private BCryptPasswordEncoder passEncoder;
	//private RestTemplate restTemplate;
	private AlbumsServiceClient albumsServiceClient;
	private Environment env;
	private static Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);
	
	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder cryptPasswordEncoder,
			AlbumsServiceClient albumsServiceClient,Environment env) {
		this.passEncoder =cryptPasswordEncoder;
		this.repo = usersRepository;
		this.albumsServiceClient = albumsServiceClient;
		this.env=env;
	}
	@Override
	public UserDto createUser(UserDto userDtoDetails) {


		userDtoDetails.setUserId(UUID.randomUUID().toString());
		userDtoDetails.setEncryptedPassword(passEncoder.encode(userDtoDetails.getPassword()));
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = modelMapper.map(userDtoDetails, UserEntity.class);
		repo.save(userEntity);
		return null;
	}
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UserEntity findByEmail = repo.findByEmail(email);
		if(findByEmail == null) {
			throw new UsernameNotFoundException(email);
		}
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = modelMapper.map(findByEmail, UserDto.class);
		return new User(userDto.getEmail(), userDto.getEncryptedPassword(), true,true,true,true,new ArrayList<>());
		
	}
	@Override
	public UserDto getByUserName(String username) {
		
		UserEntity userEntity = repo.findByEmail(username);
		if(userEntity == null)throw new UsernameNotFoundException(username);
		return new ModelMapper().map(userEntity, UserDto.class);
		
		
	}
	@Override
	public UserDto getByUserId(String userId) {		
		
		UserEntity findByUserId = repo.findByUserId(userId);
		if(findByUserId == null) {
			throw new UsernameNotFoundException(userId);
		}	
		log.info("Before calling album sevice clint");
		List<AlbumResponseModel> gertAlbums = albumsServiceClient.getAlbums(userId);
		UserDto userDto = new ModelMapper().map(findByUserId, UserDto.class);
		userDto.setAlbums(gertAlbums);
	
		return userDto;
	}
	

}
