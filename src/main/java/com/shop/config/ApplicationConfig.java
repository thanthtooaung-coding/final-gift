package com.shop.config;

import com.shop.dto.ProductResponseDto;
import com.shop.dto.UserResponseDto;
import com.shop.entity.Category;
import com.shop.entity.Product;
import com.shop.entity.Role;
import com.shop.entity.User;
import com.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // 1. Configure Product -> ProductResponseDto
        TypeMap<Product, ProductResponseDto> productMap = modelMapper.createTypeMap(Product.class, ProductResponseDto.class);
        Converter<Category, String> categoryToString = c -> c.getSource() == null ? null : c.getSource().getName();
        productMap.addMappings(mapper ->
                mapper.using(categoryToString).map(Product::getCategory, ProductResponseDto::setCategoryName)
        );

        // 2. Configure User -> UserResponseDto
        TypeMap<User, UserResponseDto> userMap = modelMapper.createTypeMap(User.class, UserResponseDto.class);
        Converter<Role, String> roleToString = c -> c.getSource() == null ? null : c.getSource().name();
        userMap.addMappings(mapper ->
                mapper.using(roleToString).map(User::getRole, UserResponseDto::setRole)
        );

        return modelMapper;
    }
}
