package bean;

/**
 * ClassName: ResultObject
 * Description: 统一返回类
 * date: 2020/7/17 10:02
 * @author zwk
 */
public class ResultObject {

    private Integer code;
    private String msg;
    private Object data;

    public ResultObject() {
    }

    public ResultObject(Integer code) {
        this.code = code;
        this.msg = "@param";
        this.data = "@value";
    }

    public ResultObject(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
