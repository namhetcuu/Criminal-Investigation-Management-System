import React from 'react';
import { Input } from '@/components/ui/input';
import { Search } from 'lucide-react';
export const Navigation = () => {
    return (
        <div className="bg-white py-6 ">
            <div className="container mx-auto px-4 text-center">
                <div className="flex items-center justify-center space-x-4">
                    <div className="w-[256px] h-[100px]">
                        <img src="/images/logo/Logo.png" alt="NYPD Logo" className="w-full h-full" />
                    </div>
                </div>
            </div>
            <nav className="bg-white border-t borader-gray-200">
                <div className="container mx-auto px-4">
                    <div className="flex flex-wrap  items-center justify-center space-y-4 gap-7 sm:space-y-0 sm:space-x-8 py-4">
                        <a href="#" className="text-gray-700 hover:text-blue-900 font-medium transition-colors">
                            Home
                        </a>
                        <a href="#" className="text-gray-700 hover:text-blue-900 font-medium transition-colors">
                            About
                        </a>
                        <a href="#" className="text-gray-700 hover:text-blue-900 font-medium transition-colors">
                            Bureau
                        </a>
                        <a href="#" className="text-gray-700 hover:text-blue-900 font-medium transition-colors">
                            Services
                        </a>
                        <a href="#" className="text-gray-700 hover:text-blue-900 font-medium transition-colors">
                            Stats
                        </a>
                        <a href="#" className="text-gray-700 hover:text-blue-900 font-medium transition-colors">
                            Policies
                        </a>
                        <div className="relative w-full sm:w-auto">
                            <Input
                                type="text"
                                placeholder="Search"
                                className="pl-10 pr-4 py-2 w-full sm:w-64 bg-white border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                            />
                            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-gray-400" />
                        </div>
                    </div>
                </div>
            </nav>
        </div>
    );
};