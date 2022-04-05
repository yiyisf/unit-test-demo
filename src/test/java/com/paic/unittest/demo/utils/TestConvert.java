package com.paic.unittest.demo.utils;

import com.paic.unittest.demo.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestConvert {
//    @Test
    void testJsonConvert() throws IOException {
        User user = new User();
        user.setLogin("test_user");
        user.setFirstName("f_name");
        user.setLastName("l_name");
        user.setEmail("email@email.com");
        user.setCreatedBy("test_user");
        user.setActivated(false);
    }

    @Mock
    List<String> mockedList;

    @Test
    public void whenNotUseMockAnnotation_thenCorrect() {
        List mockList = Mockito.mock(ArrayList.class);

        mockList.add("one");
        Mockito.verify(mockList).add("one");
        assertEquals(0, mockList.size());

        Mockito.when(mockList.size()).thenReturn(100);
        assertEquals(100, mockList.size());
    }
}
