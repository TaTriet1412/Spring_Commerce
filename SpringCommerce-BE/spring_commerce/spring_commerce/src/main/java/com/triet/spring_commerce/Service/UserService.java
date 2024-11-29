package com.triet.spring_commerce.Service;

import com.triet.spring_commerce.Entity.User;
import com.triet.spring_commerce.Repository.UserRepository;
import com.triet.spring_commerce.Request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);


    public List<User> getUsers(){
        return this.userRepository.findAll();
    }

    public User getUserById(Long id){
        return this.userRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng này"));
    }

    public void checkEmailAndPhone(String email,String phone){
        for(User user:getUsers()){
            if(user.getEmail().equals(email)){
                throw new RuntimeException("Đã tồn tại email trong hệ thống");
            }else if(user.getPhone().equals(phone)){
                throw new RuntimeException("Đã tồn tại số điện thoại trong hệ thống");
            }
        }
    }

    public User registerNewAccount(RegisterRequest registerRequest){
        checkEmailAndPhone(registerRequest.getEmail(),registerRequest.getPhone());
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPhone(registerRequest.getPhone());
        user.setName(registerRequest.getName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setGender(registerRequest.isGender());
        userRepository.save(user);
        cartService.createNewCart(user);
        return user;
    }

    public User afterLogin(String email, String password){
        for(User user:getUsers()){
            if(user.getEmail().equals(email) && passwordEncoder.matches(password,user.getPassword())){
                return user;
            }
        }
        throw new RuntimeException("Thông tin đăng nhập không chính xác");
    }

    public void encodePassword(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void encodeOldPassword(){
        List<User> userList = getUsers();
        for(User user:userList){
            if (!user.getPassword().startsWith("$2a$") && !user.getPassword().startsWith("$2b$") && !user.getPassword().startsWith("$2y$")){
                encodePassword(user);
            }
        }
    }
}
