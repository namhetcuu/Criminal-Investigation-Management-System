"use client";
import { Button } from "@/components/ui/button";
import { X } from "lucide-react";

interface ReportPreviewProps {
  onPrint: () => void;
  onClose: () => void;
}

export function ReportPreview({ onPrint, onClose }: ReportPreviewProps) {
  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
      <div className="bg-white rounded-lg shadow-2xl w-full max-w-4xl h-[90vh] flex flex-col">
        {/* Window Header */}
        <div className="bg-gray-100 px-4 py-2 rounded-t-lg border-b border-gray-300 flex items-center justify-between">
          <div className="flex items-center gap-2"></div>
          <div className="flex items-center gap-6">
            <button
              onClick={onClose}
              className="w-8 h-8 flex items-center justify-center hover:bg-red-100 rounded"
            >
              <X className="w-4 h-4 text-gray-600 hover:text-red-600" />
            </button>
          </div>
        </div>

        {/* Scrollable Content */}
        <div className="flex-1 overflow-y-auto">
          <div className="p-8">
            {/* Header */}
            <div className="flex justify-between items-start mb-8 pb-6 border-b-2 border-gray-300">
              <div className="space-y-2">
                <div className="text-gray-600 text-sm">
                  <span className="font-medium">ReportID:</span>
                </div>
                <div className="text-gray-600 text-sm">
                  <span className="font-medium">Status:</span>
                </div>
              </div>
              <div className="text-right space-y-2">
                <div className="text-gray-600 text-sm">
                  <span className="font-medium">Date:</span>
                </div>
                <div className="text-gray-600 text-sm">
                  <span className="font-medium">Time:</span>
                </div>
              </div>
            </div>

            {/* Title */}
            <div className="text-center mb-10">
              <h1 className="text-2xl font-bold text-gray-900">
                REPORT DETAIL
              </h1>
            </div>

            {/* MY INFORMATION Section */}
            <div className="mb-12 pb-8 border-b-2 border-gray-200">
              <h2 className="text-red-600 font-bold text-lg mb-8">
                MY INFORMATION
              </h2>

              <div className="grid grid-cols-2 gap-x-16 gap-y-10">
                <div>
                  <h3 className="font-semibold text-gray-900 mb-3 text-base">
                    Full name
                  </h3>
                  <div className="h-6 border-b border-gray-200"></div>
                </div>
                <div>
                  <h3 className="font-semibold text-gray-900 mb-3 text-base">
                    Email
                  </h3>
                  <div className="h-6 border-b border-gray-200"></div>
                </div>
                <div>
                  <h3 className="font-semibold text-gray-900 mb-3 text-base">
                    Relationship to the incident
                  </h3>
                  <div className="h-6 border-b border-gray-200"></div>
                </div>
                <div>
                  <h3 className="font-semibold text-gray-900 mb-3 text-base">
                    Phone
                  </h3>
                  <div className="h-6 border-b border-gray-200"></div>
                </div>
              </div>

              <div className="mt-10">
                <h3 className="font-semibold text-gray-900 mb-3 text-base">
                  Address
                </h3>
                <div className="h-20 border-b border-gray-200"></div>
              </div>
            </div>

            {/* INCIDENT INFORMATION Section */}
            <div className="mb-12 pb-8 border-b-2 border-gray-200">
              <h2 className="text-red-600 font-bold text-lg mb-8">
                INCIDENT INFORMATION
              </h2>

              <div className="grid grid-cols-2 gap-x-16 gap-y-10">
                <div>
                  <h3 className="font-semibold text-gray-900 mb-3 text-base">
                    Type of Crime
                  </h3>
                  <div className="h-6 border-b border-gray-200"></div>
                </div>
                <div>
                  <h3 className="font-semibold text-gray-900 mb-3 text-base">
                    Severity
                  </h3>
                  <div className="h-6 border-b border-gray-200"></div>
                </div>
                <div>
                  <h3 className="font-semibold text-gray-900 mb-3 text-base">
                    Date/time of occurrence
                  </h3>
                  <div className="h-6 border-b border-gray-200"></div>
                </div>
                <div>
                  <h3 className="font-semibold text-gray-900 mb-3 text-base">
                    State
                  </h3>
                  <div className="h-6 border-b border-gray-200"></div>
                </div>
                <div>
                  <h3 className="font-semibold text-gray-900 mb-3 text-base">
                    Detailed address
                  </h3>
                  <div className="h-6 border-b border-gray-200"></div>
                </div>
                <div>
                  <h3 className="font-semibold text-gray-900 mb-3 text-base">
                    Description of the incident
                  </h3>
                  <div className="h-6 border-b border-gray-200"></div>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Fixed Bottom Buttons */}
        <div className="bg-gray-100 border-t border-gray-300 p-6 rounded-b-lg">
          <div className="flex justify-center gap-6">
            <Button
              onClick={onPrint}
              className="bg-gray-500 hover:bg-gray-600 text-white px-10 py-3 rounded-lg font-medium text-base shadow-md transition-all duration-200"
            >
              Print
            </Button>
            <Button className="bg-red-400 hover:bg-red-500 text-white px-10 py-3 rounded-lg font-medium text-base shadow-md transition-all duration-200">
              Decline
            </Button>
            <Button className="bg-blue-500 hover:bg-blue-600 text-white px-10 py-3 rounded-lg font-medium text-base shadow-md transition-all duration-200">
              Approve
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
}
