package javase.equal_hashcode;

import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * 优雅重写 equals() & hashCode()
 * @author LBG - 2017/9/25 0025
 */
@Data
public class User {

    private Integer id;
    private String name;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return new EqualsBuilder().append(getId(), user.getId())
                                  .append(getName(), user.getName())
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 31).append(getId())
                                          .append(getName())
                                          .toHashCode();
    }

    public static void main(String[] args) {
        User user = new User(4, "tome");
        User user2 = new User(4, "tome");
        System.out.println(user == user2);
        System.out.println(user.equals(user2));

        System.out.println(user.hashCode());
        System.out.println(user2.hashCode());

        Set<User> userSet = new HashSet<>();
        userSet.add(user);
        userSet.add(user2);
        System.out.println(userSet.size());
    }
}
