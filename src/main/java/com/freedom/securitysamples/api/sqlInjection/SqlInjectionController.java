package com.freedom.securitysamples.api.sqlInjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SqlInjectionController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 不安全的SQL查询示例 - 存在SQL注入漏洞
    @GetMapping("/user/search")
    public List<Map<String, Object>> searchUsers(@RequestParam String username) {
        // 直接拼接SQL语句，这是不安全的做法
        String sql = "SELECT * FROM users WHERE username = '" + username + "'";

        // 执行不安全的SQL查询
        return jdbcTemplate.queryForList(sql);
    }

    // 另一个不安全的示例 - 使用动态SQL
    @GetMapping("/user/filter")
    public List<Map<String, Object>> filterUsers(@RequestParam String condition) {
        // 直接将用户输入拼接到SQL中，这是危险的
        String sql = "SELECT * FROM users WHERE " + condition;

        return jdbcTemplate.queryForList(sql);
    }
}