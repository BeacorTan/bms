package com.common.shiro;

import com.base.user.model.UserBasic;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 密码策略
 *
 *
 * @date 2016年08月31日
 */
public class EncryptPwd {
    /**
     * 随机数
     */
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    /**
     * 加密方式
     */
    private String algorithmName = "md5";
    /**
     * 散列次数(加密次数)
     */
    private int hashIterations = 3;

    /**
     * 为用户设置加密策略和盐值
     *
     * @param user 用户
     */
    public void encryptPassword(UserBasic user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());//增加盐值
        /**
         * 加密策略
         */
        String newPassword = new SimpleHash(algorithmName, user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();
        user.setPassword(newPassword);
    }


    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    public static void main(String[] args) {
        EncryptPwd p = new EncryptPwd();
//        String[] users = {"1002", "1003", "1004", "1005", "1006", "1007", "1008", "1009", "1010", "1011", "1012", "1013", "1014", "1015", "1016", "1017", "1018", "1019", "1020", "1021", "1022", "1023", "1024", "1025"};
//
//        System.out.println("salt\tpwd\tloginName");
//        for (String loginName : users) {
//            user.setLoginPwd("123456");
//            user.setLoginName(loginName);
//            p.encryptPassword(user);
//            System.out.println(user.getSalt() + "\t" + user.getLoginPwd()+"\t"+user.getLoginName());
//        }

    }
}
