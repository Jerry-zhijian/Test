package c.b.s.common.logging.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by guiqingqing on 2018/8/11.
 */
@Getter
@AllArgsConstructor
public enum LoginType {
    LOGIN(Byte.valueOf("1"), "登入"),
    LOGOUT(Byte.valueOf("2"), "登出");

    private Byte code;
    private String description;
}