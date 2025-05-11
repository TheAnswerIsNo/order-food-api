package com.wait.app.core.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 天
 *
 * @description: satoken权限路由配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value(value = "${token.jwtSecretKey}")
    private String jwtSecretKey;

    private static final String[] EXCLUDE_PATH_PATTERNS = {
            // Swagger
            "**/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**",
            "/v2/**",
            "/swagger-ui.html/**",
            "/doc.html/**",
            "/error",
            "/favicon.ico",
            "sso/auth",
            "/csrf"
    };

    /**
     * 选择jwt集成sa-token模式(Simple 简单模式,Mixin 混入模式,Stateless 无状态模式)
     * @return StpLogic
     */
    @Bean
    public StpLogic getStpLogic() {
        return new StpLogicJwtForSimple();
    }

    @Bean
    @Primary
    public SaTokenConfig getSaTokenConfigPrimary() {
        SaTokenConfig config = new SaTokenConfig();
        // token名称 (同时也是cookie名称)
        config.setTokenName("authorization");
        // token有效期，单位s 默认30天，不支持自动续签
        config.setTimeout(24 * 30 * 60 * 60);
        // token临时有效期 (指定时间内无操作就视为token过期) 单位: 秒
        config.setActiveTimeout(-1);
        // 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)
        config.setIsConcurrent(true);
        // 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)
        config.setIsShare(false);
        // token风格（默认可取值：uuid、simple-uuid、random-32、random-64、random-128、tik）
        config.setTokenStyle("random-128");
        // 是否在初始化配置时打印版本字符画
        config.setIsPrint(true);
        // 是否输出操作日志
        config.setIsLog(true);
        // 是否尝试从 cookie 里读取 Token，此值为 false 后，StpUtil.login(id) 登录时也不会再往前端注入Cookie
        config.setIsReadCookie(false);
        // 是否尝试从 请求体 里读取 Token
        config.setIsReadBody(false);
        // 设置日志等级
        config.setLogLevel("debug");
        // 设置jwt密钥
        config.setJwtSecretKey(jwtSecretKey);
        // 将token写入响应头中
        config.setIsWriteHeader(true);
        return config;
    }

    /**
     * 注册拦截器
     * addInterceptor: 注册拦截器对象
     * addPathPatterns: 拦截的请求
     * excludePathPatterns: 拦截白名单
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，定义详细认证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
            // 指定一条 match 规则
            SaRouter
                    // 拦截的 path 列表，可以写多个 */
                    .match("/**")
                    // 排除掉的 path 列表，可以写多个
                    .notMatch("/login/web","/login/wechat")
                    .notMatch(EXCLUDE_PATH_PATTERNS)
                    // 要执行的校验动作，可以写完整的 lambda 表达式
                    .check(r -> StpUtil.checkLogin());
        })).addPathPatterns("/**");
    }

    /**
     * 解决跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(3600);
    }

}
