# spring-security-oauth2
spring security oauth2 认证授权&第三方登录
-----------------------------
### 集成第三方登录
- auth-server：模拟第三方认证登录，端口8000
- app-server：本地应用后端微服务，端口8010
- web/app：本地应用vue前端，端口8080

1. 第三方登录扩展概要
   1. 在第三方平台注册，获取到client_id、client_secret、authorize-uri、access-token-uri、user-info-uri、redirect-uri
   2. 将第1步得到的信息配置到application.yml文件中的auth.server.provider下，每家认证中心一个节点
   3. AuthProvider.java中添加一项实例，名称要和第2步中配置的节点名保持一致
   4. 定义认证Api继承AbstractAuthApi.java并实现抽象方法，组件名称要和第2步中的节点名保持一致
   5. 前端页面增加第三方登录连接
   * 注：redirect-uri为前端地址/auth/login/<name>，name：第2步中的节点名

2. 本系统登录方式扩展概要
   1. LoginType.java中新增登录方式配置
   2. 定义登录实现类继承AbstractAuthenticationProvider.java并实现其方法
   3. 前端登录页面新增登录方式选择器
   4. 登录时将选择的登录方式（第1步中添加的实例的名称）添加到X-Login-Type头部中

3. 遗留问题
   1. 微服务端用户、权限管理服务未实现，需根据系统需要进行改造
   2. 令牌的生成、校验、注销等逻辑只是简单的权宜之计，需根据系统需要进行改造
   3. 注销登录未实现
   4. 前端异常处理未实现

4. 本系统为其它系统授权登录
   1. 配置AuthorizationServer
   2. 配置ResourceServer
   3. 提供客户端应用注册接口，生成client_id、client_secret
   4. 提供对外资源接口，并在ResourceServer的配置中保护起来

5. 单点登录实现方式参考oauth2-sso项目，sso前后端分离可参考本项目方案
-----------------------------

##### 认证中心授权模式
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
