package com.paic.unittest.demo.entity;

import com.paic.unittest.demo.utils.TestUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserUnitTest {

    @Test
    void testUser() throws Exception {
        TestUtil.equalsVerifier(User.class);

        User user1 = new User();
        User user2 = new User();
        assertEquals(user1, user2);

        user1.setId(1L);
        user2.setId(1L);
        assertEquals(user1, user2);

        user2.setId(2L);
        assertNotEquals(user1, user2);


    }

}