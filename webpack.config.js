'use strict';

const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const path = require('path');
const fs = require('fs');
const cwd = process.cwd();

/**
 * Reads a folder asynchronously and lists all .scss files in the given path that do not start with "_".
 * @returns {Object} An object where keys are filenames without extensions and values are full paths.
 */
const listScssFiles = () => {
    const folderPath = path.join(cwd, 'client/scss');
    try {
        const files = fs.readdirSync(folderPath, { withFileTypes: true });
        const result = {};
        files
            .filter(file => file.isFile() && file.name.endsWith('.scss') && !file.name.startsWith('_'))
            .forEach(file => {
                const name = path.basename(file.name, path.extname(file.name));
                result[name] = path.join(folderPath, file.name);
            });
        return result;
    } catch (error) {
        console.error(`Error reading folder: ${error.message}`);
        throw error;
    }
};

/**
 * Lists all .js files in the client/js folder.
 * @returns {Object} An object where keys are filenames without extensions and values are full paths.
 */
const listJSFiles = () => {
    const folderPath = path.join(cwd, 'client/js');
    try {
        const files = fs.readdirSync(folderPath, { withFileTypes: true });
        const result = {};
        files
            .filter(file => file.isFile() && file.name.endsWith('.js'))
            .forEach(file => {
                const name = path.basename(file.name, path.extname(file.name));
                result[name] = path.join(folderPath, file.name);
            });
        return result;
    } catch (error) {
        console.error(`Error reading folder: ${error.message}`);
        throw error;
    }
};

module.exports = [
    {
        mode: 'development',
        entry: listJSFiles(),
        output: {
            path: path.join(cwd, 'src/main/resources/static/dist'),

            // use [name] to create a js file for each entry point
            filename: '[name].js',

            // assetModuleFilename for assets like fonts and images
            assetModuleFilename: '[name][ext]'
        },
        ignoreWarnings: [/./],
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
