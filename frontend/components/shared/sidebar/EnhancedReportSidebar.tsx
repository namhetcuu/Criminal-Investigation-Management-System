"use client";

import { useState } from "react";
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";

export type SectionType = "initial-response" | "scene-information" | "investigation-report";

interface SidebarProps {
  activeSection: SectionType;
  onSectionChange: (section: SectionType) => void;
}

const initialResponseSections = [
  { id: "dispatch", label: "Time of dispatching forces to the scene" },
  { id: "arrival", label: "Time of arrival at the scene" },
  { id: "officers", label: "List of officers assigned to the scene" },
  { id: "assessment", label: "Preliminary assessment of the scene situation" },
  { id: "preservation", label: "Scene preservation measures taken" },
  { id: "medical", label: "Information on medical/rescue support provided" },
];

const sceneInfoSections = [
  { id: "statements", label: "Initial Statements" },
  { id: "description", label: "Scene Description" },
  { id: "media", label: "Images and Videos" },
  { id: "evidence", label: "Preliminary Physical Evidence Information" },
  { id: "sketch", label: "Scene Sketch" },
];

export default function EnhancedReportSidebar({ activeSection, onSectionChange }: SidebarProps) {
  const [expandedSection, setExpandedSection] = useState<string>("item-1");

  const handleSectionClick = (section: SectionType) => {
    onSectionChange(section);
  };

  const getAccordionValue = () => {
    switch (activeSection) {
      case "initial-response":
        return "item-1";
      case "scene-information":
        return "item-2";
      case "investigation-report":
        return "item-3";
      default:
        return "item-1";
    }
  };

  return (
    <div className="w-64 rounded-lg border p-0 bg-white sticky top-8">
      <Accordion 
        type="single" 
        collapsible 
        className="w-full" 
        value={expandedSection}
        onValueChange={setExpandedSection}
      >
        {/* Initial Response */}
        <AccordionItem value="item-1">
          <AccordionTrigger 
            className={`px-4 py-2 rounded-t-lg transition-colors ${
              activeSection === "initial-response" 
                ? "bg-blue-200 text-blue-900" 
                : "bg-gray-400 text-black hover:bg-gray-300"
            }`}
            onClick={() => handleSectionClick("initial-response")}
          >
            Initial Response
          </AccordionTrigger>
          <AccordionContent className="bg-blue-50 px-4 py-2">
            <ul className="list-none pl-0 text-sm space-y-2">
              {initialResponseSections.map((s) => (
                <li
                  key={s.id}
                  className="cursor-pointer px-2 py-1 rounded transition hover:text-blue-600"
                >
                  {s.label}
                </li>
              ))}
            </ul>
          </AccordionContent>
        </AccordionItem>

        {/* Scene Information */}
        <AccordionItem value="item-2">
          <AccordionTrigger 
            className={`px-4 py-2 transition-colors ${
              activeSection === "scene-information" 
                ? "bg-blue-200 text-blue-900" 
                : "bg-gray-400 text-black hover:bg-gray-300"
            }`}
            onClick={() => handleSectionClick("scene-information")}
          >
            Scene Information
          </AccordionTrigger>
          <AccordionContent className="bg-blue-50 px-4 py-2">
            <ul className="list-none pl-0 text-sm space-y-2">
              {sceneInfoSections.map((s) => (
                <li
                  key={s.id}
                  className="cursor-pointer px-2 py-1 rounded transition hover:text-blue-600"
                >
                  {s.label}
                </li>
              ))}
            </ul>
          </AccordionContent>
        </AccordionItem>

        {/* Initial Investigation Report */}
        <AccordionItem value="item-3">
          <AccordionTrigger 
            className={`px-4 py-2 rounded-b-lg transition-colors ${
              activeSection === "investigation-report" 
                ? "bg-blue-200 text-blue-900" 
                : "bg-gray-400 text-black hover:bg-gray-300"
            }`}
            onClick={() => handleSectionClick("investigation-report")}
          >
            Initial Investigation Report
          </AccordionTrigger>
          <AccordionContent className="bg-blue-50 px-4 py-2">
            <p className="text-sm text-gray-600">Report details and analysis...</p>
          </AccordionContent>
        </AccordionItem>
      </Accordion>
    </div>
  );
} 