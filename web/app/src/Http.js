import axios from 'axios'
import APP from '@/config/APP'
import qs from 'qs'

export const get = (url, params, fn) => {
    console.log('Http.get in');
    checkToken();
    axios.get(APP.REST.BASE_URI + url, {params: params || {}, headers: {'Authorization': token()}})
        .then(res => {
            console.log('Http.get success, data:', res.data);
            if(typeof fn === 'function') {
                fn(res.data);
            }
        })
        .catch(err => console.log(err));
};
// 本地用户登录
export const localLogin = ({data, headers, done = () => {}}) => {
    debugger;
    console.log(data, headers);
    axios.request({
        method: "POST",
        url: `${APP.REST.BASE_URI}/auth/login`,
        data: qs.stringify(data),
        headers: {
            ...headers,
            'Content-type': 'application/x-www-form-urlencoded'
            // 'Content-type': 'multipart/form-data'
        }
    })
        .then(res => {
            debugger;
            done(res.data);
        })
        .catch(err => {
            debugger;
            console.log(err)
        });
};

// 登录
export const login2 = (appName) => {
    appName = appName || 'local';
    axios.get(`${APP.REST.BASE_URI}/auth/${appName}/authorize`)
        .then(res => {
            console.log('authorize:', res.data);
            window.location.href = String(res.data);
        })
        .catch(err => console.log(err));
};

// 登出
export const logout = () => {
    return axios.post(`${APP.REST.BASE_URI}/logout`)
        .then(res => {
            sessionStorage.removeItem('access_token');
            sessionStorage.removeItem('token_type');
            sessionStorage.removeItem('refresh_token');
            sessionStorage.removeItem('expires_in');
            sessionStorage.removeItem('scope');
            sessionStorage.removeItem('token_time');
            return res;
        })
        .catch(err => console.log(err));
};

// 获取访问令牌
export const getToken = ({code, appName = 'local', done}) => {
    console.log('Http.getToken code:', code);
    axios.post(`${APP.REST.BASE_URI}/auth/${appName}/token?code=${code}`, {code})
        .then(res => {
            const data = res.data;
            console.log('Http.getToken data:', data);
            sessionStorage.setItem("accessToken", data['accessToken']);
            sessionStorage.setItem("tokenType", data['tokenType']);
            sessionStorage.setItem("refreshToken", data['refreshToken']);
            sessionStorage.setItem("expiresIn", data['expiresIn']);
            sessionStorage.setItem("scope", data['scope']);
            sessionStorage.setItem("tokenTime", data['tokenTime']);
            console.log('Http.getToken success:', code);
            if(typeof done === 'function') {
                done(data);
            }
        })
        .catch(err => {
            console.log(err);
            return err;
        });
};

// 检查访问令牌是否过期，如果过期则更新令牌
const checkToken = () => {
    if(sessionStorage.getItem('accessToken')) {
        try {
            const is_expires = Date.now() - parseInt(sessionStorage.getItem('expiresIn')) * 1000 - parseInt(sessionStorage.getItem('tokenTime')) > 0;
            if (is_expires) {
                refreshToken();
            }
        } catch (e) {
            console.log(e);
            login();
        }
    } else {
        login();
    }
};

// 更新访问令牌
const refreshToken = () => {
    console.log('Http.refreshToken.in');
    axios.post(`${APP.REST.BASE_URI}/auth/refreshToken`, sessionStorage.getItem("refreshToken"), {headers: {Authorization: token()}})
        .then(res => {
            const data = res.data;
            sessionStorage.setItem("accessToken", data['accessToken']);
            sessionStorage.setItem("expiresIn", data['expiresIn']);
            return data;
        })
        .catch(err => console.log(err))
};

const token = () => {
    return `${sessionStorage.getItem('tokenType')} ${sessionStorage.getItem('accessToken')}`;
};