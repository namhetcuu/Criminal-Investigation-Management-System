/**
 * LEFT SIDEBAR COMPONENT
 * 
 * A collapsible navigation sidebar for the police report system.
 * Contains three main sections with expandable subsections:
 * 1. Initial Response
 * 2. Scene Information  
 * 3. Initial Investigation Report
 * 
 * FEATURES:
 * - Collapsible sections with smooth animations
 * - Dynamic styling (blue when open, gray when closed)
 * - Hover effects on navigation items
 * - Individual state management for each section
 * - Responsive chevron icon rotation
 * - Clean bordered design with rounded corners
 * - Mobile responsive: hides on small screens, shows floating tab
 * - Hover trigger on left edge to reveal sidebar on mobile
 * 
 * USAGE:
 * This component is typically used in a layout file to provide
 * consistent navigation across all pages in the phase 2 section.
 */

"use client";

import { useState, useEffect } from "react";
import { useRouter, useParams, usePathname } from "next/navigation";
import { ChevronDown, Menu, X } from "lucide-react";
import { cn } from "@/lib/utils";
import {
    Collapsible,
    CollapsibleContent,
    CollapsibleTrigger,
} from "@/components/ui/collapsible";

export default function LeftSidebar() {
    const router = useRouter();
    const params = useParams();
    const pathname = usePathname();

    // Get role and reportsId from URL parameters
    const role = params.role as string;
    const reportsId = params.reportsId as string;

    const [initialResponseOpen, setInitialResponseOpen] = useState(false);
    const [sceneInfoOpen, setSceneInfoOpen] = useState(false);
    const [investigationOpen, setInvestigationOpen] = useState(false);
    
    // Mobile sidebar state
    const [isMobileSidebarOpen, setIsMobileSidebarOpen] = useState(false);
    const [isHoveringLeftEdge, setIsHoveringLeftEdge] = useState(false);

    // Determine which sections should be open based on current URL
    useEffect(() => {
        const currentPath = pathname || '';
        
        // Check if we're in initial-response section
        const isInitialResponse = currentPath.includes('/initial-response');
        setInitialResponseOpen(isInitialResponse);

        // Check if we're in scene-information section
        const isSceneInfo = currentPath.includes('/scene-information');
        setSceneInfoOpen(isSceneInfo);

        // Check if we're in field-report or investigation section
        const isInvestigation = currentPath.includes('/field-report') || currentPath.includes('/investigation');
        setInvestigationOpen(isInvestigation);
    }, [pathname]);

    // Handle left edge hover detection
    useEffect(() => {
        const handleMouseMove = (e: MouseEvent) => {
            // Trigger when mouse is within 50px of left edge
            const shouldShowSidebar = e.clientX <= 50;
            setIsHoveringLeftEdge(shouldShowSidebar);
        };

        const handleMouseLeave = () => {
            setIsHoveringLeftEdge(false);
        };

        // Only add listeners on mobile/tablet sizes
        const mediaQuery = window.matchMedia('(max-width: 1024px)');
        
        const addListeners = () => {
            if (mediaQuery.matches) {
                document.addEventListener('mousemove', handleMouseMove);
                document.addEventListener('mouseleave', handleMouseLeave);
            }
        };

        const removeListeners = () => {
            document.removeEventListener('mousemove', handleMouseMove);
            document.removeEventListener('mouseleave', handleMouseLeave);
        };

        addListeners();
        mediaQuery.addEventListener('change', (e) => {
            if (e.matches) {
                addListeners();
            } else {
                removeListeners();
                setIsHoveringLeftEdge(false);
                setIsMobileSidebarOpen(false);
            }
        });

        return () => {
            removeListeners();
        };
    }, []);

    const handleNavigation = (path: string) => {
        const fullPath = `/${role}/reports/${reportsId}${path}`;
        router.push(fullPath);
        // Close mobile sidebar after navigation
        setIsMobileSidebarOpen(false);
    };

    // Helper function to check if current path matches a specific route
    const isActivePath = (path: string) => {
        const fullPath = `/${role}/reports/${reportsId}${path}`;
        return pathname === fullPath;
    };

    // Helper function to check if current path starts with a specific route
    const isActiveSection = (path: string) => {
        const fullPath = `/${role}/reports/${reportsId}${path}`;
        return pathname?.startsWith(fullPath);
    };

    const SidebarContent = () => (
        <>
            {/* INITIAL RESPONSE SECTION */}
            <div className="pt-3 px-3">
                <div className="border border-gray-300 rounded-lg overflow-hidden">
                    <Collapsible
                        open={initialResponseOpen}
                        onOpenChange={setInitialResponseOpen}
                    >
                        <div className="flex">
                            <button
                                onClick={() => handleNavigation('/initial-response')}
                                className={cn(
                                    "flex-1 text-left p-3 transition-all duration-200 hover:bg-blue-200",
                                    isActiveSection('/initial-response')
                                        ? "bg-blue-100 text-blue-900"
                                        : "bg-gray-200 text-gray-700 hover:bg-gray-300"
                                )}
                            >
                                <span className="font-medium text-sm">Initial Response</span>
                            </button>

                            <CollapsibleTrigger asChild>
                                <button
                                    className={cn(
                                        "px-3 py-3 transition-all duration-200 hover:bg-blue-200 border-l border-gray-300",
                                        isActiveSection('/initial-response')
                                            ? "bg-blue-100 text-blue-900"
                                            : "bg-gray-200 text-gray-700 hover:bg-gray-300"
                                    )}
                                >
                                    <ChevronDown
                                        size={16}
                                        className={cn(
                                            "transition-transform duration-200",
                                            initialResponseOpen ? "rotate-0" : "-rotate-90"
                                        )}
                                    />
                                </button>
                            </CollapsibleTrigger>
                        </div>

                        <CollapsibleContent className="data-[state=open]:animate-collapsible-down data-[state=closed]:animate-collapsible-up">
                            <div className="border-t border-gray-300 bg-white">
                                <button
                                    onClick={() => handleNavigation('/initial-response/#dispatch-time')}
                                    className={cn(
                                        "w-full text-left px-4 py-2 text-sm transition-colors duration-150",
                                        isActivePath('/initial-response') && pathname?.includes('#dispatch-time')
                                            ? "bg-blue-100 text-blue-900"
                                            : "text-gray-700 hover:bg-blue-50 hover:text-blue-900"
                                    )}
                                >
                                    Time of dispatching forces to the scene
                                </button>
                                <button
                                    onClick={() => handleNavigation('/initial-response/#arrival-time')}
                                    className={cn(
                                        "w-full text-left px-4 py-2 text-sm transition-colors duration-150",
                                        isActivePath('/initial-response') && pathname?.includes('#arrival-time')
                                            ? "bg-blue-100 text-blue-900"
                                            : "text-gray-700 hover:bg-blue-50 hover:text-blue-900"
                                    )}
                                >
                                    Time of arrival at the scene
                                </button>
                                <button
                                    onClick={() => handleNavigation('/initial-response/officers')}
                                    className={cn(
                                        "w-full text-left px-4 py-2 text-sm transition-colors duration-150",
                                        isActivePath('/initial-response/officers')
                                            ? "bg-blue-100 text-blue-900"
                                            : "text-gray-700 hover:bg-blue-50 hover:text-blue-900"
                                    )}
                                >
                                    List of officers assigned to the scene
                                </button>
                                <button
                                    onClick={() => handleNavigation('/initial-response/assessment')}
                                    className={cn(
                                        "w-full text-left px-4 py-2 text-sm transition-colors duration-150",
                                        isActivePath('/initial-response/assessment')
                                            ? "bg-blue-100 text-blue-900"
                                            : "text-gray-700 hover:bg-blue-50 hover:text-blue-900"
                                    )}
                                >
                                    Preliminary assessment of the scene situation
                                </button>
                                <button
                                    onClick={() => handleNavigation('/initial-response/preservation')}
                                    className={cn(
                                        "w-full text-left px-4 py-2 text-sm transition-colors duration-150",
                                        isActivePath('/initial-response/preservation')
                                            ? "bg-blue-100 text-blue-900"
                                            : "text-gray-700 hover:bg-blue-50 hover:text-blue-900"
                                    )}
                                >
                                    Scene preservation measures taken
                                </button>
                                <button
                                    onClick={() => handleNavigation('/initial-response/medical-support')}
                                    className={cn(
                                        "w-full text-left px-4 py-2 text-sm transition-colors duration-150",
                                        isActivePath('/initial-response/medical-support')
                                            ? "bg-blue-100 text-blue-900"
                                            : "text-gray-700 hover:bg-blue-50 hover:text-blue-900"
                                    )}
                                >
                                    Information on medical/rescue support provided
                                </button>
                            </div>
                        </CollapsibleContent>
                    </Collapsible>
                </div>
            </div>

            {/* SCENE INFORMATION SECTION */}
            <div className="pt-3 px-3">
                <div className="border border-gray-300 rounded-lg overflow-hidden">
                    <Collapsible
                        open={sceneInfoOpen}
                        onOpenChange={setSceneInfoOpen}
                    >
                        <div className="flex">
                            <button
                                onClick={() => handleNavigation('/scene-information')}
                                className={cn(
                                    "flex-1 text-left p-3 transition-all duration-200 hover:bg-blue-200",
                                    isActiveSection('/scene-information')
                                        ? "bg-blue-100 text-blue-900"
                                        : "bg-gray-200 text-gray-700 hover:bg-gray-300"
                                )}
                            >
                                <span className="font-medium text-sm">Scene Information</span>
                            </button>
                            <CollapsibleTrigger asChild>
                                <button
                                    className={cn(
                                        "px-3 py-3 transition-all duration-200 hover:bg-blue-200 border-l border-gray-300",
                                        isActiveSection('/scene-information')
                                            ? "bg-blue-100 text-blue-900"
                                            : "bg-gray-200 text-gray-700 hover:bg-gray-300"
                                    )}
                                >
                                    <ChevronDown
                                        size={16}
                                        className={cn(
                                            "transition-transform duration-200",
                                            sceneInfoOpen ? "rotate-0" : "-rotate-90"
                                        )}
                                    />
                                </button>
                            </CollapsibleTrigger>
                        </div>
                        <CollapsibleContent className="data-[state=open]:animate-collapsible-down data-[state=closed]:animate-collapsible-up">
                            <div className="border-t border-gray-300 bg-white">
                                <button
                                    onClick={() => handleNavigation('/scene-information/statements')}
                                    className={cn(
                                        "w-full text-left px-4 py-2 text-sm transition-colors duration-150",
                                        isActivePath('/scene-information/statements')
                                            ? "bg-blue-100 text-blue-900"
                                            : "text-gray-700 hover:bg-blue-50 hover:text-blue-900"
                                    )}
                                >
                                    Initial Statements
                                </button>
                                <button
                                    onClick={() => handleNavigation('/scene-information/statements/details')}
                                    className={cn(
                                        "w-full text-left px-4 py-2 text-sm transition-colors duration-150",
                                        isActivePath('/scene-information/statements/details')
                                            ? "bg-blue-100 text-blue-900"
                                            : "text-gray-700 hover:bg-blue-50 hover:text-blue-900"
                                    )}
                                >
                                    Scene Description
                                </button>
                                <button
                                    onClick={() => handleNavigation('/scene-information/media')}
                                    className={cn(
                                        "w-full text-left px-4 py-2 text-sm transition-colors duration-150",
                                        isActivePath('/scene-information/media')
                                            ? "bg-blue-100 text-blue-900"
                                            : "text-gray-700 hover:bg-blue-50 hover:text-blue-900"
                                    )}
                                >
                                    Images and Videos
                                </button>
                                <button
                                    onClick={() => handleNavigation('/scene-information/evidence')}
                                    className={cn(
                                        "w-full text-left px-4 py-2 text-sm transition-colors duration-150",
                                        isActivePath('/scene-information/evidence')
                                            ? "bg-blue-100 text-blue-900"
                                            : "text-gray-700 hover:bg-blue-50 hover:text-blue-900"
                                    )}
                                >
                                    Preliminary Physical Evidence Information
                                </button>
                                <button
                                    onClick={() => handleNavigation('/scene-information/sketch')}
                                    className={cn(
                                        "w-full text-left px-4 py-2 text-sm transition-colors duration-150",
                                        isActivePath('/scene-information/sketch')
                                            ? "bg-blue-100 text-blue-900"
                                            : "text-gray-700 hover:bg-blue-50 hover:text-blue-900"
                                    )}
                                >
                                    Scene Sketch
                                </button>
                            </div>
                        </CollapsibleContent>
                    </Collapsible>
                </div>
            </div>

            {/* FIELD REPORT SUMMARY SECTION */}
            <div className="pt-3 px-3">
                <div className="border border-gray-300 rounded-lg overflow-hidden">
                    <div className="flex">
                        <button
                            onClick={() => handleNavigation('/field-report')}
                            className={cn(
                                "flex-1 text-left p-3 transition-all duration-200 hover:bg-blue-200",
                                isActivePath('/field-report')
                                    ? "bg-blue-100 text-blue-900"
                                    : "bg-gray-200 text-gray-700 hover:bg-gray-300"
                            )}
                        >
                            <span className="font-medium text-sm">Field Report Summary</span>
                        </button>
                    </div>
                </div>
            </div>
        </>
    );

    return (
        <>
            {/* Desktop Sidebar - Always visible on large screens */}
            <aside className="hidden lg:block w-92 bg-white shadow-sm border-b sticky top-0 h-screen overflow-y-auto">
                <SidebarContent />
            </aside>

            {/* Mobile Floating Tab Button */}
            <button
                onClick={() => setIsMobileSidebarOpen(true)}
                className={cn(
                    "lg:hidden fixed top-1/2 left-0 -translate-y-1/2 z-50 bg-blue-600 text-white p-3 rounded-r-lg shadow-lg transition-all duration-300 hover:bg-blue-700",
                    "transform hover:translate-x-1"
                )}
                aria-label="Open navigation menu"
            >
                <Menu size={20} />
            </button>

            {/* Mobile Sidebar - Overlay */}
            <div className={cn(
                "lg:hidden fixed inset-0 z-50 transition-all duration-300",
                (isMobileSidebarOpen || isHoveringLeftEdge) ? "visible" : "invisible"
            )}>
                {/* Backdrop */}
                <div 
                    className={cn(
                        "absolute inset-0 bg-black transition-opacity duration-300",
                        (isMobileSidebarOpen || isHoveringLeftEdge) ? "opacity-50" : "opacity-0"
                    )}
                    onClick={() => setIsMobileSidebarOpen(false)}
                />
                
                {/* Sidebar */}
                <aside className={cn(
                    "relative w-92 max-w-[80vw] bg-white shadow-lg h-full overflow-y-auto transition-transform duration-300",
                    (isMobileSidebarOpen || isHoveringLeftEdge) ? "translate-x-0" : "-translate-x-full"
                )}>
                    {/* Close Button */}
                    <div className="flex justify-between items-center p-4 border-b border-gray-200 bg-gray-50">
                        <span className="font-semibold text-gray-800">Navigation</span>
                        <button
                            onClick={() => setIsMobileSidebarOpen(false)}
                            className="p-1 rounded-md hover:bg-gray-200 transition-colors"
                            aria-label="Close navigation menu"
                        >
                            <X size={20} />
                        </button>
                    </div>
                    
                    <SidebarContent />
                </aside>
            </div>

            {/* Left Edge Hover Trigger Area - Only visible on mobile/tablet */}
            <div className="lg:hidden fixed top-0 left-0 w-8 h-full z-40 pointer-events-auto" />
        </>
    );
}