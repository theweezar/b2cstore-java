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
        path.join(cwd, 'client/js/main.js')
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
            filename: '[name].js'
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
