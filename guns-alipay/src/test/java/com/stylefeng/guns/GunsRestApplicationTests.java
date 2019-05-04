package com.stylefeng.guns;

import com.stylefeng.guns.rest.AliPayApplication;
import com.stylefeng.guns.rest.common.util.FTPUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AliPayApplication.class)
public class GunsRestApplicationTests {

	@Autowired
	private FTPUtil ftpUtil;

	@Test
	public void contextLoads() {
		File file = new File("C:\\Users\\10353\\Desktop\\qrcode\\qr-441500504510566400.png");
		ftpUtil.uploadFile("qr-441500504510566400.png",file);
	}

}
