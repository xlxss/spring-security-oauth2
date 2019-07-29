<template><div></div></template>

<script>
    import {getToken} from '@/Http'
    export default {
        name: "Login",
        mounted() {
            const appName = this.$route.params.appName;
            const authCode = this.$route.query.code;
            console.log('Login.vue -> appName:', appName);
            console.log('Login.vue -> authCode:', authCode);
            getToken({code: authCode, appName, done: data => {
                    console.log('Login.vue -> getToken:', data);
                    this.$router.push('/login/success');
                }});
        }
    }
</script>

<style scoped>

</style>