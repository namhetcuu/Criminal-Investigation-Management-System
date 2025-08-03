"use client";

import { Button } from "@/components/ui/button";
import { ArrowLeft } from "lucide-react";
import { IncidentInformation } from "./IncidentInformation";
import { MyInformation } from "./MyInformation";
import { RelevantInformation } from "./RelevantInformation";
import { ReportHeader } from "./ReportHeader";

interface FullReportProps {
  onBack: () => void;
}

export function FullReport({ onBack }: FullReportProps) {
  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
      <div className="bg-white rounded-lg shadow-2xl w-full max-w-5xl h-[95vh] flex flex-col">
        {/* Control Buttons */}
        <div className="bg-white border-b border-gray-200 p-4 print:hidden">
          <div className="flex justify-between items-center">
            <Button
              onClick={onBack}
              className="flex items-center gap-2 bg-gray-100 text-gray-700 hover:bg-gray-200 border border-gray-300"
            >
              <ArrowLeft className="w-4 h-4" />
              Back to Preview
            </Button>
          </div>
        </div>

        {/* Scrollable Content */}
        <div className="flex-1 overflow-y-auto">
          <div className="p-8 bg-white">
            {/* Header */}
            <div className="mb-8">
              <ReportHeader />
              <div className="text-center pt-6">
                <h1 className="text-2xl font-bold text-gray-900">
                  REPORT DETAIL
                </h1>
              </div>
            </div>

            {/* My Information */}
            <MyInformation />

            {/* Incident Information */}
            <IncidentInformation />

            {/* Relevant Information */}
            <RelevantInformation />
          </div>
        </div>
      </div>
    </div>
  );
}
