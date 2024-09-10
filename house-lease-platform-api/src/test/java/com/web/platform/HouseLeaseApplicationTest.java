package com.web.platform;

import com.web.platform.pojo.form.RegistryForm;
import com.web.platform.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class HouseLeaseApplicationTest {


    @Autowired
    private UserService userService;

    @Test
    public void testRegistry() {
        RegistryForm registryForm = new RegistryForm();
        registryForm.setUsername("admin");
        registryForm.setPassword("123456");
        registryForm.setAge(12);
        registryForm.setPhone("14719847190");
        registryForm.setSex(1);
        registryForm.setEmail("XXX@XXX");
        registryForm.setIdCard("13u19e743907X");
        userService.registry(registryForm);
    }


}
