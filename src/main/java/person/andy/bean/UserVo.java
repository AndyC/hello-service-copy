package person.andy.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by AndyCui on 2017/11/25.
 *
 * @Description
 */
public class UserVo {
    private User user;
    private String userName;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserVo(User user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;

    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "user=" + user +
                ", userName='" + userName + '\'' +
                '}';
    }

    public static void main(String[] args) {
        User user = new User();
        user.setAge(12);
        user.setName("user1");
        User user1 = new User();
        user1.setName("userCopy");
        user1.setAge(23);
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user1);
        List<UserVo> userVos = users.stream().map(UserVo::new).collect(Collectors.toList());
        System.err.println(userVos);
        userVos.forEach(
                userVo -> users.forEach(user2 -> {
                    if (userVo.getUser().getName().equals(user2.getName())) {
                        userVo.setUserName(user2.getName());
                    }
                }));
        System.err.println(userVos);
    }
}
