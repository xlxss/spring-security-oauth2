<template>
    <div>
        <p>Login Success</p>
        <p>{{msg}}</p>
    </div>
</template>

<script>
    import {get2} from '@/Http'
    export default {
        name: "LoginSuccess",
        data() {
            return {
                msg: ''
            }
        },
        beforeCreate() {
            const code = this.$route.query.code;
            const provider = this.$route.query.provider;
            if(provider) {
                const config = {
                    url: `/auth/${provider}/token`,
                    params: {authCode: code},
                    done: data => this.msg = data,
                };
                console.log(this.$route.params, this.$route.query);
                get2(config);
            }
        }
    }
</script>

<style scoped>

</style>