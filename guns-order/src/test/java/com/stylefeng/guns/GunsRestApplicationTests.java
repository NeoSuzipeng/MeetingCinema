package com.stylefeng.guns;

import com.stylefeng.guns.rest.OrderApplication;
import com.stylefeng.guns.rest.common.util.FTPUtil;
import com.stylefeng.guns.rest.common.util.UUIDUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class)
public class GunsRestApplicationTests {

	@Autowired
	private UUIDUtil uuidUtil;

	@Test
	public void contextLoads() {

		System.out.println("UUID: "+ uuidUtil.getUuid());
	}

}
