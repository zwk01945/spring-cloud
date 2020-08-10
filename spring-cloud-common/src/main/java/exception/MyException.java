package exception;

/**
 * ClassName: MyException
 * Description: 自定义异常
 * date: 2020/7/17 9:36
 * @author Mr.zhang
 */
public class MyException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public MyException(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
