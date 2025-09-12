'use strict';

const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const path = require('path');
const cwd = process.cwd();

/**
 * Create entry points for webpack
 * @returns {Object} Object with entry points for webpack
 */
const creatEntries = () => {
    const entries = [
        path.join(cwd, 'client/js/main.js'),
        path.join(cwd, 'client/js/search.js'),
        path.join(cwd, 'client/js/checkout.js'),
    ];
    const obj = {};
    entries.forEach((entry) => {
        const name = path.basename(entry, path.extname(entry));
        obj[name] = entry;
    });
    return obj;
}

module.exports = [
    {
        mode: 'development',
        entry: creatEntries(),
        output: {
            path: path.join(cwd, 'src/main/resources/static/dist'),

            // use [name] to create a js file for each entry point
            filename: '[name].js',

            // assetModuleFilename for assets like fonts and images
            assetModuleFilename: '[name][ext]'
        },
        module: {
            rules: [
                {
                    test: /\.m?js$/,
                    exclude: /node_modules/,
                    use: {
                        loader: 'babel-loader',
                        options: {
                            presets: ['@babel/preset-env'],
                            cacheDirectory: true
                        }
                    }
                },
                {
                    test: /\.(scss)$/,
                    use: [
                        {
                            loader: MiniCssExtractPlugin.loader
                        },
                        {
                            loader: 'css-loader'
                        },
                        {
                            loader: 'postcss-loader',
                            options: {
                                postcssOptions: {
                                    plugins: function () {
                                        return [
                                            require('autoprefixer')
                                        ];
                                    }
                                }
                            }
                        },
                        {
                            loader: 'sass-loader',
                            options: {
                                sassOptions: {
                                    silenceDeprecations: [
                                        'import',
                                        'color-functions',
                                        'global-builtin',
                                        'mixed-decls',
                                        'legacy-js-api',
                                    ],
                                    quietDeps: true
                                }
                            }
                        }
                    ]
                },
                {
                    // https://webpack.js.org/guides/asset-modules/
                    test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/,
                    include: path.resolve(__dirname, './node_modules/bootstrap-icons/font/fonts'),
                    type: 'asset/resource'
                },
                {
                    test: /\.(ttf|eot|svg)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
                    type: 'asset/resource'
                }
            ]
        },
        target: ['web', 'es5'],
        plugins: [
            new MiniCssExtractPlugin({
                filename: '[name].css'
            })
        ]
    }
];
