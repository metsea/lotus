package cn.metsea.lotus.dao;

import com.github.davidfantasy.mybatisplus.generatorui.GeneratorConfig;
import com.github.davidfantasy.mybatisplus.generatorui.MybatisPlusToolsApplication;

/**
 * Mybatis-plus Generator
 */
public class Generator {

    public static void main(String[] args) {
        GeneratorConfig config = GeneratorConfig.builder().jdbcUrl("jdbc:mysql://192.168.3.100:13306/lotus")
            .userName("root")
            .password("r1dd16c1a1980475")
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .schemaName("myBusiness")
            .basePackage("cn.metsea.lotus.dao")
            .port(8068)
            .build();
        MybatisPlusToolsApplication.run(config);
    }

}
