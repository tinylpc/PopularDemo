package tiny.populardemo.ui.login.bean;

import android.os.Parcel;

import tiny.populardemo.base.BaseBean;

/**
 * 类描述
 * 创建者:tiny
 * 日期:17/3/3
 */
public class LoginUser extends BaseBean {
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

//    @Override
//    public String toString() {
//        return "LoginUser{" +
//                "name='" + name + '\'' +
//                ", age=" + age +
//                '}';
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
    }

    public LoginUser() {
    }

    protected LoginUser(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
    }

    public static final Creator<LoginUser> CREATOR = new Creator<LoginUser>() {
        @Override
        public LoginUser createFromParcel(Parcel source) {
            return new LoginUser(source);
        }

        @Override
        public LoginUser[] newArray(int size) {
            return new LoginUser[size];
        }
    };
}
