package com.common.shiro;

import com.base.user.model.UserBasic;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 密码策略
 *
 */
public class EncryptPwd {
    /**
     * 随机数
     */
    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    /**
     * 加密方式
     */
    private static String algorithmName = "md5";
    /**
     * 散列次数(加密次数)
     */
    private static int hashIterations = 3;

    /**
     * 为用户设置加密策略和盐值
     *
     * @param user 用户
     */
    public static void encryptPassword(UserBasic user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());//增加盐值
        /**
         * 加密策略
         */
        String newPassword = new SimpleHash(algorithmName, user.getPassword(),ByteSource.Util.bytes(user.getCredentialsSalt()),hashIterations).toHex();
        user.setPassword(newPassword);
    }

    public static String encryptPassword(String passWord, String loginName) {
        String salt = randomNumberGenerator.nextBytes().toHex();
        String credentialsSalt = loginName + salt;
        return new SimpleHash(algorithmName, passWord,ByteSource.Util.bytes(credentialsSalt),hashIterations).toHex();
    }


    public static void main(String[] args) {

        UserBasic userBasic=new UserBasic();
        userBasic.setPassword("147258");
        EncryptPwd.encryptPassword(userBasic);
        System.out.println(userBasic.getPassword());

    }
}
