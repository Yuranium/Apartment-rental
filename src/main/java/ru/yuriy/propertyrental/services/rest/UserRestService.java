package ru.yuriy.propertyrental.services.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yuriy.propertyrental.models.dto.UserDTO;
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.models.graphql.input.UserInput;
import ru.yuriy.propertyrental.repositories.UserRepository;
import ru.yuriy.propertyrental.util.exceptions.UserNotFoundException;
import ru.yuriy.propertyrental.util.mappers.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "user_rest")
public class UserRestService
{
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Cacheable
    @Transactional(readOnly = true)
    public List<UserDTO> listUsers(PageRequest pageRequest)
    {
        return userRepository.findAll(pageRequest).stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDTO getById(Long id)
    {
        return userMapper.toDTO(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("Пользователь с id=%d не был найден", id))));
    }

    @CacheEvict(key = "#id")
    @Transactional(readOnly = true)
    public void deleteUser(Long id, String email)
    {
        UserDTO user = getById(id);
        if (!user.email().equals(email))
            throw new AccessDeniedException("Пользователь не имеет прав на выполнение данного действия");
        else userRepository.deleteById(id);
    }

    @CachePut(key = "#id")
    public void updateUser(UserDTO user, Long id)
    {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("Пользователь с id=%d не найден", id)));
        u.setName(user.name());
        u.setEmail(user.email());
        u.setPhone(user.phone());
        u.setActive(user.active());
        userRepository.save(u);
    }

    @CachePut(key = "#user.id()")
    public UserDTO saveUser(UserInput user)
    {
        return userMapper.toDTO(
                userRepository.save(
                        userMapper.toEntity(user)
                ));
    }

    @CachePut(key = "#user.id()")
    public UserDTO updateUser(UserInput user)
    {
        return userMapper.toDTO(
                userRepository.save(
                        userMapper.toEntity(user)
                )
        );
    }

    @CacheEvict(key = "#id")
    public void deleteUser(Long id)
    {
        userRepository.deleteById(id);
    }
}