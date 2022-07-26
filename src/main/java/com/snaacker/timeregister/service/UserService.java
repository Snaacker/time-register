package com.snaacker.timeregister.service;

import com.snaacker.timeregister.exception.TimeRegisterException;
import com.snaacker.timeregister.model.RequestUserDto;
import com.snaacker.timeregister.model.ResponseUserDto;
import com.snaacker.timeregister.persistent.User;
import com.snaacker.timeregister.repository.UserRepository;
import com.snaacker.timeregister.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired private UserRepository userRepository;

  public ResponseUserDto loadUserByUsername(String username) throws TimeRegisterException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new TimeRegisterException("Can not find user");
    }
    return new ResponseUserDto(user);
  }

  public RequestUserDto getEmployeeById(long id) {
    User user = userRepository.getById(id);
    return Utilities.model2Dto(user);
  }

  // TODO: return UserDto instead of User
  public Page<User> getListUser(int startingPage, int pageSize) {

    Pageable pageable = PageRequest.of(startingPage, pageSize);
    return userRepository.findAll(pageable);
  }

  public RequestUserDto createUser(RequestUserDto userDto) {
    User user = Utilities.dto2Model(userDto);
    //        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    User createdUser = userRepository.save(user);
    return Utilities.model2Dto(createdUser);
  }

  public RequestUserDto editUser(Long id, RequestUserDto user) {
    // TODO: Not implemented yet
    return null;
  }

  // TODO: return UserDTO instead of User
  public Page<User> deleteUser(long id) {
    User deleteUser = userRepository.getById(id);
    userRepository.delete(deleteUser);

    Pageable pageable = PageRequest.of(0, 20);
    return userRepository.findAll(pageable);
  }

  public User getUserByName(String username) {
    return userRepository.findByUsername(username);
  }
}
