<template>
    <div>
        <p>Login Success</p>
        <p>{{msg}}</p>
    </div>
</template>

<script>
    import {get} from '@/Http'
    export default {
        name: "LoginSuccess",
        data() {
            return {
                msg: ''
            }
        },
        beforeCreate() {
            const config = {
                url: '/test',
                done: data => this.msg = data,
            };
            get(config);
        }
    }
</script>

<style scoped>

</style>