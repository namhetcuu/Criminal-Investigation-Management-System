'use client';

import { useState } from 'react';
import { Eye, EyeOff } from 'lucide-react';
import Image from 'next/image';
import Link from 'next/link';

export default function LoginPage() {
    const [showPassword, setShowPassword] = useState(false);

    return (
        <div className="relative min-h-screen flex items-center justify-center bg-black">
            {/* Background Image */}
            <Image
                src="/bg-login.png"
                alt="Background"
                fill
                className="object-cover opacity-80"
                priority
            />

            {/* Form Container */}
            <div className="relative z-10 w-full max-w-md md:max-w-md lg:max-w-lg rounded-2xl overflow-hidden shadow-xl mx-4 sm:mx-6">
                {/* Main Form */}
                <div className="bg-white/90 px-4 sm:px-6 py-8 sm:py-10 md:py-12 lg:py-14">
                    <h1 className="text-center text-xl sm:text-2xl font-semibold text-gray-800/85 mb-6">
                        PD SYSTEM
                    </h1>

                    <form className="space-y-4 sm:space-y-5">
                        {/* Username */}
                        <div>
                            <label htmlFor="username" className="block text-sm font-medium text-gray-700 mb-1">
                                Username
                            </label>
                            <input
                                id="username"
                                name="username"
                                type="text"
                                placeholder="Enter username"
                                className="w-full px-3 py-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500 text-sm"
                                autoComplete="username"
                                required
                            />
                        </div>

                        {/* Password */}
                        <div>
                            <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-1">
                                Password
                            </label>
                            <div className="relative">
                                <input
                                    id="password"
                                    name="password"
                                    type={showPassword ? 'text' : 'password'}
                                    placeholder="Enter password"
                                    className="w-full px-3 py-2 pr-20 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500 text-sm"
                                    autoComplete="current-password"
                                    required
                                />
                                <button
                                    type="button"
                                    onClick={() => setShowPassword(prev => !prev)}
                                    className="absolute right-3 top-1/2 -translate-y-1/2 flex items-center gap-1 text-gray-600 text-xs focus:outline-none"
                                    tabIndex={-1}
                                >
                                    {showPassword ? <EyeOff size={16} /> : <Eye size={16} />}
                                    <span>{showPassword ? 'Hide' : 'Show'}</span>
                                </button>
                            </div>
                        </div>

                        {/* Login Button */}
                        <button
                            type="submit"
                            className="w-full sm:w-fit sm:mx-auto block bg-[#6B7A90] text-white py-2 px-6 rounded-md hover:bg-[#5b6a80] transition duration-200 text-sm font-medium"
                        >
                            Login
                        </button>
                    </form>
                </div>

                {/* Language Dropdown */}
                <div className="bg-white  px-4 sm:px-6 py-0 text-sm text-gray-700 border-t border-gray-200">
                    <select className=" w-auto rounded-md sm:w-fit sm:mx-auto block  px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                        <option>English (United States)</option>
                        <option>Tiếng Việt</option>
                        <option>Español</option>
                    </select>
                </div>


                {/* Footer Links */}
                <div className="bg-white/90 px-4 py-4 border-t border-gray-200 flex flex-wrap justify-center gap-3 text-xs text-gray-500 rounded-b-2xl">
                    <Link href="#" className="hover:underline">About</Link>
                    <Link href="#" className="hover:underline">Help Center</Link>
                    <Link href="#" className="hover:underline">Terms</Link>
                    <Link href="#" className="hover:underline">Privacy</Link>
                    <Link href="#" className="hover:underline">Cookies</Link>
                </div>
            </div>
        </div>
    );
}
