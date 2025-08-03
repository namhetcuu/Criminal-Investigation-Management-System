/**
 * LAYOUT COMPONENT FOR PHASE 2
 * 
 * This is a Next.js layout component that provides the common structure
 * for all pages in the (phase2)/dot2 route group.
 * 
 * PURPOSE:
 * - Provides consistent layout across all phase 2 pages
 * - Includes the LeftSidebar navigation component
 * - Sets up the main flex container for sidebar + content layout
 * 
 * USAGE:
 * This layout automatically wraps all pages in the (phase2)/dot2 directory.
 * Any page.tsx file in this directory will be rendered as {children}
 * alongside the persistent LeftSidebar.
 * 
 * STRUCTURE:
 * ┌─────────────────────────────────────┐
 * │  LeftSidebar  │      children       │
 * │               │    (page content)   │
 * │               │                     │
 * │               │                     │
 * └─────────────────────────────────────┘
 */

import LeftSidebar from "@/components/features/phase2/LeftSideBar";
import { ReduxProvider } from "@/providers/ReduxProvider";

export default function Page({
  children,
}: {
  children: React.ReactNode  // The page content that will be rendered
}) {
  return (
    // Main container with flex layout and full height
    <div className="flex min-h-screen bg-gray-100 w-full max-w-screen overflow-x-auto">
      <ReduxProvider>
      {/* LEFT SIDEBAR */}
      <LeftSidebar />
      {/* MAIN CONTENT AREA */}
      {children}
      </ReduxProvider>
    </div>
  );
}
