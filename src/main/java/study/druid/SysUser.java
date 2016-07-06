package study.druid;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 * Created by aaron_zh on 2016/4/28.
 */
//@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = -7465789565324620013L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 系统用户ID

     */
    private Integer userId;

    /**
     * 账号
     */
    private String name;

    /**
     * 密码
     */
    private String password;


    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 上级账户ID
     */
    private Integer parent;

    /**
     * 用户名
     */
    private String realName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 产品名
     */
    private String productName;

    /**
     * 用户来源, 1 注册 2 手动添加
     */
    private Integer source;

    /**
     * 用户注册来源. <br>
     * 注册，状态位为 1 <br>
     * 手动添加，状态位为 2 <br>
     */
    public enum SOURCE{
        REGISTER(1),
        CUSTOM_ADD(2);

        private int code;
        SOURCE(int code){
            this.code = code;
        }
        public int code(){
            return this.code;
        }
    }

    /**
     * 系统用户状态 -1 锁定 1 正常
     */
    private Integer status;

    /**
     * 用户状态 <br>
     * LOCK - 锁定用户，状态位为 -1 <br>
     * NORMAL - 正常用户，状态位为 1 <br>
     * DELETED - 已删除，状态位为 2 <br>
     */
    public enum STATUS{
        LOCK(-1),
        NORMAL(1),
        DELETED(2);

        private int code;
        STATUS(int code){
            this.code = code;
        }
        public int code(){
            return this.code;
        }
    }


    /**
     * 用户组别
     */
    private Integer group;

    /**
     * 系统用户组别 <br>
     * WAIT_TO_DEAL - 待处理用户，状态位为 1 <br>
     * TRAIL - 试用用户，状态位为 2 <br>
     * FORMAL - 正式用户，状态位为 3 <br>
     */
    public enum GROUP{
        WAIT_TO_DEAL(1),
        TRAIL(2),
        FORMAL(3);

        private int code;
        GROUP(int code){
            this.code = code;
        }
        public int code(){
            return this.code;
        }
    }

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 用户类型 <br>
     * SDK - SDK后台用户，状态位为 1 <br>
     * OPERATION - 运营后台用户，状态位为 2 <br>
     * SDK_AND_OPERATION - SDK后台和运营后台，状态位为 3 <br>
     */
    public enum USERTYPE{
        SDK(1),
        OPERATION(2),
        SDK_AND_OPERATION(3);

        private int code;
        USERTYPE(int code){
            this.code = code;
        }
        public int code(){
            return this.code;
        }
    }

    /**
     * 密码加密的盐
     */
    private String salt;

    /**
     * @see #checkGroup(Integer)
     */
    public boolean checkGroup(){
        return checkGroup(this.group);
    }

    /**
     * 校验用户组别.
     */
    public static boolean checkGroup(Integer group){
        if(group == null) return false;
        for(SysUser.GROUP g : SysUser.GROUP.values()){
            if(g.code() == group){
                return true;
            }
        }
        return false;
    }

    /**
     * @see #checkStatus(Integer)
     */
    public boolean checkStatus(){
        return checkStatus(this.status);
    }

    /**
     * 校验用户状态.
     */
    public static boolean checkStatus(Integer status){
        if(status == null) return false;
        for(SysUser.STATUS s : SysUser.STATUS.values()){
            if(s.code() == status){
                return true;
            }
        }
        return false;
    }



}
