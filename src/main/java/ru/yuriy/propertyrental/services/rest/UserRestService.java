package ru.yuriy.propertyrental.services.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yuriy.propertyrental.models.dto.UserDTO;
import ru.yuriy.propertyrental.repositories.UserRepository;
import ru.yuriy.propertyrental.util.mappers.UserMapper;
import ru.yuriy.propertyrental.util.exceptions.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRestService
{
    private final UserRepository userRepository;

    private final UserMapper userMapper;

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

    @Transactional(readOnly = true)
    public void deleteUser(Long id, String email)
    {
        UserDTO user = getById(id);
        if (!user.email().equals(email))
            throw new AccessDeniedException("Пользователь не имеет прав на выполнение данного действия");
    }
}