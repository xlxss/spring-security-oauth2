<template>
    <div>
        <form v-on:submit.prevent="login">
            <input id="username" type="text" v-model="username" placeholder="username">
            <br/>
            <input id="password" type="password" v-model="password" placeholder="password">
            <br/>
            <button type="submit">submit</button>
        </form>
    </div>
</template>

<script>
    import {localLogin} from '@/Http'
    export default {
        name: "Index",
        data() {
            return {
                username: '',
                password: ''
            }
        },
        methods: {
            login: function() {
                debugger;
                console.log(this);
                const body = {
                    'data': {
                        'username': this.username,
                        'password': this.password
                    },
                    'headers': {
                        'X-Login-Type': 'username'
                    },
                    done: data => console.log(data)
                };
                login(body)
            }
        }
    }
</script>

<style scoped>

</style>