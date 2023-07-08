package com.charles.spring.binder;

import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Charles
 * @date 2023/5/26 13:50
 */
public class SimpleBinderTest {

    public static void main(String[] args) {
        User user = new User();
        Bindable<User> bindable = Bindable.ofInstance(user);
        // 这里构建一个虚拟的配置文件
        Map<String, String> properties = new HashMap<>();
        properties.put("user.name", "Charles");
        properties.put("user.age", "18");
        List<ConfigurationPropertySource> propertySources =
                Collections.singletonList(new MapConfigurationPropertySource(properties));
        user = new Binder(propertySources).bindOrCreate("user", bindable);
        System.out.println(user);
    }

}
