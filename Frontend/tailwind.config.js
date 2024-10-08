/** @type {import('tailwindcss').Config} */
module.exports = {
    purge: {
        content: [
            './src/*.{tsx,ts}',
            './src/components/*.{tsx,ts}',
            './src/pages/*.{tsx,ts}',
        ],
        // enabled: true, // Disable tree shaking
    },
    safelist: [
        {
            pattern: /^.*$/, // Match any class pattern during development
        },
    ],
    content: [],
    theme: {
        extend: {
            colors: {
                prodPrimary: "#edf4fc",
                prodSecondary: "#add0f7",
                prodMainArea: "#c5d2e0",
                prodBackground: "#F8FBFF",
                prodError: "#f59a9a",
                prodMessage: "#c4ffc4",
                prodWarning: "#fe5d5d",
            },
        },
    },
    plugins: [],
}
