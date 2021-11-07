package eu.senla.alexbych.bulletinboard.backend.service;

import eu.senla.alexbych.bulletinboard.backend.controller.request.ProfileEditRequest;
import eu.senla.alexbych.bulletinboard.backend.controller.request.RegistrationRequest;
import eu.senla.alexbych.bulletinboard.backend.dto.UserDTO;
import eu.senla.alexbych.bulletinboard.backend.repository.RatingRepository;
import eu.senla.alexbych.bulletinboard.backend.repository.RoleRepository;
import eu.senla.alexbych.bulletinboard.backend.repository.UserRepository;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.IPostConverter;
import eu.senla.alexbych.bulletinboard.backend.utils.converter.IUserConverter;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static eu.senla.alexbych.bulletinboard.backend.prototype.UserPrototype.aUser;
import static eu.senla.alexbych.bulletinboard.backend.prototype.UserPrototype.aUserDTO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private IUserConverter userConverter;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Test
    void editProfile() {
        ProfileEditRequest request = new ProfileEditRequest();
        request.setFirstname("other first name");
        request.setLastname("other last name");
        request.setPhoneNumber("");
        UserDTO expected = aUserDTO();
        expected.setFirstname("other first name");
        expected.setLastname("other last name");
        UserDTO actual = userService.editProfile(aUserDTO(), request);
        assertThat(actual).usingRecursiveComparison().ignoringFields("role", "ratings").isEqualTo(expected);
    }

    @Test
    void saveUser() {
        RegistrationRequest request = new RegistrationRequest("alex123", "12345",
                "Alex", "Bych", "88888888888");
        UserDTO expected = aUserDTO();
        when(userConverter.convertUserDTOToUser(aUserDTO())).thenReturn(aUser());
        when(userRepository.save(any())).thenReturn(aUser());
        when(passwordEncoder.encode(any())).thenReturn("12345");
        UserDTO actual = userService.saveUser(request);
        actual.setId(1);
        assertThat(actual).usingRecursiveComparison().ignoringFields("role", "ratings").isEqualTo(expected);
    }
}