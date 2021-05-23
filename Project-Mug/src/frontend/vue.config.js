const path = require("path");

module.exports = {
  publicPath: process.env.NODE_ENV === "projection" ? "/" : "/",
  css: {
    loaderOptions: {
      sass: {
        data: `
        @import "@/scss/_reset.scss";
        @import "@/scss/_themes.scss";
        @import "@/scss/_variables.scss";
        `,
      },
    },
  },
  configureWebpack: {
    plugins: [],
  },
  pages: {
    index: {
      entry: "src/main.js",
      template: "public/index.html",
      filename: "index.html",
      title: "index page :)",
    },
  },
};
