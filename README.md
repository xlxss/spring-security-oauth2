# spring-security-oauth2
spring security oauth2 认证授权
---

### 基于内存认证授权测试流程
1. 获取授权码
```
GET http://localhost:8000/oauth/authorize?client_id=client&response_type=code&redirect_uri=https://www.baidu.com
获取到授权码，即跳转到https://www.baidu.com?code=xxx中的xxx
```
2. 获取令牌
```
POST http://localhost:8000/oauth/token?grant_type=authorization_code&code=PDeEAI&socpe=all&client_id=client&client_secret=123456&redirect_uri=https://www.baidu.com
 - access_token
 - token_type
 - refresh_token
```
3. 刷新令牌 POST
```
http://localhost:8000/oauth/token?grant_type=refresh_token&refresh_token=${refresh_token}&client_id=client&client_secret=123456&socpe=all
```