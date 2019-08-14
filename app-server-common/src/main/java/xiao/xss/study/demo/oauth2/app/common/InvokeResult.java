package xiao.xss.study.demo.oauth2.app.common;

import lombok.Data;

/**
 * 返回值
 *
 * @author xiaoliang
 * @since 2019-08-13 11:44
 */
@Data
public class InvokeResult {
    private int status;
    private Object data;

    public InvokeResult() {
        this(200);
    }

    public InvokeResult(int status) {
        this(status, null);
    }

    public InvokeResult(Object data) {
        this(200, data);
    }

    public InvokeResult(int status, Object data) {
        this.status = status;
        this.data = data;
    }
}
