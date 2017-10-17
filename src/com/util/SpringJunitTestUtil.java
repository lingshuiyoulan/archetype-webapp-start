package com.util;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author LiChaoJie
 * @description
 * @date 2017-09-28 9:37
 * @modified By
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//这里可以声明一个事务管理 每个单元测试都进行事务回滚 无论成功与否
@TransactionConfiguration(defaultRollback = true)
@Transactional
@ContextConfiguration(locations = {"classpath:app-ctx.xml","classpath:app-datasource.xml","classpath:app-mybatis.xml","classpath:app-redis.xml","classpath:app-shiro.xml","classpath:app-tx.xml","classpath:appmvc.xml"})
public class SpringJunitTestUtil extends AbstractJUnit4SpringContextTests {
}
