module.exports = {
    root: true,
    env: {
        es6: true,
        node: true,
    },
    extends: [
        "eslint:recommended",
        "plugin:import/errors",
        "plugin:import/warnings",
        "plugin:import/typescript",
        "google",
        "plugin:@typescript-eslint/recommended",
    ],
    parser: "@typescript-eslint/parser",
    parserOptions: {
        project: ["tsconfig.json", "tsconfig.dev.json"],
        sourceType: "module",
    },
    ignorePatterns: [
        "/lib/**/*", // Ignore built files.
        "/generated/**/*", // Ignore generated files.
    ],
    plugins: [
        "@typescript-eslint",
        "import",
    ],
    rules: {
        "@typescript-eslint/no-explicit-any": "off",
        "camelcase": "off",
        "comma-dangle": "off",
        "linebreak-style": "off",
        "object-curly-spacing": "off",
        "padded-blocks": "off",
        "quotes": ["error", "double"],
        "require-jsdoc": "off",
        "spaced-comment": "off",
        "valid-jsdoc": "off",
        "import/no-unresolved": 0,
        "indent": ["error", 4],
        "max-len": ["error", {code: 120}]
    },
};
