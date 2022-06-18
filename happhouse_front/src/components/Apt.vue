<template>
  <div>
    <br />
    <br />
    <br />
    <br />
    <apt-search-bar id="searchBar"></apt-search-bar>
    <apt-map id="aptMap"></apt-map>
  </div>
</template>

<script>
import AptMap from "./AptMap.vue";
import AptSearchBar from "./AptSearchBar.vue";

export default {
  components: {
    AptSearchBar,
    AptMap,
  },
};
</script>

<style>
#aptMap {
  position: relative;
}
</style>
