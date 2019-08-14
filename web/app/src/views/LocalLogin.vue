<template>
    <div>
        <form class="form-signin" v-on:submit.prevent="localLogin">
            <p>
                <label class="hidden" for="username">username:</label>
                <input id="username" class="form-control" type="text" v-model="username" placeholder="请输入用户名">
            </p>
            <p>
                <label class="hidden" for="password">password:</label>
                <input id="password" class="form-control" type="password" v-model="password" placeholder="请输入密码">
            </p>
            <p><button class="btn" type="submit">登录</button></p>
            <div style="display: table-footer-group">
                <label style="font-size: 14px">第三方登录：</label>
                <a id="thirdpartyLogin-local" v-on:click="thirdpartyLogin('local')">local</a>
                <a v-for="(v,k) in clients" :id="k" v-on:click="auth(v)">{{k}}</a>
                <p>
                    <a v-for="(v,k) in clients" :id="k" :href="v">{{k}}</a>
                </p>
            </div>
        </form>
    </div>
</template>

<script>
    import {localLogin, thirdpartyLogin, get2} from '@/Http'
    export default {
        name: "localLogin",
        data() {
            return {
                username: '',
                password: '',
                clients: {}
            }
        },
        created() {
            // debugger;
            get2({url:'/auth/clients?callback=http://localhost:8080/login/success', done: result => {
                    console.log("clients:", result, result.data);
                    this.clients = result.data;
                    console.log(this.clients, this.clients.valueOf());
                    for (let c in this.clients) {
                        console.log(c, this.clients[c], c.key, c.value)
                    }
                }
            })
        },
        methods: {
            localLogin: function() {
                const body = {
                    'data': {
                        'username': this.username,
                        'password': this.password
                    },
                    'headers': {
                        'X-Login-Type': 'UP'
                    },
                    done: data => {
                        console.log(data);
                        this.$router.push('/login/success');
                    }
                };
                localLogin(body)
            },
            thirdpartyLogin: function (provider) {
                thirdpartyLogin({provider});
            },
            auth: function (url) {
                console.log('url:', url);
                get2({url})
            },
        }
    }
</script>

<style scoped>
    a {
        color: #42b983;
        cursor: pointer;
        text-decoration: underline;
        padding: 5px;
    }
    .form-signin {
        max-width: 250px;
        padding: 15px;
        margin: 0 auto;
    }
    .form-control {
        position: relative;
        box-sizing: border-box;
        height: auto;
        padding: 10px;
        font-size: 16px;
        border: 1px solid rgba(0,0,0,.15);
        border-radius: 0.25rem;
        width: 100%;
    }
    .btn {
        display: block;
        width: 100%;
        padding: 0.5rem 1rem;
        font-size: 16px;
        line-height: 1.5;
        border: 1px solid transparent;
        border-radius: 0.25rem;
        color: #ffffff;
        background-color: #007bff;
    }
    .hidden {
        position: absolute;
        width: 1px;
        height: 1px;
        padding: 0;
        overflow: hidden;
        display: inline-block;
    }
</style>