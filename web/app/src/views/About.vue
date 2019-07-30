<template>
    <div class="about">
        <h1>This is an about page</h1>
        <p>--------------</p>
        <p v-if="err">{{err}}</p>
        <p v-else>Auth ok!</p>
    </div>
</template>

<script>
    import {get} from '@/Http'
        export default {
            name: 'about',
            data() {
                return {
                    err: ''
                }
            },
            mounted() {
              get({url: '/test', fail: err => this.err = err});
            }
    }
</script>
