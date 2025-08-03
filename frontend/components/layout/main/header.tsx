import React from 'react';
import { DropdownMenu, DropdownMenuTrigger, DropdownMenuContent, DropdownMenuItem } from "@/components/ui/dropdown-menu";
import { ChevronDown } from "lucide-react";
import Image from "next/image";
import Link from "next/link";
import { Navigation } from '../../Nav';
export const Header = () => {
  return (
    <header className="bg-black text-white">
      <div className="container mx-auto px-4 py-3 flex items-center justify-between">
        <div className="flex items-center space-x-4">
          <div className="w-[66px] h-[23px]">
            <Link href="/" className="w-[66px] h-[23px] relative block">
              <Image
                src="/images/logo/Logo-text.png"
                alt="NYC Logo"
                fill
                className="object-contain"
              />
            </Link>
          </div>
          <div className="text-sm font-bold text-gray-300">New York City Police Department</div>
        </div>
        <div className="flex items-center space-x-6 text-sm">
          <DropdownMenu>
            <DropdownMenuTrigger className="flex items-center font-bold focus:outline-none">
              <span>English</span>
              <ChevronDown className="ml-1 h-4 w-4" />
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end" className="bg-white text-black">
              <DropdownMenuItem>English</DropdownMenuItem>
              <DropdownMenuItem>Español</DropdownMenuItem>
              <DropdownMenuItem>中文</DropdownMenuItem>
              <DropdownMenuItem>Русский</DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </div>
      </div>
      <Navigation />
    </header>
  );
};
