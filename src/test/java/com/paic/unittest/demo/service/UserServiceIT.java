package com.paic.unittest.demo.service;

import com.paic.unittest.demo.entity.User;
import com.paic.unittest.demo.repository.UserRepository;
import com.paic.unittest.demo.service.dto.XserviceDto;
import com.paic.unittest.demo.utils.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

@IntegrationTest
public class UserServiceIT {

    private final static String DEFAULT_PASSWORD = "default_pass";

    @Autowired
    private UserRepository userRepository;

    private UserService userService;

    @Spy  //注解形式
    private XServiceClient serviceClient;

    @Captor
    private ArgumentCaptor<String> xServiceParamCaptor;

    @BeforeEach
    void setUp() {
        userService = Mockito.spy(new UserService(userRepository, serviceClient)); //代码形式
        userRepository.deleteAll();
    }

    private User createUser(String login) {
        User user = new User();
        user.setLogin(login);
        user.setPasswordHash(DEFAULT_PASSWORD);
        user.setFirstName("f_name");
        user.setLastName("l_name");
        user.setEmail("email@email.com");
        user.setCreatedBy("test_user");
        user.setActivated(false);
        return user;
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void saveUser() {
        XserviceDto dto = new XserviceDto();
        dto.setField("xxxxx");
        Optional<XserviceDto> optional = Optional.of(dto);
        ResponseEntity<XserviceDto> en = ResponseEntity.of(optional);
        Mockito.when(serviceClient.getDto()).thenReturn(en);
//        Mockito.when(serviceClient.getDto()).then(returnsFirstArg());
        User saveUser = userService.saveUser(createUser("test_user"));
        assertThat(saveUser).isNotNull(); //assertj
        assertNotNull(saveUser.getId()); //junit
        assertThat(saveUser.getId()).isNotNull();//assertj

        assertThat(saveUser.getPasswordHash()).isEqualTo(dto.getField());
        userRepository.delete(saveUser);
    }

    @Test
    void deleteUserWithOutVerify() {
        User user = createUser("test_user");
        userService.deleteUser(user);
    }

    @Test
    void testSaveUserCallXserviceReturnNullFieldThenPasswordUserDefaultPass() {
        XserviceDto dto = new XserviceDto();
        Optional<XserviceDto> optional = Optional.of(dto);
        ResponseEntity<XserviceDto> en = ResponseEntity.of(optional);
        Mockito.when(serviceClient.getDto()).thenReturn(en);
        User saveUser = userService.saveUser(createUser("test_user1"));
        assertNotNull(saveUser.getId()); //junit
        assertThat(saveUser.getId()).isNotNull();//assertj
        assertThat(saveUser.getPasswordHash()).isEqualTo(DEFAULT_PASSWORD);
        Mockito.verify(serviceClient, Mockito.times(1)).getDto();
        userRepository.delete(saveUser);
    }

    @Test
    void testSaveUserCallXserviceWithParamCaptor() {
        User test_user1 = createUser("test_user1");
        XserviceDto dto = new XserviceDto();
        dto.setField(test_user1.getLogin());
        Optional<XserviceDto> optional = Optional.of(dto);
        ResponseEntity<XserviceDto> en = ResponseEntity.of(optional);
        Mockito.when(serviceClient.getDtoWithParam(test_user1.getLogin())).thenReturn(en);

        User saveUser = userService.saveUserWithParam(test_user1);
        Mockito.verify(serviceClient).getDtoWithParam(xServiceParamCaptor.capture());//拦截调用方法的参数

        assertThat(xServiceParamCaptor.getValue()).isEqualTo(test_user1.getLogin());//验证参数是否符合预期
        Mockito.verify(serviceClient, Mockito.times(1)).getDtoWithParam(test_user1.getLogin());
        userRepository.delete(saveUser);
    }

    @Test
    void deleteUserWithVerify() {
        User delUser = createUser("del_user");
        userRepository.save(delUser);
        userService.deleteUser(delUser);
        delUser = userRepository.findUserByLogin("test_user");
        assertThat(delUser).isNull();
    }
}