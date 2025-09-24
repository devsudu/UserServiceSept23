package dev.sudu.userservicesept23.services;

import dev.sudu.userservicesept23.exceptions.*;
import dev.sudu.userservicesept23.models.Role;
import dev.sudu.userservicesept23.models.Token;
import dev.sudu.userservicesept23.models.User;
import dev.sudu.userservicesept23.repositories.RoleRepository;
import dev.sudu.userservicesept23.repositories.TokenRepository;
import dev.sudu.userservicesept23.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.*;

@Service
@Primary
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SecretKey secretKey;

    public UserServiceImpl(UserRepository userRepository, TokenRepository tokenRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, SecretKey secretKey) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.secretKey = secretKey;
    }

    @Override
    public User signUp(String name, String email, String password) throws MandatoryFieldException, UserAlreadyRegisteredException {
        if(!email.isBlank()){
            Optional<User> checkUser = userRepository.findByEmail(email);
            if(checkUser.isPresent()){
                throw new UserAlreadyRegisteredException("User already registered pls login");
            }
        }
        User user = User.getBuilder().setName(name).setEmail(email).setPassword(bCryptPasswordEncoder.encode(password)).build();
        return userRepository.save(user);
    }

    @Override
    public Token login(String email, String password) throws MandatoryFieldException, PasswordMismatchException {
        if(email.isBlank() || password.isBlank()) {
            throw new MandatoryFieldException("Please enter all mandatory fields");
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()){
            throw new RuntimeException("Please register to sign in");
        }

        if(optionalUser.isPresent() && bCryptPasswordEncoder.matches(password, optionalUser.get().getPassword())) {
            // check if already session is open

            User user = optionalUser.get();
            List<Token> tokenList = tokenRepository.findAllByUserAndIsActiveAndExpiryAtAfter(user, true, new Date().getTime());
            if(!tokenList.isEmpty()) {
                tokenList.stream().iterator().forEachRemaining(token -> token.setIsActive(false));
                tokenRepository.saveAll(tokenList);
            }

//            Token newToken = new Token();
//            newToken.setToken(RandomStringUtils.randomAlphanumeric(128));
//
//            Calendar calender = Calendar.getInstance();
//            calender.add(Calendar.DAY_OF_YEAR, 30);
//            newToken.setExpiryAt(calender.getTimeInMillis());
//            newToken.setIsActive(true);
//            newToken.setUser(optionalUser.get());
//
//            Token optionalToken = tokenRepository.save(newToken);
//
//            return optionalToken;

//            Generate a jwt token using jwt library

            // payload
            Map<String, Object> claims = new HashMap<>();
            claims.put("iss", "sudu.com");
            claims.put("userId", user.getId());

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 30);
            Long expiryDate = calendar.getTimeInMillis();
            claims.put("expiryDate", expiryDate);
            claims.put("roles", user.getRoles());

//            // algo
//            MacAlgorithm  macAlgorithm = Jwts.SIG.HS256;
//
//            // signature
//            SecretKey secretKey = macAlgorithm.key().build();

            String token = Jwts.builder().claims(claims).signWith(secretKey).compact();

            Token savedToked = tokenRepository.save(Token.getBuilder().setToken(token).setExpiryAt(expiryDate).setIsActive(true).setUser(user).build());
            return savedToked;
        }

        throw new PasswordMismatchException("Invalid user credentails");
    }

    @Override
    public Token validateToken(String token) throws InvalidTokenException {
//        Optional<Token> optionalToken = tokenRepository.findByToken(token);
//
//        if(!optionalToken.isPresent()) {
//            throw new InvalidTokenException("Invalid Token, pls login");
//        }
//
//        Token returnedToken = optionalToken.get();
//        if(!returnedToken.getIsActive()){
//            return returnedToken;
//        }
//
//        if(returnedToken.getExpiryAt() < new Date().getTime()){
//            returnedToken.setIsActive(false);
//            Token updatedToken = tokenRepository.save(returnedToken);
//            return updatedToken;
//        }
//
//        return optionalToken.get();

        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();

        Long expiryDate = (Long) claims.get("expiryDate");

        if(expiryDate < new Date().getTime()){
            // token is invalid
            throw new InvalidTokenException("Invalid token");
        }

        // Token is valid
        Optional<Token> optionalToken = tokenRepository.findByToken(token);
        return optionalToken.get();
    }

    @Override
    public Token logout(String token) throws InvalidTokenException {
        Optional<Token> optionalToken = tokenRepository.findByToken(token);

        if(!optionalToken.isPresent()) {
            throw new InvalidTokenException("Invalid Token");
        }

        optionalToken.get().setIsActive(false);

        Token returnedToken = tokenRepository.save(optionalToken.get());
        return returnedToken;
    }

    @Override
    public Role addRole(String name) throws InvalidRoleException, InvalidTokenException {
        if(name.isBlank()){
            throw new InvalidRoleException("Please enter a role name");
        }

        Role newRole = Role.getBuilder().setName(name).build();
        return roleRepository.save(newRole);
    }

    public Role updateRole(String name, Long role_id) throws InvalidRoleException, InvalidTokenException {
        if(role_id == null){
            throw new InvalidRoleException("role_id is required");
        }

        if(name.isBlank()){
            throw new InvalidRoleException("Please enter a role name");
        }

        Optional<Role> optionalRole = roleRepository.findById(role_id);
        if(optionalRole.isPresent()){
            Role roleToUpdate = optionalRole.get();
            roleToUpdate.setName(name);
            return roleRepository.save(roleToUpdate);
        }
        throw new InvalidRoleException("Invalid role_id");
    }

    public Role deleteRole(Long role_id) throws InvalidRoleException, InvalidTokenException {
        if(role_id == null){
            throw new InvalidRoleException("Invalid role_id");
        }

        Optional<Role> optionalRole = roleRepository.findById(role_id);
        if(optionalRole.isPresent()){
            Role roleToDelete = optionalRole.get();
            roleRepository.delete(roleToDelete);
            return roleToDelete;
        }

        throw new InvalidRoleException("Could not find role with id "+role_id);
    }
}
