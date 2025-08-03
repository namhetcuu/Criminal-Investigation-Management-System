import Image from "next/image";
import Link from "next/link";
import { Button } from "@/components/ui/button";

export default function HomeHeader() {
  return (
    <>
      {/* Top Bar */}
      <div className="bg-gray-800 text-white text-sm py-2">
        <div className="container mx-auto px-4 flex justify-between items-center">
          <span>New York City Police Department</span>
          <div className="flex items-center gap-4">
            <span>English</span>
          </div>
        </div>
      </div>

      {/* Main Header */}
      <header className="bg-white shadow-md">
        <div className="container mx-auto px-4">
          {/* Logo Section */}
          <div className="flex justify-center py-4">
            <div className="flex items-center gap-4">
              <Image
                src="/SC_001/Logo.png"
                alt="NYPD Logo"
                width={60}
                height={60}
                className="object-contain"
              />
              <h1 className="text-4xl font-bold text-blue-600">NYPD</h1>
            </div>
          </div>

          {/* Navigation */}
          <nav className="border-t border-gray-200">
            <div className="flex justify-center">
              <ul className="flex items-center space-x-8 py-4">
                <li>
                  <Link href="/" className="text-blue-600 font-medium hover:text-blue-800 transition-colors">
                    Home
                  </Link>
                </li>
                <li>
                  <Link href="/about" className="text-gray-700 hover:text-blue-600 transition-colors">
                    About
                  </Link>
                </li>
                <li>
                  <Link href="/bureaus" className="text-gray-700 hover:text-blue-600 transition-colors">
                    Bureaus
                  </Link>
                </li>
                <li>
                  <Link href="/services" className="text-gray-700 hover:text-blue-600 transition-colors">
                    Services
                  </Link>
                </li>
                <li>
                  <Link href="/stats" className="text-gray-700 hover:text-blue-600 transition-colors">
                    Stats
                  </Link>
                </li>
                <li>
                  <Link href="/policies" className="text-gray-700 hover:text-blue-600 transition-colors">
                    Policies
                  </Link>
                </li>
                <li>
                  <div className="relative">
                    <input
                      type="search"
                      placeholder="Search..."
                      className="bg-gray-100 px-4 py-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                  </div>
                </li>
              </ul>
            </div>
          </nav>
        </div>
      </header>
    </>
  );
}
