"use client";

import Sidebar from "@/components/Sidebar";
import { useEffect, useState } from "react";
import { FiMenu } from "react-icons/fi";

export default function MainLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const [sidebarOpen, setSidebarOpen] = useState(false);

  // Prevent body scroll when sidebar is open
  useEffect(() => {
    if (sidebarOpen) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.overflow = "";
    }
  }, [sidebarOpen]);

  return (
    <div className="relative flex flex-col min-h-screen bg-white text-black overflow-x-hidden">
      {/* Mobile header */}
      <div className="md:hidden flex items-center p-4 bg-gray-100 shadow">
        <button onClick={() => setSidebarOpen(true)}>
          <FiMenu size={24} />
        </button>
        <span className="ml-4 font-semibold">Dashboard</span>
      </div>

      {/* Overlay khi sidebar mở trên mobile */}
      {sidebarOpen && (
        <div
          onClick={() => setSidebarOpen(false)}
          className="fixed inset-0 z-30 bg-black/30 backdrop-blur-sm md:hidden"
        />
      )}

      {/* Sidebar + Main content */}
      <div className="flex flex-1 h-full min-h-screen">
        <Sidebar isOpen={sidebarOpen} onClose={() => setSidebarOpen(false)} />
        <main className="flex-grow p-6 z-0">{children}</main>
      </div>
    </div>
  );
}
