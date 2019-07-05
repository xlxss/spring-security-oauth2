# spring-security-oauth2
spring security oauth2 认证授权
---
### 认证中心授权模式
1. 授权码模式（authorization-code）
```
* 前提条件：
  - 默认已支持授权码模式，授权码保存在内存中，通过注入AuthorizationCodeServices可以改变实现方式
a. GET http://auth-server:port/oauth/authorize
   URL参数:
   - response_type: 必须，固定值为code
   - client_id: 必须，在认证中心注册的客户端id
   - redirect_uri: 客户端回调url，必须为在认证中心注册的地址中的一个；
                   没有传时，如果在认证中心注册的回调接口只有一个，则跳转到该接口，如果有多个则报错
   - scope: 申请授权范围，必须为在认证中心注册的范围只能
   - state: 客户端随机状态码
b. 上述通过授权后，会在回调url中传递参数code，即为授权码
c. POST http://localhost:8000/oauth/token
   URL参数:
   - grant_type: 必须，固定值为authorization_code
   - code: 必须，上述步骤获取的授权码，授权码仅一次使用有效
   - client_id: 必须，同上
   - client_secret: 必须，客户端密钥
   - redirect_uri: 必须，与a中一致
   - socpe: 同上
d. POST http://localhost:8000/oauth/token
   刷新tokenURL参数
   - grant_type: 必须，固定值为refresh_token
   - refresh_token: 必须，c中获取的refresh_token
   - client_id: 必须，同上
   - client_secret: 必须，客户端密钥
   - redirect_uri: 必须，与a中一致
   - socpe: 同上，必须在c的范围之内
```
2. 授权码隐藏式（implicit）
```
* 前提条件：
  - 默认已支持授权码隐藏式，适用于纯前端应用，允许直接向前端颁发令牌，没有授权码这个中间步骤
a. GET http://auth-server:port/oauth/authorize
   URL参数:
   - response_type: 必须，固定值为token
   - client_id: 必须，在认证中心注册的客户端id
   - redirect_uri: 客户端回调url，必须为在认证中心注册的地址中的一个；
                   没有传时，如果在认证中心注册的回调接口只有一个，则跳转到该接口，如果有多个则报错
   - scope: 申请授权范围，必须为在认证中心注册的范围只能
   - state: 客户端随机状态码
b. 上述通过授权后，会在回调url中锚点位置传递参数token、token_type、expires_in，浏览器跳转时锚点不会发送到服务器
```
3. 密码模式（password）
```
* 前提条件：
  - 默认不支持，通过注入AuthenticationManager开启密码模式，适用于高度信任的应用
a. POST http://localhost:8000/oauth/token
   URL参数:
   - grant_type: 必须，固定值为password
   - client_id: 必须，同上
   - client_secret: 必须，同上
   - username: 必须，用户名
   - password: 必须， 密码
b. 获取结果：access_token、token_type、refresh_token、expires_in、scope
c. 刷新token同上
```
4. 凭证模式（client credentials）
```
* 前提条件：
  - 默认已支持授权码隐藏式，适用于没有前端的应用
a. POST http://localhost:8000/oauth/token
   URL参数:
   - grant_type: 必须，固定值为client_credentials
   - client_id: 必须，同上
   - client_secret: 必须， 同上
b. 获取结果：access_token、token_type、expires_in、scope
c. 该模式禁止刷新token
```
