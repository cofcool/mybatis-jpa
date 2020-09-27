package net.cofcool.data.mybatis.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    private Long userId;
    private String username;
    private String desc;
    private LocalDateTime signUpTime;

    @Transient
    private String noMessage;

    public User(Long userId, String username, String desc, LocalDateTime signUpTime) {
        this.userId = userId;
        this.username = username;
        this.desc = desc;
        this.signUpTime = signUpTime;
    }
}
