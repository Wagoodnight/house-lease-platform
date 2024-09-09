package com.web.platform;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.platform.mapper.HouseRentalRequestMapper;
import com.web.platform.mapper.UserMapper;
import com.web.platform.pojo.form.LoginForm;
import com.web.platform.pojo.form.RegistryForm;
import com.web.platform.pojo.query.HouseRentalRequestQuery;
import com.web.platform.pojo.vo.HouseRentalRequestVO;
import com.web.platform.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

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
