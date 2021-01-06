package cn.metsea.lotus.common.utils;

import java.io.Serializable;
import org.junit.Test;

public class JsonUtilsTest {

    @Test
    public void toBytesTest() {
        User user1 = new User(1, "zhangsan");
        System.out.println(user1);
        byte[] bytes = JsonUtils.toBytes(user1);
        User user2 = JsonUtils.parseObject(bytes, User.class);
        System.out.println(user2);
    }

    static class User implements Serializable {

        private long id;
        private String name;

        public User() {
        }

        public User(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return this.id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                '}';
        }

    }

}
