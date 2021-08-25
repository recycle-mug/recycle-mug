const VueLoaderPlugin = require("vue-loader/lib/plugin");
const HtmlWebpackPlugin = require("html-webpack-plugin");

const path = require("path");

module.exports = {
  mode: "history",
  devtool: "eval",
  resolve: {
    extensions: [".js", ".vue"],
    alias: {
      "@": path.resolve(__dirname, "src/"),
    },
  },
  devServer: {
    historyApiFallback: true,
    proxy: {
      "/backend": {
        target: "http://18.189.54.77:5000/",
        changeOrigin: true,
        pathRewrite: {
          "^/backend": "",
        },
      },
      "/tokenApi": {
        target: "https://kauth.kakao.com",
        changeOrigin: true,
        pathRewrite: {
          "^/tokenApi": "",
        },
      },
      "/infoApi": {
        target: "https://kapi.kakao.com",
        changeOrigin: true,
        pathRewrite: {
          "^/infoApi": "",
        },
      },
    },
  },
  entry: {
    // app: "src/main.js"
    app: path.join(__dirname, "src/main.js"),
  },
  module: {
    rules: [
      {
        test: /\.vue$/,
        loader: "vue-loader",
      },
      {
        test: /\.s[ac]ss$/i,
        use: [
          "vue-style-loader",
          "css-loader",
          "sass-loader",
          {
            loader: "sass-resources-loader",
            options: {
              resources: [
                path.resolve(__dirname, "src/scss/_variables.scss"),
                path.resolve(__dirname, "src/scss/_themes.scss"),
              ],
            },
          },
        ],
      },
      {
        test: /\.(gif|png|jpe?g|svg)$/,
        use: [
          {
            loader: "file-loader",
            options: { esModule: false },
          },
          "image-webpack-loader",
        ],
      },
    ],
  },
  plugins: [
    new VueLoaderPlugin(),
    new HtmlWebpackPlugin({
      template: path.join(__dirname, "public/index.html"),
      minify: { collapseWhitespace: true },
    }),
  ],
  output: {
    filename: "[name].js",
    path: path.join(__dirname, "../../../docs"),
    publicPath: process.env.NODE_ENV === "production" ? "/recycle-mug/" : "/",
  },
};
