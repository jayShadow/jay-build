package cn.jay.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @Author: Jay
 * @Date: 2020/1/16 12:03
 */
@Slf4j
@Configuration
public class MybatisPlusConfig {

    public MybatisPlusConfig() {
        log.info("=======init MybatisPlusConfig finish==========");
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }

    /**
     * 乐观锁
     *
     * @return 乐观锁拦截器
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 字段填充器（创建人、创建时间、更新人、更新时间）
     *
     * @return 填充器
     */
    @Bean
    public MetaObjectHandler MetaObjectHandler() {
        return new MetaObjectHandler() {
            // TODO ThreadLocal中暂存 登陆用户 或者session中获取
            @Override
            public void insertFill(MetaObject metaObject) {
                this.setFieldValByName("createdAt", LocalDateTime.now(), metaObject);
                this.setFieldValByName("createdBy", "1", metaObject);
                this.setFieldValByName("modifiedAt", LocalDateTime.now(), metaObject);
                this.setFieldValByName("modifiedBy", "1", metaObject);
                this.setFieldValByName("version", 0, metaObject);
                this.setFieldValByName("delete", false, metaObject);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.setFieldValByName("modifiedAt", LocalDateTime.now(), metaObject);
                this.setFieldValByName("modifiedBy", "1", metaObject);
            }
        };
    }

}
