import axios from 'axios'
import APP from '@/config/APP'
import qs from 'qs'

export const get = ({url, params, done, fail}) => {
    Promise.resolve()
        .then(checkToken)
        .then(token => {
            return axios.request({
                method: 'GET',
                url: `${APP.REST.BASE_URI}${url}`,
                params: params,
                headers: {
                    'Authorization': token,
                }
            })
        }).then(res => {
            success(res.data, done);
        }).catch(err => failure(err, fail));
};

// 本地用户登录
export const localLogin = ({data, headers, done, fail}) => {
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
    }).then(res => {
        const {token} = res.data;
        sessionStorage.setItem('app_token', JSON.stringify(token));
        success(res.data, done);
    }).catch(err => {
        failure(err, fail);
    });
};
// 第三方登录
export const thirdpartyLogin = ({provider, fail}) => {
    if(!provider) {
        throw new Error('Missing authorization provider name');
    }
    axios.request({
        method: 'GET',
        url: `${APP.REST.BASE_URI}/auth/${provider}/authorize`
    }).then(res => {
        console.log('authorize:', res.data);
        window.location.href = String(res.data);
    }).catch(err => failure(err, fail))
};
// 登出
export const logout = ({done, fail}) => {
    axios.request({
        method: 'POST',
        url: `${APP.REST.BASE_URI}/auth/logout`
    }).then(res => {
        sessionStorage.removeItem('app_token');
        success(res.data, done);
    }).catch(err => failure(err, fail));
};
// 获取访问令牌
export const getToken = ({authCode, provider, done, fail}) => {
    axios.request({
        method: 'POST',
        url: `${APP.REST.BASE_URI}/auth/${provider}/token`,
        data: qs.stringify({'authCode': authCode}),
        headers: {
            'Content-type': 'application/x-www-form-urlencoded'
        }
    }).then(res => {
        const {token} = res.data;
        sessionStorage.setItem('app_token', JSON.stringify(token));
        success(res.data, done);
    }).catch(err => failure(err, fail));
};

// 检查访问令牌是否过期，如果过期则更新令牌
const checkToken = () => {
    const strToken = sessionStorage.getItem('app_token');
    if(strToken) {
        const token = JSON.parse(strToken);
        try {
            const is_expires = Date.now() - parseInt(token['expiresIn']) * 1000 - parseInt(token['tokenTime']) > 0;
            if (is_expires) {
                return refreshToken();
            } else {
                return Promise.resolve(bearerToken());
            }
        } catch (e) {
            console.log(e);
            return Promise.reject('请先登录');
        }
    } else {
        return Promise.reject('请先登录');
    }
};

// 更新访问令牌
const refreshToken = () => {
    const token = JSON.parse(sessionStorage.getItem('app_token'));
    return new Promise((resolve, reject) => {
        axios.request({
            method: 'POST',
            url: `${APP.REST.BASE_URI}/auth/token/refresh`,
            data: qs.stringify({refresh: token['refreshToken']}),
            headers: {
                // 'Authorization': token(),
                'Content-type': 'application/x-www-form-urlencoded'
            }
        }).then(res => {
            sessionStorage.setItem('app_token', JSON.stringify(res.data));
            return resolve(bearerToken());
        }).catch(err => {
            console.log(err.response);
            return reject(err);
        });
    });
};
// 获取token头
const bearerToken = () => {
    const token = JSON.parse(sessionStorage.getItem('app_token'));
    return `${token['tokenType']} ${token['accessToken']}`;
};
// 成功回调
const success = (data, done) => {
    if(typeof done === 'function') {
        done(data);
    }
};
// 失败回调
const failure = (err, fail) => {
    if(typeof fail === 'function') {
        const body = {
            status: '',
            statusText: '',
            msg: ''
        };
        if(err && err.response) {
            body.status = err.response.status;
            body.statusText = err.response.statusText;
            body.msg = err.response.data;
        } else if(err) {
            body.status = 500;
            body.statusText = err;
            body.msg = err;
        } else {
            body.status = 500;
            body.statusText = '未知异常';
            body.msg = '未知异常';
        }
        fail(body);
    } else {
        console.log(err);
    }
};