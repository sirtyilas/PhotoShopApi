package com.matyila.photoshop.users.PhotoShopUsers.controllers;



import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matyila.photoshop.users.PhotoShopUsers.data.UserEntity;
import com.matyila.photoshop.users.PhotoShopUsers.model.UserResponseModel;
import com.matyila.photoshop.users.PhotoShopUsers.model.UserResquestModel;
import com.matyila.photoshop.users.PhotoShopUsers.service.UsersService;
import com.matyila.photoshop.users.PhotoShopUsers.shared.UserDto;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UsersService service;

    @Autowired
    Environment environment;

    @GetMapping(path = "/status/check")
    public String status(){
        return "working on " + environment.getProperty("local.server.port") + " with token "
        			+ environment.getProperty("token.secret");

    }
   
    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody UserResquestModel model) {
    	
    	ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto  = modelMapper.map(model, UserDto.class);
		service.createUser(userDto);
    	
    	return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId) {
    	
    	ModelMapper modelMapper = new ModelMapper();
		UserDto byUserId = service.getByUserId(userId);
    	UserResponseModel userResonse = modelMapper.map(byUserId,UserResponseModel.class);
    	return new ResponseEntity<>(userResonse,HttpStatus.OK);
    }
}
