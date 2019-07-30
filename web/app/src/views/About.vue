<template>
  <div class="about">
    <h1>This is an about page</h1>
  </div>
</template>

<script>
  import {get} from '@/Http'
  export default {
    name: 'about',
    mounted() {
      get({url: '/test'});
    }
  }
</script>
