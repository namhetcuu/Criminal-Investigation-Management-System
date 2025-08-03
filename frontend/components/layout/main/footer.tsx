import React from "react";
import Image from "next/image";

export const Footer = () => {
    return (
        <footer className="bg-black text-white px-4 md:px-6 py-10 text-sm">
            <div className="max-w-screen-xl mx-auto grid grid-cols-1 md:grid-cols-4 gap-8">
                {/* Cột 1 */}
                <div>
                    <ul className="space-y-2">
                        <li><a href="#" className="hover:underline">Directory of City Agencies</a></li>
                        <li><a href="#" className="hover:underline">Notify NYC</a></li>
                        <li><a href="#" className="hover:underline">NYC Mobile Apps</a></li>
                    </ul>
                </div>

                {/* Cột 2 */}
                <div>
                    <ul className="space-y-2">
                        <li><a href="#" className="hover:underline">311 Service</a></li>
                        <li><a href="#" className="hover:underline">City Events</a></li>
                        <li><a href="#" className="hover:underline">Report Issue</a></li>
                    </ul>
                </div>

                {/* Cột 3 */}
                <div>
                    <ul className="space-y-2">
                        <li><a href="#" className="hover:underline">Social Media</a></li>
                        <li><a href="#" className="hover:underline">Email Updates</a></li>
                        <li><a href="#" className="hover:underline">Feedback</a></li>
                    </ul>
                </div>

                {/* Cột 4 */}
                <div className="space-y-4">
                    {/* Logo + Search */}
                    <div className="flex flex-col sm:flex-row sm:items-center sm:gap-4 gap-2">
                        <img src="/images/logo/Logo-text.png" alt="NYC Logo" className="w-full h-full" />
                        <input
                            type="text"
                            placeholder="Search..."
                            className="px-3 py-1 rounded bg-gray-700 text-white border border-gray-600 w-full sm:w-40"
                        />
                    </div>

                    {/* Text */}
                    <p className="text-gray-400">
                        © 2025 City of New York. All rights reserved. NYC is a trademark and service mark of the City of New York.
                    </p>

                    {/* Footer bottom row */}
                    <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-2">
                        <p className="text-gray-400">Privacy Policy, Terms of Use</p>
                        <Image
                            src="/images/logo/wheelchair.png"
                            alt="Accessibility Icon"
                            width={30}
                            height={30}
                            className="rounded"
                        />
                    </div>
                </div>
            </div>
        </footer>
    );
};
