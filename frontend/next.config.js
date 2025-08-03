// import type { NextConfig } from "next";

/** @type {import('next').NextConfig} */

// output: 'standalone'
// ignore ESlint

const nextConfig = {
  /* config options here */
  output: 'standalone',
  eslint: {
    ignoreDuringBuilds: true,
  },
  async redirects() {
    return [
      {
        source: '/', // Đường dẫn nguồn (khi user vào đây)
        destination: '/home', // Sẽ được chuyển hướng tới đây
        permanent: true, // Đánh dấu đây là chuyển hướng vĩnh viễn
      },
    ]
  },
};

module.exports = nextConfig;
