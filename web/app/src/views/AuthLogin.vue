<template><div></div></template>

<script>
    import {getToken} from '@/Http'
    export default {
        name: "authLogin",
        beforeCreate() {
            const provider = this.$route.params.provider;
            const authCode = this.$route.query.code;
            console.log('AuthLogin.vue -> provider:', provider);
            console.log('AuthLogin.vue -> authCode:', authCode);
            getToken({authCode, provider, done: data => {
                    console.log('AuthLogin.vue -> getToken:', data);
                    this.$router.push('/login/success');
                }});
        }
    }
</script>

<style scoped>

</style>