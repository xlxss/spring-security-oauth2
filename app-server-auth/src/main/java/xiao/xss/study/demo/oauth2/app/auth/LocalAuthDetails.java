package xiao.xss.study.demo.oauth2.app.auth;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
import xiao.xss.study.demo.oauth2.app.common.constant.LoginType;

import javax.servlet.http.HttpServletRequest;

/**
 * A holder of selected HTTP details related to a web authentication request.
 *
 * @author xiaoliang
 * @since 2019-07-26 9:14
 */
public class LocalAuthDetails extends WebAuthenticationDetails {
//    private static final long serialVersionUID = App.SERIAL_VERSION_UID;
    private static final long serialVersionUID = -1L;
    private LoginType loginType;

    public LocalAuthDetails(HttpServletRequest request) {
        super(request);
        String type = request.getHeader("X-Login-Type");
        if(type == null || type.length() == 0) {
            type = request.getParameter("loginType");
        }
        loginType = LoginType.of(type);
    }

    public LoginType getLoginType() {
        return this.loginType;
    }

    public boolean equals(final Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof LocalAuthDetails)) {
            return false;
        }
        LocalAuthDetails other = (LocalAuthDetails)o;
        if(!other.canEqual(this)) {
            return false;
        }
        if(!super.equals(o)) {
            return false;
        }
        return this.getLoginType() == null ? other.getLoginType() == null : this.getLoginType().equals(other.getLoginType());
    }

    private boolean canEqual(final Object other) {
        return other instanceof LocalAuthDetails;
    }

    public int hashCode() {
        int result = super.hashCode();
        if(this.getLoginType() != null) {
            result = result * (this.getLoginType().hashCode() % 7);
        }
        return result;
    }

    @Override
    public String toString() {
        return "LocalAuthDetails{RemoteIpAddress: " + this.getRemoteAddress() + ", SessionId: "
                + this.getSessionId() + ", loginType: " + this.getLoginType() + "}";
    }
}
